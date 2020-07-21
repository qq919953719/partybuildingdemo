package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by CK on 2018/5/4.
 * Email:910663958@qq.com
 */

public class PayMoonListBean {

    /**
     * code : 0
     * msg : 成功
     * data : [{"year":"2019","lists":[{"id":5953,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"1","oid":48,"wechatorder":""},{"id":5962,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"10","oid":48,"wechatorder":""},{"id":5963,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"11","oid":48,"wechatorder":""},{"id":5964,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"12","oid":48,"wechatorder":""},{"id":5954,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"2","oid":48,"wechatorder":""},{"id":5955,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"3","oid":48,"wechatorder":""},{"id":5956,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"4","oid":48,"wechatorder":""},{"id":5957,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"5","oid":48,"wechatorder":""},{"id":5958,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"6","oid":48,"wechatorder":""},{"id":5959,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"7","oid":48,"wechatorder":""},{"id":5960,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"8","oid":48,"wechatorder":""},{"id":5961,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"9","oid":48,"wechatorder":""}]}]
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
         * year : 2019
         * lists : [{"id":5953,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"1","oid":48,"wechatorder":""},{"id":5962,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"10","oid":48,"wechatorder":""},{"id":5963,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"11","oid":48,"wechatorder":""},{"id":5964,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"12","oid":48,"wechatorder":""},{"id":5954,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"2","oid":48,"wechatorder":""},{"id":5955,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"3","oid":48,"wechatorder":""},{"id":5956,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"4","oid":48,"wechatorder":""},{"id":5957,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"5","oid":48,"wechatorder":""},{"id":5958,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"6","oid":48,"wechatorder":""},{"id":5959,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"7","oid":48,"wechatorder":""},{"id":5960,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"8","oid":48,"wechatorder":""},{"id":5961,"uid":503,"uname":"测试2","ordernum":"","yfee":10,"fee":null,"remark":"","status":0,"type":1,"content":"","time":0,"json":"0","year":"2019","month":"9","oid":48,"wechatorder":""}]
         */

        private String year;
        private List<ListsBean> lists;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * id : 5953
             * uid : 503
             * uname : 测试2
             * ordernum :
             * yfee : 10
             * fee : null
             * remark :
             * status : 0
             * type : 1
             * content :
             * time : 0
             * json : 0
             * year : 2019
             * month : 1
             * oid : 48
             * wechatorder :
             */

            private int id;
            private int uid;
            private String uname;
            private String ordernum;
            private int yfee;
            private Object fee;
            private String remark;
            private int status;
            private int type;
            private String content;
            private int time;
            private String json;
            private String year;
            private String month;
            private int oid;
            private String wechatorder;
            private int canPay ;

            public int getCanPay() {
                return canPay;
            }

            public void setCanPay(int canPay) {
                this.canPay = canPay;
            }

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

            public int getYfee() {
                return yfee;
            }

            public void setYfee(int yfee) {
                this.yfee = yfee;
            }

            public Object getFee() {
                return fee;
            }

            public void setFee(Object fee) {
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

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
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

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
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
}
