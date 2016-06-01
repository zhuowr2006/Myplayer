package com.hema.myplayer.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hema.myplayer.R;
import com.hema.myplayer.base.BaseFragment;

/**
 * @ClassName: AttentionFragment
 * @Description:关注界面
 */
public class AttentionFragment extends BaseFragment  {
    //上下文
    private Activity mActivity;
    private View layoutView;

    private Button login_btn;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (Activity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mActivity = (Activity) getActivity();
        if (layoutView == null) {
            layoutView = inflater.inflate(R.layout.fragment_attention, container, false); // 加载fragment布局
            findViewById();
            init();
            setListener();
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) layoutView.getParent();
        if (parent != null) {
            parent.removeView(layoutView);
        }
        return layoutView;
    }

    @Override
    protected void findViewById() {
        login_btn = (Button) layoutView.findViewById(R.id.at_login);
    }

    @Override
    protected void setListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    protected void init() {

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
