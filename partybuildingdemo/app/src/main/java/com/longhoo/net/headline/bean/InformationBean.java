package com.longhoo.net.headline.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CK on 2018/1/3.
 * Email:910663958@qq.com
 */

public class InformationBean {
    private String code;
    private String msg;
    private InformationBean.DataBean data;

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

    public InformationBean.DataBean getData() {
        return data;
    }

    public void setData(InformationBean.DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private List<InformationBean.DataBean.NewsBean> news;

        public List<InformationBean.DataBean.NewsBean> getNews() {
            return news;
        }

        public void setNews(List<InformationBean.DataBean.NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean implements Serializable {

            private String mid;
            private String pic;
            private String title;
            private String time;
            private String type;
            private String tid;

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }
        }
    }
}
