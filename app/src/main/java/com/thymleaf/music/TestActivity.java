package com.thymleaf.music;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.thymleaf.music.base.BaseSimpleActivity;
import com.thymleaf.music.test.HomeService;
import com.thymleaf.music.test.NewsList;

import androidx.annotation.NonNull;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import timber.log.Timber;



@RuntimePermissions
public class TestActivity extends BaseSimpleActivity
{


    @Override
    public int setContentLayout(Bundle savedInstanceState)
    {
//        return R.layout.activity_main;
        return 0;
    }

    @Override
    public void initActivity(Bundle savedInstanceState)
    {


//        MainActivityPermissionsDispatcher.loadDataWithPermissionCheck(this);
    }



    @NeedsPermission({Manifest.permission.INTERNET,
    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS})
    void loadData()
    {
        loadRepository(getRepository().obtainRetrofitService(HomeService.class)
                               .getNews("1", String.valueOf(1), "100"),
                       new OnSubscriberListener<NewsList>()
                       {
                           @Override
                           public void onSubscribe(NewsList newsList)
                           {
                               Timber.e(newsList.toString());
                               

                           }
                       });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }
}