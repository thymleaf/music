package com.library.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.library.common.R;

import java.io.File;

/**
 * Description: 图片加载工具类<br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/25 16:17 <br>
 * @version glide:4.0.0-RC1
 */
public class ImageLoaderUtil
{

    private static RequestOptions getOption()
    {
        return new RequestOptions().centerCrop()
                                   .placeholder(R.drawable.ic_image_loading)
                                   .error(R.drawable.ic_empty_picture)
                                   .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                   .priority(Priority.HIGH);

    }

    private static RequestOptions getOption(int icPlaceHolder, int icError)
    {
        return new RequestOptions().centerCrop()
                                   .placeholder(icPlaceHolder)
                                   .error(icError)
                                   .diskCacheStrategy(DiskCacheStrategy.ALL)
                                   .priority(Priority.HIGH);

    }

    public static void display(Context context, ImageView imageView, String url, int icPlaceHolder,
                               int icError)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context)
//             .asBitmap()
             .load(url)
             .transition(DrawableTransitionOptions.withCrossFade())
             .apply(getOption(icPlaceHolder, icError))
             .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url,int icError)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions().error(icError);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
             .load(url)
             .transition(DrawableTransitionOptions.withCrossFade())
             .apply(getOption())
             .into(imageView);
    }

    public static void display(Context context, ImageView imageView, File url)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
             .load(url)
             .transition(DrawableTransitionOptions.withCrossFade())
             .apply(getOption())
             .into(imageView);
    }

    public static void displaySmallPhoto(Context context, ImageView imageView, String url)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
             .load(url)
             .transition(DrawableTransitionOptions.withCrossFade())
             .apply(getOption())
             .thumbnail(0.5f)
             .into(imageView);
    }

    public static void displayBigPhoto(Context context, ImageView imageView, String url)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
             .load(url)
             .transition(DrawableTransitionOptions.withCrossFade())
             .apply(getOption().format(DecodeFormat.PREFER_ARGB_8888))
             .into(imageView);
    }

    public static void display(Context context, ImageView imageView, int url)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
             .load(url)
             .apply(getOption())
             .transition(DrawableTransitionOptions.withCrossFade())
             .into(imageView);
    }


    public static void displayRound(Context context, ImageView imageView, String url)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
             .load(url)
             .transition(DrawableTransitionOptions.withCrossFade())
             .apply(RequestOptions.bitmapTransform(new CircleCrop()))
             .into(imageView);
    }

    public static void displayRoundCenterCrop(Context context, ImageView imageView, String url,int radius){
        //设置图片圆角角度
        RequestOptions options = new RequestOptions();
        options.transform(new GlideRoundTransform(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url,
                               RequestOptions options)
    {
        Glide.with(context)
             .load(url)
             .apply(options)
             .transition(DrawableTransitionOptions.withCrossFade())

             .into(imageView);
    }

    public static void displayGif(Context context, ImageView imageView, String url)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
             .asGif()
             .load(url)
             .transition(DrawableTransitionOptions.withCrossFade())
             .apply(getOption())
             .into(imageView);
    }

    public static void displayGif(Context context, ImageView imageView, String url, RequestOptions options)
    {
        if (imageView == null)
        {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
             .asGif()
             .load(url)
             .transition(DrawableTransitionOptions.withCrossFade())
             .apply(options)
             .into(imageView);
    }


    /**
     * Drawable to bitmap.
     *
     * @param drawable The drawable.
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null)
            {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
        {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        else
        {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap to drawable.
     *
     * @param bitmap The bitmap.
     * @return drawable
     */
    public static Drawable bitmap2Drawable(Context context, final Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(context.getResources(), bitmap);
    }

}
