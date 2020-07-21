package com.longhoo.net.utils;

import com.longhoo.net.MyApplication;

/**
 * Created by CK on 2017/7/6.
 */
public class Consts {
    public static String BASE_DOMAIN = "";
    public static String BASE_URL = "";
    public static final String TAG = "cc";
    public static final int REQUEST_REFRESH = 0;
    public static final int REQUEST_LOADMORE = 1;
    public static final int REQUEST_METHOD_GET = 100;
    public static final int REQUEST_METHOD_POST = 101;
    public static final String IS_FIRST_IN_APP = "is_first_in_app";
    public static final String SP_LOGIN_BEAN = "sp_login_bean";
    public static final String SP_JPUSH_ALIAS_REGISTED = "sp_jpush_alias_registed";
    public static final String SD_ROOT = Utils.getRoot("/partybuilding/");
    public static final String SD_ROOT_OLD = Utils.getRoot("/partybuilding/");
    public static String SP_PHONE= "sp_phone";
    public static String SP_PWD= "sp_pwd";
    public static String SP_ISREMBERPWD= "sp_isremberpwd";
    public static final String SD_TEMPAPK = SD_ROOT + "temp.apk";

    /**
     * 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY
     */

    public static final String SinaAPP_KEY = "2058435861";
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响， 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String SINAREDIRECT_URL = "http://open.weibo.com";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
     * <p>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的 使用权限，高级权限需要进行申请。
     * <p>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
//    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
//            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
//            + "follow_app_official_microblog," + "invitation_write";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

//           SPTool.putString(LoginActivity.this, Consts.SP_LOGINTYPE, "2");
//                                        SPTool.putString(LoginActivity.this, Consts.SP_NICKNAME, strNickname);
//                                        SPTool.putString(LoginActivity.this, Consts.SP_UID, strOuid);
//                                        SPTool.putString(LoginActivity.this, Consts.SP_ATOKEN, strOpenid);
}
