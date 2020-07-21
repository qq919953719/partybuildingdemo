package com.longhoo.net.supervision.bean;

import java.util.List;

public class PartyfeeSituationBean {

    /**
     * code : 0
     * msg : 成功
     * data : [{"oid":17,"name":"机关党政办党支部","yes":0,"no":0},{"oid":18,"name":"机关组宣党支部","yes":0,"no":0},{"oid":19,"name":"机关纪监党支部","yes":0,"no":0},{"oid":20,"name":"机关学工党支部","yes":0,"no":0},{"oid":21,"name":"机关保卫党支部","yes":0,"no":0},{"oid":22,"name":"机关团委党支部","yes":0,"no":0},{"oid":23,"name":"机关工会党支部","yes":0,"no":0},{"oid":24,"name":"机关人事党支部","yes":0,"no":0},{"oid":26,"name":"机关教务党支部","yes":0,"no":0},{"oid":27,"name":"机关国际处党支部","yes":0,"no":0},{"oid":28,"name":"机关质控党支部","yes":0,"no":0},{"oid":29,"name":"机关科技党支部","yes":0,"no":0},{"oid":30,"name":"机关招就党支部","yes":0,"no":0},{"oid":31,"name":"国际教育学院师生支部","yes":0,"no":0},{"oid":32,"name":"机关资产党支部","yes":0,"no":0},{"oid":68,"name":"机关纪检党支部","yes":0,"no":0}]
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
         * oid : 17
         * name : 机关党政办党支部
         * yes : 0
         * no : 0
         */

        private int oid;
        private String name;
        private int yes;
        private int no;

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

        public int getYes() {
            return yes;
        }

        public void setYes(int yes) {
            this.yes = yes;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }
    }
}
