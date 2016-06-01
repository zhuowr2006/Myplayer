package com.hema.myplayer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hema.myplayer.R;
import com.hema.myplayer.bean.VideoItemBean;
import com.hema.myplayer.util.SelectableRoundedImageView;
import com.hema.myplayer.util.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class FanjuAdapter extends PtrrvBaseAdapter<RecyclerView.ViewHolder> {
    private List<VideoItemBean> datalist;
    private Context context;
    private DisplayImageOptions defaultOptions;
    private int paihang;
    private OnItemClickLitener mOnItemClickLitener;

    public FanjuAdapter(Context context, List<VideoItemBean> dingList, int paihang) {
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
        if (this.datalist != null && datalist.size() > 0) {
            datalist.clear();
        }
        this.datalist = datalist;
    }

    @Override
    public int getItemCount() {
        return datalist != null ? datalist.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        if (position < 3) {
            return ITEM_TYPE.ITEM1.ordinal();
        }else if (position==3||position==14){
            return ITEM_TYPE.ITEM2.ordinal();
        }else if(position<14){
            return ITEM_TYPE.ITEM3.ordinal();
        }else {
            return ITEM_TYPE.ITEM4.ordinal();
        }

    }

    @Override
    public VideoItemBean getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new headerViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.fanju_header, parent,
                    false));
        } else if (viewType == ITEM_TYPE.ITEM2.ordinal()){
            return new typeViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.fanju_header_2, parent,
                    false));
        } else if (viewType == ITEM_TYPE.ITEM3.ordinal()){
            return new contentViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.fanju_xfitem, parent,
                    false));
        }else {
            return new contentViewHolder2(LayoutInflater.from(
                    context).inflate(R.layout.fanju_wjitem, parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof headerViewHolder) {
            ((headerViewHolder) holder).mTextView.setText(datalist.get(position).getTitle());
        } else if (holder instanceof typeViewHolder){
              ((typeViewHolder) holder).title.setText(datalist.get(position).getTitle());
            if (StringUtils.isEquals(datalist.get(position).getTitle(),"新番连载")){
                ((typeViewHolder) holder).more.setVisibility(View.GONE);
                ((typeViewHolder) holder).img.setImageResource(R.mipmap.xf_icon);
            }else if (StringUtils.isEquals(datalist.get(position).getTitle(),"完结动画")){
                ((typeViewHolder) holder).more.setVisibility(View.GONE);
                ((typeViewHolder) holder).img.setImageResource(R.mipmap.wjdh_icon);
            }else {
                ((typeViewHolder) holder).img.setImageResource(R.mipmap.fjtj_icon);
            }
        } else if (holder instanceof contentViewHolder) {
            ((contentViewHolder) holder).title.setText(datalist.get(position).getTitle());
            ImageLoader.getInstance().displayImage(datalist.get(position).getPic(), ((contentViewHolder) holder).img, defaultOptions);
        }else {
            ((contentViewHolder2) holder).title.setText(datalist.get(position).getTitle());
            ImageLoader.getInstance().displayImage(datalist.get(position).getPic(), ((contentViewHolder2) holder).img, defaultOptions);
        }

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


    //建立枚举 2个item 类型
    public enum ITEM_TYPE {
        ITEM1,
        ITEM2,
        ITEM3,
        ITEM4,
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }


    class headerViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CardView cardView;

        public headerViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.fanju_header_text);
            cardView = (CardView) itemView.findViewById(R.id.fanju_header_cradview);
        }
    }
    class typeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        Button more;


        public typeViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.fanju_header_title);
            img = (ImageView) itemView.findViewById(R.id.fanju_header_img);
            more = (Button) itemView.findViewById(R.id.fanju_header_morebtn);
        }
    }

    class contentViewHolder extends RecyclerView.ViewHolder {
        SelectableRoundedImageView img;
        TextView title;

        public contentViewHolder(View itemView) {
            super(itemView);
            img = (SelectableRoundedImageView) itemView.findViewById(R.id.fanju_xf_img);
            title = (TextView) itemView.findViewById(R.id.fanju_xf_title);
        }
    }
    class contentViewHolder2 extends RecyclerView.ViewHolder {
        SelectableRoundedImageView img;
        TextView title;

        public contentViewHolder2(View itemView) {
            super(itemView);
            img = (SelectableRoundedImageView) itemView.findViewById(R.id.fanju_wj_img);
            title = (TextView) itemView.findViewById(R.id.fanju_wj_title);
        }
    }


}
