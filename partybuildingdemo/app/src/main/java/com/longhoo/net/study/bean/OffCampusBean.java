package com.longhoo.net.study.bean;

import java.util.List;

public class OffCampusBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"aid":"3","name":"南工院","addtime":"2018-12-05 17:18:57"},{"aid":"2","name":"test","addtime":"2018-12-05 09:45:03"}]
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
         * aid : 3
         * name : 南工院
         * addtime : 2018-12-05 17:18:57
         */

        private String aid;
        private String name;
        private String addtime;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
