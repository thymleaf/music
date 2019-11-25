package com.library.common.utils;

import android.content.Context;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/9/14 16:23 <br>
 */

public class DensityUtil
{
    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dpToPixel(Context context, final float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int pixelToDp(Context context, final float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int spToPixel(Context context, final float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int pixelToSp(Context context, final float pxValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**获取屏幕的宽度（像素）*/
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;//1080
    }

    /**获取屏幕的高度（像素）*/
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;//1776
    }
}
