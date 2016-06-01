package com.hema.myplayer.base;

import android.app.Application;

import com.hema.myplayer.util.LogUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * @ClassName: BaseApplication
 * @Description: (全局变量类) Application对象只有在应用程序中所有Activity都destroy时才会destrory，
 * 所有我们可以在任何一个Activity中访问它
 */
public class BaseApplication extends Application {
    public static ImageLoader imageLoader = ImageLoader.getInstance();
    private static BaseApplication sInstance = null;
    private String TAG = BaseApplication.class.getSimpleName();

    public static BaseApplication getInstance() {
        return sInstance;
    }

    /**
     * 返回设备的版本号 默认为11 即3.0
     **/
    public static int getSDKVersion() {
        try {
            return Integer.parseInt(android.os.Build.VERSION.SDK);
        } catch (Exception e) {
            return 11;
        }
    }

    /**
     * Global request queue for Volley
     */
    /*
	 * (non-Javadoc)
	 *
	 * @see android.app.Application#onCreate()
	 */
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, AppConfig.YOUJIAOPIC_PATH);
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建

        imageLoader.getInstance().init(config);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Application#onLowMemory()
     */
    @Override
    public void onLowMemory() {
        LogUtils.i(TAG, "BaseApplication  onError  onLowMemory");
        super.onLowMemory();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Application#onTerminate()
     */
    @Override
    public void onTerminate() {
        LogUtils.i(TAG, "BaseApplication  onError  onTerminate");
        super.onTerminate();
    }

}
