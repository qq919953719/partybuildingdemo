package com.longhoo.net.study.bean;

import java.util.List;

public class VideoConCommentBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":46,"lid":4,"uid":503,"content":"你好，南工院","status":2,"time":"2018-02-26 17:02:28","realname":"测试2","headpic":""},{"id":35,"lid":4,"uid":1,"content":"222","status":2,"time":"2018-01-11 09:01:54","realname":"步雨笋","headpic":""}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 46
             * lid : 4
             * uid : 503
             * content : 你好，南工院
             * status : 2
             * time : 2018-02-26 17:02:28
             * realname : 测试2
             * headpic :
             */

            private int id;
            private int lid;
            private int uid;
            private String content;
            private int status;
            private String time;
            private String realname;
            private String headpic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLid() {
                return lid;
            }

            public void setLid(int lid) {
                this.lid = lid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getHeadpic() {
                return headpic;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }
        }
    }
}
