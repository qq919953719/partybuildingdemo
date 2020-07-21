package com.longhoo.net.study.bean;

import java.util.List;

public class TrainReportDetailsBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"aid":206,"class_hour":"5","add_time":"1970-01-01","host_unit":"太","title":"111","content":"图","train_starttime":"2019-03-31 14:06","train_endtime":"2019-03-31 15:07","addr":"111","uploads":[]}
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
         * aid : 206
         * class_hour : 5
         * add_time : 1970-01-01
         * host_unit : 太
         * title : 111
         * content : 图
         * train_starttime : 2019-03-31 14:06
         * train_endtime : 2019-03-31 15:07
         * addr : 111
         * uploads : []
         */

        private int aid;
        private String class_hour;
        private String add_time;
        private String host_unit;
        private String title;
        private String content;
        private String train_starttime;
        private String train_endtime;
        private String addr;
        private List<String> uploads;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getClass_hour() {
            return class_hour;
        }

        public void setClass_hour(String class_hour) {
            this.class_hour = class_hour;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getHost_unit() {
            return host_unit;
        }

        public void setHost_unit(String host_unit) {
            this.host_unit = host_unit;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public List<String> getUploads() {
            return uploads;
        }

        public void setUploads(List<String> uploads) {
            this.uploads = uploads;
        }
    }
}
