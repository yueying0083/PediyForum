package cn.yueying0083.pediyforum.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import android.text.TextUtils;
import android.widget.ImageView;

public class ImageDisplay {

    public static void display(ImageView iv, String url, boolean anim) {
        if (iv == null || TextUtils.isEmpty(url))
            return;
        if (url != null && url.startsWith("/")) {
            url = "file://" + url;
        }
        ImageDisplayConfig config = ImageDisplayConfig.getInstance(iv.getContext());
        if(anim) {
            ImageLoader.getInstance().displayImage(url, iv, config.getDisplayOptions(), config.getDisplayListener());
        }else{
            ImageLoader.getInstance().displayImage(url, iv, config.getDisplayOptions());
        }
    }

    public static void display(ImageView iv, String url) {
        display(iv, url, true);
    }

    public static void display(ImageView iv, String url, int defaultImg) {
        if (iv == null || TextUtils.isEmpty(url))
            return;
        if (url != null && url.startsWith("/")) {
            url = "file://" + url;
        }
        DisplayImageOptions opt = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .showImageForEmptyUri(defaultImg)
                .showImageOnFail(defaultImg)
                .showImageOnLoading(defaultImg)
                .build();
        ImageDisplayConfig config = ImageDisplayConfig.getInstance(iv.getContext());
        ImageLoader.getInstance().displayImage(url, iv, opt, config.getDisplayListener());
    }

    public static void display(ImageView iv, String url, int defaultImg, BitmapProcessor bp) {
        display(iv, url, defaultImg, bp, true);
    }

    public static void display(ImageView iv, String url, int defaultImg, BitmapProcessor bp, boolean isAnimOpen) {
        if (iv == null || TextUtils.isEmpty(url))
            return;
        if (url != null && url.startsWith("/")) {
            url = "file://" + url;
        }
        DisplayImageOptions opt = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .showImageForEmptyUri(defaultImg)
                .showImageOnFail(defaultImg)
                .showImageOnLoading(defaultImg)
                .preProcessor(bp)
                .build();
        ImageDisplayConfig config = ImageDisplayConfig.getInstance(iv.getContext());
        if (isAnimOpen) {
            ImageLoader.getInstance().displayImage(url, iv, opt, config.getDisplayListener());
        } else {
            ImageLoader.getInstance().displayImage(url, iv, opt);
        }
    }
}
