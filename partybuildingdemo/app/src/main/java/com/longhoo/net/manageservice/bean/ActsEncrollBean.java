package com.longhoo.net.manageservice.bean;

import java.util.List;

public class ActsEncrollBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"aid":"245","ptitle":"","cdate":"2015-01-22 15:48","enddate":"-0001-11-30 00:00","stime":"-0001-11-30 00:00","etime":"-0001-11-30 00:00","addr":"","addtime":"0000-00-00 00:00:00","styleid":2,"stylename":"已结束"},{"aid":"247","ptitle":"","cdate":"2015-01-22 15:49","enddate":"-0001-11-30 00:00","stime":"-0001-11-30 00:00","etime":"-0001-11-30 00:00","addr":"","addtime":"0000-00-00 00:00:00","styleid":2,"stylename":"已结束"},{"aid":"249","ptitle":"","cdate":"2015-01-22 15:49","enddate":"-0001-11-30 00:00","stime":"-0001-11-30 00:00","etime":"-0001-11-30 00:00","addr":"","addtime":"0000-00-00 00:00:00","styleid":2,"stylename":"已结束"}]
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
         * aid : 245
         * ptitle :
         * cdate : 2015-01-22 15:48
         * enddate : -0001-11-30 00:00
         * stime : -0001-11-30 00:00
         * etime : -0001-11-30 00:00
         * addr :
         * addtime : 0000-00-00 00:00:00
         * styleid : 2
         * stylename : 已结束
         */

        private String aid;
        private String ptitle;
        private String cdate;
        private String enddate;
        private String stime;
        private String etime;
        private String addr;
        private String addtime;
        private int styleid;
        private String stylename;


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String status;
        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getStyleid() {
            return styleid;
        }

        public void setStyleid(int styleid) {
            this.styleid = styleid;
        }

        public String getStylename() {
            return stylename;
        }

        public void setStylename(String stylename) {
            this.stylename = stylename;
        }
    }
}
