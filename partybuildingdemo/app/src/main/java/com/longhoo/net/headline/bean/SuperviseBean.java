package com.longhoo.net.headline.bean;

/**
 * Created by CK on 2018/5/14.
 * Email:910663958@qq.com
 */

public class SuperviseBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"info":"<img width='100%' src='http://dangjiantest.025nj.com/UploadFile/'/><p>啊啊啊啊啊<\/p>","time":"1526286542"}
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
         * info : <img width='100%' src='http://dangjiantest.025nj.com/UploadFile/'/><p>啊啊啊啊啊</p>
         * time : 1526286542
         */

        private String info;
        private String time;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
