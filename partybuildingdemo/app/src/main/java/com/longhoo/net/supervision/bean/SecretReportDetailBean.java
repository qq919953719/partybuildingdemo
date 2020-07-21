package com.longhoo.net.supervision.bean;

public class SecretReportDetailBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":{"id":18,"sid":30,"uid":503,"title":"阿斯顿发","content":"<p>阿斯顿发<br/><\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-05 16:04:29"}}
     */

    private String code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : {"id":18,"sid":30,"uid":503,"title":"阿斯顿发","content":"<p>阿斯顿发<br/><\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-05 16:04:29"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 18
             * sid : 30
             * uid : 503
             * title : 阿斯顿发
             * content : <p>阿斯顿发<br/></p>
             * put_file :
             * put_filename :
             * status : 0
             * time : 2019-01-05 16:04:29
             */

            private int id;
            private int sid;
            private int uid;
            private String title;
            private String content;
            private String put_file;
            private String put_filename;
            private int status;
            private String time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPut_file() {
                return put_file;
            }

            public void setPut_file(String put_file) {
                this.put_file = put_file;
            }

            public String getPut_filename() {
                return put_filename;
            }

            public void setPut_filename(String put_filename) {
                this.put_filename = put_filename;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
