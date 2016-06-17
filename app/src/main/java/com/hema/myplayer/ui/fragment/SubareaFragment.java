package com.hema.myplayer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hema.myplayer.R;
import com.hema.myplayer.base.BaseFragment;
import com.hema.myplayer.ui.MainItemActivity;
import com.hema.myplayer.ui.TVActivity;
import com.hema.myplayer.ui.adapter.sb_gidAdapter;

/**
 * @ClassName: SubareaFragment
 * @Description:分区界面
 */
public class SubareaFragment extends BaseFragment {
    //上下文
    private Activity mActivity;
    private View layoutView;
    //标签
    private String[] mVals = new String[]
            {"动画","音乐","游戏 "," 娱乐","电视剧","番剧","电影","科技","鬼畜","舞蹈","时尚","直播"};
    private GridView gridView;
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
            layoutView = inflater.inflate(R.layout.fragment_subarea, container, false); // 加载fragment布局
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
        gridView = (GridView) layoutView.findViewById(R.id.sb_gidview);
    }

    @Override
    protected void setListener() {
      gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent =new Intent();
              boolean isonline=false;
              switch (position) {
                  case 0:
                      intent.putExtra("type",0);
                      break;
                  case 1:
                      intent.putExtra("type",1);
                      break;
                  case 2:
                      intent.putExtra("type",2);
                      break;
                  case 3:
                      intent.putExtra("type",3);
                      break;
                  case 4:
                      intent.putExtra("type",4);
                      break;
                  case 5:
                      intent.putExtra("type",5);
                      break;
                  case 6:
                      intent.putExtra("type",6);
                      break;
                  case 7:
                      intent.putExtra("type",7);
                      break;
                  case 8:
                      intent.putExtra("type",8);
                      break;
                  case 9:
                      intent.putExtra("type",9);
                      break;
                  case 10:
                      intent.putExtra("type",10);
                      break;
                  case 11:
                      intent.putExtra("type",11);
                      isonline=true;
                      break;

              }
              if (isonline){
                  intent.setClass(mActivity, TVActivity.class);
                  mActivity.startActivity(intent);
              }else {
                  intent.setClass(mActivity, MainItemActivity.class);
                  mActivity.startActivity(intent);
              }
          }
      });

    }

    @Override
    protected void init() {
        gridView.setAdapter(new sb_gidAdapter(mActivity,mVals));

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
