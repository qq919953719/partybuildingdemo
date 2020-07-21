package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by ${CC} on 2018/1/3.
 */

public class ApplyBean {


    /**
     * code : 0
     * msg : 查找成功
     * data : [{"id":"1","name":"孟成真1·1同志入党申请审批流程","status":"2","oname":"龙虎网党总支"},{"id":"2","name":"测试同志入党申请审批流程","status":"2","oname":"龙虎网党总支"}]
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
         * id : 1
         * name : 孟成真1·1同志入党申请审批流程
         * status : 2
         * oname : 龙虎网党总支
         */

        private String id;
        private String name;
        private String status;
        private String oname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getOname() {
            return oname;
        }

        public void setOname(String oname) {
            this.oname = oname;
        }
    }
}
