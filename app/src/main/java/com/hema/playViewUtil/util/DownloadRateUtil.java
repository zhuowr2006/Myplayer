package com.hema.playViewUtil.util;

import android.os.Handler;
import android.os.Message;

import com.hema.myplayer.R;
import com.hema.playViewUtil.widget.media.MediaController;

import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.MediaPlayerProxy;

/**
 * Created by Administrator on 2016/3/28.
 */
public class DownloadRateUtil {
    private MediaController mMediaController;
    private IMediaPlayer mMediaPlayer;

    private void setRowValue(int id, String value) {
        mMediaController.setDownloadRate(value);
    }

    public void setMediaPlayer(IMediaPlayer mp,MediaController mMediaController) {
        this.mMediaController=mMediaController;
        mMediaPlayer = mp;
        if (mMediaPlayer != null) {
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_HUD, 500);
        } else {
            mHandler.removeMessages(MSG_UPDATE_HUD);
        }
    }

    private static String formatedDurationMilli(long duration) {
        if (duration >=  1000) {
            return String.format(Locale.US, "%.2f sec", ((float)duration) / 1000);
        } else {
            return String.format(Locale.US, "%d msec", duration);
        }
    }

    private static String formatedSize(long bytes) {
        if (bytes >= 100 * 1000) {
            return String.format(Locale.US, "%.2f MB", ((float)bytes) / 1000 / 1000);
        } else if (bytes >= 100) {
            return String.format(Locale.US, "%.1f KB", ((float)bytes) / 1000);
        } else {
            return String.format(Locale.US, "%d B", bytes);
        }
    }


    private static final int MSG_UPDATE_HUD = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_HUD: {
                    IjkMediaPlayer mp = null;
                    if (mMediaPlayer == null)
                        break;
                    if (mMediaPlayer instanceof IjkMediaPlayer) {
                        mp = (IjkMediaPlayer) mMediaPlayer;
                    } else if (mMediaPlayer instanceof MediaPlayerProxy) {
                        MediaPlayerProxy proxy = (MediaPlayerProxy) mMediaPlayer;
                        IMediaPlayer internal = proxy.getInternalMediaPlayer();
                        if (internal != null && internal instanceof IjkMediaPlayer)
                            mp = (IjkMediaPlayer) internal;
                    }
                    if (mp == null)
                        break;

                    int vdec = mp.getVideoDecoder();
                    switch (vdec) {
                        case IjkMediaPlayer.FFP_PROPV_DECODER_AVCODEC:
                            setRowValue(R.string.vdec, "avcodec");
                            break;
                        case IjkMediaPlayer.FFP_PROPV_DECODER_MEDIACODEC:
                            setRowValue(R.string.vdec, "MediaCodec");
                            break;
                        default:
                            setRowValue(R.string.vdec, "");
                            break;
                    }

                    float fpsOutput = mp.getVideoOutputFramesPerSecond();
                    float fpsDecode = mp.getVideoDecodeFramesPerSecond();
                    setRowValue(R.string.fps, String.format(Locale.US, "%.2f / %.2f", fpsDecode, fpsOutput));

                    long videoCachedDuration = mp.getVideoCachedDuration();
                    long audioCachedDuration = mp.getAudioCachedDuration();
                    long videoCachedBytes    = mp.getVideoCachedBytes();
                    long audioCachedBytes    = mp.getAudioCachedBytes();

//                    setRowValue(R.string.v_cache, String.format(Locale.US, "%s, %s", formatedDurationMilli(videoCachedDuration), formatedSize(videoCachedBytes)));
//                    setRowValue(R.string.a_cache, String.format(Locale.US, "%s, %s", formatedDurationMilli(audioCachedDuration), formatedSize(audioCachedBytes)));
                    setRowValue(R.string.a_cache, String.format(Locale.US, "%s, %s", formatedDurationMilli(audioCachedDuration), formatedSize(audioCachedBytes)));

                    mHandler.removeMessages(MSG_UPDATE_HUD);
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_HUD, 1000);
                }
            }
        }
    };
}

