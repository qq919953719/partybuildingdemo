package com.longhoo.net.mine.bean;

import java.util.List;

public class ReadNumBean {


    /**
     * code : 0
     * msg : 成功
     * data : [{"date":"1月","num":7,"duration":4},{"date":"2月","num":7,"duration":4},{"date":"3月","num":7,"duration":4},{"date":"4月","num":7,"duration":4},{"date":"5月","num":7,"duration":4},{"date":"6月","num":7,"duration":4},{"date":"7月","num":7,"duration":4},{"date":"8月","num":7,"duration":4},{"date":"9月","num":7,"duration":4},{"date":"10月","num":7,"duration":4},{"date":"11月","num":7,"duration":4},{"date":"12月","num":7,"duration":4}]
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
         * date : 1月
         * num : 7
         * duration : 4
         */

        private String date;
        private int num;
        private int duration;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}
