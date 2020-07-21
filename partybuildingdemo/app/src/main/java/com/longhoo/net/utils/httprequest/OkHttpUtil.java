package com.longhoo.net.utils.httprequest;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.longhoo.net.BuildConfig;
import com.longhoo.net.mine.ui.LoginActivity;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ULog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by ck on 2016/12/13.
 * 使用okhttpfinal会导致内存泄漏，故在此根据okhttp3封装的一个网络框架
 * 调用前先init初始化
 */

public class OkHttpUtil {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg; charset=utf-8");
    private static OkHttpUtil instance;
    private OkHttpClient client;
    private Context context;
    //    private WeakReference<Context> context;
    private Handler mHandler;

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            instance = new OkHttpUtil();
        }
        return instance;
    }

    /**
     * 全局初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        File sdcache = context.getApplicationContext().getExternalCacheDir();
        int cacheSize = 200 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        if (sdcache != null) {
            builder.cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        }
        client = builder.build();
        mHandler = new Handler(Looper.getMainLooper());
    }

//    /**
//     * 每次请求前调用一次
//     * @param context
//     */
//    public OkHttpUtil getContext(Context context) {
//        if (this.context == null) {
//            this.context = new WeakReference<Context>(context);
//        } else if (this.context.get() == null) {
//            this.context = new WeakReference<Context>(context);
//        }
//        return this;
//    }

    /**
     * 异步GET
     *
     * @param url
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncGet(String url, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || okHttpCallback == null) {
            return false;
        }
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    if (TextUtils.equals(jsonObject.optString("code"), "5")) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        EnterCheckUtil.getInstance().outLogin();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });
            }
        });
        return true;
    }

    /**
     * 异步POST
     *
     * @param url
     * @param map
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncPost(String url, Map<String, String> map, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || okHttpCallback == null)
            return false;
        //添加表单数据
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    if (TextUtils.equals(jsonObject.optString("code"), "5")) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        EnterCheckUtil.getInstance().outLogin();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 异步POST
     *
     * @param url
     * @param param
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncPost2(String url, String param, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || okHttpCallback == null)
            return false;
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, param))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncUpload(String url, File file, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || file == null || okHttpCallback == null)
            return false;
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 上传多个form表单数据和单张图片文件(图片参数名：pic)
     *
     * @param url
     * @param map
     * @param file
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncMultiUpload(String url, Map<String, String> map, File file, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || okHttpCallback == null)
            return false;
        //添加表单数据
        MultipartBody.Builder formBuilder = new MultipartBody.Builder();
        formBuilder.setType(MultipartBody.FORM);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        if (file != null) {
            formBuilder.addFormDataPart("headpic", file.getAbsolutePath(), RequestBody.create(MEDIA_TYPE_PNG, file));
        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 上传多个form表单数据和图片文件(图片参数名：pic[])
     *
     * @param url
     * @param map
     * @param list
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncMultiUpload2(String url, Map<String, String> map, List<File> list, String strIsFile, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || list == null || okHttpCallback == null)
            return false;
        //添加表单数据
        MultipartBody.Builder formBuilder = new MultipartBody.Builder();
        formBuilder.setType(MultipartBody.FORM);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        int fileSize = list.size();

        if (strIsFile.equals("1")) {
            if (fileSize > 0) {
                for (int i = 0; i < fileSize; i++) {
                    formBuilder.addFormDataPart("pic[]", list.get(i).getAbsolutePath(), RequestBody.create(MEDIA_TYPE_PNG, list.get(i)));
                }
            }
        } else {
            if (fileSize > 0) {
                for (int i = 0; i < fileSize; i++) {
                    formBuilder.addFormDataPart("video", list.get(i).getAbsolutePath(), RequestBody.create(MediaType.parse("video/mp4"), list.get(i)));
                }
            }
        }


        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 上传多个form表单数据和图片文件(图片参数名：thumb)附件files
     *
     * @param url
     * @param map
     * @param list
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncMultiUpload3(String url, Map<String, String> map, File mFile, List<File> list, String strIsFile, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || list == null || okHttpCallback == null)
            return false;
        //添加表单数据
        MultipartBody.Builder formBuilder = new MultipartBody.Builder();
        formBuilder.setType(MultipartBody.FORM);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        int fileSize = list.size();

        if (strIsFile.equals("1")) {
            if (fileSize > 0) {
                for (int i = 0; i < fileSize; i++) {
                    formBuilder.addFormDataPart("thumb[]", list.get(i).getAbsolutePath(), RequestBody.create(MEDIA_TYPE_PNG, list.get(i)));
                }
            }
        } else {
            if (fileSize > 0) {
                for (int i = 0; i < fileSize; i++) {
                    formBuilder.addFormDataPart("video", list.get(i).getAbsolutePath(), RequestBody.create(MediaType.parse("video/mp4"), list.get(i)));
                }
            }
        }

        if (mFile != null) {
            formBuilder.addFormDataPart("files", mFile.getAbsolutePath(), RequestBody.create(MediaType.parse("text/plain"), mFile));
        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 上传多个form表单数据和图片文件(图片参数名：thumb)附件files
     *
     * @param url
     * @param map
     * @param list
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncMultiUpload4(String url, Map<String, String> map, File mFileVideo, File mFileVideoImage, List<File> list, String strIsFile, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || list == null || okHttpCallback == null)
            return false;
        //添加表单数据
        MultipartBody.Builder formBuilder = new MultipartBody.Builder();
        formBuilder.setType(MultipartBody.FORM);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        int fileSize = list.size();

        if (strIsFile.equals("1")) {
            if (fileSize > 0) {
                for (int i = 0; i < fileSize; i++) {
                    formBuilder.addFormDataPart("pic[]", list.get(i).getAbsolutePath(), RequestBody.create(MEDIA_TYPE_PNG, list.get(i)));
                }
            }
        } else {
            if (fileSize > 0) {
                for (int i = 0; i < fileSize; i++) {
                    formBuilder.addFormDataPart("video", list.get(i).getAbsolutePath(), RequestBody.create(MediaType.parse("video/mp4"), list.get(i)));
                }
            }
        }

        if (mFileVideo != null) {
            formBuilder.addFormDataPart("video_url", mFileVideo.getAbsolutePath(), RequestBody.create(MediaType.parse("text/plain"), mFileVideo));

        }
        if (mFileVideoImage != null) {
            formBuilder.addFormDataPart("video_img", mFileVideoImage.getAbsolutePath(), RequestBody.create(MediaType.parse("text/plain"), mFileVideoImage));

        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 上传多个form表单数据和图片文件(图片参数名：自定义)
     *
     * @param url
     * @param map
     * @param list
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncMultiUpload(String url, Map<String, String> map, List<File> list, String paramName, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || list == null || okHttpCallback == null)
            return false;
        //添加表单数据
        MultipartBody.Builder formBuilder = new MultipartBody.Builder();
        formBuilder.setType(MultipartBody.FORM);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        int fileSize = list.size();
        if (fileSize > 0) {
            for (int i = 0; i < fileSize; i++) {
                formBuilder.addFormDataPart(paramName, list.get(i).getAbsolutePath(), RequestBody.create(MEDIA_TYPE_PNG, list.get(i)));
            }
        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }


    public boolean doAsyncMultiUpload223(String url, Map<String, String> map, File mFileVideo, File mFileVideoImage, List<File> list, String paramName, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || list == null || okHttpCallback == null)
            return false;
        //添加表单数据
        MultipartBody.Builder formBuilder = new MultipartBody.Builder();
        formBuilder.setType(MultipartBody.FORM);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        int fileSize = list.size();
        if (fileSize > 0) {
            for (int i = 0; i < fileSize; i++) {
                formBuilder.addFormDataPart(paramName, list.get(i).getAbsolutePath(), RequestBody.create(MEDIA_TYPE_PNG, list.get(i)));
            }
        }
        if (mFileVideo != null) {
            formBuilder.addFormDataPart("videourl", mFileVideo.getAbsolutePath(), RequestBody.create(MediaType.parse("text/plain"), mFileVideo));

        }
        if (mFileVideoImage != null) {
            formBuilder.addFormDataPart("videoimg", mFileVideoImage.getAbsolutePath(), RequestBody.create(MediaType.parse("text/plain"), mFileVideoImage));

        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }
    public boolean doAsyncMultiUpload443(String url, Map<String, String> map, File mFileVideo, File mFileFujian, File mFileVideoImage, List<File> list, String paramName, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || list == null || okHttpCallback == null)
            return false;
        //添加表单数据
        MultipartBody.Builder formBuilder = new MultipartBody.Builder();
        formBuilder.setType(MultipartBody.FORM);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        int fileSize = list.size();
        if (fileSize > 0) {
            for (int i = 0; i < fileSize; i++) {
                formBuilder.addFormDataPart(paramName, list.get(i).getAbsolutePath(), RequestBody.create(MEDIA_TYPE_PNG, list.get(i)));
            }
        }
        if (mFileVideo != null) {
            formBuilder.addFormDataPart("videourl", mFileVideo.getAbsolutePath(), RequestBody.create(MediaType.parse("text/plain"), mFileVideo));

        }
        if (mFileVideoImage != null) {
            formBuilder.addFormDataPart("videoimg", mFileVideoImage.getAbsolutePath(), RequestBody.create(MediaType.parse("text/plain"), mFileVideoImage));

        }
        if (mFileFujian!=null){
            formBuilder.addFormDataPart("appendix", mFileFujian.getAbsolutePath(), RequestBody.create(MediaType.parse("text/plain"), mFileFujian));
        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    public boolean doAsyncMultiUpload(String url, String name, List<File> videoList, List<File> picList, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || videoList == null || picList == null || okHttpCallback == null)
            return false;
        //添加表单数据
        MultipartBody.Builder formBuilder = new MultipartBody.Builder();
        formBuilder.setType(MultipartBody.FORM);
        int fileSize = videoList.size();
        for (int i = 0; i < fileSize; i++) {
            formBuilder.addFormDataPart(name, videoList.get(i).getAbsolutePath(), RequestBody.create(MediaType.parse("video/mp4"), videoList.get(i)));
        }
        int fileSize2 = picList.size();
        for (int i = 0; i < fileSize2; i++) {
            formBuilder.addFormDataPart(name, picList.get(i).getAbsolutePath(), RequestBody.create(MEDIA_TYPE_PNG, picList.get(i)));
        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 异步GET
     * 广告统计专用，定制user_agent
     * httpclient 会重复请求
     *
     * @param url
     * @param okHttpCallback
     * @return
     */
    public boolean doAsyncStatsGet(String url, final OkHttpCallback okHttpCallback) {
        if (TextUtils.isEmpty(url) || okHttpCallback == null) {
            return false;
        }
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onFailure(call.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onSuccess(str);
                    }
                });

            }
        });
        return true;
    }

    /**
     * 下载文件
     *
     * @param fileUrl 文件url
     * @param destFileDir 存储目标目录
     */
    private long current = 0;

    public void downLoadFile(String fileUrl, final String destFileDir, final String fileName, final OkHttpDownCallBack callBack) {
        isStop = false;
        current = 0;
        final File file = new File(destFileDir, fileName);
        if (file.exists()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onDownSuccess(file);
                }
            });
            return;
        }
        Request.Builder builder = new Request.Builder().url(fileUrl);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onDownFailed("下载失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    final long total = response.body().contentLength();
                    ULog.e("ck", "total------>" + total);
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        ULog.e("ck", "current------>" + current);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onDownloading(total, current);
                            }
                        });
                        if (isStop) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onDownFailed("下载失败");
                                }
                            });
                            return;
                        }
                    }
                    fos.flush();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onDownSuccess(file);
                        }
                    });
                } catch (IOException e) {
                    ULog.e("ck", e.toString());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onDownFailed("下载失败");
                        }
                    });
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        ULog.e("ck", e.toString());
                    }
                }
            }
        });
    }

    private boolean isStop;

    public void stop() {
        isStop = true;
    }
}
