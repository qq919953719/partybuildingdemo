package com.longhoo.net.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.headline.ui.HeadlineFragment;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * CC
 */
@SuppressLint("NewApi")
public class UpgradeManager {
    public static final int UPGRADEMANAGER_FORCEUPDATE = 3;
    public static final int UPGRADEMANAGER_NORMALUPDATE = 0;
    public static final int UPGRADEMANAGER_UNCONNECTED = -1;
    public static final int UPLOADING = 0; // 正在升级
    public static final int FINISH = 1; // 升级完成
    public static final int EXCEPTION = 2; // 升级异常
    public static final int CANCEL = 3; // 用户取消
    public static final int NOTUPDATE = 4; // 不需要更新
    public PopupWindow mPopupIsUpData;//
    View myVpopu = null;
    // public PopupWindow mPopupIsUpYouXian;//
    Context mContext;
    // ProgressDialog mProgressDlg;
    VersionJsonNode mVersionJsonNode = new VersionJsonNode();
    int miTotalSize; // 文件总大小
    int miCurrentSize;// 文件当前大小
    int miUpdateStatus = UPGRADEMANAGER_UNCONNECTED;// 升级状态
    UpGradeListener mListener;
    boolean mIsNeedShowUpdateDlg = false;
    // public AlertDialog mnoticeDialog; // 提示弹出框
    public ArrowProgressBar mArrowProgressBar = null;
    public boolean mIsCancel = false;// 取消下载
    TextView jindu;
    public boolean MyQiangzhi = false;

    public interface UpGradeListener {
        void OnUpgradeResult(int iRetCode, int iUpgradeType);
    }


    public UpgradeManager(HeadlineFragment home, Context context, boolean isForceShow) {
        mContext = context;
        mListener = (UpGradeListener) home;
        mIsNeedShowUpdateDlg = isForceShow;
//        VersionJsonNode.Request(mContext, this, 0);
        Map<String, String> map = new HashMap<>();
        map.put("sys", "1");
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/application/public_version", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {

                ULog.e("------------------------>" + response);
                Gson gson = new Gson();
                try {
                    mVersionJsonNode = gson.fromJson(response.toString(), VersionJsonNode.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                boolean isQiangzhi = false;
                if (mVersionJsonNode == null) {
                    return;
                }
                if (mVersionJsonNode != null) {
                    if (mVersionJsonNode.getData() == null) {
                        return;
                    }
                    if (mVersionJsonNode.getData().getVersion() == null) {
                        return;
                    }
                    if (mVersionJsonNode.getData().getVersion().getVcode() == null) {
                        return;
                    }
                    if (!TextUtils.equals(mVersionJsonNode.getCode(), "0")) {
                        return;
                    }
                    try {
                        int onlineVersion = Integer.parseInt(mVersionJsonNode.getData().getVersion().getVcode());
                        int localVersion = Utils.getLocalVersion(mContext);
                        if (localVersion < onlineVersion) {
                            if (mVersionJsonNode.getData().getVersion().getStatus().equals("0")) {
                                isQiangzhi = false;
                            } else {
                                isQiangzhi = true;
                            }
                            initPopupMenu("发现新版本"
                                            + mVersionJsonNode.getData().getVersion().getVname(),
                                    mVersionJsonNode.getData().getVersion().getVinfo(), isQiangzhi);
                        } else {
                            mListener.OnUpgradeResult(NOTUPDATE,
                                    UPGRADEMANAGER_NORMALUPDATE);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });


    }


    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOADING: {
                    int iProgress = (int) (miCurrentSize / (miTotalSize * 1.0) * 100);

                    mArrowProgressBar.setProgress(iProgress);
                    jindu.setText(iProgress + "%");
                    break;
                }
                case FINISH: {

                    if (MyQiangzhi) {
                        mIsCancel = true;
                        myVpopu.findViewById(R.id.progressBackLayoutId)
                                .setVisibility(View.GONE);
                        myVpopu.findViewById(R.id.queren).setVisibility(View.VISIBLE);
                    } else {
                        mIsCancel = true;
                        mPopupIsUpData.dismiss();
                    }
                    File file = new File(Consts.SD_TEMPAPK);
                    MyApplication App = (MyApplication) mContext.getApplicationContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(App, "com.longhoo.net.provider", file);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    if (App.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                        App.startActivity(intent);
                    }
                    break;
                }
                case EXCEPTION: {
                    ShowExceptionDlg();
                    mListener.OnUpgradeResult(EXCEPTION, miUpdateStatus);
                    break;
                }

            }

        }
    };

