package com.longhoo.net.study.bean;

import java.util.List;

public class ChildOrgListBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"oid":48,"name":"└\u2015计算机与软件学院师生一支部","pid":6}]
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
         * oid : 48
         * name : └―计算机与软件学院师生一支部
         * pid : 6
         */

        private int oid;
        private String name;
        private int pid;

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
