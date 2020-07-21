package com.longhoo.net.partyaffairs.bean;

/**
 * Created by CK on 2017/12/26.
 * Email:910663958@qq.com
 */

public class LibraryDetailBean {

    /**
     * code : 0
     * msg : 查找成功
     * data : {"gid":"18","userid":"1","gname":"123","content":"<p>暗室逢灯阿什顿飞暗室逢灯暗室逢灯。<\/p><p>阿什顿飞阿什顿飞阿什顿飞as。<\/p><p>艾弗森的阿什顿飞阿什顿飞暗室逢灯大 阿斯顿是的。 啊时代发生地方阿什顿飞 &nbsp; 啊时代发生党费啊时代发生爱神阿斯顿啊。<\/p>","thumb":"books/20171226/thumb_15142555095a41b49554fb2.jpg","score":"11","time":"1514255510","status":"0","show":"1","type":"0"}
     */

    private String code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * gid : 18
         * userid : 1
         * gname : 123
         * content : <p>暗室逢灯阿什顿飞暗室逢灯暗室逢灯。</p><p>阿什顿飞阿什顿飞阿什顿飞as。</p><p>艾弗森的阿什顿飞阿什顿飞暗室逢灯大 阿斯顿是的。 啊时代发生地方阿什顿飞 &nbsp; 啊时代发生党费啊时代发生爱神阿斯顿啊。</p>
         * thumb : books/20171226/thumb_15142555095a41b49554fb2.jpg
         * score : 11
         * time : 1514255510
         * status : 0
         * show : 1
         * type : 0
         */

        private String gid;
        private String userid;
        private String gname;
        private String content;
        private String thumb;
        private String score;
        private String time;
        private String status;
        private String show;
        private String type;
        private String title;
        private String store;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }
    }
}
