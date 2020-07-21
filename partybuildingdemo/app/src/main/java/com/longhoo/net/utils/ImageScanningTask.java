package com.longhoo.net.utils;

import android.net.Uri;
import android.os.AsyncTask;

import com.google.zxing.Result;

public class ImageScanningTask extends AsyncTask<String, Void, Result> {
    private String path;
    private ImageScanningCallback callback;

    public ImageScanningTask(String path, ImageScanningCallback callback) {
        this.path = path;
        this.callback = callback;
    }

    @Override
    protected Result doInBackground(String... params) {
        return CodeScanningUtil.scanImage(path);
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        callback.onFinishScanning(result);
    }

    public interface ImageScanningCallback {
        void onFinishScanning(Result result);
    }
}
