package com.thymleaf.example;

import com.library.common.LibraryConfig;

/**
 * App Config
 */
public class AppConfig
{
    //准正式环境
    //https://116.62.38.223:86/
    //
    //https://testapi.qiandw.com/

    public static final boolean DEBUG = LibraryConfig.DEBUG;
    private static final boolean BETA = LibraryConfig.BATE;

    private static final String APP_DOMAIN_DEBUG = "https://10.1.1.19";
//    private static final String APP_DOMAIN_DEBUG = "http://10.1.1.85:10667/";
    private static final String APP_DOMAIN_RELEASE = "https://api.qiandw.com/";
    private static final String APP_DOMAIN_BETA = "https://testapi.qiandw.com/";
    public static final String APP_DOMAIN = DEBUG ? BETA ? APP_DOMAIN_BETA : APP_DOMAIN_DEBUG : APP_DOMAIN_RELEASE;

    /**
     *  WAP2.0
     */
    private static final String WAP_DOMAIN_DEBUG = "http://10.1.1.254:10202/";
    private static final String WAP_DOMAIN_RELEASE = "http://m.qiandw.com/";
    public static final String WAP_DOMAIN = DEBUG ? WAP_DOMAIN_DEBUG : WAP_DOMAIN_RELEASE;

    /**
     *  WAP3.0
     */
    private static final String WAP_NEW_DOMAIN_DEBUG = "https://10.1.1.254:10207/";
    public static final String WAP_NEW_DOMAIN_RELEASE = "https://m3.qiandw.com/";
    private static final String WAP_NEW_DOMAIN_BATE = "https://116.62.38.223:86/";
    public static final String WAP_NEW_DOMAIN = DEBUG ? BETA ? WAP_NEW_DOMAIN_BATE : WAP_NEW_DOMAIN_DEBUG : WAP_NEW_DOMAIN_RELEASE;

    /**
     * PC: http://www.qiandw.com/
     */
    private static final String PC_DOMAIN_DEBUG = "http://10.1.1.254:10901/";
    public static final String PC_DOMAIN_RELEASE = "http://old.qiandw.com/";
    public static final String PC_DOMAIN = DEBUG ? PC_DOMAIN_DEBUG : PC_DOMAIN_RELEASE;

    /**
     * PC4
     */
    private static final String PC_DOMAIN_4_DEBUG = "https://10.1.1.229/";
    public static final String PC_DOMAIN_4_RELEASE = "https://www.qiandw.com/";
    public static final String PC_DOMAIN_4 = DEBUG ? PC_DOMAIN_4_DEBUG : PC_DOMAIN_4_RELEASE;

    /**
     * 省心自助投协议地址
     */
    private static final String APPOINTMENT_BID_AGREEMENT_DEBUG = "http://10.1.1.241:9081/businessHandle/agreement";
    private static final String APPOINTMENT_BID_AGREEMENT_RELEASE = "http://ncrm.qiandw.com:9081/businessHandle/agreement";
    public static final String APPOINTMENT_BID_AGREEMENT_DOMAIN = DEBUG ? APPOINTMENT_BID_AGREEMENT_DEBUG : APPOINTMENT_BID_AGREEMENT_RELEASE;

//    /**
//     * 自助投补充协议地址
//     */
//    private static final String PC_DOMAIN_SUPPORT_DEBUG = "http://10.1.1.254:10901/";
//    private static final String PC_DOMAIN_SUPPORT_RELEASE = "http://old.qiandw.com/";
//    public static final String PC_DOMAIN_SUPPORT = DEBUG ? PC_DOMAIN_SUPPORT_DEBUG : PC_DOMAIN_SUPPORT_RELEASE;


    public static final int RequestSuccess = 200;

    public static final int RequestUnauthorized = 401;

    public static final int MOST_EARLY_YEAR = 2013;

    public static final int MOST_LEAST_YEAH = 2028;


    /**
     * 神策数据接收的 URL
     */
    public static final String SA_SERVER_URL_RELEASE = "https://shence.qiandw.com:4006/sa?project=production";
    public static final String SA_SERVER_URL_DEBUG = "https://shence.qiandw.com:4006/sa?project=default";

    /**
     * FaceId产品Api配置
     */
    public static String FACE_API_KEY = "UNQwYlDybUA__dbaE2B5ywuu9wqdG9cY";
    public static String FACE_API_SECRET = "fDw2uq3WvqoFm6IhIXa0inxgpeOtx7xT";

    public static String FACE_API_KEY_DEBUG = "WNKLX20a5LslrHhjKo78rRvSUOduRf6r";
    public static String FACE_API_SECRET_DEBUG = "PO_wipe-2wvGFZzUROdHHVN48hmR8XRv";

    public static String FACE_API_ORC_CARD_URL = "https://api.faceid.com/faceid/v3/";
}
