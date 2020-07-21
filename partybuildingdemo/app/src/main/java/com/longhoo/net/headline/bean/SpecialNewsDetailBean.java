package com.longhoo.net.headline.bean;

import java.util.List;

/**
 * Created by CK on 2017/7/18.
 */

public class SpecialNewsDetailBean {
    /**
     * code : 0
     * msg : 查询成功
     * data : {"special":{"nid":"77","pic":"http://decorumtest.025nj.com/UploadFile/celebrity/20170612/thumb_1497256600593e529885367.jpg","title":"环保专题","digest":"颜真卿","browse":"27","isenshrine":"0"},"news":[{"nid":"86","pic":"http://decorumtest.025nj.com/UploadFile/news/20170628/thumb_1498634662595359a63a5f0.jpg","title":"7月起南京月最低工资标准将调整为1890元/月","digest":"","time":"1498634663","browse":"0"}]}
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
         * special : {"nid":"77","pic":"http://decorumtest.025nj.com/UploadFile/celebrity/20170612/thumb_1497256600593e529885367.jpg","title":"环保专题","digest":"颜真卿","browse":"27","isenshrine":"0"}
         * news : [{"nid":"86","pic":"http://decorumtest.025nj.com/UploadFile/news/20170628/thumb_1498634662595359a63a5f0.jpg","title":"7月起南京月最低工资标准将调整为1890元/月","digest":"","time":"1498634663","browse":"0"}]
         */

        private SpecialBean special;
        private List<NewsBean> news;

        public SpecialBean getSpecial() {
            return special;
        }

        public void setSpecial(SpecialBean special) {
            this.special = special;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class SpecialBean {
            /**
             * nid : 77
             * pic : http://decorumtest.025nj.com/UploadFile/celebrity/20170612/thumb_1497256600593e529885367.jpg
             * title : 环保专题
             * digest : 颜真卿
             * browse : 27
             * isenshrine : 0
             */

            private String nid;
            private String pic;
            private String title;
            private String digest;
            private String browse;
            private String isenshrine;

            public String getNid() {
                return nid;
            }

            public void setNid(String nid) {
                this.nid = nid;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getBrowse() {
                return browse;
            }

            public void setBrowse(String browse) {
                this.browse = browse;
            }

            public String getIsenshrine() {
                return isenshrine;
            }

            public void setIsenshrine(String isenshrine) {
                this.isenshrine = isenshrine;
            }

        }

        public static class NewsBean {
            /**
             * nid : 86
             * pic : http://decorumtest.025nj.com/UploadFile/news/20170628/thumb_1498634662595359a63a5f0.jpg
             * title : 7月起南京月最低工资标准将调整为1890元/月
             * digest :
             * time : 1498634663
             * browse : 0
             */

            private String nid;
            private String pic;
            private String title;
            private String digest;
            private String time;
            private String browse;

            public String getNid() {
                return nid;
            }

            public void setNid(String nid) {
                this.nid = nid;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
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
        }
    }
}
