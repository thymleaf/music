package com.thymleaf.example.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.library.common.IPresenter;
import com.library.common.base.delegate.IFragment;
import com.library.common.di.component.AppComponent;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment
{
    protected Activity activity;

    @Inject
    protected P mPresenter;


    public BaseFragment()
    {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return setContentLayout(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mPresenter != null)
        {
            mPresenter.detachView();//释放资源
        }
        this.mPresenter = null;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {

    }

}
