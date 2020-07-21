package com.longhoo.net.supervision.bean;

import java.util.List;

public class OrgLifeArrangeBean {

    /**
     * code : 0
     * msg : 成功
     * data : [{"tid":153,"oid":48,"type":1,"content":"111111111111111111111111","time":1547190721,"meeting_address":"123213123123123123123","oname":"计算机与软件学院师生一支部"}]
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
         * tid : 153
         * oid : 48
         * type : 1
         * content : 111111111111111111111111
         * time : 1547190721
         * meeting_address : 123213123123123123123
         * oname : 计算机与软件学院师生一支部
         */

        private int tid;
        private int oid;
        private int type;
        private String content;
        private int time;
        private String meeting_address;
        private String oname;
        private String meeting_time;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        private String user_name;
        public String getMeeting_time() {
            return meeting_time;
        }

        public void setMeeting_time(String meeting_time) {
            this.meeting_time = meeting_time;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getMeeting_address() {
            return meeting_address;
        }

        public void setMeeting_address(String meeting_address) {
            this.meeting_address = meeting_address;
        }

        public String getOname() {
            return oname;
        }

        public void setOname(String oname) {
            this.oname = oname;
        }
    }
}
