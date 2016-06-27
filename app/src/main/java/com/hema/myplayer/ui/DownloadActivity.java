package com.hema.myplayer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hema.myplayer.R;
import com.hema.myplayer.download.BaseDownloadHolder;
import com.hema.myplayer.download.DownloadInfo;
import com.hema.myplayer.download.DownloadManager;
import com.hema.myplayer.download.DownloadRequestCallBack;
import com.hema.myplayer.download.DownloadService;
import com.hema.myplayer.util.FileSizeUtil;
import com.hema.myplayer.util.StringUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/4/8.
 */
public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back, shuaxin_btn;
    private TextView dowload_text, all_start, edit_text, title_text;
    private ProgressBar download_pg;
    //
    private ListView download_listview;
    private DownloadManager downloadManager;
    private MyAdapter adapter;
    private String url;
    private String title;
    private boolean editordel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        downloadManager = DownloadService.getDownloadManager(getApplicationContext());
        findview();
        initView();
        setListener();
    }

    private void setListener() {
        back.setOnClickListener(this);
        shuaxin_btn.setOnClickListener(this);
        all_start.setOnClickListener(this);
        edit_text.setOnClickListener(this);
    }

    private void initView() {
        setRamShow();
        adapter = new MyAdapter();
        download_listview.setAdapter(adapter);
        download_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DownloadInfo info = (DownloadInfo) adapter.getItem(position);
                if (info.getState() == HttpHandler.State.SUCCESS) {
                    com.hema.playViewUtil.ui.VideoActivityex.intentTo(DownloadActivity.this, info.getFileSavePath(), info.getFileName());
                }

            }
        });
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        if (!StringUtils.isEmpty(title) && !StringUtils.isEmpty(url)) {
            setDownload();
        }
        download_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                seteidtlayout();
                return false;
            }
        });
    }

    private void setRamShow() {
        long maxsize = FileSizeUtil.getTotalInternalMemorySize();//获取系统最大内存
        long usablesize = FileSizeUtil.getAvailableInternalMemorySize();//获取可用内存
        download_pg.setMax(FileSizeUtil.formatFileSize2(maxsize, true));
        download_pg.setProgress((FileSizeUtil.formatFileSize2(maxsize, true) - FileSizeUtil.formatFileSize2(usablesize, true)));
        String datasize = String.format("主存储：%s /可用 %s", FileSizeUtil.formatFileSize(maxsize, false), FileSizeUtil.formatFileSize(usablesize, false));
        dowload_text.setText(datasize);
    }

    private void findview() {
        download_listview = (ListView) findViewById(R.id.download_listview);
        back = (ImageView) findViewById(R.id.back);
        shuaxin_btn = (ImageView) findViewById(R.id.shuaxin_btn);
        title_text = (TextView) findViewById(R.id.title);
        dowload_text = (TextView) findViewById(R.id.dowload_text);
        all_start = (TextView) findViewById(R.id.all_start);
        edit_text = (TextView) findViewById(R.id.edit_text);
        download_pg = (ProgressBar) findViewById(R.id.download_pg);

    }

    private void setDownload() {
        String target = "/sdcard/xUtils/" + title + ".mp4";
        try {
            downloadManager.addNewDownload(url, title, target, true, true, new DownloadRequestCallBack());
            adapter.notifyDataSetChanged();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                if (StringUtils.isEquals(title_text.getText().toString(), "选择视频")) {
                    setnormallayout();
                    return;
                }
                finish();
                break;
            case R.id.shuaxin_btn:
                setRamShow();
                break;
            case R.id.all_start:
                if (!editordel) {
                    if (downloadManager.getDownloadInfoListCount() > 0) {
                        if (StringUtils.isEquals(all_start.getText().toString(), "全部开始")) {
                            for (int i = 0; i < downloadManager.getDownloadInfoListCount(); i++) {
                                try {
                                    if (downloadManager.getDownloadInfo(i).getState() == HttpHandler.State.CANCELLED || downloadManager.getDownloadInfo(i).getState() == HttpHandler.State.FAILURE) {
                                        downloadManager.resumeDownload(downloadManager.getDownloadInfo(i), new DownloadRequestCallBack());
                                    }
                                } catch (DbException e) {
                                    LogUtils.e(e.getMessage(), e);
                                }
                            }
                            all_start.setText("全部暂停");
                            adapter.notifyDataSetChanged();
                        } else {
                            try {
                                downloadManager.stopAllDownload();
                            } catch (DbException e) {
                                LogUtils.e(e.getMessage(), e);
                            }
                            all_start.setText("全部开始");
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (StringUtils.isEquals(all_start.getText().toString(), "全部选择")) {
                        for (int n = 0; n < downloadManager.getDownloadInfoListCount(); n++) {
                            downloadManager.getDownloadInfo(n).setIscheck(true);
                        }
                        all_start.setText("全部取消");

                    } else {
                        for (int i = 0; i < downloadManager.getDownloadInfoListCount(); i++) {
                            downloadManager.getDownloadInfo(i).setIscheck(false);
                        }
                        all_start.setText("全部选择");
                    }
                    adapter.notifyDataSetChanged();
                }

                break;
            case R.id.edit_text:
                if (!editordel) {
                    if (!StringUtils.isEquals(title_text.getText().toString(), "选择视频")) {
                        seteidtlayout();
                        return;
                    }
                } else {

                    for (int i = downloadManager.getDownloadInfoListCount()-1; i >=0 ; i--) {
                        DownloadInfo info = downloadManager.getDownloadInfo(i);
                        if (info.ischeck()) {
                            try {
                                File file = new File(info.getFileSavePath());
                                if (file.exists()) {
                                    file.delete();
                                }
                                downloadManager.removeDownload(info);
                                adapter.notifyDataSetChanged();
                            } catch (DbException e) {
                                LogUtils.e(e.getMessage(), e);
                            }
                        }
                    }
                }
                break;
        }
    }

    private void seteidtlayout() {
        title_text.setText("选择视频");
        shuaxin_btn.setVisibility(View.GONE);
        all_start.setText("全部选择");
        edit_text.setText("删除");
        if (downloadManager.getDownloadInfoListCount() > 0) {
            for (int i = 0; i < downloadManager.getDownloadInfoListCount(); i++) {
                downloadManager.getDownloadInfo(i).setIscheck(false);
                downloadManager.getDownloadInfo(i).setIscheckshow(true);
            }
            adapter.notifyDataSetChanged();
        } else {

        }
        editordel = true;

    }

    private void setnormallayout() {
        title_text.setText("缓存下载");
        shuaxin_btn.setVisibility(View.VISIBLE);
        all_start.setText("全部开始");
        edit_text.setText("编辑");
        if (downloadManager.getDownloadInfoListCount() > 0) {
            for (int i = 0; i < downloadManager.getDownloadInfoListCount(); i++) {
                downloadManager.getDownloadInfo(i).setIscheck(false);
                downloadManager.getDownloadInfo(i).setIscheckshow(false);
            }
            adapter.notifyDataSetChanged();
        } else {

        }
        editordel = false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setnormallayout();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return downloadManager.getDownloadInfoListCount();
        }

        @Override
        public Object getItem(int position) {
            return downloadManager.getDownloadInfo(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DownloadInfo downloadInfo = downloadManager.getDownloadInfo(position);
            holdler holder = null;
            if (convertView == null) {
                convertView = View.inflate(DownloadActivity.this, R.layout.download_item, null);
                holder = new holdler(downloadInfo);
                holder.label = (TextView) convertView.findViewById(R.id.download_label);
                holder.state = (TextView) convertView.findViewById(R.id.download_state);
                holder.pg_text = (TextView) convertView.findViewById(R.id.pg_text);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_btn);
                holder.progressBar = (ProgressBar) convertView.findViewById(R.id.download_pb);
                holder.stopBtn = (ImageView) convertView.findViewById(R.id.download_stop_btn);
                holder.removeBtn = (Button) convertView.findViewById(R.id.download_remove_btn);
                convertView.setTag(holder);
                holder.refresh();
            } else {
                holder = (DownloadActivity.holdler) convertView.getTag();
                holder.update(downloadInfo);
            }
            holder.stop();
            holder.remove();
            holder.check();

            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                DownloadManager.ManagerCallBack requestCallBack = (DownloadManager.ManagerCallBack) handler.getRequestCallBack();
                if (requestCallBack.getBaseCallBack() == null) {
                    requestCallBack.setBaseCallBack(new DownloadRequestCallBack());
                }
                requestCallBack.setUserTag(new WeakReference<holdler>(holder));
            }

            return convertView;
        }
    }

    public class holdler extends BaseDownloadHolder {
        TextView label;
        TextView state;
        TextView pg_text;
        CheckBox checkBox;
        ProgressBar progressBar;
        ImageView stopBtn;
        Button removeBtn;

        public holdler(DownloadInfo downloadInfo) {
            super(downloadInfo);

        }

        @Override
        public void refresh() {
            checkBox.setVisibility(downloadInfo.ischeckshow() ? View.VISIBLE : View.GONE);
            checkBox.setChecked(downloadInfo.ischeck());
            label.setText(downloadInfo.getFileName() + downloadInfo.getId());
            state.setText(downloadInfo.getState().toString());
            if (downloadInfo.getFileLength() > 0) {
                progressBar.setProgress((int) (downloadInfo.getProgress() * 100 / downloadInfo.getFileLength()));
                String datasize = String.format("%s / %s", FileSizeUtil.formatFileSize(downloadInfo.getProgress(), false), FileSizeUtil.formatFileSize(downloadInfo.getFileLength(), false));
                pg_text.setText(datasize);
            } else {
                progressBar.setProgress(0);
            }

            stopBtn.setVisibility(View.VISIBLE);
//            stopBtn.setText("暂停");
            HttpHandler.State state = downloadInfo.getState();
            switch (state) {
                case WAITING:
//                    stopBtn.setText("暂停");
                    stopBtn.setBackgroundResource(R.mipmap.zanting_btn);
                    pg_text.setText("等待中");
                    break;
                case STARTED:
//                    stopBtn.setText("暂停");
                    stopBtn.setBackgroundResource(R.drawable.xiazai_btn);
                    pg_text.setText("等待中");
                    break;
                case LOADING:
//                    stopBtn.setText("暂停");
                    stopBtn.setBackgroundResource(R.mipmap.zanting_btn);
                    break;
                case CANCELLED:
//                    stopBtn.setText("继续");
                    stopBtn.setBackgroundResource(R.drawable.xiazai_btn);
                    break;
                case SUCCESS:
                    stopBtn.setVisibility(View.INVISIBLE);
                    setRamShow();
                    break;
                case FAILURE:
                    pg_text.setText("重试");
                    stopBtn.setBackgroundResource(R.mipmap.shuaxin_btn);
                    break;
                default:
                    break;
            }
        }


        public void remove() {
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        downloadManager.removeDownload(downloadInfo);
                        File file = new File(downloadInfo.getFileSavePath());
                        if (file.exists()) {
                            file.delete();
                        }
                        adapter.notifyDataSetChanged();
                    } catch (DbException e) {
                        LogUtils.e(e.getMessage(), e);
                    }
                }
            });

        }

        public void check() {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    downloadInfo.setIscheck(isChecked);
                    adapter.notifyDataSetChanged();
                }
            });

        }

        public void stop() {
            stopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpHandler.State state = downloadInfo.getState();
                    switch (state) {
                        case WAITING:
                        case STARTED:
                        case LOADING:
                            try {
                                downloadManager.stopDownload(downloadInfo);
                            } catch (DbException e) {
                                LogUtils.e(e.getMessage(), e);
                            }
                            break;
                        case CANCELLED:
                        case FAILURE:
                            try {
                                downloadManager.resumeDownload(downloadInfo, new DownloadRequestCallBack());
                            } catch (DbException e) {
                                LogUtils.e(e.getMessage(), e);
                            }
                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            break;
                    }
                }
            });

        }
    }
}
