package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/14.
 */

public class OrganizationallCommentBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"coms":[{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"491","info":"12","time":"1515466966"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"490","info":"11","time":"1515466942"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"489","info":"10","time":"1515466939"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"488","info":"9","time":"1515466935"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"487","info":"8","time":"1515466932"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"486","info":"7","time":"1515466929"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"485","info":"6","time":"1515466926"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"484","info":"5","time":"1515466923"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"483","info":"4","time":"1515466919"},{"uid":"1138","realname":"二一党","headpic":"http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg","scid":"482","info":"3","time":"1515466916"}]}
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
        private List<ComsBean> coms;

        public List<ComsBean> getComs() {
            return coms;
        }

        public void setComs(List<ComsBean> coms) {
            this.coms = coms;
        }

        public static class ComsBean {
            /**
             * uid : 1138
             * realname : 二一党
             * headpic : http://test.025nj.com/dangjian/UploadFile/UploadFile/head/2018/0102/5a4b19aeb8a9a.jpg
             * scid : 491
             * info : 12
             * time : 1515466966
             */

            private String uid;
            private String realname;
            private String headpic;
            private String scid;
            private String info;
            private String time;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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

            public String getScid() {
                return scid;
            }

            public void setScid(String scid) {
                this.scid = scid;
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
        }
    }
}
