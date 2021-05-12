package com.thymleaf.music;

import com.library.common.LibraryConfig;

/**
 * App Config
 */
public class AppConfig
{

    public static final boolean DEBUG = LibraryConfig.DEBUG;
    private static final boolean BETA = LibraryConfig.BATE;

    private static final String APP_DOMAIN_DEBUG = "https://autumnfish.cn/";
    private static final String APP_DOMAIN_RELEASE = "https://autumnfish.cn/";
    private static final String APP_DOMAIN_BETA = "https://testapi..../";
    public static final String APP_DOMAIN = DEBUG ? BETA ? APP_DOMAIN_BETA : APP_DOMAIN_DEBUG : APP_DOMAIN_RELEASE;

}
