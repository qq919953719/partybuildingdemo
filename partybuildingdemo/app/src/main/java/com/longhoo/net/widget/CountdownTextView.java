package com.longhoo.net.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 *倒计时控件
 */

public class CountdownTextView extends android.support.v7.widget.AppCompatTextView {

    long mSeconds = -1;
    String mStrFormat;
    Map<Integer,Timer> mTimerMap;
    TimerTask mTimerTask;
    final int what_count_down_tick = 1;
    String TAG = "CountdownTextView";
    private OnTimeOutListener mOnTimeOutListener;
    public CountdownTextView(Context context) {
        super(context);
    }

    public CountdownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountdownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * @param format  例如：剩余%s
     * @param seconds
     */
    public void init(String format,long seconds,OnTimeOutListener onTimeOutListener){
        mTimerMap = new HashMap<>();
        if(!TextUtils.isEmpty(format)){
            mStrFormat = format;
        }
        mSeconds = seconds; //设置总共的秒数
        mOnTimeOutListener = onTimeOutListener;
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(mSeconds > 0){
                    mSeconds --;
                    mHandler.sendEmptyMessage(what_count_down_tick);
                }else{
                    //时间到
                    if (mOnTimeOutListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mOnTimeOutListener.onTimeOut();
                            }
                        });
                    }
                }
            }
        };
        setText(mStrFormat== null ?second2TimeSecond(mSeconds):String.format(mStrFormat,second2TimeSecond(mSeconds)));
    }
    public void start(int position){
        if(mTimerMap.get(position) == null){
            Timer timer = new Timer();
            mTimerMap.put(position,timer);
            mTimerMap.get(position).schedule(mTimerTask,1000,1000);
        }
    }

    public void stop(){
        if(mTimerMap!= null){
            for(Map.Entry<Integer,Timer> map:mTimerMap.entrySet()){
                map.getValue().cancel();
            }
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case what_count_down_tick:
                    if(mSeconds <= 0){
                        setText(mStrFormat== null?"00:00:00":String.format(mStrFormat,"00:00:00"));
                    }else {
                        //Log.e(TAG,"mSeconds="+mSeconds+"#what_count_down_tick:"+second2TimeSecond(mSeconds)+"#"+String.format(mStrFormat,second2TimeSecond(mSeconds)));
                        setText(mStrFormat== null ?second2TimeSecond(mSeconds):String.format(mStrFormat,second2TimeSecond(mSeconds)));
                    }
                    break;
            }
        }
    };

    public boolean isStop(){
        return mSeconds==0;
    }

    @Override
    public void removeOnLayoutChangeListener(OnLayoutChangeListener listener) {
        Log.e(TAG,"removeOnLayoutChangeListener");
        super.removeOnLayoutChangeListener(listener);
    }

    @Override
    public void removeOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        Log.e(TAG,"removeOnAttachStateChangeListener");
        super.removeOnAttachStateChangeListener(listener);
    }

    /**
     * 转化为 hh:mm:ss 格式
     * @param second
     * @return
     */
    private String second2TimeSecond(long second) {
        long hours = second / 3600;
        long minutes = (second%3600)/ 60;
        long seconds = second % 60;

        String hourString = "";
        String minuteString = "";
        String secondString = "";
        if(hours < 10){
            hourString = "0";
            if(hours == 0){
                hourString += "0";
            }else{
                hourString += String.valueOf(hours);
            }
        }else{
            hourString = String.valueOf(hours);
        }
        if (minutes < 10) {
            minuteString = "0";
            if (minutes == 0) {
                minuteString += "0";
            }else {
                minuteString += String.valueOf(minutes);
            }
        }else{
            minuteString = String.valueOf(minutes);
        }
        if(seconds < 10){
            secondString = "0";
            if (seconds == 0) {
                secondString += "0";
            }else {
                secondString += String.valueOf(seconds);
            }
        }else {
            secondString = String.valueOf(seconds);
        }
        return hourString + ":" + minuteString+":"+secondString;
    }

    /**
     * 倒计时已到的监听
     */
    public interface OnTimeOutListener{
        void onTimeOut();
    }
}