package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by ck on 2017/12/13.
 */

public class PartyBranchBean {
    /**
     * code : 0
     * msg : 查找成功
     * data : [{"oid":"1","name":"报业集团","pid":"0","time":"1511832571"},{"oid":"2","name":"龙虎网党小组","pid":"3","time":"1511833860"},{"oid":"3","name":"龙虎网党支部","pid":"1","time":"1511833873"},{"oid":"4","name":"南京日报党支部","pid":"1","time":"1511837682"},{"oid":"5","name":"现代家庭报党支部","pid":"1","time":"1511837696"},{"oid":"6","name":"东方卫报党支部","pid":"1","time":"1511837717"},{"oid":"7","name":"周末报党支部","pid":"1","time":"1511837729"},{"oid":"8","name":"龙虎网党小组2","pid":"3","time":"1511866873"}]
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
         * oid : 1
         * name : 报业集团
         * pid : 0
         * time : 1511832571
         */

        private String oid;
        private String name;
        private String pid;
        private String time;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
