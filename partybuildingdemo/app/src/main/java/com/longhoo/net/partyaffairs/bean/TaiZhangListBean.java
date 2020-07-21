package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by CK on 2017/11/24.
 */

public class TaiZhangListBean {


    /**
     * code : 0
     * msg : 成功
     * data : [{"tid":151,"type":1,"content":"测试标题","time":1547103327}]
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
         * tid : 151
         * type : 1
         * content : 测试标题
         * time : 1547103327
         */

        private int tid;
        private int type;
        private String content;
        private int time;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        //0 正常    2取消
        private int status;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
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
    }
}
