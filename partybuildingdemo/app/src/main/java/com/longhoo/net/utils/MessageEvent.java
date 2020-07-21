package com.longhoo.net.utils;

/**
 * Created by ck on 2017/12/19.
 */

public class MessageEvent {
    public static final int MSG_SHARE_NOTE = 0;
    public static final int MSG_ADD_NOTE = 1;
    public static final int MSG_DEL_NOTE = 2;
    public static final int MSG_EDIT_NOTE = 3;
    public static final int MSG_REFRESH_LIST = 4;
    public static final int MSG_PICK_PEOPLE = 5;
    public static final int MSG_REFRESH_ORG_LIFE = 6;
    public static final int MSG_REFRESH_ORG_LIFE_LIST = 7;
    public static final int MSG_REFRESH_STUDY_ARCHIVES = 8;
    public static final int MSG_FINISH_ORGNIZATION_ARCHIVES = 9;
    public static final int MSG_FINISH_ACTIVITY_ARCHIVES =10;
    public static final int MSG_FINISH_SEND_ACTIVITY_ARCHIVES =11;
    public static final int MSG_FINISH_VIDEO_TIME = 12;
    public static final int MSG_FRESH_STUDY_CLASS = 13;
    public static final int MSG_REFRESH_VOTE_LIST= 14;
    public static final int MSG_REFRESH_VOTE_DEL= 15;
    public int position;
    public String message;
    public String message2;
    public String message3;
    public int msgType = -1;
}
