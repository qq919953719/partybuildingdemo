package com.longhoo.net.utils.httprequest;

/**
 * Created by ck on 2016/12/13.
 * 配合OkHttpUtil使用的回调接口
 */

public interface OkHttpCallback {
    void onSuccess(String response);
    void onFailure(String errorMsg);
}
