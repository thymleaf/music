package com.library.common.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;

import com.library.common.IActivity;
import com.library.common.IApp;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/25 10:47 <br>
 */

public class ActivityDelegateImpl implements ActivityDelegate
{
    private Activity mActivity;
    private IActivity iActivity;

    public ActivityDelegateImpl(Activity activity)
    {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        iActivity.setupActivityComponent(
                ((IApp) mActivity.getApplication()).getAppComponent());//依赖注入
    }

    @Override
    public void onStart()
    {

    }

    @Override
    public void onResume()
    {

    }

    @Override
    public void onPause()
    {

    }

    @Override
    public void onStop()
    {

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {

    }

    @Override
    public void onDestroy()
    {
        this.iActivity = null;
        this.mActivity = null;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }

    protected ActivityDelegateImpl(Parcel in)
    {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
    }

    public static final Creator<ActivityDelegateImpl> CREATOR = new Creator<ActivityDelegateImpl>()
    {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source)
        {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size)
        {
            return new ActivityDelegateImpl[size];
        }
    };
}
