package com.library.common.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;

import com.library.common.IApp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/25 11:01 <br>
 */
public class FragmentDelegateImpl implements FragmentDelegate
{
    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment iFragment;


    public FragmentDelegateImpl(FragmentManager fragmentManager,
                                Fragment fragment)
    {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
        this.iFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(Context context)
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        iFragment.setupFragmentComponent(((IApp) mFragment.getActivity()
                                                          .getApplication()).getAppComponent());
    }

    @Override
    public void onCreateView(View view, Bundle savedInstanceState)
    {

    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState)
    {
        iFragment.initFragment(savedInstanceState);
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
    public void onDestroyView()
    {
    }

    @Override
    public void onDestroy()
    {
        this.mFragmentManager = null;
        this.mFragment = null;
        this.iFragment = null;
    }

    @Override
    public void onDetach()
    {

    }

    /**
     * Return true if the fragment is currently added to its activity.
     */
    @Override
    public boolean isAdded()
    {
        return mFragment == null ? false : mFragment.isAdded();
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

    protected FragmentDelegateImpl(Parcel in)
    {
        this.mFragmentManager = in.readParcelable(FragmentManager.class.getClassLoader());
        this.mFragment = in.readParcelable(Fragment.class.getClassLoader());
        this.iFragment = in.readParcelable(IFragment.class.getClassLoader());
    }

    public static final Creator<FragmentDelegateImpl> CREATOR = new Creator<FragmentDelegateImpl>()
    {
        @Override
        public FragmentDelegateImpl createFromParcel(Parcel source)
        {
            return new FragmentDelegateImpl(source);
        }

        @Override
        public FragmentDelegateImpl[] newArray(int size)
        {
            return new FragmentDelegateImpl[size];
        }
    };
}
