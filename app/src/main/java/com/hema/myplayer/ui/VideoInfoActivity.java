package com.hema.myplayer.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hema.myplayer.R;
import com.hema.myplayer.bean.VideoItemBean;
import com.hema.myplayer.util.HttpUtil;
import com.hema.myplayer.util.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/3/24.
 */
public class VideoInfoActivity extends Activity implements View.OnClickListener,AppBarLayout.OnOffsetChangedListener {
    protected Gson gson;

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TagFlowLayout mFlowLayout;
    private String[] list;
    private DisplayImageOptions defaultOptions;

    private TextView title;//顶部标题
    private ImageView xiazai,back;//下载按钮
    private LinearLayout play_layout;

    private TextView titleTextView;
    private TextView upTextView;
    private TextView bofangTextView;
    private TextView danmaguTextView;
    private FloatingActionButton bofangButton;
    private TextView durationTextView;
    private ImageView arrowButton;
    private boolean isClickable = true;
    private VideoItemBean videoinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        findViewById();
        init();
        setListener();
    }

    protected void findViewById() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        appBarLayout= (AppBarLayout) findViewById(R.id.main_appbar);
        title = (TextView) findViewById(R.id.title);
        xiazai= (ImageView) findViewById(R.id.xiazai);
        back= (ImageView) findViewById(R.id.back);
        play_layout= (LinearLayout) findViewById(R.id.play_layout);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        upTextView = (TextView) findViewById(R.id.authorTextView);
        bofangTextView = (TextView) findViewById(R.id.playTextView);
        danmaguTextView = (TextView) findViewById(R.id.video_reviewTextView);
        bofangButton = (FloatingActionButton) findViewById(R.id.playButton);
        durationTextView = (TextView) findViewById(R.id.durationTextView);
        arrowButton = (ImageView) findViewById(R.id.arrowButton);
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void setListener() {
        arrowButton.setOnClickListener(this);
        bofangButton.setOnClickListener(this);
        play_layout.setOnClickListener(this);
        xiazai.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    protected void init() {
        gson = new Gson();
        videoinfo = (VideoItemBean) getIntent().getSerializableExtra("videoItemdata");
        //
        defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.userlogo)
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoader.getInstance().loadImage(videoinfo.getPic(), defaultOptions, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Drawable drawable =new BitmapDrawable(bitmap);
                collapsingToolbarLayout.setBackground(drawable);
                drawable=null;
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        //
        title.setText("AV"+videoinfo.getAid());
        titleTextView.setText(videoinfo.getTitle());
        upTextView.setText("Up主：" + videoinfo.getAuthor());
        bofangTextView.setText("播放：" + videoinfo.getPlay());
        danmaguTextView.setText("弹幕：" + videoinfo.getVideo_review());
        durationTextView.setText("  " + videoinfo.getDescription());


        videoRequest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playButton:
            case R.id.play_layout:
                videoRequest2(1);
                break;
            case R.id.arrowButton:
                if (isClickable) {
                    durationTextView.setMaxLines(durationTextView.getLineCount());
                    isClickable = false;
                    arrowButton.setImageResource(R.mipmap.abcp__expander_close_holo_light);
                } else {
                    durationTextView.setMaxLines(2);
                    isClickable = true;
                    arrowButton.setImageResource(R.mipmap.abcp__expander_open_holo_light);
                }
                break;
            case R.id.xiazai:
                videoRequest2(2);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void videoRequest() {
        HttpUtil.get(this, "http://www.bilibili.com/mobile/video/av" + videoinfo.getAid() + ".html", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                if (!StringUtils.isEmpty(response)) {
                    org.jsoup.nodes.Document listDoc = Jsoup.parse(response);

                    Elements labelElements = listDoc.select("[name=keywords]");
                    Log.d("QAQ--->", "===>" + labelElements.attr("content"));
                    String s = labelElements.attr("content");
                    list = s.split(",");
                    mFlowLayout.setAdapter(new TagAdapter<String>(list) {

                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            View view = LayoutInflater.from(VideoInfoActivity.this).inflate(R.layout.biaoqian_text, null);
                            TextView tv = (TextView) view;
                            tv.setText(s);
                            return tv;
                        }

                        @Override
                        public boolean setSelected(int position, String s) {
                            return s.equals("Android");
                        }
                    });
                    if (durationTextView.getLineCount() > 2) {
                        arrowButton.setVisibility(View.VISIBLE);
                    } else {
                        arrowButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    private void videoRequest2(final int gototype) {//1.跳转到播放界面，2.下载界面
        HttpUtil.get(this, "http://www.bilibili.com/m/html5?aid=" + videoinfo.getAid() + "&page=" + 1, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                if (!StringUtils.isEmpty(response)) {
                    try {
                        JSONObject js = new JSONObject(response);
                        String url = js.getString("src").toString();
                        String danmakuPath = js.getString("cid").toString();
                        if (gototype==1){
                            com.hema.playViewUtil.ui.VideoActivityex.intentTo(VideoInfoActivity.this, url,danmakuPath, videoinfo.getTitle());
                        }else {
                            Intent intent=new Intent(VideoInfoActivity.this,DownloadActivity.class);
                            intent.putExtra("title",videoinfo.getTitle());
                            intent.putExtra("url",url);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset==0){//顶部展开
            play_layout.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            xiazai.setVisibility(View.GONE);
        }
        if ((appBarLayout.getTotalScrollRange()+verticalOffset)==0){
            play_layout.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
            xiazai.setVisibility(View.VISIBLE);
        }
    }
}
