package com.qishouqing.frame.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.qishouqing.frame.base.QApp;

/**
 * Created by ggg on 2018/1/12 15.
 */

public class ImageUtils {
    public static final int TYPE_CIRCULAR = 1;
    public static final int TYPE_NAORMAL = 0;

    /**
     * @param context
     * @param url
     * @param imageView
     * @param error
     * @param isCache   是否采取缓存策略
     * @param type      0普通图片 1圆形图
     */
    public static void imageLoad(Context context, Object url, ImageView imageView, int error, boolean isCache, int type) {
        RequestOptions options = new RequestOptions()
                .placeholder(error)
                .error(error);
        if (!isCache) {
            options.signature(new ObjectKey(QApp.getInstance().getTimestamp()));
        }

        if (type == TYPE_CIRCULAR) {
            options.circleCrop();
        }

        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }

}
