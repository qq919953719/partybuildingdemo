package com.longhoo.net.partyaffairs.bean;

import java.util.List;

public class CategrovyVoteNode {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"id":12,"name":"十佳青年"},{"id":13,"name":"巾帼榜样"},{"id":14,"name":"最美紫金人"},{"id":42,"name":"最美党员先锋模范"}]
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
         * id : 12
         * name : 十佳青年
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
