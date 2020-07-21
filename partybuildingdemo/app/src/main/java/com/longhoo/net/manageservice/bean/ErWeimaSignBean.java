package com.longhoo.net.manageservice.bean;

import java.util.List;

public class ErWeimaSignBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"ewm_img":"http://ngy3.dangjian.025nj.com/upload/ewm/1547631436160.png","userlist":[{"id":26,"time":1547464532,"realname":"刘畅"},{"id":27,"time":1547464552,"realname":"测试2"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
         * ewm_img : http://ngy3.dangjian.025nj.com/upload/ewm/1547631436160.png
         * userlist : [{"id":26,"time":1547464532,"realname":"刘畅"},{"id":27,"time":1547464552,"realname":"测试2"}]
         */

        private String ewm_img;
        private List<UserlistBean> userlist;

        public String getEwm_img() {
            return ewm_img;
        }

        public void setEwm_img(String ewm_img) {
            this.ewm_img = ewm_img;
        }

        public List<UserlistBean> getUserlist() {
            return userlist;
        }

        public void setUserlist(List<UserlistBean> userlist) {
            this.userlist = userlist;
        }

        public static class UserlistBean {
            /**
             * id : 26
             * time : 1547464532
             * realname : 刘畅
             */

            private int id;
            private int time;
            private String realname;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
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
