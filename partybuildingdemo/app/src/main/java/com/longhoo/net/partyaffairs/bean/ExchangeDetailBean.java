package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by ck on 2017/12/18.
 */

public class ExchangeDetailBean {
    /**
     * code : 0
     * msg : 查询成功
     * data : [{"thumb":"goods/20171212/thumb_15130445145a2f3a22aabd5.png","gname":"trhtr","score":"66","time":"1513047089"},{"thumb":"goods/20171212/thumb_15130445145a2f3a22aabd5.png","gname":"trhtr","score":"66","time":"1513047232"}]
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
         * thumb : goods/20171212/thumb_15130445145a2f3a22aabd5.png
         * gname : trhtr
         * score : 66
         * time : 1513047089
         */

        private String thumb;
        private String gname;
        private String score;
        private String time;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
