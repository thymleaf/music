package com.library.common.widget.swipebacklayout;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


class SwipeBackUtil
{
    static final Set<String> NO_NAVIGATION_BAR_MODEL_SET = new HashSet<>();

    static
    {
        NO_NAVIGATION_BAR_MODEL_SET.add("Nexus 4");
        NO_NAVIGATION_BAR_MODEL_SET.add("H60-L01");
        NO_NAVIGATION_BAR_MODEL_SET.add("P7-L07");
        NO_NAVIGATION_BAR_MODEL_SET.add("MT7-UL00");
    }

    private SwipeBackUtil()
    {
    }

    /**
     * 获取底部导航栏高度
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity)
    {
        int navigationBarHeight = 0;
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier(resources.getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape",
                "dimen", "android");
        if (resourceId > 0 && checkDeviceHasNavigationBar(activity))
        {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    /**
     * 检测是否具有底部导航栏
     *
     * @param activity
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Activity activity)
    {
        boolean hasNavigationBar = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            if (NO_NAVIGATION_BAR_MODEL_SET.contains(Build.MODEL))
            {
                hasNavigationBar = false;
            }
            else
            {
                hasNavigationBar = newCheckDeviceHasNavigationBar(activity);
            }
        }
        else
        {
            hasNavigationBar = oldCheckDeviceHasNavigationBar(activity);
        }
        return hasNavigationBar;
    }

    private static boolean oldCheckDeviceHasNavigationBar(Activity activity)
    {
        boolean hasNavigationBar = false;
        Resources resources = activity.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0)
        {
            hasNavigationBar = resources.getBoolean(id);
        }
        try
        {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride))
            {
                hasNavigationBar = false;
            }
            else if ("0".equals(navBarOverride))
            {
                hasNavigationBar = true;
            }
        }
        catch (Exception e)
        {
        }
        return hasNavigationBar;
    }

    private static boolean newCheckDeviceHasNavigationBar(Activity activity)
    {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            display.getRealMetrics(realDisplayMetrics);
        }
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }
}
