package com.hema.myplayer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hema.myplayer.R;
import com.hema.myplayer.base.BaseFragment;
import com.hema.myplayer.ui.MainItemActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * @ClassName: FindFragment
 * @Description:发现界面
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {
    //上下文
    private Activity mActivity;
    private View layoutView;
    //标签
    private String[] mVals = new String[]
            {"疯狂动物城", "太阳的后裔", "papi酱", "吃货木下", "暴走大事件", "了不起的挑战", "宋仲基"};
    private TagFlowLayout mFlowLayout;
    private LinearLayout qcphlayout, yxzxlayou;

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
            layoutView = inflater.inflate(R.layout.fragment_find, container, false); // 加载fragment布局
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
        mFlowLayout = (TagFlowLayout) layoutView.findViewById(R.id.id_flowlayout);
        qcphlayout = (LinearLayout) layoutView.findViewById(R.id.find_qcph);
        yxzxlayou = (LinearLayout) layoutView.findViewById(R.id.find_yxzx);
    }

    @Override
    protected void setListener() {
        yxzxlayou.setOnClickListener(this);
        qcphlayout.setOnClickListener(this);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Toast.makeText(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
                view.setVisibility(View.GONE);
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.find_qcph://排行
                Intent intent =new Intent();
                intent.putExtra("type",7);
                intent.setClass(mActivity, MainItemActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.find_yxzx://游戏中心

                break;
        }
    }

    @Override
    protected void init() {
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(mActivity).inflate(R.layout.biaoqian_text, null);
                TextView tv = (TextView) view;
                tv.setText(s);
                return tv;
            }

            @Override
            public boolean setSelected(int position, String s) {
                return s.equals("Android");
            }
        });

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
