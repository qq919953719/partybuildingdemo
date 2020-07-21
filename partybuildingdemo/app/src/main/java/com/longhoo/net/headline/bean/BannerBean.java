package com.longhoo.net.headline.bean;

import java.io.Serializable;
import java.util.List;

public class BannerBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"aid":632,"pic":"http://ngy3.dangjian.025nj.com/upload/20181229/c2953457e4ed14ce133834d44795f326.jpg","title":"新时代 新思想 新目标 新征程","type":0,"url":""}]
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

    public static class DataBean implements Serializable{
        /**
         * aid : 632
         * pic : http://ngy3.dangjian.025nj.com/upload/20181229/c2953457e4ed14ce133834d44795f326.jpg
         * title : 新时代 新思想 新目标 新征程
         * type : 0
         * url :
         */

        private int aid;
        private String pic;
        private String title;
        private int type;
        private String url;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
