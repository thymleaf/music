package com.thymleaf.music.base;

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
import androidx.viewbinding.ViewBinding;

import org.jetbrains.annotations.NotNull;


public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment
{
    protected Activity activity;

    @Inject
    protected P mPresenter;

    private ViewBinding binding;


    public BaseFragment()
    {
        //必须确保在Fragment实例化时setArguments()
        Bundle bundle = getArguments();
        if (bundle == null)
        {
            bundle = new Bundle();
        }
        setArguments(bundle);
    }

    @Override
    public void onAttach(@NotNull Context context)
    {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = setViewBinding();
        return binding.getRoot();
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
        this.binding = null;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {

    }

}
