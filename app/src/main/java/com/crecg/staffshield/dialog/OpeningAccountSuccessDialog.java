package com.crecg.staffshield.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;

import java.util.ArrayList;

/**
 * 请开通理财账户 弹框
 */
public class OpeningAccountSuccessDialog extends Dialog implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    private Context mContext;
    private LayoutInflater inflater;
    private LayoutParams lp;
    private int percentageH = 4;
    private int percentageW = 8;
    private ImageView iv_close_dialog = null; //  关闭

    ArrayList<OnCancelListener> m_arrCancelListeners = new ArrayList<OnCancelListener>();
    ArrayList<OnDismissListener> m_arrDismissListeners = new ArrayList<OnDismissListener>();

    public OpeningAccountSuccessDialog(Context context) {
        super(context, R.style.UpdateDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.dialog_open_account_success, null);
        setContentView(mView);
        // 设置window属性
        lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0.6f; // 去背景遮盖
        lp.alpha = 1.0f;
        int[] wh = initWithScreenWidthAndHeight(mContext);
        lp.width = wh[0] - wh[0] / percentageW;
        lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        setOnDismissListener(this);
        setOnCancelListener(this);

        initView(mView);
    }

    private void initView(View mView) {
        iv_close_dialog = mView.findViewById(R.id.iv_close_dialog);

        iv_close_dialog.setOnClickListener(cancelListener);
    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onDismiss();
        }
    };

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (m_arrDismissListeners != null) {
            for (int x = 0; x < m_arrDismissListeners.size(); x++)
                m_arrDismissListeners.get(x).onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (m_arrCancelListeners != null) {
            for (int x = 0; x < m_arrDismissListeners.size(); x++)
                m_arrCancelListeners.get(x).onCancel(dialog);
        }
    }

    public void addListeners(OnCancelListener c, OnDismissListener d) {
        m_arrDismissListeners.add(d);
        m_arrCancelListeners.add(c);
    }

    public void removeListeners(OnCancelListener c, OnDismissListener d) {
        m_arrDismissListeners.remove(d);

        m_arrCancelListeners.remove(c);
    }

    private void onDismiss() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }

    /**
     * 获取当前window width,height
     *
     * @param context
     * @return
     */
    private static int[] initWithScreenWidthAndHeight(Context context) {
        int[] wh = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        wh[0] = dm.widthPixels;
        wh[1] = dm.heightPixels;
        return wh;
    }

    public interface OnCheckVersion {
        void onConfirm();
    }

}