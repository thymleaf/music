package com.thymleaf.music.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.library.common.IActivity;
import com.library.common.IPresenter;
import com.library.common.R;
import com.library.common.di.component.AppComponent;
import com.library.common.utils.KeyboardUtil;
import com.library.common.widget.swipebacklayout.SwipeBackHelper;

import javax.inject.Inject;

/**
 * Description: BaseActivity <br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/31 16:44 <br>
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity
        implements IActivity, SwipeBackHelper.Delegate
{
    protected SwipeBackHelper mSwipeBackHelper;

    @Inject
    protected P mPresenter;

    protected Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        /*try
        {
            int layoutResID = setContentLayout(savedInstanceState);
            if (layoutResID != 0)
            {
                //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
                setContentView(layoutResID);
                //绑定到butterknife
                unbinder = ButterKnife.bind(this);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        setContentView(setBindingView());

        context = this;

        if (mPresenter != null)
        {
            // FIXME: 2017/7/31 this function should be called in #onCreate
            mPresenter.attachView();
        }
//        setupWindowAnimations();
        initActivity(savedInstanceState);
    }

    /**
     * 重写该方法，如果子类需要初始化Activity，直接重写改方法，增加灵活性
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void initActivity(Bundle savedInstanceState)
    {
        setLightStatusBar();
    }

    private void setLightStatusBar()
    {
        if (setIsLightStatusBar())
        {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public boolean setIsLightStatusBar()
    {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setupWindowAnimations()
    {
        Slide slide = new Slide();
        slide.setDuration(2000);
        getWindow().setExitTransition(slide);

        Fade fade = new Fade();
        fade.setDuration(5000);
        getWindow().setEnterTransition(slide);
    }


    @Override
    protected void onDestroy()
    {
        if (mPresenter != null)
        {
            mPresenter.detachView();//释放资源
        }
        this.mPresenter = null;
        super.onDestroy();
    }

    /**
     * 使用MVP模式必须在子Activity中重写该方法，并注入component <br>
     * <p>
     * <pre>
     *         DaggerUserComponent.builder()
     *                            .appComponent(appComponent)
     *                            .userModule(new XXXModule(this))
     *                            .build()
     *                            .inject(this);
     * </pre>
     */
    @Override
    public void setupActivityComponent(AppComponent appComponent)
    {

    }

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册
     * {@link FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于
     * {@link BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    @Override
    public boolean useFragment()
    {
        return true;
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，
     * 如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack()
    {
        return false;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset)
    {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel()
    {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted()
    {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed()
    {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding())
        {
            return;
        }
        mSwipeBackHelper.backward();
        KeyboardUtil.closeKeyboard(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
    }

    public void startTargetWithFlags(Class<?> cls, int flags)
    {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.setFlags(flags);
        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish()
    {
        mSwipeBackHelper = new SwipeBackHelper(this, this);

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsSlideStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.shadow_swipe_back
        mSwipeBackHelper.setShadowResId(R.drawable.shadow_swipe_back);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }
}
