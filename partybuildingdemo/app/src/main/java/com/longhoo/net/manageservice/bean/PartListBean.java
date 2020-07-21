package com.longhoo.net.manageservice.bean;

import java.util.List;

public class PartListBean {


    /**
     * code : 0
     * msg : 成功
     * data : [{"oid":1416,"name":"测试支部","pid":1415},{"oid":1415,"name":"测试总支","pid":0},{"oid":1,"name":"党委","pid":0}]
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
         * oid : 1416
         * name : 测试支部
         * pid : 1415
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
