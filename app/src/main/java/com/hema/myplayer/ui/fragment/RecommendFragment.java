package com.hema.myplayer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.hema.myplayer.R;
import com.hema.myplayer.base.BaseFragment;
import com.hema.myplayer.bean.BannerBean;
import com.hema.myplayer.bean.VideoItemBean;
import com.hema.myplayer.https.Urls;
import com.hema.myplayer.ui.MainActivity;
import com.hema.myplayer.ui.VideoInfoActivity;
import com.hema.myplayer.ui.adapter.MainAdapter;
import com.hema.myplayer.util.HttpUtil;
import com.hema.myplayer.util.StringUtils;
import com.hema.myplayer.widget.DemoLoadMoreView;
import com.hema.myplayer.widget.NetworkImageHolderView;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @ClassName: RecommendFragment
 * @Description:推荐界面
 */
public class RecommendFragment extends BaseFragment {
    private static final String TAG = RecommendFragment.class.getSimpleName();
    private static final int DEFAULT_ITEM_SIZE = 20;
    private static final int ITEM_SIZE_OFFSET = 20;
    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    private static final int TIME = 1000;


    private PullToRefreshRecyclerView mPtrrv;
    private MainAdapter mAdapter;
    //
    private MainActivity mActivity;
    private View layoutView;
    //
    private View view;//listview头部
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private List<List<VideoItemBean>> dingListx;//0:douga 动画  1:music 音乐 2：游戏 game 3：ent 娱乐 4：technology 电视剧 5：bangumi 番剧 6：movie 电影 7：technology 科技
    private List<VideoItemBean> data;

    //8:kichiku 鬼畜 9：dace 舞蹈 10：fashion 时尚


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        if (layoutView == null) {
            layoutView = inflater.inflate(R.layout.fragment_recommend, container, false); // 加载fragment布局
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
        mPtrrv = (PullToRefreshRecyclerView) layoutView.findViewById(R.id.ptrrv);
        view = LayoutInflater.from(mActivity).inflate(R.layout.header, null);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
    }



    @Override
    protected void setListener() {
        mPtrrv.addOnScrollListener(new PullToRefreshRecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {//滑动到顶部时不抬起手指继续下拉可以刷新，改为抬起手指才能再次下拉刷新
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//滑动停止的时候才允许下拉刷新,
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    //计算第一个item距离父控件顶部是不是等于头view的高度，并且是第一个item;只有添加了headerview才需要这样做.
                    if (lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop() == mPtrrv.getheaderHeight() && lm.findFirstVisibleItemPosition() == 0&&mActivity.sz) {
                        mPtrrv.setEnabled(true);
                    } else {
                        mPtrrv.setEnabled(false);
                    }
                } else {
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }

            @Override
            public void onScroll(RecyclerView recyclerView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mPtrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adRequest();
                dingRequest();
            }
        });
        mAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                VideoItemBean item = (VideoItemBean) data.get(position);
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
        data = new ArrayList<>();

        mPtrrv.setSwipeEnable(true);//open swipe
        GridLayoutManager manager = new GridLayoutManager(mActivity, 4);
        manager.setOrientation(GridLayoutManager.VERTICAL);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override

            public int getSpanSize(int position) {
                if (position % 5 == 0) {
                    return 4;
                }
                return 2;

            }

        });
        mPtrrv.setLayoutManager(manager);
        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(mActivity, mPtrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("加载中");
        loadMoreView.setLoadMorePadding(100);
        mPtrrv.setLoadMoreFooter(loadMoreView);
//        mPtrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(mActivity,
//                DividerItemDecoration.VERTICAL_LIST));
        mPtrrv.addHeaderView(view);//添加头view

        mAdapter = new MainAdapter(mActivity, data);

        mPtrrv.setAdapter(mAdapter);
//        mPtrrv.onFinishLoading(true, false);

        mPtrrv.setProgressViewOffset(false, 0, 60);//自动刷新
        mPtrrv.setRefreshing(true);
        adRequest();
        dingRequest();

    }

    private void adRequest() {
        if (!HttpUtil.isNetworkConnected(getActivity())) {
            convenientBanner.setVisibility(View.GONE);
        }
        HttpUtil.get(mActivity, Urls.AD_LIST, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                mPtrrv.setRefreshing(false);
                String response = new String(responseBody);
                if (!StringUtils.isEmpty(response)) {
                    convenientBanner.setVisibility(View.VISIBLE);
                    BannerBean data = gson.fromJson(response, BannerBean.class);
                    intiAd(data.getList());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mPtrrv.setRefreshing(false);
            }
        });
    }

    private void dingRequest() {
        HttpUtil.get(mActivity, Urls.DING_LIST, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                mPtrrv.setOnRefreshComplete();
                if (!StringUtils.isEmpty(response)) {
                    setDingList(response);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(mActivity, "请求失败", Toast.LENGTH_SHORT).show();
                mPtrrv.setOnRefreshComplete();
            }
        });
    }

    private void setDingList(String json) {
        //0:douga 动画  1:music 音乐 2：游戏 game 3：ent 娱乐 4：technology 电视剧 5：bangumi 番剧 6：movie 电影 7：technology 科技
        //8:kichiku 鬼畜 9：dace 舞蹈 10：fashion 时尚
        String[] name = new String[]{"douga", "music", "game", "ent", "technology", "bangumi", "movie", "technology", "kichiku", "dance", "fashion"};
        for (int i = 0; i < name.length; i++) {
            JSONObject jsondata = null;
            try {
                jsondata = new JSONObject(json).getJSONObject(name[i]);
                VideoItemBean d = new VideoItemBean();
                data.add(d);
                for (int c = 0; c < 4; c++) {
                    data.add(VideoItemBean.getdata(jsondata, c));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void intiAd(List<BannerBean.ListBean> list) {
        //网络加载广告图片
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
//       .setOnItemClickListener(mActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }


}
