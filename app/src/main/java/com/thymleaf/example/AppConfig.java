package com.thymleaf.example;

import com.library.common.LibraryConfig;

/**
 * App Config
 */
public class AppConfig
{

    public static final boolean DEBUG = LibraryConfig.DEBUG;
    private static final boolean BETA = LibraryConfig.BATE;

    private static final String APP_DOMAIN_DEBUG = "http://39.108.146.212:8899/renren-fast/";
    private static final String APP_DOMAIN_RELEASE = "https://api...";
    private static final String APP_DOMAIN_BETA = "https://testapi..../";
    public static final String APP_DOMAIN = DEBUG ? BETA ? APP_DOMAIN_BETA : APP_DOMAIN_DEBUG : APP_DOMAIN_RELEASE;

}
