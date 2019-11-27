package com.thymleaf.example;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/8/18 11:29 <br>
 */

public final class Constants
{
    public static final String IS_FIRST_LAUNCHED = "is_first_launched";

    public static final String KEY_TITLE = "key_title";

    public static final String ENABLE_NOTIFICATION_CODE = "key_notification_code";

    public static final String KEY_TOKEN = "key_token";

    public static final String KEY_IS_ON_GESTURE = "is_on_gesture";

    public static final String KEY_QDW_ID = "key_qdw_id";

    public static final String KEY_APP_MSG = "key_app_msg";

    public static final String KEY_APP_HANDLED_MSG = "key_app_handled_msg";

    public static final String KEY_QDW_USER_OBJ = "key_qdw_user_obj";

    public static final String KEY_TELEPHONE = "key_telephone";

    public static final String KEY_VCODE = "key_vcode";

    public static final String KEY_PROJECT_ID = "key_project_id";

    public static final String KEY_PROJECT_OBJ = "key_project_obj";

    public static final String KEY_TRANSLATION_RESPONSE = "key_translation_response";

    public static final String KEY_INVESTMENT_AMOUNT = "key_invest_amount";

    public static final String KEY_AMOUNT = "key_amount";

    public static final String KEY_RULE_OBJ = "key_rule_obj";

    public static final String KEY_RULE_MODE = "key_rule_mode";

    public static final String KEY_USER_PHONE = "key_user_phone";

    public static final String KEY_USER_EMAIL = "key_user_email";

    public static final String KEY_AD_EVENT_OBJ = "key_ad_event_obj";

    public static final String KEY_USER_URGENTPERSON = "key_user_urgentperson";

    public static final String KEY_USER_URGENTMOBILE = "key_user_mobile";

    public static final String KEY_INVESTMENT_RATE = "key_investment_rate";

    public static final String KEY_INVESTMENT_TERM = "key_investment_term";

    public static final String KEY_TARGET_TYPE = "key_target_type";

    public static final String VOICE_CAPTCHA = "95213176";

    public static final String KEY_PROJECT_STATUS = "key_project_status";

    public static final String KEY_ORDER_ID = "key_order_id";

    public static final String KEY_WITHDRAW_ORDER_OBJ = "key_withdraw_order_obj";

    public static final String KEY_EXCHANGE_OBJ = "key_exchange_obj";

    public static final String KEY_ENTRANCE_TYPE = "key_entrance_type";

    public static final String WEB_VIEW_URL = "web_view_url";

    public static final String WEB_IS_ADD_TOKEN = "web_is_add_token";

    public static final String KEY_WEB_CAN_GO_BACK = "key_web_can_go_back";

    public static final String WEB_VIEW_TITLE = "web_view_title";

    public static final String KEY_DESC = "key_desc";

    public static final String DEPOSITORY_INFO_OJB = "depository_info_obj";

    public static final String KEY_BANK_OBJ = "key_bank_obj";

    public static final String XW_SHOULD_OVERRIDE_URL = "XwBank/XW_CALLBACK_EXPAND";

    public static final String KEY_ORIGINAL_ID = "key_original_id";//原始标id用于查看原始标

    public static final String KEY_TRANSLATION_PROJNAME = "key_translation_projname";

    public static final String KEY_PROJECT_VACCT = "key_project_vacct";//虚户

    public static final String KEY_SIGN_SUCCESS_FLAG = "key_sign_success_flag";

    public static final String KEY_UPDATE_DEPOSITORY_FLAG = "UPDATE_DEPOSITORY";

    public static final String KEY_UPDATE_BANK_CARD_FLAG = "key_update_bank_card";

    public static final String KEY_PROGRAM_ID = "key_program_id";

    public static final String KEY_WECHAT = "key_wechat";

    public static final String KEY_PROJECT_PHOTO = "key_project_photo";

    public static final String KEY_PDF_URL = "key_pdf_url";

    public static final String KEY_RECHARGE_FLAG = "key_recharge_flag";

    public static final String KEY_RECHARGE_ERROR = "key_recharge_error";

    public static final String KEY_UPDATE_URL = AppConfig.APP_DOMAIN + "update_version";

    public static final String APPOINTMENT_INFO = "appointment_info";

    public static final String APPOINTMENT_INVEST_MONEY = "appointment_invest_money";

    public static final String KEY_APP_AD_URL = "app_ad_url";

    public static final String KEY_AD_OBJ = "app_ad_obj";

    public static final String KEY_EXTERNAL_AD_URL = "external_ad_url";

    public static final String ACTION_NOTIFICATION_CANCEL = "com.qiandw.notification_cancelled";

    public static final String KEY_NOTICE_PERSONAL_DATAIL = "key_notice_personal_datail";

