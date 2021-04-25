package com.thymleaf.music.di.component;

import com.library.common.di.component.AppComponent;
import com.library.common.di.scope.FragmentScope;
import com.thymleaf.music.di.module.DiskSongModule;
import com.thymleaf.music.ui.MusicAlbumFragment;

import dagger.Component;

@FragmentScope
@Component(modules = DiskSongModule.class, dependencies = AppComponent.class)
public interface DiskSongComponent {

    void inject(MusicAlbumFragment fragment);
}
