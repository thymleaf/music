package com.thymleaf.music.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.thymleaf.music.App;

import java.lang.ref.WeakReference;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.widget.TextViewCompat;

public final class ToastUtil
{
    private static final int GRAVITY = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static final int Y_OFFSET = (int) (64 * App.getInstance()
                                                       .getResources()
                                                       .getDisplayMetrics().density + 0.5);
    private static final int X_OFFSET = 0;

    private static final int DEFAULT_COLOR = 0xFEFFFFFF;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int sLayoutId = -1;
    private static int gravity = GRAVITY;
    private static int xOffset = X_OFFSET;
    private static int yOffset = Y_OFFSET;
    private static int bgColor = DEFAULT_COLOR;
    private static int bgResource = -1;
    private static int msgColor = DEFAULT_COLOR;



    private ToastUtil()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x偏移
     * @param yOffset y偏移
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset)
    {
        ToastUtil.gravity = gravity;
        ToastUtil.xOffset = xOffset;
        ToastUtil.yOffset = yOffset;
    }


    public static void setDefaultGravity()
    {
        ToastUtil.gravity = GRAVITY;
        ToastUtil.yOffset = Y_OFFSET;
        ToastUtil.xOffset = X_OFFSET;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt final int backgroundColor)
    {
        ToastUtil.bgColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes final int bgResource)
    {
        ToastUtil.bgResource = bgResource;
    }

    /**
     * 设置消息颜色
     *
     * @param msgColor 颜色
     */
    public static void setMsgColor(@ColorInt final int msgColor)
    {
        ToastUtil.msgColor = msgColor;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortSafe(@NonNull final CharSequence text)
    {
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortSafe(@StringRes final int resId)
    {
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortSafe(@StringRes final int resId, final Object... args)
    {
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortSafe(final String format, final Object... args)
    {
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongSafe(@NonNull final CharSequence text)
    {
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongSafe(@StringRes final int resId)
    {
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongSafe(@StringRes final int resId, final Object... args)
    {
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongSafe(final String format, final Object... args)
    {
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(@NonNull final CharSequence text)
    {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShort(@StringRes final int resId)
    {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShort(@StringRes final int resId, final Object... args)
    {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    public static void showShortCenter(@NonNull final CharSequence text)
    {
        setGravity(Gravity.CENTER, 0,0);
        showShort(text);
    }

    public static void showLongCenter(@NonNull final CharSequence text)
    {
        setGravity(Gravity.CENTER, 0,0);
        showLong(text);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShort(final String format, final Object... args)
    {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLong(@NonNull final CharSequence text)
    {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLong(@StringRes final int resId)
    {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLong(@StringRes final int resId, final Object... args)
    {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(final String format, final Object... args)
    {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示短时自定义吐司
     */
    public static View showCustomShortSafe(@LayoutRes final int layoutId)
    {
        final View view = getView(layoutId);
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(view, Toast.LENGTH_SHORT);
            }
        });
        return view;
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static View showCustomLongSafe(@LayoutRes final int layoutId)
    {
        final View view = getView(layoutId);
        HANDLER.post(new Runnable()
        {
            @Override
            public void run()
            {
                show(view, Toast.LENGTH_LONG);
            }
        });
        return view;
    }

    /**
     * 显示短时自定义吐司
     */
    public static View showCustomShort(@LayoutRes final int layoutId)
    {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * 显示长时自定义吐司
     */
    public static View showCustomLong(@LayoutRes final int layoutId)
    {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * 取消吐司显示
     */
    public static void cancel()
    {
        if (sToast != null)
        {
            sToast.cancel();
            sToast = null;
        }
    }

    private static void show(@StringRes final int resId, final int duration)
    {
        show(App.getInstance()
                .getResources()
                .getText(resId)
                .toString(), duration);
    }

    private static void show(@StringRes final int resId, final int duration, final Object... args)
    {
        show(String.format(App.getInstance()
                              .getResources()
                              .getString(resId), args), duration);
    }

    private static void show(final String format, final int duration, final Object... args)
    {
        show(String.format(format, args), duration);
    }

    private static void show(final CharSequence text, final int duration)
    {
        cancel();
        sToast = Toast.makeText(App.getInstance(), text, duration);
        // solve the font of toast
        TextView tvMessage = sToast.getView().findViewById(android.R.id.message);
        TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
        tvMessage.setTextColor(msgColor);
        setBgAndGravity();
        sToast.show();
    }

    private static void show(final View view, final int duration)
    {
        cancel();
        sToast = new Toast(App.getInstance());
        sToast.setView(view);
        sToast.setDuration(duration);
        setBgAndGravity();
        sToast.show();
    }

    private static void setBgAndGravity()
    {
        View toastView = sToast.getView();
        if (bgResource != -1)
        {
            toastView.setBackgroundResource(bgResource);
        }
        else if (bgColor != DEFAULT_COLOR)
        {
            Drawable background = toastView.getBackground();
            background.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
        }
        sToast.setGravity(gravity, xOffset, yOffset);
    }

    private static View getView(@LayoutRes final int layoutId)
    {
        if (sLayoutId == layoutId)
        {
            if (sViewWeakReference != null)
            {
                final View toastView = sViewWeakReference.get();
                if (toastView != null)
                {
                    return toastView;
                }
            }
        }
        LayoutInflater inflate = (LayoutInflater) App.getInstance()
                                                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View toastView = inflate.inflate(layoutId, null);
        sViewWeakReference = new WeakReference<>(toastView);
        sLayoutId = layoutId;
        return toastView;
    }
}