package com.hema.myplayer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hema.myplayer.R;
import com.hema.myplayer.bean.VideoItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class listAdapter extends PtrrvBaseAdapter<listAdapter.ViewHolder> {
    private List<VideoItemBean> datalist;
    private Context context;
    private DisplayImageOptions defaultOptions;
    private int paihang;
    private OnItemClickLitener mOnItemClickLitener;

    public listAdapter(Context context, List<VideoItemBean> dingList, int paihang) {
        super(context);
        this.datalist = dingList;
        this.context = context;
        this.paihang = paihang;
        defaultOptions = new DisplayImageOptions.Builder().
                imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.mipmap.userlogo) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.userlogo)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.userlogo) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .build();
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void setDatalist(List<VideoItemBean> datalist) {
        if (this.datalist!=null&&datalist.size()>0){
            datalist.clear();
        }
        this.datalist = datalist;
    }

    @Override
    public int getItemCount() {
        return datalist != null ? datalist.size() : 0;
    }
    public int getlistsize(){
        return datalist.size();
    }


    @Override
    public VideoItemBean getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.include_item_mainitem, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(datalist.get(position).getTitle());
        holder.up.setText(datalist.get(position).getAuthor());
        holder.bf.setText(towang(datalist.get(position).getPlay()));
        if (paihang == 7) {
            holder.layout.setVisibility(View.GONE);
        } else {
            holder.pl.setText(towang(datalist.get(position).getVideo_review()));
        }
        ImageLoader.getInstance().displayImage(datalist.get(position).getPic(), holder.img, defaultOptions);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }
    ;


    private String towang(String s) {
        if (s.length() == 5) {
            String d = s.charAt(0) + "万";
            return d;
        } else if (s.length() > 5) {
            String d = s.substring(0, s.length() - 5) + "万";
            return d;
        }
        return s;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView up;
        TextView bf;
        TextView pl;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.pl_layout);
            img = (ImageView) itemView.findViewById(R.id.include_item_mainitem_img);
            title = (TextView) itemView.findViewById(R.id.include_item_mainitem_title);
            up = (TextView) itemView.findViewById(R.id.up_name);
            bf = (TextView) itemView.findViewById(R.id.include_item_mainitem_bf);
            pl = (TextView) itemView.findViewById(R.id.include_item_mainitem_pl);
        }
    }
}
