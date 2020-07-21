package com.longhoo.net.study.bean;

public class TrainCourseDetailBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"aid":117,"add_time":"2019-01-24 00:00:00","title":"鑫鑫测试","content":"123123123","train_starttime":"2019-01-24 15:13","train_endtime":"2019-01-26 00:00","enroll_starttime":"2019-01-24 15:13","enroll_endtime":"2019-01-26 00:00","addr":"鑫鑫鑫","fengcai":"<p>sdfsdf<br/><\/p>","uidstr":"39,3,119","des":"123","unamestr":"陈青,陈薇,陈敏","isEnroll":3}
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
         * aid : 117
         * add_time : 2019-01-24 00:00:00
         * title : 鑫鑫测试
         * content : 123123123
         * train_starttime : 2019-01-24 15:13
         * train_endtime : 2019-01-26 00:00
         * enroll_starttime : 2019-01-24 15:13
         * enroll_endtime : 2019-01-26 00:00
         * addr : 鑫鑫鑫
         * fengcai : <p>sdfsdf<br/></p>
         * uidstr : 39,3,119
         * des : 123
         * unamestr : 陈青,陈薇,陈敏
         * isEnroll : 3
         */

        private int aid;
        private String add_time;
        private String title;
        private String content;
        private String train_starttime;
        private String train_endtime;
        private String enroll_starttime;
        private String enroll_endtime;
        private String addr;
        private String fengcai;
        private String uidstr;
        private String des;
        private String unamestr;
        private int isEnroll;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
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

        public String getFengcai() {
            return fengcai;
        }

        public void setFengcai(String fengcai) {
            this.fengcai = fengcai;
        }

        public String getUidstr() {
            return uidstr;
        }

        public void setUidstr(String uidstr) {
            this.uidstr = uidstr;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getUnamestr() {
            return unamestr;
        }

        public void setUnamestr(String unamestr) {
            this.unamestr = unamestr;
        }

        public int getIsEnroll() {
            return isEnroll;
        }

        public void setIsEnroll(int isEnroll) {
            this.isEnroll = isEnroll;
        }
    }
}
