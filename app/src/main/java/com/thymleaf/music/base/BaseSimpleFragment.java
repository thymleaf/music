package com.thymleaf.music.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.library.common.IView;
import com.library.common.integration.IRepositoryManager;
import com.thymleaf.music.App;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 简化非MVP模式业务逻辑 <br>
 *
 * @see BaseFragment
 */

public abstract class BaseSimpleFragment extends BaseFragment implements IView
{

    private IRepositoryManager mRepository;
    private CompositeDisposable mCompositeDisposable;

    protected void addSubscribe(Disposable... disposables)
    {
        if (mCompositeDisposable == null)
        {
            mCompositeDisposable = new CompositeDisposable();
        }
        for (Disposable disposable : disposables)
        {
            mCompositeDisposable.add(disposable);
        }
}

    public IRepositoryManager getRepository()
    {
        if (mRepository == null)
        {
            mRepository = App.getInstance()
                             .getRepositoryManage();
        }
        return mRepository;
    }

    protected void unSubscribe()
    {
        if (mCompositeDisposable != null)
        {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
        mCompositeDisposable = null;
    }

    @Override
    public void onDestroy()
    {
        unSubscribe();
        super.onDestroy();
    }


    /**
     * 效果需要子类自己实现
     */
    @Override
    public void showLoading()
    {
    }

    @Override
    public void showContent()
    {
    }

    @Override
    public void showNoNetwork()
    {
    }

    @Override
    public void showError(String msg)
    {
    }

    public void showEmpty()
    {
    }

    @Override
    public Context getHostContext()
    {
        return getActivity();
    }


    /**
     * 通过Class跳转界面
     **/
    public void startTarget(Class<?> cls)
    {
        startTarget(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startTargetForResult(Class<?> cls, int requestCode)
    {
        startTargetForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startTargetForResult(Class<?> cls, Bundle bundle, int requestCode)
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
    }

    public void startTarget(Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
    }
}
