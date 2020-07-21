package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by CK on 2018/1/3.
 * Email:910663958@qq.com
 */

public class MemberShipRecordsBean {
    private String code;
    private String msg;
    private List<MemberShipRecordsBean.DataBean> data;

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

    public List<MemberShipRecordsBean.DataBean> getData() {
        return data;
    }

    public void setData(List<MemberShipRecordsBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String ordernum;
        private String fee;
        private String remark;
        private String status;
        private String type;
        private String time;
        private String content;

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
