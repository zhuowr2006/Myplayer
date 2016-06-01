package com.hema.myplayer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hema.myplayer.R;
import com.hema.myplayer.base.BaseFragment;
import com.hema.myplayer.bean.VideoItemBean;
import com.hema.myplayer.bean.VideolistBean;
import com.hema.myplayer.bean.VideolistexBean;
import com.hema.myplayer.https.Urls;
import com.hema.myplayer.ui.VideoInfoActivity;
import com.hema.myplayer.ui.adapter.listAdapter;
import com.hema.myplayer.util.HttpUtil;
import com.hema.myplayer.util.StringUtils;
import com.hema.myplayer.widget.DemoLoadMoreView;
import com.hema.myplayer.widget.DividerItemDecoration;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @ClassName: MainItemFragment
 * @Description: 分区的列表界面
 */
public class MainItemFragment extends BaseFragment {
    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    private static final int TIME = 2000;
    //上下文
    private Activity mActivity;
    private View layoutView;
    private PullToRefreshRecyclerView mPtrrv;
    private listAdapter mAdapter;
    private int videoType = 1; // 视频类别
    private int paihang = 0; // 视频类别
    private List<VideoItemBean> templist;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                mAdapter.notifyDataSetChanged();
                mPtrrv.setOnRefreshComplete();
            } else if (msg.what == MSG_CODE_LOADMORE) {
                mAdapter.notifyDataSetChanged();
                mPtrrv.onFinishLoading(true, false);
            }
        }
    };
    private List<VideolistexBean> templistex;

    public MainItemFragment(String videoType, int paihang) {
        this.videoType = Integer.valueOf(videoType);
        this.paihang = paihang;
    }

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
            layoutView = inflater.inflate(R.layout.fragment_mainitem, container, false); // 加载fragment布局
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
        mPtrrv = (PullToRefreshRecyclerView) layoutView.findViewById(R.id.main_item_list);

    }

    @Override
    protected void setListener() {
        mPtrrv.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                LoadMoreRequest();
            }
        });
        mPtrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRequest();
            }
        });
        mAdapter.setOnItemClickLitener(new listAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                VideoItemBean item = (VideoItemBean) templist.get(position);
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoItemdata", item);
                i.setClass(getActivity(), VideoInfoActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }


    @Override
    protected void init() {
        templist = new ArrayList<>();

        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(mActivity, mPtrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("加载更多");
        loadMoreView.setLoadMorePadding(100);
        mPtrrv.setLayoutManager(new LinearLayoutManager(mActivity));
        mPtrrv.setSwipeEnable(true);//open swipe
        mPtrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL_LIST));
        mPtrrv.setLoadMoreFooter(loadMoreView);
        mPtrrv.removeHeader();
        mAdapter = new listAdapter(mActivity, templist, paihang);

        mPtrrv.setAdapter(mAdapter);
        if (paihang != 7) {
            mPtrrv.onFinishLoading(true, false);
        }
        mPtrrv.setProgressViewOffset(false, 0, 60);//自动刷新
        mPtrrv.setRefreshing(true);
        refreshRequest();
        System.out.println(Urls.getVideoListURL(videoType)+"----");
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void refreshRequest() {
        HttpUtil.get(mActivity, Urls.getVideoListURL(videoType), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                mPtrrv.setOnRefreshComplete();
                if (!StringUtils.isEmpty(response)) {
                    if (paihang == 7) {
                        VideolistexBean data = gson.fromJson(response, VideolistexBean.class);
                        if (templist == null) {
                            templist = new ArrayList<VideoItemBean>();
                        }
                        for (int i = 0; i < 10; i++) {
                            templist.add(data.getRank().getList().get(i));
                        }
                    } else {
                        VideolistBean data = gson.fromJson(response, VideolistBean.class);
                        if (templist != null && templist.size() > 0) {
                            templist.clear();
                        }
                        for (VideoItemBean c : data.getList()) {
                            templist.add(c);
                        }
                        mPtrrv.onFinishLoading(true, false);
                    }
                    mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, 0);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mPtrrv.setOnRefreshComplete();
            }
        });
    }

    private void LoadMoreRequest() {
        HttpUtil.get(mActivity, Urls.getVideoListURL(videoType), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                if (!StringUtils.isEmpty(response)) {
//                    if (paihang == 7) {
//                        VideolistexBean data = gson.fromJson(response, VideolistexBean.class);
//                        templist = data.getRank().getList();
//                        if (templist.size() > 0) {
//                            for (int i = 0; i < data.getRank().getList().size(); i++) {
//                                templist.add(data.getRank().getList().get(i));
//                            }
//                        }
//                    }else {
                    VideolistBean data = gson.fromJson(response, VideolistBean.class);
                    for (VideoItemBean c : data.getList()) {
                        templist.add(c);
                    }
                    mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
            }
        }

        @Override
        public void onFailure ( int statusCode, Header[] headers,byte[] responseBody, Throwable
        error){
            mPtrrv.setOnRefreshComplete();
        }
    }

    );
}


}
