package com.longhoo.net.headline.bean;

import java.util.List;

/**
 * Created by ck on 2017/12/4.
 */

public class PartybuildxfBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"news":[{"nid":"366","pic":"http://test.025nj.com/dangjian/UploadFile/news/20171129/thumb_15119461695a1e77b9a7996.jpg","title":"2017年南京市 \u201c儒雅少年\u201d百强选手名单揭晓","digest":"","time":"1510903008","browse":"6","zan":"0","iszan":"0"}]}
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
        private List<NewsBean> news;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {
            /**
             * nid : 366
             * pic : http://test.025nj.com/dangjian/UploadFile/news/20171129/thumb_15119461695a1e77b9a7996.jpg
             * title : 2017年南京市 “儒雅少年”百强选手名单揭晓
             * digest :
             * time : 1510903008
             * browse : 6
             * zan : 0
             * iszan : 0
             */

            private String nid;
            private String pic;
            private String title;
            private String digest;
            private String time;
            private String browse;
            private String zan;
            private String iszan;

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
        }
    }
}
