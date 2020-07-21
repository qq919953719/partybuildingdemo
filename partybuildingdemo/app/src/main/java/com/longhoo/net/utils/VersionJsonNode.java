package com.longhoo.net.utils;

/**
 * Created by cuichuang on 2017/8/4.
 */

public class VersionJsonNode {


    /**
     * code : 0
     * msg :
     * data : {"version":{"vid":"116","vcode":"1","vname":"1.0","vurl":"version/20170921/com.longhoo.decorumnj_1.apk","vdate":"2017-09-21 10:22:50","vinfo":"文明南京","type":"1","status":"0","url":"http://test.025nj.com/dangjian/UploadFile/version/20170921/com.longhoo.decorumnj_1.apk"}}
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
         * version : {"vid":"116","vcode":"1","vname":"1.0","vurl":"version/20170921/com.longhoo.decorumnj_1.apk","vdate":"2017-09-21 10:22:50","vinfo":"文明南京","type":"1","status":"0","url":"http://test.025nj.com/dangjian/UploadFile/version/20170921/com.longhoo.decorumnj_1.apk"}
         */

        private VersionBean version;

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public static class VersionBean {
            /**
             * vid : 116
             * vcode : 1
             * vname : 1.0
             * vurl : version/20170921/com.longhoo.decorumnj_1.apk
             * vdate : 2017-09-21 10:22:50
             * vinfo : 文明南京
             * type : 1
             * status : 0
             * url : http://test.025nj.com/dangjian/UploadFile/version/20170921/com.longhoo.decorumnj_1.apk
             */

            private String vid;
            private String vcode;
            private String vname;
            private String vurl;
            private String vdate;
            private String vinfo;
            private String type;
            private String status;
            private String url;

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getVcode() {
                return vcode;
            }

            public void setVcode(String vcode) {
                this.vcode = vcode;
            }

            public String getVname() {
                return vname;
            }

            public void setVname(String vname) {
                this.vname = vname;
            }

            public String getVurl() {
                return vurl;
            }

            public void setVurl(String vurl) {
                this.vurl = vurl;
            }

            public String getVdate() {
                return vdate;
            }

            public void setVdate(String vdate) {
                this.vdate = vdate;
            }

            public String getVinfo() {
                return vinfo;
            }

            public void setVinfo(String vinfo) {
                this.vinfo = vinfo;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
