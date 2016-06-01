package com.hema.myplayer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hema.myplayer.R;
import com.hema.myplayer.base.BaseFragment;
import com.hema.myplayer.bean.VideoItemBean;
import com.hema.myplayer.bean.VideolistBean;
import com.hema.myplayer.https.Urls;
import com.hema.myplayer.ui.VideoInfoActivity;
import com.hema.myplayer.ui.adapter.FanjuAdapter;
import com.hema.myplayer.util.HttpUtil;
import com.hema.myplayer.util.StringUtils;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @ClassName: FanjuFragment
 * @Description:番剧界面
 */
public class FanjuFragment extends BaseFragment {
    private static final String TAG = FanjuFragment.class.getSimpleName();
    private PullToRefreshRecyclerView fanju_rcview;
    private FanjuAdapter mAdapter;
    //
    private Activity mActivity;
    private View layoutView;
    //
    private List<VideoItemBean> datalist;

    public static final String[] headername = new String[]{"追番","放送表","索引"};
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
            layoutView = inflater.inflate(R.layout.fragment_fanju, container, false); // 加载fragment布局
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
        fanju_rcview = (PullToRefreshRecyclerView) layoutView.findViewById(R.id.fanju_rcview);
    }

    @Override
    protected void setListener() {
        fanju_rcview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRequest();
            }
        });
        mAdapter.setOnItemClickLitener(new FanjuAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position>3) {
                    VideoItemBean item = (VideoItemBean) datalist.get(position);
                    Intent i = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("videoItemdata", item);
                    i.setClass(getActivity(), VideoInfoActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void init() {
        datalist = new ArrayList<>();

        fanju_rcview.setSwipeEnable(true);//open swipe
        GridLayoutManager manager = new GridLayoutManager(mActivity, 6);
        manager.setOrientation(GridLayoutManager.VERTICAL);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override

            public int getSpanSize(int position) {
                if (position<3){
                    return 2;
                }
                if (position==3||position==14){
                    return 6;
                }
                if (position>14){
                    return 2;
                }
                return 3;

            }

        });

        fanju_rcview.setLayoutManager(manager);
        fanju_rcview.removeHeader();
        mAdapter = new FanjuAdapter(mActivity, datalist, 0);
        fanju_rcview.setAdapter(mAdapter);
//        mPtrrv.onFinishLoading(true, false);

        fanju_rcview.setProgressViewOffset(false, 0, 60);//自动刷新
        fanju_rcview.setRefreshing(true);
        refreshRequest();
    }

    private void refreshRequest() {//33 新番   32 完结动画
        HttpUtil.get(mActivity, Urls.getVideoListURL(33), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                fanju_rcview.setOnRefreshComplete();
                if (!StringUtils.isEmpty(response)) {
                    VideolistBean data = gson.fromJson(response, VideolistBean.class);
                    if (datalist != null && datalist.size() > 0) {
                        datalist.clear();
                    }
                    for (int i = 0; i < 10; i++) {
                        datalist.add(data.getList().get(i));
                    }
                    for (int i = 0; i < headername.length; i++) {
                        VideoItemBean d =new VideoItemBean();
                        d.setTitle(headername[i]);
                        datalist.add(i,d);
                    }
                    VideoItemBean d =new VideoItemBean();
                    d.setTitle("新番连载");
                    datalist.add(3,d);
                    mAdapter.notifyDataSetChanged();
                    fanju_rcview.setOnRefreshComplete();
                    Request();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                fanju_rcview.setOnRefreshComplete();
            }
        });
    }
    private void Request() {//33 新番   32 完结动画
        HttpUtil.get(mActivity, Urls.getVideoListURL(32), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                fanju_rcview.setOnRefreshComplete();
                if (!StringUtils.isEmpty(response)) {
                    VideolistBean data = gson.fromJson(response, VideolistBean.class);
                    VideoItemBean d =new VideoItemBean();
                    d.setTitle("完结动画");
                    datalist.add(d);
                    for (int i = 0; i < 3; i++) {
                        datalist.add(data.getList().get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                    fanju_rcview.setOnRefreshComplete();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                fanju_rcview.setOnRefreshComplete();
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
