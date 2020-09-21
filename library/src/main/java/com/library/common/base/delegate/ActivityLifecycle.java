package com.library.common.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.library.common.IActivity;
import com.library.common.integration.AppManager;
import com.library.common.integration.ConfigModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import timber.log.Timber;


/**
 * Description: 全局监听Activity生命周期 <br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/24 17:18 <br>
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks
{
    private AppManager mAppManager;
    private Application mApplication;
    private Map<String, Object> mExtras;
    private FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycle;
    private List<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle(AppManager appManager, Application application,
                             Map<String, Object> extras)
    {
        this.mAppManager = appManager;
        this.mApplication = application;
        this.mExtras = extras;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        // 如果intent包含了此字段,并且为true说明不加入到list
        // 默认为true,如果不需要管理(比如不需要在退出所有activity(killAll)时，
        // 退出此activity就在intent加此字段为false)
        Timber.w(activity + " - onActivityCreated");
        boolean isAdd = false;
        if (activity.getIntent() != null)
        {
            isAdd = activity.getIntent()
                    .getBooleanExtra(AppManager.IS_ADD_ACTIVITY_MANAGE, true);
        }

        if (isAdd)
        {
            mAppManager.addActivity(activity);
        }

        //配置ActivityDelegate
        if (activity instanceof IActivity && activity.getIntent() != null)
        {
            ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
            if (activityDelegate == null)
            {
                activityDelegate = new ActivityDelegateImpl(activity);
                activity.getIntent()
                        .putExtra(ActivityDelegate.ACTIVITY_DELEGATE, activityDelegate);
            }
            activityDelegate.onCreate(savedInstanceState);
        }

        /**
         * 给每个Activity配置Fragment的监听,Activity可以通过 {@link IActivity#useFragment()}
         * 设置是否使用监听，如果这个Activity返回false的话,这个Activity将不能使用
         * {@link FragmentDelegate},意味着 {@link com.library.common.base.BaseFragment}也不能使用
         */
        boolean useFragment = activity instanceof IActivity ? ((IActivity) activity).useFragment() : true;
        if (activity instanceof FragmentActivity && useFragment)
        {
            /**
             * Description: FragmentLifecycle  & GlobalConfiguration.injectFragmentLifecycle <br>
             * FragmentLifecycle 中的方法优先注册优先执行，在需要在Fragment注入操作时，应该视两者执行顺序而定
             */
            if (mFragmentLifecycle == null)
            {
                mFragmentLifecycle = new FragmentLifecycle();
            }

            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(mFragmentLifecycle, true);

            if (mFragmentLifecycles == null)
            {
                mFragmentLifecycles = new ArrayList<>();
                List<ConfigModule> modules = (List<ConfigModule>) mExtras.get(ConfigModule.class.getName());
                for (ConfigModule module : modules)
                {
                    module.injectFragmentLifecycle(mApplication, mFragmentLifecycles);
                }
            }

            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles)
            {
                ((FragmentActivity) activity).getSupportFragmentManager()
                        .registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }

        }

    }

    @Override
    public void onActivityStarted(Activity activity)
    {
        Timber.w(activity + " - onActivityStarted");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null)
        {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        Timber.w(activity + " - onActivityResumed");
        mAppManager.setCurrentActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null)
        {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
        Timber.w(activity + " - onActivityPaused");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null)
        {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        Timber.w(activity + " - onActivityStopped");
        if (mAppManager.getCurrentActivity() == activity)
        {
            mAppManager.setCurrentActivity(null);
        }

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null)
        {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {
        Timber.w(activity + " - onActivitySaveInstanceState");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null)
        {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        Timber.w(activity + " - onActivityDestroyed");
        mAppManager.removeActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null)
        {
            activityDelegate.onDestroy();
            activity.getIntent()
                    .removeExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity)
    {
        ActivityDelegate activityDelegate = null;
        if (activity instanceof IActivity && activity.getIntent() != null)
        {
            activityDelegate = activity.getIntent()
                    .getParcelableExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
        return activityDelegate;
    }


    /**
     * Description: 全局监控Fragment生命周期 <br>
     * <p>
     * Creator: thymejoe@gmail.com <br>
     * Date: 2017/7/25 10:55 <br>
     */
    static class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks
    {

        @Override
        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context)
        {
            super.onFragmentAttached(fm, f, context);

            if (f.getArguments() == null)
            {
                f.setArguments(new Bundle());
            }
            if (f instanceof IFragment )
            {
                FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
                if (fragmentDelegate == null || !fragmentDelegate.isAdded())
                {
                    fragmentDelegate = new FragmentDelegateImpl(fm, f);
                    f.getArguments()
                            .putParcelable(FragmentDelegate.FRAGMENT_DELEGATE, fragmentDelegate);
                }
                fragmentDelegate.onAttach(context);
            }
        }

        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState)
        {
            super.onFragmentCreated(fm, f, savedInstanceState);
            Timber.w(f.toString() + " - onFragmentCreated");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onCreate(savedInstanceState);
            }
        }

        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v,
                                          Bundle savedInstanceState)
        {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            Timber.w(f.toString() + " - onFragmentViewCreated");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onCreateView(v, savedInstanceState);
            }
        }

        @Override
        public void onFragmentActivityCreated(FragmentManager fm, Fragment f,
                                              Bundle savedInstanceState)
        {
            super.onFragmentActivityCreated(fm, f, savedInstanceState);
            Timber.w(f.toString() + " - onFragmentActivityCreated");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onActivityCreate(savedInstanceState);
            }
        }

        @Override
        public void onFragmentStarted(FragmentManager fm, Fragment f)
        {
            super.onFragmentStarted(fm, f);
            Timber.w(f.toString() + " - onFragmentStarted");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onStart();
            }
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f)
        {
            super.onFragmentResumed(fm, f);
            Timber.w(f.toString() + " - onFragmentResumed");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onResume();
            }
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f)
        {
            super.onFragmentPaused(fm, f);
            Timber.w(f.toString() + " - onFragmentPaused");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onPause();
            }
        }

        @Override
        public void onFragmentStopped(FragmentManager fm, Fragment f)
        {
            super.onFragmentStopped(fm, f);
            Timber.w(f.toString() + " - onFragmentStopped");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onStop();
            }
        }

        @Override
        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f)
        {
            super.onFragmentViewDestroyed(fm, f);
            Timber.w(f.toString() + " - onFragmentViewDestroyed");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onDestroyView();
            }
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f)
        {
            super.onFragmentDestroyed(fm, f);
            Timber.w(f.toString() + " - onFragmentDestroyed");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onDestroy();
            }
        }

        @Override
        public void onFragmentDetached(FragmentManager fm, Fragment f)
        {
            super.onFragmentDetached(fm, f);
            Timber.w(f.toString() + " - onFragmentDetached");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null)
            {
                fragmentDelegate.onDetach();
                f.getArguments()
                        .clear();
            }
        }

        private FragmentDelegate fetchFragmentDelegate(Fragment fragment)
        {
            if (fragment instanceof IFragment)
            {
                return fragment.getArguments() ==
                        null ? null : (FragmentDelegate) fragment.getArguments()
                        .getParcelable(FragmentDelegate.FRAGMENT_DELEGATE);
            }
            return null;
        }
    }

}
