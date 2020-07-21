package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/15.
 */

public class DemocraticLifeBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"did":"1","content":"1111","time":"1512981467"},{"did":"2","content":"aaaaaaaaa","time":"1512981609"},{"did":"4","content":"qweqweqwe","time":"1513042439"},{"did":"5","content":"123456","time":"1513042502"},{"did":"6","content":"ceshi","time":"1513067563"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * did : 1
             * content : 1111
             * time : 1512981467
             */

            private String did;
            private String content;
            private String time;

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
