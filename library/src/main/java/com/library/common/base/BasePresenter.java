package com.library.common.base;

import com.library.common.IModel;
import com.library.common.IPresenter;
import com.library.common.IView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/28 13:57 <br>
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter
{
    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mView;


    public BasePresenter(M model, V mView)
    {
        this.mModel = model;
        this.mView = mView;
//        attachView();
    }

    @Override
    public void attachView()
    {

    }

    @Override
    public void detachView()
    {
        if (mModel != null)
        {
            mModel.onDestroy();
        }
        unSubscribe();//解除订阅
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }


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
//        mCompositeDisposable.add(disposable);//将所有disposable放入,集中处理
    }

    protected void unSubscribe()
    {
        if (mCompositeDisposable != null)
        {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }

}
