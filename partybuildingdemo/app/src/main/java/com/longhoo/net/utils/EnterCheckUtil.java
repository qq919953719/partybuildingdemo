package com.longhoo.net.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.longhoo.net.MyApplication;
import com.longhoo.net.mine.bean.LoginBean;
import com.longhoo.net.utils.jpush.TagAliasOperatorHelper;

import static com.longhoo.net.utils.jpush.TagAliasOperatorHelper.sequence;

/**
 * Created by CK on 2018/5/14.
 * Email:910663958@qq.com
 * 登录、角色判断
 */

public class EnterCheckUtil {
    public static EnterCheckUtil instance;
    private Context context;

    public static EnterCheckUtil getInstance() {
        if (instance == null) {
            instance = new EnterCheckUtil();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param app
     */
    public void init(Application app) {
        context = app.getApplicationContext();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin(boolean isNeedToLogin) {
        LoginBean loginBean = SPTool.getBeanFromSp(context, Consts.SP_LOGIN_BEAN);
        MyApplication app = (MyApplication) context;
        if (loginBean != null) {
            if (loginBean.getData() != null) {
                app.setmLoginBean(loginBean);
                if (!SPTool.getBoolean(context, Consts.SP_JPUSH_ALIAS_REGISTED, false)) {
                    saveLogin(loginBean);
                    ULog.e("设置别名");
                }
                return true;
            }
        }
        if (isNeedToLogin) {
            Intent intent = new Intent();
            intent.setAction("com.longhoo.net.login");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return false;
    }

    /**
     * 获取uid和token
     *
     * @return
     */
    public String[] getUid_Token() {
        String uid = "";
        String token = "";
        MyApplication app = (MyApplication) context;
        if (app.getmLoginBean() != null) {
            if (app.getmLoginBean().getData() != null) {
                uid = app.getmLoginBean().getData().getUid();
                token = app.getmLoginBean().getData().getToken();
            }
        }
        return new String[]{uid, token};
    }

    /**
     * @return 1表示是龙虎网；0表示不是龙虎网
     */
    public String getIs_lh() {
        String is_lh = "";
        MyApplication app = (MyApplication) context;
        if (app.getmLoginBean() != null) {
            if (app.getmLoginBean().getData() != null) {
                is_lh = app.getmLoginBean().getData().getIs_lh();
            }
        }
        return is_lh;
    }

    /**
     * 保持登录状态
     *
     * @param loginBean
     */
    public void saveLogin(LoginBean loginBean) {
        SPTool.remove(context, Consts.SP_LOGIN_BEAN);
        SPTool.saveBean2Sp(context, loginBean, Consts.SP_LOGIN_BEAN);
        MyApplication app = (MyApplication) context;
        app.setLoginReturn(false);
        app.setmLoginBean(loginBean);
        //设置别名
        if (!TextUtils.isEmpty(loginBean.getData().getUid())) {
            ULog.e("ck--", "设置别名成功");
            sequence++;
            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
            tagAliasBean.alias = loginBean.getData().getUid();
            tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
            tagAliasBean.isAliasAction = true;
            TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
            SPTool.putBoolean(context, Consts.SP_JPUSH_ALIAS_REGISTED, true);
        }
    }

    /**
     * 退出登录
     */
    public void outLogin() {
        SPTool.remove(context, Consts.SP_LOGIN_BEAN);
        MyApplication app = (MyApplication) context;
        app.setmLoginBean(null);
        //删除别名
        sequence++;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.alias = null;
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
        SPTool.remove(context, Consts.SP_JPUSH_ALIAS_REGISTED);
    }


    private boolean checkType() {
        boolean isLogin = isLogin(false);
        if (!isLogin) {
            Intent intent = new Intent();
            intent.setAction("com.longhoo.net.login");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return false;
        }
        MyApplication app = (MyApplication) context;
        int roleId = app.getmLoginBean().getData().getRoleid();
        if (roleId == 0) {
            return true;
        }

//        else if (roleId == 2) {
//            return true;
//        }

        else {
            return false;
        }
    }

    /**
     * 是否党员
     *
     * @return
     *///0普通党员  1支部书记  2总支书记   3组织部
    public boolean IS_MEMBER() {
        return checkType();
    }

}
