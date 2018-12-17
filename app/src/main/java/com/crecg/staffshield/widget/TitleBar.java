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

public class TitleBar extends RelativeLayout{

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
//    private DisplayImageOptions options;
    private TextView child;
    private ImageView iv_right_btn;
    private String shareId;
    private String shareTitle; // 分享出去时显示的标题
    private String shareText; // 分享出去时显示的描述
    private String flag; //判断搜索、分享
    private String shareUrl; // 分享时用到的url
    private String userId = null;

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
//        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.titlebar, this);
//        actions = (LinearLayout) view.findViewById(R.id.ll_search);// action
//        actions_back = (LinearLayout) view.findViewById(R.id.title_back);// 大块返回，包括Title文字
//        tv_groupcount = (TextView) view.findViewById(R.id.top_groupcount);// 返回中的Title右侧文字
//        more = (TextView) view.findViewById(R.id.top_title_rightbtn);// 右侧
        // 更多菜单
//        redpoint = (ImageView) view.findViewById(R.id.top_title_redpoint);//
        // 右侧提示的红点
        // back = view.findViewById(R.id.title_back);

        // left = (TextView) view.findViewById(R.id.top_title_leftbtn);
//        left = (ImageView) view.findViewById(R.id.top_title_leftbtn);// 返回中的logo图标
//        title = (TextView) view.findViewById(R.id.top_title);// 返回中的Title左侧文字
//        leftImg = (ImageView) view.findViewById(R.id.top_title_leftview);// 返回中最左侧的回去图标
//        top_title_menu = (TextView) view.findViewById(R.id.top_title_menu);// 有下拉菜单按钮的Title
//        rl_top_title_menu = (RelativeLayout) findViewById(R.id.rl_top_title_menu);
//        iv_top_title_menu = (ImageView) findViewById(R.id.iv_top_title_menu);
//        tv_center = (TextView) findViewById(R.id.title_center);// 中间文字
//        iv_right_btn = (ImageView) findViewById(R.id.iv_right_btn);

//        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.move_rightlittle);
//        animation.setFillAfter(true);
    //    leftImg.startAnimation(animation);// 为左侧回去图标设置动来动去的动画
        // setAnimationLeft(R.anim.left_title_first);
        //       actions_back.setBackgroundResource(R.drawable.title_selector);// 设置大块返回点击给用户反应
        // --变身灰色

//        actions_back.setOnClickListener(this);
//        left.setOnClickListener(this);
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




}