package com.library.common.base.delegate;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/24 17:59 <br>
 */

public interface ActivityDelegate extends Parcelable
{
    String ACTIVITY_DELEGATE = "activity_delegate";

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();
}
