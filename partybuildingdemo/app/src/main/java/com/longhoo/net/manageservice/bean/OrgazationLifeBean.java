package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/18.
 */

public class OrgazationLifeBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"lid":"2","type":"4324234","content":"qweqweq","time":"1513153825"}]}
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
            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            /**
             * lid : 2
             * type : 4324234
             * content : qweqweq
             * time : 1513153825
             */
            private String tid;
            private String lid;
            private String type;
            private String content;
            private String time;

            public String getLid() {
                return lid;
            }

            public void setLid(String lid) {
                this.lid = lid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
