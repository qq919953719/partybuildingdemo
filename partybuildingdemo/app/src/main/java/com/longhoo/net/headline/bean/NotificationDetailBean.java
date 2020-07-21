package com.longhoo.net.headline.bean;

/**
 * Created by CK on 2018/5/3.
 * Email:910663958@qq.com
 */

public class NotificationDetailBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"details":{"content":"撒旦法sdf","meeting_time":"1525327363","meeting_address":"撒旦法","compere":"蓉蓉","recorder":"蓉蓉","attend_memb":"陈凯\\r","desc":"<p>撒旦法萨芬暗示法<\/p>","type":"党员大会","sign_memb":"无","meeting_title":"测试标题","meeting_content":"阿桑地方撒df","ttype":"1"}}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * details : {"content":"撒旦法sdf","meeting_time":"1525327363","meeting_address":"撒旦法","compere":"蓉蓉","recorder":"蓉蓉","attend_memb":"陈凯\\r","desc":"<p>撒旦法萨芬暗示法<\/p>","type":"党员大会","sign_memb":"无","meeting_title":"测试标题","meeting_content":"阿桑地方撒df","ttype":"1"}
         */

        private DetailsBean details;

        public DetailsBean getDetails() {
            return details;
        }

        public void setDetails(DetailsBean details) {
            this.details = details;
        }

        public static class DetailsBean {
            /**
             * content : 撒旦法sdf
             * meeting_time : 1525327363
             * meeting_address : 撒旦法
             * compere : 蓉蓉
             * recorder : 蓉蓉
             * attend_memb : 陈凯\r
             * desc : <p>撒旦法萨芬暗示法</p>
             * type : 党员大会
             * sign_memb : 无
             * meeting_title : 测试标题
             * meeting_content : 阿桑地方撒df
             * ttype : 1
             */

            private String content;
            private String time;
            private String meeting_time;
            private String meeting_address;
            private String compere;
            private String recorder;
            private String attend_memb;
            private String desc;
            private String type;
            private String sign_memb;
            private String meeting_title;
            private String meeting_content;
            private String ttype;
            private String leavestatus; //1已请假  0 未请假
            private String applystatus;// 1已报名  0 未报名
            private String leavereason;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getMeeting_time() {
                return meeting_time;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setMeeting_time(String meeting_time) {
                this.meeting_time = meeting_time;
            }

            public String getMeeting_address() {
                return meeting_address;
            }

            public void setMeeting_address(String meeting_address) {
                this.meeting_address = meeting_address;
            }

            public String getCompere() {
                return compere;
            }

            public void setCompere(String compere) {
                this.compere = compere;
            }

            public String getRecorder() {
                return recorder;
            }

            public void setRecorder(String recorder) {
                this.recorder = recorder;
            }

            public String getAttend_memb() {
                return attend_memb;
            }

            public void setAttend_memb(String attend_memb) {
                this.attend_memb = attend_memb;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSign_memb() {
                return sign_memb;
            }

            public void setSign_memb(String sign_memb) {
                this.sign_memb = sign_memb;
            }

            public String getMeeting_title() {
                return meeting_title;
            }

            public void setMeeting_title(String meeting_title) {
                this.meeting_title = meeting_title;
            }

            public String getMeeting_content() {
                return meeting_content;
            }

            public void setMeeting_content(String meeting_content) {
                this.meeting_content = meeting_content;
            }

            public String getTtype() {
                return ttype;
            }

            public void setTtype(String ttype) {
                this.ttype = ttype;
            }

            public String getLeavestatus() {
                return leavestatus;
            }

            public void setLeavestatus(String leavestatus) {
                this.leavestatus = leavestatus;
            }

            public String getApplystatus() {
                return applystatus;
            }

            public void setApplystatus(String applystatus) {
                this.applystatus = applystatus;
            }

            public String getLeavereason() {
                return leavereason;
            }

            public void setLeavereason(String leavereason) {
                this.leavereason = leavereason;
            }
        }
    }
}
