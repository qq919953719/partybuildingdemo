package com.longhoo.net.widget;



import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingDialog extends Dialog {


    private TextView tv;
  
    /** 
     * style很关键 
     */  
    public LoadingDialog(Context context) {  
        super(context, R.style.loadingDialogStyle);
    }
    long startTime=System.currentTimeMillis();
    class MyTask extends TimerTask {

        @Override
        public void run() {
            long endTime=System.currentTimeMillis();
            long spentTime=(endTime-startTime)/1000;
            tv.setText("正在上传数据....."+spentTime+"秒");

        }}

        @Override
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.dialog_loading);  
        tv = (TextView) findViewById(R.id.tv);
            tv.setText("正在上传数据.....");
//            Timer timer = new Timer();
//            timer.schedule(new MyTask(), 1000, 2000);
            LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);  
    }  
  
}  
