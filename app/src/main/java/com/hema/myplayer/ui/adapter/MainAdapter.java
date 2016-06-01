package com.hema.myplayer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hema.myplayer.R;
import com.hema.myplayer.bean.VideoItemBean;
import com.hema.myplayer.ui.MainItemActivity;
import com.hema.myplayer.util.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class MainAdapter extends PtrrvBaseAdapter<RecyclerView.ViewHolder> {

    private List<VideoItemBean> datalist;
    private Context context;
    private DisplayImageOptions defaultOptions;
    private OnItemClickLitener mOnItemClickLitener;

    public MainAdapter(Context context, List<VideoItemBean> dingList) {
        super(context);
        this.datalist = dingList;
        this.context = context;
        defaultOptions = new DisplayImageOptions.Builder().
                imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.mipmap.userlogo) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.userlogo)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.userlogo) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .build();
    }
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    //建立枚举 2个item 类型
    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }
    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        if (position%5==0) {
            return ITEM_TYPE.ITEM1.ordinal();
        }else {
            return ITEM_TYPE.ITEM2.ordinal();
        }

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new typeViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.fanju_header_2, parent,
                    false));
        } else {
            return new contentViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.include_item_recommend, parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //0:douga 动画  1:music 音乐 2：游戏 game 3：ent 娱乐 4：technology 电视剧 5：bangumi 番剧 6：movie 电影 7：technology 科技
        //8:kichiku 鬼畜 9：dace 舞蹈 10：fashion 时尚
        if (holder instanceof contentViewHolder) {
            ((contentViewHolder)holder).title.setText(datalist.get(position).getTitle());
            ((contentViewHolder)holder).bf_number.setText(datalist.get(position).getPlay());
            ((contentViewHolder)holder).pi_number.setText(datalist.get(position).getVideo_review());
            ImageLoader.getInstance().displayImage(datalist.get(position).getPic(), ((contentViewHolder)holder).img, defaultOptions);
        } else if (holder instanceof typeViewHolder){
            final Intent intent =new Intent();
            switch (position) {
                case 0:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.dh_icon);
                    ((typeViewHolder) holder).title.setText("动画");
                    intent.putExtra("type", 0);
                    break;
                case 5:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.yy_icon);
                    ((typeViewHolder) holder).title.setText("音乐");
                    intent.putExtra("type", 1);
                    break;
                case 10:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.yx_icon);
                    ((typeViewHolder) holder).title.setText("游戏");
                    intent.putExtra("type", 2);
                    break;
                case 15:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.yl_icon);
                    ((typeViewHolder) holder).title.setText("娱乐");
                    intent.putExtra("type", 3);
                    break;
                case 20:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.dsj_icon);
                    ((typeViewHolder) holder).title.setText("电视剧");
                    intent.putExtra("type", 4);
                    break;
                case 25:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.fj_icon);
                    ((typeViewHolder) holder).title.setText("番剧");
                    intent.putExtra("type", 5);
                    break;
                case 30:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.dy_icon);
                    ((typeViewHolder) holder).title.setText("电影");
                    intent.putExtra("type", 6);
                    break;
                case 35:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.kj_icon);
                    ((typeViewHolder) holder).title.setText("科技");
                    intent.putExtra("type", 7);
                    break;
                case 40:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.gc_icon);
                    ((typeViewHolder) holder).title.setText("鬼畜");
                    intent.putExtra("type", 8);
                    break;
                case 45:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.wd_icon);
                    ((typeViewHolder) holder).title.setText("舞蹈");
                    intent.putExtra("type", 9);
                    break;
                case 50:
                    ((typeViewHolder) holder).img.setImageResource(R.mipmap.ss_icon);
                    ((typeViewHolder) holder).title.setText("时尚");
                    intent.putExtra("type", 10);
                    break;

            }
            ((typeViewHolder) holder).more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.setClass(context, MainItemActivity.class);
                    context.startActivity(intent);
                }
            });
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
        TextView title, bf_number, pi_number, Status;
        SelectableRoundedImageView img;

        public contentViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.include_item_recommend_title);
            bf_number = (TextView) itemView.findViewById(R.id.include_item_recommend_bf);
            pi_number = (TextView) itemView.findViewById(R.id.include_item_recommend_pl);
            img = (SelectableRoundedImageView) itemView.findViewById(R.id.include_item_recommend_img);
        }
    }

}
