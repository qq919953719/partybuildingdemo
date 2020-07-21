package com.longhoo.net.partyaffairs.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ck on 2017/12/13.
 */

public class TaiZhangMenuBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"cid":1,"name":"党员大会"},{"cid":2,"name":"支委会"}]
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

    public static class DataBean implements Serializable{
        /**
         * cid : 1
         * name : 党员大会
         */

        private int cid;

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;
        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
