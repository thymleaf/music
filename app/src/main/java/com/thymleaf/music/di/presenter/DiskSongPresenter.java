package com.thymleaf.music.di.presenter;

import com.thymleaf.music.di.contract.DiskSongContract;
import com.library.common.base.BasePresenter;
import com.library.common.di.scope.FragmentScope;

import javax.inject.Inject;

@FragmentScope
public class DiskSongPresenter extends BasePresenter<DiskSongContract.Model, DiskSongContract.View> {

    @Inject
    public DiskSongPresenter(DiskSongContract.Model model, DiskSongContract.View mView) {
        super(model, mView);

    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
