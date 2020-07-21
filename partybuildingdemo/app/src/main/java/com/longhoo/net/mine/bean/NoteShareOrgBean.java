package com.longhoo.net.mine.bean;

import java.util.List;

public class NoteShareOrgBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"org":[{"oid":"148","pid":"154","name":"中国共产党南京市体育彩票管理中心支部委员会"},{"oid":"154","pid":"0","name":"111111"}]}
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
        private List<OrgBean> org;

        public List<OrgBean> getOrg() {
            return org;
        }

        public void setOrg(List<OrgBean> org) {
            this.org = org;
        }

        public static class OrgBean {
            /**
             * oid : 148
             * pid : 154
             * name : 中国共产党南京市体育彩票管理中心支部委员会
             */

            private String oid;
            private String pid;
            private String name;

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
