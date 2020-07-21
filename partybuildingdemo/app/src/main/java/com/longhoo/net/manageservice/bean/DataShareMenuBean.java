package com.longhoo.net.manageservice.bean;

import java.util.List;

public class DataShareMenuBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":[{"fid":"1","name":"专项材料上报","status":"0","listorder":"1"},{"fid":"2","name":"获奖材料上报","status":"0","listorder":"2"}]}
     */

    private String code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * fid : 1
             * name : 专项材料上报
             * status : 0
             * listorder : 1
             */

            private String fid;
            private String name;
            private String status;
            private String listorder;

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getListorder() {
                return listorder;
            }

            public void setListorder(String listorder) {
                this.listorder = listorder;
            }
        }
    }
}
