package com.library.common.base;

import com.library.common.IModel;
import com.library.common.integration.IRepositoryManager;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/31 10:57 <br>
 */
public class BaseModel implements IModel
{
    protected IRepositoryManager mRepositoryManager;//用于管理网络请求层,以及数据缓存层

    public BaseModel(IRepositoryManager repositoryManager)
    {
        this.mRepositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy()
    {
        mRepositoryManager = null;
    }
}
