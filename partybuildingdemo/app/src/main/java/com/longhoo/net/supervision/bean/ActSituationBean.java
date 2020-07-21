package com.longhoo.net.supervision.bean;

import java.util.List;

public class ActSituationBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"ptitle":"ceshi","oid":48,"num":1,"oname":"计算机与软件学院师生一支部"}]
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
         * ptitle : ceshi
         * oid : 48
         * num : 1
         * oname : 计算机与软件学院师生一支部
         */

        private String ptitle;
        private int oid;
        private int num;
        private String oname;
        private String typename;

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getPtitle() {
            return ptitle;
        }

        public void setPtitle(String ptitle) {
            this.ptitle = ptitle;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getOname() {
            return oname;
        }

        public void setOname(String oname) {
            this.oname = oname;
        }
    }
}
