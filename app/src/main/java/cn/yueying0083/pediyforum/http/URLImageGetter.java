package cn.yueying0083.pediyforum.http;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.InputStream;

import cn.yueying0083.pediyforum.utils.ImageDisplayConfig;

/**
 * Created by luoj@huoli.com on 2016/11/24.
 */


public class URLImageGetter implements Html.ImageGetter {
    private TextView mTextView;

    /***
     * Construct the URLImageGetter which will execute AsyncTask and refresh the container
     *
     * @param t
     */
    public URLImageGetter(TextView t) {
        mTextView = t;
    }

    @Override
    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        ImageLoader.getInstance().loadImage(source, ImageDisplayConfig.getInstance(mTextView.getContext()).getDisplayOptions(), new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String url, View v, Bitmap b) {
                urlDrawable.bitmap = b;
                urlDrawable.setBounds(0, 0, b.getWidth(), b.getHeight());
                mTextView.invalidate();
                mTextView.setText(mTextView.getText());
            }
        });
        return urlDrawable;
    }


    private class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}