package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by CK on 2018/4/24.
 * Email:910663958@qq.com
 */

public class DocumentBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"news":[{"nid":"595","pic":"","title":"11","filename":"9.30 活动详情页面修改.doc","files":"http://dangjiantest.025nj.com/UploadFile/files/job/20180425/15246396235ae0278763290.doc","filesize":"219136","time":"1524639623","browse":"0","mid":"25"},{"nid":"594","pic":"","title":"不需要所属tab","filename":"4.25文明旅游主题宣传活动直播需求.xlsx","files":"http://dangjiantest.025nj.com/UploadFile/files/job/20180425/15246215015adfe0bda20cf.xlsx","filesize":"9546","time":"1524621501","browse":"0","mid":"24"},{"nid":"593","pic":"","title":"teste","filename":"1470712849.xls","files":"http://dangjiantest.025nj.com/UploadFile/files/job/20180424/15245391015ade9edd8e648.xls","filesize":"104960","time":"1524539101","browse":"0","mid":"5"}]}
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
             * nid : 595
             * pic :
             * title : 11
             * filename : 9.30 活动详情页面修改.doc
             * files : http://dangjiantest.025nj.com/UploadFile/files/job/20180425/15246396235ae0278763290.doc
             * filesize : 219136
             * time : 1524639623
             * browse : 0
             * mid : 25
             */

            private String nid;
            private String pic;
            private String title;
            private String filename;
            private String files;
            private String filesize;
            private String time;
            private String browse;
            private String mid;

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

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getFiles() {
                return files;
            }

            public void setFiles(String files) {
                this.files = files;
            }

            public String getFilesize() {
                return filesize;
            }

            public void setFilesize(String filesize) {
                this.filesize = filesize;
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

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }
        }
    }
}
