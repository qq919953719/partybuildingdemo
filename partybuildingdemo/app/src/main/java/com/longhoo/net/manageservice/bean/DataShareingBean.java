package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by ck on 2018/12/9.
 */

public class DataShareingBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":[{"sid":"16","title":"2222","content":"asdf <\/p>","files":"http://ngy2.dangjian.025nj.com/UploadFile/files/study/20181209/15443326665c0ca57ac264e.xlsx","filename":"孟成真.xlsx","time":"2018-12-09 13:17:46"}]}
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
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * sid : 16
             * title : 2222
             * content : asdf </p>
             * files : http://ngy2.dangjian.025nj.com/UploadFile/files/study/20181209/15443326665c0ca57ac264e.xlsx
             * filename : 孟成真.xlsx
             * time : 2018-12-09 13:17:46
             */

            private String sid;
            private String title;
            private String content;
            private String files;
            private String filename;
            private String time;

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
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

            public String getFiles() {
                return files;
            }

            public void setFiles(String files) {
                this.files = files;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
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
