package com.longhoo.net.mine.bean;

import java.util.List;

public class MyEnrollBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"id":1697,"ptitle":"是否","cdate":"2018-12-25 09:00:00","enddate":"2018-12-27 10:00:00","stime":"2018-12-26 11:20:00","etime":"2018-12-27 00:00:00","addr":"是的发送阿斯顿发送到啥打法是否对"}]
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
         * id : 1697
         * ptitle : 是否
         * cdate : 2018-12-25 09:00:00
         * enddate : 2018-12-27 10:00:00
         * stime : 2018-12-26 11:20:00
         * etime : 2018-12-27 00:00:00
         * addr : 是的发送阿斯顿发送到啥打法是否对
         */

        private int id;
        private String ptitle;
        private String cdate;
        private String enddate;
        private String stime;
        private String etime;
        private String addr;
        private String addtime;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPtitle() {
            return ptitle;
        }

        public void setPtitle(String ptitle) {
            this.ptitle = ptitle;
        }

        public String getCdate() {
            return cdate;
        }

        public void setCdate(String cdate) {
            this.cdate = cdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }
}
