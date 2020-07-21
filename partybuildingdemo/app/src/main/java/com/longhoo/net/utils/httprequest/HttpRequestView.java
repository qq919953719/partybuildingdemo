package com.longhoo.net.utils.httprequest;

/**
 * Created by CK on 2017/7/7.
 */

public interface HttpRequestView {
    void onNetworkError();
    void onRefreshSuccess(String response);
    void onRefreshError();
    void onLoadMoreSuccess(String response);
    void onLoadMoreError();
}
