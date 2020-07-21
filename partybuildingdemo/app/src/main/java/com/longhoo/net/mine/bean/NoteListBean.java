package com.longhoo.net.mine.bean;

import java.util.List;

/**
 * Created by CK on 2018/5/8.
 * Email:910663958@qq.com
 */

public class NoteListBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"biji":[{"id":"1","title":"11","content":"11","uploads":"1111","uid":"3","time":"0"}]}
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
        private List<BijiBean> biji;

        public List<BijiBean> getBiji() {
            return biji;
        }

        public void setBiji(List<BijiBean> biji) {
            this.biji = biji;
        }

        public static class BijiBean {
            /**
             * id : 1
             * title : 11
             * content : 11
             * uploads : 1111
             * uid : 3
             * time : 0
             */

            private String id;
            private String title;
            private String content;
            private String uploads;
            private String uid;
            private String time;
            private String realname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getUploads() {
                return uploads;
            }

            public void setUploads(String uploads) {
                this.uploads = uploads;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }
        }
    }
}
