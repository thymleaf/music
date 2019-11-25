package com.library.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import timber.log.Timber;


public class BasePopupWindow extends PopupWindow implements PopupWindow.OnDismissListener
{
    private Context context;
    private View mContentView;
    private OnBasePopupWindowInitListener mListener;
    private boolean isOutsideTouch;
    private boolean isFocus;
    private Drawable mBackgroundDrawable;
    private int mAnimationStyle;
    private boolean isWidthWrap;
    private boolean isHeightWrap;
    private float alpha;

    private OnShowListener onShowListener;

    private BasePopupWindow(Builder builder)
    {
        context = builder.context;
        this.mContentView = builder.contentView;
        this.mListener = builder.listener;
        this.isOutsideTouch = builder.isOutsideTouch;
        this.isFocus = builder.isFocus;
        this.mBackgroundDrawable = builder.backgroundDrawable;
        this.mAnimationStyle = builder.animationStyle;
        this.isWidthWrap = builder.isWidthWrap;
        this.isHeightWrap = builder.isHeightWrap;
        this.alpha = builder.alpha;
        this.onShowListener = builder.onShowListener;
        initLayout();
    }

    public static Builder builder(Context context)
    {
        return new Builder(context);
    }

    private void initLayout()
    {
        if (mListener != null)
        {
            mListener.onPopupInitViewListener(mContentView, this);
        }
        setWidth(isWidthWrap ? LayoutParams.WRAP_CONTENT : LayoutParams.MATCH_PARENT);
        setHeight(isHeightWrap ? LayoutParams.WRAP_CONTENT : LayoutParams.MATCH_PARENT);
        setFocusable(isFocus);
        setOutsideTouchable(isOutsideTouch);
        setBackgroundDrawable(mBackgroundDrawable);
        if (mAnimationStyle != -1)//如果设置了动画则使用动画
        {
            setAnimationStyle(mAnimationStyle);
        }
        setContentView(mContentView);
        setOnDismissListener(this);
    }

    /**
     * 获得用于展示popup内容的view
     *
     * @return
     */
    public View getContentView()
    {
        return mContentView;
    }

    private void setAlpha(float alpha)
    {
        Window mWindow = ((Activity) context).getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        Timber.d("alpha: "+params.alpha + "");
        params.alpha = alpha;
        Timber.d("alpha: "+params.alpha + "");
        mWindow.setAttributes(params);
    }

    /**
     * 用于填充contentView,必须传ContextThemeWrapper(比如activity)不然popupwindow要报错
     *
     * @param context
     * @param layoutId
     * @return
     */
    public static View inflateView(ContextThemeWrapper context, int layoutId)
    {
        return LayoutInflater.from(context)
                .inflate(layoutId, null);
    }

    public void show()
    {
        //默认显示到中间
        showAtLocation(mContentView, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        Timber.d("show");
        setAlpha(alpha);
        if (onShowListener != null)
        {
            onShowListener.onShowListener();
        }
    }

    public void showBottom()
    {
        showAtLocation(mContentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        setAlpha(alpha);
        if (onShowListener != null)
        {
            onShowListener.onShowListener();
        }
    }


    public void show(View parent, int gravity, int x, int y)
    {
        setAlpha(alpha);
        showAtLocation(parent, gravity, x, y);
        if (onShowListener != null)
        {
            onShowListener.onShowListener();
        }
    }

    @Override
    public void onDismiss()
    {
        Timber.d("on dismiss");
        setAlpha(1f);
        if (onShowListener != null)
        {
            onShowListener.onDismissListener();
        }
    }


    public static final class Builder
    {
        private Context context;
        private View contentView;
        private OnBasePopupWindowInitListener listener;
        private OnShowListener onShowListener;
        private boolean isOutsideTouch = true;//默认为true
        private boolean isFocus = true;//默认为true
        private Drawable backgroundDrawable = new ColorDrawable(0x00000000);//默认为透明
        private int animationStyle = -1;
        private float alpha = 0.3f;
        private boolean isWidthWrap = false;
        private boolean isHeightWrap = false;

        private Builder(Context context)
        {
            this.context = context;
        }

        public Builder contentView(View contentView)
        {
            this.contentView = contentView;
            return this;
        }

        public Builder setOnShowListener(OnShowListener onShowListener)
        {
            this.onShowListener = onShowListener;
            return this;
        }

        public Builder isWidthWrap(boolean isWidthWrap)
        {
            this.isWidthWrap = isWidthWrap;
            return this;
        }

        public Builder isHeightWrap(boolean isHeightWrap)
        {
            this.isHeightWrap = isHeightWrap;
            return this;
        }


        public Builder setAlpha(float alpha)
        {
            this.alpha = alpha;
            return this;
        }

        public Builder setOnBasePopupWindowInitListener(OnBasePopupWindowInitListener listener)
        {
            this.listener = listener;
            return this;
        }


        public Builder isOutsideTouch(boolean isOutsideTouch)
        {
            this.isOutsideTouch = isOutsideTouch;
            return this;
        }

        public Builder isFocus(boolean isFocus)
        {
            this.isFocus = isFocus;
            return this;
        }

        public Builder backgroundDrawable(Drawable backgroundDrawable)
        {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder animationStyle(int animationStyle)
        {
            this.animationStyle = animationStyle;
            return this;
        }

        public BasePopupWindow build()
        {
            if (contentView == null)
            {
                throw new IllegalStateException("contentView is required");
            }
            return new BasePopupWindow(this);
        }
    }

    public interface OnBasePopupWindowInitListener
    {
        void onPopupInitViewListener(View contentView, PopupWindow popupWindow);
    }

    public interface OnShowListener
    {
        void onShowListener();

        void onDismissListener();
    }
}
