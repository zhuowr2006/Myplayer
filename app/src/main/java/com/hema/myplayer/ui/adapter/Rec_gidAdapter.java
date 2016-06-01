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
import com.hema.myplayer.bean.VideoItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * @ClassName: Rec_gidAdapter
 * @Description:首页gid适配器
 */
public class Rec_gidAdapter extends BaseAdapter {
    private Context context;
    private List<VideoItemBean> data;
    private DisplayImageOptions defaultOptions;

    public Rec_gidAdapter(Context context, List<VideoItemBean> data) {
        super();
        this.context = context;
        this.data = data;
        defaultOptions = new DisplayImageOptions.Builder().
                imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.mipmap.userlogo) // 设置图片在下载期间显1111111111111111111示的图片
                .showImageForEmptyUri(R.mipmap.userlogo)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.userlogo) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data != null ? data.size() : 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stu11b111111111111111111111111111111111
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.include_item_recommend, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.include_item_recommend_title);
            viewHolder.bf_number = (TextView) convertView.findViewById(R.id.include_item_recommend_bf);
            viewHolder.pi_number = (TextView) convertView.findViewById(R.id.include_item_recommend_pl);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.include_item_recommend_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (data != null) {
            viewHolder.title.setText(data.get(position).getTitle());
            viewHolder.bf_number.setText(data.get(position).getPlay());
            viewHolder.pi_number.setText(towang(data.get(position).getVideo_review()));
            ImageLoader.getInstance().displayImage(data.get(position).getPic(), viewHolder.img, defaultOptions);
        }
        return convertView;
    }

    private String towang(String s) {
        if (s.length() == 5) {
            String d = s.charAt(0) + "." + "万";
            return d;
        } else if (s.length() > 5) {
            String d = s.substring(0, s.length() - 5) + "." + "万";
            return d;
        }
        return s;
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
        TextView title, bf_number, pi_number, Status;
        ImageView img;
    }

}
