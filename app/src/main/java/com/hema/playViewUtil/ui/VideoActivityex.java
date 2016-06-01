/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hema.playViewUtil.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hema.myplayer.R;
import com.hema.myplayer.util.CompressionTools;
import com.hema.myplayer.util.HttpUtil;
import com.hema.myplayer.util.StringUtils;
import com.hema.playViewUtil.widget.media.IjkVideoViewex;
import com.hema.playViewUtil.widget.media.MediaController;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import cz.msebera.android.httpclient.Header;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoActivityex extends Activity {
    private static final String TAG = "VideoActivity";

    private String mVideoPath;
    private String danmakuPath;
    private Uri mVideoUri;


    private MediaController mMediaController;
    private IjkVideoViewex mVideoView;
    private IDanmakuView mDanmakuView;//弹幕
    private BaseDanmakuParser mParser;
    private DanmakuContext mContext;
    private RelativeLayout startVideo;
    private TextView startVideoInfo;
    private String startText = "初始化播放器...";
    private ImageView biliAnim;
    private AnimationDrawable anim;

    private boolean mBackPressed;

    private boolean mNeedLock;//是不是锁屏
    private String mBatteryLevel;//电量监听
    private  BatteryReceiver batteryReceiver;


    public static Intent newIntent(Context context, String videoPath, String videoTitle) {
        Intent intent = new Intent(context, VideoActivityex.class);
        intent.putExtra("videoPath", videoPath);
        intent.putExtra("videoTitle", videoTitle);
        return intent;
    }
    public static Intent newIntent(Context context, String videoPath,String danmakuPath, String videoTitle) {
        Intent intent = new Intent(context, VideoActivityex.class);
        intent.putExtra("videoPath", videoPath);
        intent.putExtra("videoTitle", videoTitle);
        intent.putExtra("danmakuPath", danmakuPath);
        return intent;
    }

    public static void intentTo(Context context, String videoPath, String videoTitle) {
        context.startActivity(newIntent(context, videoPath, videoTitle));
    }
    public static void intentTo(Context context, String videoPath, String danmakuPath,String videoTitle) {
        context.startActivity(newIntent(context, videoPath, danmakuPath, videoTitle));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_ex);
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        findviewbyid();
        init();
        //注册电量广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryReceiver = new BatteryReceiver();
        registerReceiver(batteryReceiver, intentFilter);

    }
    private  void init(){
        mVideoPath = getIntent().getStringExtra("videoPath");
        danmakuPath = getIntent().getStringExtra("danmakuPath");
        startVideo.setVisibility(View.VISIBLE);//启动加载页面
        anim = (AnimationDrawable) biliAnim.getBackground();
        anim.start();
        if (!StringUtils.isEmpty(danmakuPath)){
            intidanmuku();
            getdanmuRequest();
        }
        // handle arguments
        Intent intent = getIntent();
        String intentAction = intent.getAction();
        if (!TextUtils.isEmpty(intentAction)) {
            if (intentAction.equals(Intent.ACTION_VIEW)) {
                mVideoPath = intent.getDataString();
            } else if (intentAction.equals(Intent.ACTION_SEND)) {
                mVideoUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    String scheme = mVideoUri.getScheme();
                    if (TextUtils.isEmpty(scheme)) {
                        Log.e(TAG, "Null unknown ccheme\n");
                        finish();
                        return;
                    }
                    if (scheme.equals(ContentResolver.SCHEME_ANDROID_RESOURCE)) {
                        mVideoPath = mVideoUri.getPath();
                    } else if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                        Log.e(TAG, "Can not resolve content below Android-ICS\n");
                        finish();
                        return;
                    } else {
                        Log.e(TAG, "Unknown scheme " + scheme + "\n");
                        finish();
                        return;
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(mVideoPath)) {
            new RecentMediaStorage(this).saveUrlAsync(mVideoPath);
        }
        //        // init 播放控制器
        mMediaController = new MediaController(this, mNeedLock);
        if (!StringUtils.isEmpty(danmakuPath)){
            mVideoView.setplayType(Settings.PV_PLAYER__IjkMediaPlayer);
            mMediaController.setDanmakuVisible(true);
        }else {
            mVideoView.setplayType(Settings.PV_PLAYER__AndroidMediaPlayer);
            mMediaController.setDanmakuVisible(false);
        }
        mVideoView.setMediaController(mMediaController);
//        mVideoView.setdownloadRate();

        // prefer mVideoPath
        if (mVideoPath != null)
            mVideoView.setVideoPath(mVideoPath);
        else if (mVideoUri != null)
            mVideoView.setVideoURI(mVideoUri);
        else {
            Log.e(TAG, "Null Data Source\n");
            finish();
            return;
        }
        startText = startText + "【完成】\n解析视频地址...【完成】\n全舰弹幕填装...";
        startVideoInfo.setText(startText);
        if (StringUtils.isEmpty(danmakuPath)){
            startVideo.setVisibility(View.GONE);
            mVideoView.start();
        }

    }
    private void findviewbyid(){
        startVideo = (RelativeLayout) findViewById(R.id.video_start);
        startVideoInfo = (TextView) findViewById(R.id.video_start_info);
        biliAnim = (ImageView) findViewById(R.id.bili_anim);
        mVideoView = (IjkVideoViewex) findViewById(R.id.video_view_ex);
        mDanmakuView = (IDanmakuView) findViewById(R.id.sv_danmaku);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView.isPlaying()&&mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }


    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    //电量检测广播
    public class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            int percent = scale > 0 ? level * 100 / scale : 0;
            if (percent > 100)
                percent = 100;
            mBatteryLevel = String.valueOf(percent) + "%";
            if (mVideoView!=null){
                mVideoView.setBatteryLevel(mBatteryLevel);
            }

        }
    }
    private void intidanmuku(){
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示3行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mContext = DanmakuContext.create();
        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
//        .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        mDanmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
               handler.sendEmptyMessage(0);
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
//

    }

    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }
    private void getdanmuRequest(){
        HttpUtil.get(this,danmakuPath, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(CompressionTools.decompressXML(responseBody));
                    if (mDanmakuView != null) {
                        mParser = createParser(byteArrayInputStream);
                        mDanmakuView.prepare(mParser, mContext);
                        mDanmakuView.showFPS(false);
                        mDanmakuView.enableDanmakuDrawingCache(true);
                        mVideoView.setDanmaku(mDanmakuView);

                    }
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    protected Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            startVideo.setVisibility(View.GONE);
            mVideoView.start();
            if (!StringUtils.isEmpty(danmakuPath)){
                mDanmakuView.start(mVideoView.getCurrentPosition());
            }
        }

    };

}
