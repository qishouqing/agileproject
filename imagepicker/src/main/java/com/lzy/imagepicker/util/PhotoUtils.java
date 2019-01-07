package com.lzy.imagepicker.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by ggg on 2018/1/12 15.
 */

public class PhotoUtils {


    public static final Bitmap.CompressFormat FORMAT;

    static {
        FORMAT = Bitmap.CompressFormat.JPEG;
    }

    public PhotoUtils() {
    }

    // 圆角图片
    public static final int TYPE_ARC = 2;
    // 圆形图片
    public static final int TYPE_CIRCULAR = 1;
    // 正常图片
    public static final int TYPE_NORMAL = 0;


    /**
     * 可缓存正方形图片加载
     * @param context
     * @param url
     * @param imageView
     * @param error
     */
    public static void imageLoadNormal(Context context, Object url, ImageView imageView, int error) {
        imageLoad(context, url, imageView, error, true, TYPE_NORMAL, 0, 0);
    }

    public static void imageLoadNormal(Context context, Object url, ImageView imageView, int error, long timestamp) {
        imageLoad(context, url, imageView, error, false, TYPE_NORMAL, 0, timestamp);
    }

    public static void imageLoadCircular(Context context, Object url, ImageView imageView, int error) {
        imageLoad(context, url, imageView, error, true, TYPE_CIRCULAR, 0, 0);
    }

    public static void imageLoadCircular(Context context, Object url, ImageView imageView, int error, long timestamp) {
        imageLoad(context, url, imageView, error, true, TYPE_CIRCULAR, 0, timestamp);
    }

    public static void imageLoadArc(Context context, Object url, ImageView imageView, int error, int angle) {
        imageLoad(context, url, imageView, error, true, TYPE_ARC, angle, 0);
    }

    public static void imageLoadArc(Context context, Object url, ImageView imageView, int error, int angle, long timestamp) {
        imageLoad(context, url, imageView, error, true, TYPE_ARC, angle, timestamp);
    }


    private static void imageLoad(Context context, Object url, ImageView imageView, int error, boolean isCache, int type, int angle, long timestamp) {
        RequestOptions options = new RequestOptions()
                .placeholder(error)
                .error(error);

        if (!isCache) {
            options.signature(new ObjectKey(timestamp));
        }

        if (type == TYPE_CIRCULAR) {
            options.circleCrop();
        } else if (type == TYPE_ARC) {
            options.transform(new GlideRoundTransform(context, angle));
        } else if (type == TYPE_NORMAL) {
            // 不作处理
        }

        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }


    public static String compressPictureWithSaveDir(String path, int reheight, int rewidth, int quality, String savedir, Context context) throws IOException {
        Bitmap bitmap = getLocPicByDBYS(path, reheight, rewidth);
        File dir = new File(savedir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String savepath = savedir + getFileName() + "_" + UUID.randomUUID().toString() + ".jpg";
        File file = new File(savepath);
        FileOutputStream out = new FileOutputStream(file);
        if (quality >= 0 && quality <= 100) {
            if (bitmap.compress(FORMAT, quality, out)) {
                out.flush();
                out.close();
            }
        } else if (bitmap.compress(FORMAT, 100, out)) {
            out.flush();
            out.close();
        }

        bitmap.recycle();
        System.gc();
        return savepath;
    }

    public static Bitmap getLocPicByWidthorHeight(String path, int want, int type) throws IOException {
        if (type != 1 && type != 2) {
            return null;
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int inSampleSize = getInSampleSize(path, options, want, type);
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            FileInputStream fis = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(fis, (Rect)null, options);
            if (bitmap == null) {
                return null;
            } else {
                fis.close();
                int degree = getPictureDegree(path);
                float scale_w = getScale(want, bitmap.getWidth());
                float scale_h = getScale(want, bitmap.getHeight());
                float scale = scale_w > scale_h ? scale_h : scale_w;
                Matrix matrix = new Matrix();
                matrix.postRotate((float)degree);
                matrix.postScale(scale, scale);
                Bitmap bit = Bitmap.createBitmap(bitmap, 0, 0, options.outWidth, options.outHeight, matrix, false);
                return bit;
            }
        }
    }

    public static Bitmap getLocPicByDBYS(String path, int wantHeight, int wantWidth) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int wInSampleSize = getInSampleSize(path, options, wantWidth, 1);
        int hInSampleSize = getInSampleSize(path, options, wantHeight, 2);
        return wInSampleSize > hInSampleSize ? getLocPicByWidthorHeight(path, wantWidth, 1) : getLocPicByWidthorHeight(path, wantHeight, 2);
    }

    public static int getPictureDegree(String path) throws IOException {
        int degree = 0;
        ExifInterface exifInterface = new ExifInterface(path);
        int orientation = exifInterface.getAttributeInt("Orientation", 1);
        switch(orientation) {
            case 3:
                degree = 180;
            case 4:
            case 5:
            case 7:
            default:
                break;
            case 6:
                degree = 90;
                break;
            case 8:
                degree = 270;
        }

        return degree;
    }

    private static int getInSampleSize(String path, BitmapFactory.Options options, int want, int type) throws IOException {
        int degree = getPictureDegree(path);
        int size = 0;
        switch(type) {
            case 1:
                size = degree != 0 && degree != 180 ? options.outHeight : options.outWidth;
                break;
            case 2:
                size = degree != 0 && degree != 180 ? options.outWidth : options.outHeight;
        }

        int inSampleSize = size / want;
        return inSampleSize;
    }

    private static float getScale(int want, int size) {
        return size > want ? (float)want / (float)size : 1.0F;
    }

    public static String getFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        return dateFormat.format(date);
    }


}
