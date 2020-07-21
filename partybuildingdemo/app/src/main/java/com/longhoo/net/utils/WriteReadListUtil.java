package com.longhoo.net.utils;

import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WriteReadListUtil {
    public void UpLoadWriteInfo(Context mContext, String strType, String strTid) {

        MyApplication App = (MyApplication) mContext.getApplicationContext();
        if (App.getmLoginBean() == null) {
            ULog.e("用户数据丢失");
            return;
        }
        /**
         * 我的学习报告/记录提交
         * @desc /app/report/setreport
         * 参数
         * @poram int     type    类型  1资讯类阅读（只包括首页党风廉政、工作动态、基层党建、干部工作）    2视频   3答题   4心得   5评论
         * @poram int  tid 来源id
         *
         *
         * 返回值 无
         */
        Map<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(App.getmLoginBean().getData().getUid()));
        map.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        map.put("type", strType);
        map.put("tid", strTid);
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/report/setreport", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("提交阅读记录：" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            ULog.e("阅读记录提交成功");
                        } else {

                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {


            }
        });


    }

    public void UpLoadWriteInfo(Context mContext, String strType, String strTid, long time) {

        MyApplication App = (MyApplication) mContext.getApplicationContext();
        if (App.getmLoginBean() == null) {
            ULog.e("用户数据丢失");
            return;
        }
        /**
         * 我的学习报告/记录提交
         * @desc /app/report/setreport
         * 参数
         * @poram int     type    类型  1资讯类阅读（只包括首页党风廉政、工作动态、基层党建、干部工作）    2视频   3答题   4心得   5评论
         * @poram int  tid 来源id
         *
         *
         * 返回值 无
         */
        Map<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(App.getmLoginBean().getData().getUid()));
        map.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        map.put("type", strType);
        map.put("tid", strTid);
        map.put("ctime", time + "");
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/report/setreport", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("提交阅读记录：" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            ULog.e("阅读记录提交成功");
                        } else {

                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {


            }
        });


    }
}
