package com.lzy.imagepicker.loader;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.util.PhotoUtils;

import java.io.File;

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        PhotoUtils.imageLoadNormal(activity, Uri.fromFile(new File(path)), imageView, R.drawable.ic_default_image);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        PhotoUtils.imageLoadNormal(activity, Uri.fromFile(new File(path)), imageView, R.drawable.ic_default_image);
    }

    @Override
    public void clearMemoryCache() {
    }
}
