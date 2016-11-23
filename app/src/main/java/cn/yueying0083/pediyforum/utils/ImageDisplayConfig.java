package cn.yueying0083.pediyforum.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageDisplayConfig {

	private static ImageDisplayConfig mInstance;
	private DisplayImageOptions mDisplayImageOptions;
	private AnimateDisplayImageListener mAnimateDisplayImageListener;

	private ImageDisplayConfig(Context context) {
		mDisplayImageOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.build();
		mAnimateDisplayImageListener = new AnimateDisplayImageListener();
	}

	public synchronized static ImageDisplayConfig getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new ImageDisplayConfig(context);
		}
		return mInstance;
	}

	public DisplayImageOptions getDisplayOptions() {
		return mDisplayImageOptions;
	}

	public AnimateDisplayImageListener getDisplayListener() {
		return mAnimateDisplayImageListener;
	}
	
	public static class AnimateDisplayImageListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}


}