    void ShowExceptionDlg() {
        if (miUpdateStatus == UPGRADEMANAGER_FORCEUPDATE) {
            new AlertDialog.Builder(mContext, ProgressDialog.THEME_HOLO_LIGHT)
                    .setTitle("升级失败")
                    .setMessage("程序升级异常,请退出后重新开启")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {// 添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {// 确定按钮的响应事件
                                    mListener.OnUpgradeResult(EXCEPTION,
                                            miUpdateStatus);
                                }
                            }).show();
        } else {
            new AlertDialog.Builder(mContext, ProgressDialog.THEME_HOLO_LIGHT)
                    .setTitle("升级失败")
                    .setMessage("程序升级异常")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {// 添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {// 确定按钮的响应事件
                                    // 正常启动程序
                                    mListener.OnUpgradeResult(EXCEPTION,
                                            miUpdateStatus);
                                }
                            }).show();
        }
    }

    public class UpdateAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        public UpdateAsyncTask() {

        }

        @Override
        protected void onPostExecute(Integer iRet) {
            super.onPostExecute(iRet);

        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                if (mVersionJsonNode.getData().getVersion().getUrl().equals("")) {
                    Message msg = new Message();
                    msg.what = NOTUPDATE;
                    mHandler.sendMessage(msg);
                    return 1;
                }
                String strPkgAddr = mVersionJsonNode.getData().getVersion().getUrl();
                URL url = new URL(strPkgAddr);// 创建一个URL对象
                HttpURLConnection urlConn = (HttpURLConnection) url
                        .openConnection();// 创建一个Http连接

                miTotalSize = urlConn.getContentLength();
                miCurrentSize = 0;

                BufferedInputStream bis = new BufferedInputStream(
                        urlConn.getInputStream());

                File file = new File(Consts.SD_TEMPAPK);

//                File file = new File(Consts.SD_TEMPAPK);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                int iSize = 0;
                byte[] buf = new byte[256];

                while ((iSize = bis.read(buf)) != -1) {
                    fos.write(buf, 0, iSize);
                    miCurrentSize += iSize;
                    Message msg = new Message();
                    // 取消 正在下载
                    if (mIsCancel) {
                        mIsCancel = false;
                        msg.what = UPLOADING;
                        mHandler.sendMessage(msg);
                        fos.close();
                        bis.close();
                        urlConn.disconnect();
                        return 0;
                    } else {
                        msg.what = UPLOADING;
                        mHandler.sendMessage(msg);
                    }
                }
                fos.close();
                bis.close();
                urlConn.disconnect();
                Message msg = new Message();
                msg.what = FINISH;
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
                miCurrentSize = -1;
                Message msg = new Message();
                msg.what = EXCEPTION;
                mHandler.sendMessage(msg);
            }

            return 1;
        }
    }

    ;

    /**
     * 版本更新
     *
     * @param mstrBanBenHao
     * @param mstrBanBenJieShao
     */
    private void initPopupMenu(String mstrBanBenHao, String mstrBanBenJieShao, final boolean isQiangzhi) {
        MyQiangzhi = isQiangzhi;
        LayoutInflater lay = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            myVpopu = lay.inflate(R.layout.activity_updatafloat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((View) myVpopu.findViewById(R.id.updata)).getBackground().setAlpha(90);
//        ((View) myVpopu.findViewById(R.id.updata)).setBackgroundColor(Color.parseColor("#ac000000"));

        ((TextView) myVpopu.findViewById(R.id.banbenhao))
                .setText(mstrBanBenHao);

        ((TextView) myVpopu.findViewById(R.id.banbenjieshao))
                .setText(mstrBanBenJieShao);
        myVpopu.findViewById(R.id.progressBackLayoutId)
                .setVisibility(View.GONE);
        myVpopu.findViewById(R.id.queren).setVisibility(View.VISIBLE);
        mArrowProgressBar = (ArrowProgressBar) myVpopu
                .findViewById(R.id.arrowProgressBarId);
        jindu = ((TextView) myVpopu.findViewById(R.id.jindu));
        ((View) myVpopu.findViewById(R.id.shaohougengxin))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        mPopupIsUpData.dismiss();
                    }
                });
        ((View) myVpopu.findViewById(R.id.lijigengxin))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // 判断 有无 网络
                        if (Utils.isNetworkConnected(mContext) == false) {
                            Toast.makeText(mContext, "请求失败，请检查您的网络连接是否正常！",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 判断是否 是 无线网
                            // if (Tools.checkNetworkConnection(mContext) ==
                            // false) {
                            // mPopupIsUpData.dismiss();
                            // // 弹出网络提醒框 WIFI未连接
                            // initPopupMenuShuJu();
                            // } else {
                            // WIFI已连接
                            // Update();
                            // mPopupIsUpData.dismiss();
                            Utils.creatTempFolder();
                            Utils.delFolder(Consts.SD_ROOT_OLD);
                            myVpopu.findViewById(R.id.progressBackLayoutId)
                                    .setVisibility(View.VISIBLE);
                            myVpopu.findViewById(R.id.queren).setVisibility(
                                    View.GONE);
                            mArrowProgressBar.initArrowProgressBar(mContext);
                            new UpdateAsyncTask().execute();

                            // }
                        }
                    }
                });
        ((View) myVpopu.findViewById(R.id.quxiao))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (isQiangzhi) {
                            mIsCancel = true;
                            myVpopu.findViewById(R.id.progressBackLayoutId)
                                    .setVisibility(View.GONE);
                            myVpopu.findViewById(R.id.queren).setVisibility(View.VISIBLE);
                        } else {
                            mIsCancel = true;
                            mPopupIsUpData.dismiss();
                        }
                    }
                });
        mPopupIsUpData = new PopupWindow(myVpopu, mContext.getResources()
                .getDisplayMetrics().widthPixels, mContext.getResources()
                .getDisplayMetrics().heightPixels + 200, true);

        // 设置整个popupwindow的样式。
        mPopupIsUpData.setBackgroundDrawable(new BitmapDrawable());
        // 使窗口里面的空间显示其相应的效果，比较点击button时背景颜色改变。
        // 如果为false点击相关的空间表面上没有反应，但事件是可以监听到的。
        // listview的话就没有了作用。
        if (isQiangzhi) {
            mPopupIsUpData.setFocusable(false);
            ((TextView) myVpopu.findViewById(R.id.shaohougengxin)).setVisibility(View.GONE);
            ((TextView) myVpopu.findViewById(R.id.shuxian)).setVisibility(View.GONE);


        } else {
            mPopupIsUpData.setFocusable(true);
            ((TextView) myVpopu.findViewById(R.id.shaohougengxin)).setVisibility(View.VISIBLE);
            ((TextView) myVpopu.findViewById(R.id.shuxian)).setVisibility(View.VISIBLE);
        }

        mPopupIsUpData.setOutsideTouchable(true);
        mPopupIsUpData.update();

        mPopupIsUpData.setClippingEnabled(false);
        mPopupIsUpData.showAtLocation(myVpopu, 0, 0, 0);
        mPopupIsUpData.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub

                if (isQiangzhi) {
                    mIsCancel = true;
                    myVpopu.findViewById(R.id.progressBackLayoutId)
                            .setVisibility(View.GONE);
                    myVpopu.findViewById(R.id.queren).setVisibility(View.VISIBLE);
                } else {
                    mIsCancel = true;
                    mPopupIsUpData.dismiss();
                }


            }
        });


    }


}
