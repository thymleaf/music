package com.library.common.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/25 11:04 <br>
 */
public interface FragmentDelegate extends Parcelable
{

    String FRAGMENT_DELEGATE = "fragment_delegate";

    void onAttach(Context context);

    void onCreate(Bundle savedInstanceState);

    void onCreateView(View view, Bundle savedInstanceState);

    void onActivityCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroyView();

    void onDestroy();

    void onDetach();

    /**
     * Return true if the fragment is currently added to its activity.
     */
    boolean isAdded();
}
