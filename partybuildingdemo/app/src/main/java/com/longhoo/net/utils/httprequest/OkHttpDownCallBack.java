package com.longhoo.net.utils.httprequest;

import java.io.File;

/**
 * Created by CK on 2018/1/3.
 * Email:910663958@qq.com
 * okhttp下载文件的接口
 */

public interface OkHttpDownCallBack {
    void onDownSuccess(File file);
    void onDownloading(long total,long current);
    void onDownFailed(String msg);
}
