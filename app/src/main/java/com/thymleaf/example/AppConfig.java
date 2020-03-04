package com.thymleaf.example;

import com.library.common.LibraryConfig;

/**
 * App Config
 */
public class AppConfig
{

    public static final boolean DEBUG = LibraryConfig.DEBUG;
    private static final boolean BETA = LibraryConfig.BATE;

    private static final String APP_DOMAIN_DEBUG = "https://10.1.1.19";
//    private static final String APP_DOMAIN_DEBUG = "http://10.1.1.85:10667/";
    private static final String APP_DOMAIN_RELEASE = "https://api.qiandw.com/";
    private static final String APP_DOMAIN_BETA = "https://testapi.qiandw.com/";
    public static final String APP_DOMAIN = DEBUG ? BETA ? APP_DOMAIN_BETA : APP_DOMAIN_DEBUG : APP_DOMAIN_RELEASE;

}
