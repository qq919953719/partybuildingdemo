package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by ck on 2017/12/18.
 */

public class ScoreDetailBean {
    /**
     * code : 0
     * msg : 查询成功
     * data : [{"score":"30","note":"签到成功","time":"1513047089"}]
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
         * score : 30
         * note : 签到成功
         * time : 1513047089
         */

        private String score;
        private String note;
        private String time;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
