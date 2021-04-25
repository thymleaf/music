package com.thymleaf.music.di.module;

import com.library.common.di.scope.FragmentScope;
import com.thymleaf.music.di.contract.DiskSongContract;
import com.thymleaf.music.di.model.DiskSongModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DiskSongModule {
    private final DiskSongContract.View view;

    public DiskSongModule(DiskSongContract.View view) {
        this.view = view;
    }


    @FragmentScope
    @Provides
    DiskSongContract.View provideView()
    {
        return this.view;
    }

    @FragmentScope
    @Provides
    DiskSongContract.Model provideModel(DiskSongModel model)
    {
        return model;
    }
}
