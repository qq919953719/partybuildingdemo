package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/5.
 */

public class PTCConetntBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"said":{"uid":"1","nickname":"文明南京","headpic":"","sid":"12634","pic":["http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030bd761.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030bdd78.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be21b.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be52c.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be855.jpg"],"picbig":["http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030bd761.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030bdd78.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be21b.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be52c.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be855.jpg"],"title":"wdwdw","info":"Dwdwdwdw","time":"1498722352","browse":"1","com":"0","zan":"0","iszan":"0"}}
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
         * said : {"uid":"1","nickname":"文明南京","headpic":"","sid":"12634","pic":["http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030bd761.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030bdd78.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be21b.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be52c.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be855.jpg"],"picbig":["http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030bd761.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030bdd78.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be21b.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be52c.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be855.jpg"],"title":"wdwdw","info":"Dwdwdwdw","time":"1498722352","browse":"1","com":"0","zan":"0","iszan":"0"}
         */

        private SaidBean said;

        public SaidBean getSaid() {
            return said;
        }

        public void setSaid(SaidBean said) {
            this.said = said;
        }

        public static class SaidBean {
            /**
             * uid : 1
             * nickname : 文明南京
             * headpic :
             * sid : 12634
             * pic : ["http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030bd761.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030bdd78.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be21b.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be52c.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/thumb_14987223525954b030be855.jpg"]
             * picbig : ["http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030bd761.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030bdd78.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be21b.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be52c.jpg","http://test.025nj.com/dangjian/UploadFile/said/20170629/14987223525954b030be855.jpg"]
             * title : wdwdw
             * info : Dwdwdwdw
             * time : 1498722352
             * browse : 1
             * com : 0
             * zan : 0
             * iszan : 0
             */

            private String uid;
            private String nickname;
            private String headpic;
            private String sid;
            private String title;
            private String info;
            private String time;
            private String browse;
            private String com;
            private String zan;
            private String iszan;
            private List<String> pic;
            private List<String> picbig;
            private String video_url;

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            public String getVideo_img() {
                return video_img;
            }

            public void setVideo_img(String video_img) {
                this.video_img = video_img;
            }

            private String video_img;
            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHeadpic() {
                return headpic;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

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

            public String getBrowse() {
                return browse;
            }

            public void setBrowse(String browse) {
                this.browse = browse;
            }

            public String getCom() {
                return com;
            }

            public void setCom(String com) {
                this.com = com;
            }

            public String getZan() {
                return zan;
            }

            public void setZan(String zan) {
                this.zan = zan;
            }

            public String getIszan() {
                return iszan;
            }

            public void setIszan(String iszan) {
                this.iszan = iszan;
            }

            public List<String> getPic() {
                return pic;
            }

            public void setPic(List<String> pic) {
                this.pic = pic;
            }

            public List<String> getPicbig() {
                return picbig;
            }

            public void setPicbig(List<String> picbig) {
                this.picbig = picbig;
            }
        }
    }
}
