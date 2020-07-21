package com.longhoo.net.mine.bean;

import java.util.List;

/**
 * Created by CC on 2018/5/9.
 * Email:910663958@qq.com
 */

public class NoteDetailBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"info":{"id":2120,"title":"777","content":"Yyi","uploads":[],"uid":10106,"time":1581934152,"listorder":0,"type":null,"comments":[{"id":1,"bid":2120,"content":"11111","time":"2020-02-18 12:27:29"}]}}
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
         * info : {"id":2120,"title":"777","content":"Yyi","uploads":[],"uid":10106,"time":1581934152,"listorder":0,"type":null,"comments":[{"id":1,"bid":2120,"content":"11111","time":"2020-02-18 12:27:29"}]}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * id : 2120
             * title : 777
             * content : Yyi
             * uploads : []
             * uid : 10106
             * time : 1581934152
             * listorder : 0
             * type : null
             * comments : [{"id":1,"bid":2120,"content":"11111","time":"2020-02-18 12:27:29"}]
             */

            private int id;
            private String title;
            private String content;
            private int uid;
            private int time;
            private int listorder;
            private Object type;
            private List<String> uploads;
            private List<CommentsBean> comments;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getListorder() {
                return listorder;
            }

            public void setListorder(int listorder) {
                this.listorder = listorder;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public List<String> getUploads() {
                return uploads;
            }

            public void setUploads(List<String> uploads) {
                this.uploads = uploads;
            }

            public List<CommentsBean> getComments() {
                return comments;
            }

            public void setComments(List<CommentsBean> comments) {
                this.comments = comments;
            }

            public static class CommentsBean {
                /**
                 * id : 1
                 * bid : 2120
                 * content : 11111
                 * time : 2020-02-18 12:27:29
                 */

                private int id;
                private int bid;
                private String content;
                private String time;

                public String getRealname() {
                    return realname;
                }

                public void setRealname(String realname) {
                    this.realname = realname;
                }

                private String realname;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getBid() {
                    return bid;
                }

                public void setBid(int bid) {
                    this.bid = bid;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
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
}
