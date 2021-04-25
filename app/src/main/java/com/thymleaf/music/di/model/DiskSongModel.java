package com.thymleaf.music.di.model;

import com.library.common.base.BaseModel;
import com.library.common.di.scope.FragmentScope;
import com.library.common.integration.IRepositoryManager;
import com.thymleaf.music.di.contract.DiskSongContract;

import javax.inject.Inject;

@FragmentScope
public class DiskSongModel extends BaseModel implements DiskSongContract.Model {

    @Inject
    public DiskSongModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

}
