package com.thymleaf.example.base;

import android.content.Context;
import android.os.Bundle;

import com.library.common.IView;
import com.library.common.integration.IRepositoryManager;
import com.thymleaf.example.App;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 简化非MVP模式业务逻辑 <br>
 *
 * @see BaseActivity
 */
public abstract class BaseSimpleActivity extends BaseActivity implements IView
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
            mRepository = App.getInstance().getRepositoryManage();
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
    protected void onDestroy()
    {
        super.onDestroy();
        unSubscribe();
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
        return this;
    }

    protected Bundle getBundle()
    {
        return getIntent().getExtras();
    }

}
