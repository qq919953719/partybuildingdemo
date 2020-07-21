package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by ck on 2017/12/18.
 */

public class IntegralExchangeBean {
    /**
     * code : 0
     * msg : 查询成功
     * data : [{"gid":"2","gname":"fewfewfew","thumb":"goods/20171211/thumb_15129836535a2e4c6598537.png","score":"23","store":"23","time":"1512983655"},{"gid":"1","gname":"dianzishu","thumb":"goods/20171211/thumb_15129797255a2e3d0dcbf3a.png","score":"22","store":"111","time":"1512979737"}]
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
         * gid : 2
         * gname : fewfewfew
         * thumb : goods/20171211/thumb_15129836535a2e4c6598537.png
         * score : 23
         * store : 23
         * time : 1512983655
         */

        private String gid;
        private String gname;
        private String thumb;
        private String score;
        private String store;
        private String time;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
