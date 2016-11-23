package cn.yueying0083.pediyforum;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.yueying0083.pediyforum.utils.Constant;

/**
 * Created by luoj@huoli.com on 2016/11/23.
 */

public class PediyForumApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO);
        if (Constant.App.DEBUG) {
            builder.writeDebugLogs();
        }
        ImageLoaderConfiguration config = builder.build();
        ImageLoader.getInstance().init(config);


    }
}
