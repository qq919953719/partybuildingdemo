package com.longhoo.net.study.bean;

import java.util.List;

public class VideoCommentBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"time":"2019-01-07 16:37:07","content":"1111111","realname":"测试2","headpic":"http://ngy3.dangjian.025nj.com/UploadFile/head/2018/1206/5c08e5dd3cc2e.png"},{"time":"2019-01-07 10:33:36","content":"1111111","realname":"演示账号","headpic":"http://ngy3.dangjian.025nj.com/UploadFile/head/2018/1212/5c10d89a358b9.png"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2019-01-07 16:37:07
         * content : 1111111
         * realname : 测试2
         * headpic : http://ngy3.dangjian.025nj.com/UploadFile/head/2018/1206/5c08e5dd3cc2e.png
         */

        private String time;
        private String content;
        private String realname;
        private String headpic;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
