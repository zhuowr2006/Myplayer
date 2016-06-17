package com.hema.myplayer.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hema.myplayer.R;

/**
 * Created by Administrator on 2016/3/23.
 */
public class sb_gidAdapter extends BaseAdapter{
    private Context context;
    private String[]  data;

    public sb_gidAdapter(Context context, String[] data) {
        super();
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data != null ? data.length : 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_subarea, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_sb_title);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_sb_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (data != null) {
            switch (position) {
                case 0:
                    viewHolder.img.setImageResource(R.mipmap.dh_icon);
                    viewHolder.title.setText("动画");
                    break;
                case 1:
                    viewHolder.img.setImageResource(R.mipmap.yy_icon);
                    viewHolder.title.setText("音乐");
                    break;
                case 2:
                    viewHolder.img.setImageResource(R.mipmap.yx_icon);
                    viewHolder.title.setText("游戏");
                    break;
                case 3:
                    viewHolder.img.setImageResource(R.mipmap.yl_icon);
                    viewHolder.title.setText("娱乐");
                    break;
                case 4:
                    viewHolder.img.setImageResource(R.mipmap.dsj_icon);
                    viewHolder.title.setText("电视剧");
                    break;
                case 5:
                    viewHolder.img.setImageResource(R.mipmap.fj_icon);
                    viewHolder.title.setText("番剧");
                    break;
                case 6:
                    viewHolder.img.setImageResource(R.mipmap.dy_icon);
                    viewHolder.title.setText("电影");
                    break;
                case 7:
                    viewHolder.img.setImageResource(R.mipmap.kj_icon);
                    viewHolder.title.setText("科技");
                    break;
                case 8:
                    viewHolder.img.setImageResource(R.mipmap.gc_icon);
                    viewHolder.title.setText("鬼畜");
                    break;
                case 9:
                    viewHolder.img.setImageResource(R.mipmap.wd_icon);
                    viewHolder.title.setText("舞蹈");
                    break;
                case 10:
                    viewHolder.img.setImageResource(R.mipmap.ss_icon);
                    viewHolder.title.setText("时尚");
                    break;
                case 11:
                    viewHolder.img.setImageResource(R.mipmap.zb_icon);
                    viewHolder.title.setText("直播");
                    break;
            }
        }
        return convertView;
    }


    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    class ViewHolder {
        TextView title;
        ImageView img;
    }
}
