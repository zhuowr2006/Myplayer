package com.hema.myplayer.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.hema.myplayer.R;
import com.hema.myplayer.bean.BannerBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<BannerBean.ListBean> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerBean.ListBean data) {
        imageView.setImageResource(R.mipmap.userlogo);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.userlogo)
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoader.getInstance().displayImage(data.getImg(),imageView,defaultOptions);
    }

}
