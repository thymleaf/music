package com.library.common.widget.swipebacklayout;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;

import java.util.Collection;
import java.util.Stack;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

public class SwipeBackManager implements Application.ActivityLifecycleCallbacks
{
    private static final SwipeBackManager sInstance = new SwipeBackManager();
    private Stack<Activity> mActivityStack = new Stack<>();

    public static SwipeBackManager getInstance()
    {
        return sInstance;
    }

    private SwipeBackManager()
    {
    }

    /**
     * 必须在 Application 的 onCreate 方法中调用
     */
    public void init(Application application)
    {
        application.registerActivityLifecycleCallbacks(this);
    }

    /**
     * 忽略底部出现空白区域的手机对应的 android.Build.MODEL
     *
     * @param models
     */
    public static void ignoreNavigationBarModels(Collection<String> models)
    {
        if (models != null && models.size() > 0)
        {
            SwipeBackUtil.NO_NAVIGATION_BAR_MODEL_SET.addAll(models);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        mActivityStack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity)
    {
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        mActivityStack.remove(activity);
    }

    /**
     * 获取倒数第二个 Activity
     *
     * @return
     */
    @Nullable
    public Activity getPenultimateActivity()
    {
        Activity activity = null;
        try
        {
            if (mActivityStack.size() > 1)
            {
                activity = mActivityStack.get(mActivityStack.size() - 2);
            }
        }
        catch (Exception e)
        {
        }
        return activity;
    }

    public static void onPanelSlide(float slideOffset)
    {
        try
        {
            Activity activity = getInstance().getPenultimateActivity();
            if (activity != null)
            {
                View decorView = activity.getWindow()
                                         .getDecorView();
                ViewCompat.setTranslationX(decorView,
                        -(decorView.getMeasuredWidth() / 3.0f) * (1 - slideOffset));
            }
        }
        catch (Exception e)
        {
        }
    }

    public static void onPanelClosed()
    {
        try
        {
            Activity activity = getInstance().getPenultimateActivity();
            if (activity != null)
            {
                View decorView = activity.getWindow()
                                         .getDecorView();
                decorView.setTranslationX(0);
            }
        }
        catch (Exception e)
        {
        }
    }
}