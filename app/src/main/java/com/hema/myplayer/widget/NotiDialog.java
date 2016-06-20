package com.hema.myplayer.widget;

/**
 * 提示框
 *
 * @author zwr
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hema.myplayer.R;
import com.hema.myplayer.base.BaseApplication;


public class NotiDialog extends Dialog {
    Button okButton;
    TextView conteTextView;
    TextView titleTextView;
    Button cancelButton;
    String contentStr;
    String titleStr;
    LinearLayout doublebtn;
    LinearLayout onebtn;
    Button knowButton;
    ImageView loding_Iv;
    Context context;

    public NotiDialog(Context context, String contentStr) {
        super(context, R.style.NotiDialog);
        this.contentStr = contentStr;
        this.context = context;
    }

    public NotiDialog(Context context, String title, String contentStr) {
        super(context, R.style.NotiDialog);
        this.contentStr = contentStr;
        this.titleStr = title;
        this.context = context;
    }

    public NotiDialog(Context context) {
        super(context, R.style.NotiDialog);
        this.context = context;
    }

    public NotiDialog(Context context, int theme, String contentStr) {
        super(context, theme);
        this.contentStr = contentStr;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        this.setContentView(R.layout.noticedialog);
        loding_Iv = (ImageView) this.findViewById(R.id.notice_Iv);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
        loding_Iv.startAnimation(hyperspaceJumpAnimation);
        doublebtn = (LinearLayout) this.findViewById(R.id.notice_buttonLayout);
        onebtn = (LinearLayout) this.findViewById(R.id.notice_one_btn_Layout);
        knowButton = (Button) this.findViewById(R.id.notice_know);

        okButton = (Button) this.findViewById(R.id.notice_confirm);
        conteTextView = (TextView) this.findViewById(R.id.notice_content);
        conteTextView.setText(contentStr);
        titleTextView = (TextView) this.findViewById(R.id.notice_title);
        titleTextView.setText(titleStr);

        cancelButton = (Button) this.findViewById(R.id.notice_cancel);
        this.setCanceledOnTouchOutside(false);
        if (BaseApplication.getSDKVersion() < 11) {
            okButton.setBackgroundResource(R.drawable.notidialog_leftbtn_selector);
            cancelButton
                    .setBackgroundResource(R.drawable.notidialog_rightbtn_selector);
        }
    }

    // 设置加载框
    public NotiDialog setloding(String btnString, final View.OnClickListener onClick) {
        setTitleStr("");
        loding_Iv.setVisibility(View.VISIBLE);
        setKnowButtonText(btnString);
        setKnowListener(onClick);
        return this;
    }

    // 设置确定按钮事件
    public NotiDialog setPositiveListener(final View.OnClickListener onClick) {
        if (onClick != null) {
            okButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    NotiDialog.this.dismiss();
                    onClick.onClick(v);
                }
            });
        }
        return this;
    }

    /** 设置知道按钮事件 **/
    public NotiDialog setKnowListener(final View.OnClickListener onClick) {
        if (onClick != null) {
            knowButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    NotiDialog.this.dismiss();
                    onClick.onClick(v);
                }
            });
        } else {
            knowButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NotiDialog.this.dismiss();
                }
            });
        }
        return this;
    }
    // 设置点击周围是不是消失
    public NotiDialog setOnTouchOutside(boolean yOrn) {
        this.setCanceledOnTouchOutside(yOrn);
        return this;
    }

    // 设置取消按钮事件
    public NotiDialog setNegativeListener(final View.OnClickListener onClick) {
        if (onClick != null) {
            cancelButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    NotiDialog.this.dismiss();
                    onClick.onClick(v);
                }
            });
        } else {
            cancelButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NotiDialog.this.dismiss();
                }
            });
        }
        return this;
    }

    public void setContentStr(String str) {
        contentStr = str;
        conteTextView.setText(contentStr);
    }

    public void setTitleStr(String str) {
        titleStr = str;
        titleTextView.setText(titleStr);
    }

    public void setOkButtonText(String text) {
        doublebtn.setVisibility(View.VISIBLE);
        onebtn.setVisibility(View.GONE);
        okButton.setText(text);
    }

    public void setCancelButtonText(String text) {
        cancelButton.setText(text);
    }

    public void setKnowButtonText(String text) {
        doublebtn.setVisibility(View.GONE);
        onebtn.setVisibility(View.VISIBLE);
        knowButton.setText(text);
    }
}
