package com.longhoo.net.utils.httprequest;

import android.app.Activity;
import android.content.Context;

import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.NetworkUtils;

import java.util.Map;

/**
 * Created by CK on 2017/7/7.
 */

public class HttpRequestPresenter {
    private Context context;
    private HttpRequestView requestView;

    public HttpRequestPresenter(Context context, HttpRequestView requestView) {
        this.context = context;
        this.requestView = requestView;
    }

    /**
     * 初次进入页面时网络请求数据
     * @param url
     * @param requestType  请求方式 get post
     * @param getMode  0：刷新  1：获取个更多
     * @param requestParams 请求参数
     */
    public void doInitHttpData(String url, int requestType, final int getMode, Map<String,String> requestParams){
        if(!NetworkUtils.isNetAvailable(context)){
            if(requestView!=null){
                requestView.onNetworkError();
            }
            return;
        }
        switch (requestType){
            case Consts.REQUEST_METHOD_GET:
                OkHttpUtil.getInstance().doAsyncGet(url, new OkHttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        if(context instanceof Activity){
                            if(((Activity) context).isFinishing()){
                                return;
                            }
                        }
                        if(requestView!=null){
                            if(getMode==Consts.REQUEST_REFRESH){
                                requestView.onRefreshSuccess(response);
                            }else if(getMode==Consts.REQUEST_LOADMORE){
                                requestView.onLoadMoreSuccess(response);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        if(context instanceof Activity){
                            if(((Activity) context).isFinishing()){
                                return;
                            }
                        }
                        if(requestView!=null){
                            requestView.onNetworkError();
                        }
                    }
                });
                break;
            case Consts.REQUEST_METHOD_POST:
                OkHttpUtil.getInstance().doAsyncPost(url, requestParams,new OkHttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        if(context instanceof Activity){
                            if(((Activity) context).isFinishing()){
                                return;
                            }
                        }
                        if(requestView!=null){
                            if(getMode==Consts.REQUEST_REFRESH){
                                requestView.onRefreshSuccess(response);
                            }else if(getMode==Consts.REQUEST_LOADMORE){
                                requestView.onLoadMoreSuccess(response);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        if(context instanceof Activity){
                            if(((Activity) context).isFinishing()){
                                return;
                            }
                        }
                        if(requestView!=null){
                            requestView.onNetworkError();
                        }
                    }
                });
                break;
        }
    }

    /**
     * 网络请求数据
     * @param url
     * @param requestType  请求方式 get post
     * @param getMode  0：刷新  1：获取更多
     * @param requestParams 请求参数
     */
    public void doHttpData(String url, int requestType, final int getMode, Map<String,String> requestParams){
        if(!NetworkUtils.isNetAvailable(context)){
            if(requestView!=null){
                if(getMode==Consts.REQUEST_REFRESH){
                    requestView.onRefreshError();
                }else if(getMode==Consts.REQUEST_LOADMORE){
                    requestView.onLoadMoreError();
                }
            }
            return;
        }
        switch (requestType){
            case Consts.REQUEST_METHOD_GET:
                OkHttpUtil.getInstance().doAsyncGet(url, new OkHttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        if(context instanceof Activity){
                            if(((Activity) context).isFinishing()){
                                return;
                            }
                        }
                        if(requestView!=null){
                            if(getMode==Consts.REQUEST_REFRESH){
                                requestView.onRefreshSuccess(response);
                            }else if(getMode==Consts.REQUEST_LOADMORE){
                                requestView.onLoadMoreSuccess(response);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        if(context instanceof Activity){
                            if(((Activity) context).isFinishing()){
                                return;
                            }
                        }
                        if(requestView!=null){
                            if(getMode==Consts.REQUEST_REFRESH){
                                requestView.onRefreshError();
                            }else if(getMode==Consts.REQUEST_LOADMORE){
                                requestView.onLoadMoreError();
                            }
                        }
                    }
                });
                break;
            case Consts.REQUEST_METHOD_POST:
                OkHttpUtil.getInstance().doAsyncPost(url, requestParams,new OkHttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        if(context instanceof Activity){
                            if(((Activity) context).isFinishing()){
                                return;
                            }
                        }
                        if(requestView!=null){
                            if(getMode==Consts.REQUEST_REFRESH){
                                requestView.onRefreshSuccess(response);
                            }else if(getMode==Consts.REQUEST_LOADMORE){
                                requestView.onLoadMoreSuccess(response);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        if(context instanceof Activity){
                            if(((Activity) context).isFinishing()){
                                return;
                            }
                        }
                        if(requestView!=null){
                            if(getMode==Consts.REQUEST_REFRESH){
                                requestView.onRefreshError();
                            }else if(getMode==Consts.REQUEST_LOADMORE){
                                requestView.onLoadMoreError();
                            }
                        }
                    }
                });
                break;
        }
    }
}
