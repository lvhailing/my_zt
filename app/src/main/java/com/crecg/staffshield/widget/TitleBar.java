package com.crecg.staffshield.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.crecg.staffshield.R;

import java.util.ArrayList;
import java.util.Collections;

public class TitleBar extends RelativeLayout implements OnClickListener {

    public LinearLayout actions;
    private LinearLayout actions_back;
    private ListView menuList;
    private LayoutInflater inflater;
    //    private StickItemWindow menuWindow, titleMenuWindow;
    private TextView title;// left;
    private ImageView left;
    private TextView more;
    private ImageView redpoint;
    private ImageView leftImg;
    private TextView tv_groupcount;
    private TextView top_title_menu;// TitleBar的下拉菜单
    private ImageView iv_top_title_menu;// 下拉菜单的图标
    private RelativeLayout rl_top_title_menu;
    private TextView tv_center;
    private String url = null;
    private String logoName = null;

    private Context mContext;

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.titlebar, this);
//        actions = (LinearLayout) view.findViewById(R.id.ll_search);// action
        actions_back = (LinearLayout) view.findViewById(R.id.title_back);// 大块返回，包括Title文字
//        tv_groupcount = (TextView) view.findViewById(R.id.top_groupcount);// 返回中的Title右侧文字
//        more = (TextView) view.findViewById(R.id.top_title_rightbtn);// 右侧
        // 更多菜单
//        redpoint = (ImageView) view.findViewById(R.id.top_title_redpoint);//
        // 右侧提示的红点
        // back = view.findViewById(R.id.title_back);

        // left = (TextView) view.findViewById(R.id.top_title_leftbtn);
//        left = (ImageView) view.findViewById(R.id.top_title_leftbtn);// 返回中的logo图标
        title = (TextView) view.findViewById(R.id.top_title);// 返回中的Title左侧文字
//        leftImg = (ImageView) view.findViewById(R.id.top_title_leftview);// 返回中最左侧的回去图标
//        top_title_menu = (TextView) view.findViewById(R.id.top_title_menu);// 有下拉菜单按钮的Title
//        rl_top_title_menu = (RelativeLayout) findViewById(R.id.rl_top_title_menu);
//        iv_top_title_menu = (ImageView) findViewById(R.id.iv_top_title_menu);
//        tv_center = (TextView) findViewById(R.id.title_center);// 中间文字
//        iv_right_btn = (ImageView) findViewById(R.id.iv_right_btn);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.move_rightlittle);
        animation.setFillAfter(true);
        leftImg.startAnimation(animation);// 为左侧回去图标设置动来动去的动画

        actions_back.setOnClickListener(this);
        left.setOnClickListener(this);
        // title.setOnClickListener(this);
        // leftImg.setOnClickListener(this);
//        more.setOnClickListener(this);
//        iv_right_btn.setOnClickListener(this);

//        options = ImageLoaderManager.initDisplayImageOptions(R.mipmap.logo, R.mipmap.logo, R.mipmap.logo);
    }


    /**
     * 设置标题文本
     *
     * @param res
     * @return
     */
    public TitleBar setTitle(int res) {
        title.setText(res);
        return TitleBar.this;
    }

    /**
     * 设置标题文本
     *
     * @param string
     * @return
     */
    public TitleBar setTitle(String string) {
        title.setText(string);
        return TitleBar.this;
    }

    /**
     * 设置LOGO显示图片及是否显示
     *
     * @param id
     * @param b
     * @return
     */
    public TitleBar setLogo(int id, boolean b) {
        if (b) {
            left.setImageResource(id);
        } else {
            left.setVisibility(View.GONE);
        }
        return TitleBar.this;
    }

    /**
     * 设置最左侧的指示图标资源
     *
     * @param r
     * @return
     */
    public TitleBar setIndicator(int r) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.move_rightlittle);
        animation.setFillAfter(true);
        //      leftImg.startAnimation(animation);
        leftImg.setImageResource(r);
        return TitleBar.this;
    }

    public TitleBar setCenterText(String text) {
        tv_center.setText(text);
        return TitleBar.this;
    }

    /**
     * 设置是否显示菜单按钮
     *
     * @param isShow
     * @return
     */
    public TitleBar showMore(boolean isShow) {
        more.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (!isShow) {
            LayoutParams p = ((LayoutParams) actions.getLayoutParams());
            p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        return TitleBar.this;
    }

    /**
     * 设置title中各个控件的点击事件监听
     *
     * @param l
     */
    public void setOnActionListener(OnActionListener l) {
        this.mListener = l;
    }


    OnActionListener mListener;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.title_back:
            mListener.onBack();
            break;

        }
    }

    public interface OnActionListener {
        void onAction(int id);

        void onMenu(int id);

        void onBack();
    }

}