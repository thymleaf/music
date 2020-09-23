package com.thymleaf.example.util;

import android.content.Context;
import android.text.TextUtils;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.library.common.IView;
import com.library.common.utils.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;


/**
 * Description: 统一视图错误处理 <br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/11/13 16:09 <br>
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T>
{
    private Context context;
    private IView mView;
    private KProgressHUD progressHUD;

    public CommonSubscriber(IView view)
    {
        this.mView = view;
        this.context = view.getHostContext();
        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setAnimationSpeed(2);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (isShowProgress() && progressHUD != null && !progressHUD.isShowing())
        {
            progressHUD.show();
        }
    }

    @Override
    public void onComplete()
    {
        if (isShowProgress() && progressHUD != null && progressHUD.isShowing())
        {
            progressHUD.dismiss();
        }
    }

    @Override
    public void onError(Throwable e)
    {
        if (e instanceof ApiException)
        {
            if (((ApiException) e).isUnauthorized())
            {
                onUnauthorized();
            }
            else
            {
                mView.showError(e.getMessage());
            }
        }
        else if (e instanceof ConnectException)
        {
            if (!NetworkUtils.isConnected())
            {
                if (mView != null)
                {
                    mView.showNoNetwork();
                }
            }
            else
            {
                onConnectionError();
            }
        }
        else if (e instanceof TimeoutException || e instanceof SocketTimeoutException)
        {
            onTimeOutError();
        }
        else if (e instanceof UnknownHostException)
        {
            if (!NetworkUtils.isConnected())
            {
                if (mView != null)
                {
                    mView.showNoNetwork();
                }
            }
            else
            {
                onUnKnownHostError();
            }
        }
        else if (e instanceof HttpException)
        {
            if (mView != null)
            {
                mView.showError(e.getMessage());
            }
        }
        else
        {
            if (e instanceof NullPointerException)
            {
                return;
            }
            mView.showError("未知错误");
            onUnknownError(TextUtils.isEmpty(e.getMessage()) ? "未知错误" : e.getMessage());
        }

        if (isShowProgress() && progressHUD != null && progressHUD.isShowing())
        {
            progressHUD.dismiss();
        }
    }

    public boolean isShowProgress()
    {
        return true;
    }

    /**
     * 默认是弹窗提示用户登录
     */
    public void onUnauthorized()
    {
        if (context != null)
        {
            showLoginDialog();
        }
    }

    public void onConnectionError()
    {
    }

    public void onUnKnownHostError()
    {
        ToastUtil.showShortCenter("服务器连接异常，请稍后重试");
    }

    public void onUnknownError(String msg)
    {
        ToastUtil.showShortCenter(msg);
    }

    public void onTimeOutError()
    {
        ToastUtil.showShortCenter("网络连接超时，请稍后再试");
    }

    private void showLoginDialog()
    {
//        BaseDialog.builder(context).setTitle("登录过期").setMessage("登录已过期，请重新登录")
//                .setNegativeButton("取消")
//                .setPositiveButton("登录")
//                .setCancelable(false)
//                .setNegativeButtonListener(new BaseDialog.NegativeListener() {
//                    @Override
//                    public void onNegativeListener(View view)
//                    {
//                        AppUtil.clearToken(context);
//                    }
//                })
//                .setPositiveOnclickListener(new BaseDialog.PositiveListener() {
//                    @Override
//                    public void onPositiveListener(View view)
//                    {
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        context.startActivity(intent);
//                        ((Activity)context).finish();
//                    }
//                })
//                .create().show();
    }

}