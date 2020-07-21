package com.longhoo.net.study.bean;

import java.util.List;

/**
 * Created by ck on 2018/12/9.
 */

public class ClassDisplayMenuBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"cid":"1","name":"分类"},{"cid":"2","name":"分类2"},{"cid":"3","name":"分类3"}]
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
         * cid : 1
         * name : 分类
         */

        private String cid;
        private String name;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
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
