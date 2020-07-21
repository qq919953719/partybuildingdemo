package com.longhoo.net.study.bean;

import java.util.List;

public class TrainCourseBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"aid":"5","title":"ceshi3","train_starttime":"2018-12-06 14:54:28","train_endtime":"2018-12-07 14:54:31","enroll_starttime":"2018-12-04 14:54:33","enroll_endtime":"2018-12-04 14:54:34","addr":"报业大厦2","content":"","add_time":"2018-12-04 15:15:13"},{"aid":"1","title":"ceshi","train_starttime":"2018-12-03 17:23:11","train_endtime":"2018-12-03 22:23:14","enroll_starttime":"2018-11-30 17:23:25","enroll_endtime":"2018-12-02 17:23:39","addr":"报业大厦","content":"1111111","add_time":"2018-11-29 17:23:55"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * aid : 5
         * title : ceshi3
         * train_starttime : 2018-12-06 14:54:28
         * train_endtime : 2018-12-07 14:54:31
         * enroll_starttime : 2018-12-04 14:54:33
         * enroll_endtime : 2018-12-04 14:54:34
         * addr : 报业大厦2
         * content :
         * add_time : 2018-12-04 15:15:13
         */

        private String aid;
        private String title;
        private String train_starttime;
        private String train_endtime;
        private String enroll_starttime;
        private String enroll_endtime;
        private String addr;
        private String content;
        private String add_time;
        private String styleid;
        private String stylename;
        private String class_hour;

        public String getClass_hour() {
            return class_hour;
        }

        public void setClass_hour(String class_hour) {
            this.class_hour = class_hour;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTrain_starttime() {
            return train_starttime;
        }

        public void setTrain_starttime(String train_starttime) {
            this.train_starttime = train_starttime;
        }

        public String getTrain_endtime() {
            return train_endtime;
        }

        public void setTrain_endtime(String train_endtime) {
            this.train_endtime = train_endtime;
        }

        public String getEnroll_starttime() {
            return enroll_starttime;
        }

        public void setEnroll_starttime(String enroll_starttime) {
            this.enroll_starttime = enroll_starttime;
        }

        public String getEnroll_endtime() {
            return enroll_endtime;
        }

        public void setEnroll_endtime(String enroll_endtime) {
            this.enroll_endtime = enroll_endtime;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStyleid() {
            return styleid;
        }

        public void setStyleid(String styleid) {
            this.styleid = styleid;
        }

        public String getStylename() {
            return stylename;
        }

        public void setStylename(String stylename) {
            this.stylename = stylename;
        }
    }
}