    public static final String KEY_HOME_NOTICE_ID = "key_home_notice_id";

    public static final String KEY_HOME_NOTICE_SHOW = "key_home_notice_show";

    public static final String KEY_HOME_MSG_POP_SHOW = "key_home_msg_pop_show";

    public static final String KEY_TASK_INIT_TIME = "key_task_init_time";

    public static final String KEY_CONTACTS_UPLOAD_TIME = "key_contacts_upload_time";

    public static final String KEY_APP_UPLOAD_TIME = "key_app_upload_time";

    public static final String KEY_GUIDE_NEWS = "key_guide_news";

    public static final String KEY_REPAYMENT_PROGRESS = "key_repayment_progress";

    public static final String KEY_TYPE = "key_type";

    public static final String KEY_TYPE_1 = "key_type_1";

    public static final String NEWS_INFO = "news_info";

    public static final String KEY_XSB_ID = "key_xsb_id";

    public static final String KEY_LOGIN_BACK_MAIN = "key_login_back_main";

    public static final String NEWS_INFO_POSITION = "news_info_position";

    public static final int NOTIFICATION_ID = 1;

    public static final int JPUSH_SEQUENCE_ID = 1000;

    /**
     * 我的福利
     */
    public final class Welfare
    {
        /**
         *
         */
        public static final String CASH_PRIZE = "CASH_PRIZE";
        /**
         * 可使用
         */
        public static final int TIME_AVAILABLE = 1;
        /**
         * 已使用
         */
        public static final int TIME_ALREADYUSED = 2;

        /**
         * 即将到期
         */
        public static final int TIME_SOONTOEXPIRED = 2;

        /**
         * 已过期
         */
        public static final int TIME_ALREADYEXPIRED = 4;

        /**
         * 现金红包
         */
        public static final int TYPE_CASHCOUPON = 1;
        /**
         * 抵扣券
         */
        public static final int TYPE_VOUCHER = 2;
        /**
         * 体验金
         */
        public static final int TYPE_EXPRIENCE_CASH = 3;

        /**
         * 增利券
         */
        public static final int TYPE_HIKE_COUPON = 4;

//        LCB 散标，QJH 精选自助投，EXP 体验宝，YCB 省心自助投
        public static final String TARGET_TYPE_LCB = "LCB";
        public static final String TARGET_TYPE_QJH = "QJH";
        public static final String TARGET_TYPE_EXP = "EXP";
        public static final String TARGET_TYPE_YCB = "YCB";

        /**
         * 选择福利-id
         */
        public static final String COUPON_SELECTED_IDS = "COUPON_SELECTED_IDS";

        /**
         * 选择福利-金额
         */
        public static final String COUPON_SELECTED_AMOUNT = "COUPON_SELECTED_AMOUNT";

        /**
         * 选择福利-类型
         */
        public static final String COUPON_SELECTED_TYPE = "COUPON_SELECTED_TYPE";

        /**
         * 选择福利-加息券利率
         */
        public static final String COUPON_SELECTED_RATE = "COUPON_SELECTED_RATE";
    }


    public static final String WE_CHET_APP_ID = "wxfe26ff24fb12b395";

    public final static int TIME_DOWN_SECONDS = 60; //倒计时常量

    //            0  :首页 1  :登录 2  :我的页面 3  :我的任务 4  :我的日历 5  :我的福利
//            6  :我的邀请 7  :签到有礼 8  :我要借款 9  :项目 - 散标区 10  :项目 - 精选自助投
//            11 :开通银行存管 12 :激活银行存管 13 :风险测评 14：体验宝 15 新手专享 16：资讯 17 发现
//            100:关闭 webview 101:邀请好友弹窗 102:打开小程序
    public final class Web {
        public static final int VIEW_HOME = 0;
        public static final int VIEW_LOGIN = 1;
        public static final int VIEW_MINE = 2;
        public static final int VIEW_TASK = 3;
        public static final int VIEW_CALENDAR = 4;
        public static final int VIEW_WELFARE = 5;
        public static final int VIEW_INVEST_DIRECT= 9;
        public static final int VIEW_INVEST_PLAN = 10;
        public static final int VIEW_OPEN_DEPOSITORY = 11;
        public static final int VIEW_ACTIVE_DEPOSITORY = 12;
        public static final int VIEW_VENTURE = 13;
        public static final int VIEW_TYB = 14;
        public static final int VIEW_XSB= 15;
        public static final int VIEW_NEWS= 16;
        public static final int VIEW_EVENT= 17;
        public static final int VIEW_FINISH_WEBVIEW = 100;
        public static final int VIEW_SHARE_POP = 101;
        public static final int VIEW_MINI_PROGRAM = 102;
        public static final int VIEW_CALL = 103;
    }
}
