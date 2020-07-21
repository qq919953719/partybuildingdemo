package com.longhoo.net.study.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.CodeUtils;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

public class VerificationCodeActivity extends BaseActivity {


    @BindView(R.id.pic_image1)
    ImageView picImage1;
    @BindView(R.id.phone_edit_pic)
    EditText phoneEditPic;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @Override
    protected int getContentId() {
        return R.layout.activity_verification_code;
    }

    String picCode = "";
    Boolean isSucess = true;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        public void run() {
            //每0.1秒执行一次
            mSecond--;
            if (mSecond < 0) {
                //已超时
                tvTime.setText("已超时");
                onBackPressed();
            } else {
                tvTime.setText("剩余" + mSecond + "s");
                mHandler.postDelayed(mRunnable, 1000);
            }


        }

    };
    int mSecond = 20;

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        if (isSucess) {
            MessageEvent event = new MessageEvent();
            event.msgType = MessageEvent.MSG_FINISH_VIDEO_TIME;
            EventBus.getDefault().post(event);
        }
        super.onBackPressed();
    }

    //获取验证码
    private String getCode() {
        picCode = "";
        CodeUtils codeUtils = null;
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        picImage1.setImageBitmap(bitmap);
        picCode = codeUtils.getCode();
        return picCode;
    }

    @Override
    protected void initViews() {
        mHandler.post(mRunnable);
        getCode();
        picImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneEditPic.getText().toString().trim().equals(picCode)) {
                    ToastUtils.getInstance().showToast(VerificationCodeActivity.this, "验证成功！");
                    isSucess = false;
                    onBackPressed();
                } else {
                    ToastUtils.getInstance().showToast(VerificationCodeActivity.this, "您输入的验证码错误，请重新输入！");
                    getCode();
                }
            }
        });


    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
