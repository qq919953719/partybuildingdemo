package com.longhoo.net.mine.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/20.
 */

public class PartyFeeBean {


    /**
     * code : 0
     * msg : 成功
     * data : [{"id":25,"uid":3,"uname":"陈青","ordernum":"201901241101492526137","yfee":0.01,"fee":0.01,"remark":"","status":1,"type":1,"content":"2019年1月党费","time":"2019-1","json":"{\"appid\":\"wxc640271b6cbba949\",\"bank_type\":\"CFT\",\"cash_fee\":\"1\",\"fee_type\":\"CNY\",\"is_subscribe\":\"N\",\"mch_id\":\"1524824071\",\"nonce_str\":\"0af787945872196b42c9f73ead2565c8\",\"openid\":\"orkPt5zU-bizJ392qqeXBLQWaYT4\",\"out_trade_no\":\"201901241101492526137\",\"result_code\":\"SUCCESS\",\"return_code\":\"SUCCESS\",\"sign\":\"82E4147A475AF76C6958EACDF6C5B921\",\"time_end\":\"20190124112205\",\"total_fee\":\"1\",\"trade_type\":\"APP\",\"transaction_id\":\"4200000258201901241363588595\"}","year":"2019","month":1,"oid":17,"wechatorder":"4200000258201901241363588595"},{"id":26,"uid":3,"uname":"陈青","ordernum":"201901241001202654424","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年2月党费","time":"2019-2","json":"0","year":"2019","month":2,"oid":17,"wechatorder":""},{"id":27,"uid":3,"uname":"陈青","ordernum":"201901241001042760087","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年3月党费","time":"2019-3","json":"0","year":"2019","month":3,"oid":17,"wechatorder":""},{"id":28,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年4月党费","time":"2019-4","json":"0","year":"2019","month":4,"oid":17,"wechatorder":""},{"id":29,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年5月党费","time":"2019-5","json":"0","year":"2019","month":5,"oid":17,"wechatorder":""},{"id":30,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年6月党费","time":"2019-6","json":"0","year":"2019","month":6,"oid":17,"wechatorder":""},{"id":31,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年7月党费","time":"2019-7","json":"0","year":"2019","month":7,"oid":17,"wechatorder":""},{"id":32,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年8月党费","time":"2019-8","json":"0","year":"2019","month":8,"oid":17,"wechatorder":""},{"id":33,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年9月党费","time":"2019-9","json":"0","year":"2019","month":9,"oid":17,"wechatorder":""},{"id":34,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年10月党费","time":"2019-10","json":"0","year":"2019","month":10,"oid":17,"wechatorder":""},{"id":35,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年11月党费","time":"2019-11","json":"0","year":"2019","month":11,"oid":17,"wechatorder":""},{"id":36,"uid":3,"uname":"陈青","ordernum":"","yfee":0.01,"fee":null,"remark":"","status":0,"type":1,"content":"2019年12月党费","time":"2019-12","json":"0","year":"2019","month":12,"oid":17,"wechatorder":""}]
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
         * id : 25
         * uid : 3
         * uname : 陈青
         * ordernum : 201901241101492526137
         * yfee : 0.01
         * fee : 0.01
         * remark :
         * status : 1
         * type : 1
         * content : 2019年1月党费
         * time : 2019-1
         * json : {"appid":"wxc640271b6cbba949","bank_type":"CFT","cash_fee":"1","fee_type":"CNY","is_subscribe":"N","mch_id":"1524824071","nonce_str":"0af787945872196b42c9f73ead2565c8","openid":"orkPt5zU-bizJ392qqeXBLQWaYT4","out_trade_no":"201901241101492526137","result_code":"SUCCESS","return_code":"SUCCESS","sign":"82E4147A475AF76C6958EACDF6C5B921","time_end":"20190124112205","total_fee":"1","trade_type":"APP","transaction_id":"4200000258201901241363588595"}
         * year : 2019
         * month : 1
         * oid : 17
         * wechatorder : 4200000258201901241363588595
         */

        private int id;
        private int uid;
        private String uname;
        private String ordernum;
        private double yfee;
        private double fee;
        private String remark;
        private int status;
        private int type;
        private String content;
        private String time;
        private String json;
        private String year;
        private int month;
        private int oid;
        private String wechatorder;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public double getYfee() {
            return yfee;
        }

        public void setYfee(double yfee) {
            this.yfee = yfee;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public String getWechatorder() {
            return wechatorder;
        }

        public void setWechatorder(String wechatorder) {
            this.wechatorder = wechatorder;
        }
    }
}
