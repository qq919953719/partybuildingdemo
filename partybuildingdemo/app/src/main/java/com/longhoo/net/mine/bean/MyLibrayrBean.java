package com.longhoo.net.mine.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/28.
 */

public class MyLibrayrBean {


    /**
     * code : 0
     * msg : 查找成功
     * data : [{"id":"20","gid":"22","uid":"1134","bstatus":"1","time":"1515048808","jtime":"1515484893","gname":"中国共产党与西部大开发","thumb":"http://test.025nj.com/dangjian/UploadFile/books/20171229/thumb_15145180195a45b6030caa9.jpg","rtime":"2018-2-09"},{"id":"21","gid":"23","uid":"1134","bstatus":"1","time":"1515056864","jtime":"1515137112","gname":"辉煌岁月","thumb":"http://test.025nj.com/dangjian/UploadFile/books/20171229/thumb_15145180445a45b61c7a5e5.jpg","rtime":"2018-2-05"}]
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
         * id : 20
         * gid : 22
         * uid : 1134
         * bstatus : 1
         * time : 1515048808
         * jtime : 1515484893
         * gname : 中国共产党与西部大开发
         * thumb : http://test.025nj.com/dangjian/UploadFile/books/20171229/thumb_15145180195a45b6030caa9.jpg
         * rtime : 2018-2-09
         */

        private String id;
        private String gid;
        private String uid;
        private String bstatus;
        private String time;
        private String jtime;
        private String gname;
        private String thumb;
        private String rtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBstatus() {
            return bstatus;
        }

        public void setBstatus(String bstatus) {
            this.bstatus = bstatus;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getJtime() {
            return jtime;
        }

        public void setJtime(String jtime) {
            this.jtime = jtime;
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

        public String getRtime() {
            return rtime;
        }

        public void setRtime(String rtime) {
            this.rtime = rtime;
        }
    }
}
