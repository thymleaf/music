package com.thymleaf.example;

import android.Manifest;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.thymleaf.example.base.BaseSimpleActivity;
import com.thymleaf.example.test.HomeService;
import com.thymleaf.example.test.NewsList;

import androidx.annotation.NonNull;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import timber.log.Timber;



@RuntimePermissions
public class MainActivity extends BaseSimpleActivity
{

    @BindView(R.id.fab)
    FloatingActionButton fab;



    @Override
    public int setContentLayout(Bundle savedInstanceState)
    {
        return R.layout.activity_main;
    }

    @Override
    public void initActivity(Bundle savedInstanceState)
    {



        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        MainActivityPermissionsDispatcher.loadDataWithPermissionCheck(this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
