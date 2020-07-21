package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by admin on 2017/11/27.
 */

public class OrganizationallBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"topOrganization":{"oid":"1","name":"报业集团"},"organization":[{"oid":"3","name":"龙虎网党支部","click":"1"},{"oid":"4","name":"南京日报党支部","click":"0"},{"oid":"5","name":"现代家庭报党支部","click":"0"},{"oid":"6","name":"东方卫报党支部","click":"0"},{"oid":"7","name":"周末报党支部","click":"0"}]}
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
        /**
         * topOrganization : {"oid":"1","name":"报业集团"}
         * organization : [{"oid":"3","name":"龙虎网党支部","click":"1"},{"oid":"4","name":"南京日报党支部","click":"0"},{"oid":"5","name":"现代家庭报党支部","click":"0"},{"oid":"6","name":"东方卫报党支部","click":"0"},{"oid":"7","name":"周末报党支部","click":"0"}]
         */

        private TopOrganizationBean topOrganization;
        private List<OrganizationBean> organization;

        public TopOrganizationBean getTopOrganization() {
            return topOrganization;
        }

        public void setTopOrganization(TopOrganizationBean topOrganization) {
            this.topOrganization = topOrganization;
        }

        public List<OrganizationBean> getOrganization() {
            return organization;
        }

        public void setOrganization(List<OrganizationBean> organization) {
            this.organization = organization;
        }

        public static class TopOrganizationBean {
            /**
             * oid : 1
             * name : 报业集团
             */

            private String oid;
            private String name;

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
        }

        public static class OrganizationBean {
            /**
             * oid : 3
             * name : 龙虎网党支部
             * click : 1
             */

            private String oid;
            private String name;
            private String click;

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

            public String getClick() {
                return click;
            }

            public void setClick(String click) {
                this.click = click;
            }
        }
    }
}
