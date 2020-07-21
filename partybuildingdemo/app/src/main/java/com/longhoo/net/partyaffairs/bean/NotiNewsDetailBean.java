package com.longhoo.net.partyaffairs.bean;

/**
 * Created by CK on 2018/1/4.
 */

public class NotiNewsDetailBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"mid":1856,"title":"测试测试","info":"<p>士大夫<br/><\/p>","time":1547537162}
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
         * mid : 1856
         * title : 测试测试
         * info : <p>士大夫<br/></p>
         * time : 1547537162
         */

        private int mid;
        private String title;
        private String info;
        private int time;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
