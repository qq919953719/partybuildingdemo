package com.longhoo.net.study.bean;

import java.util.List;

/**
 * Created by CK on 2017/12/27
 * Email:910663958@qq.com
 */

public class LiveDetailListBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":"8","lid":"1","uid":"1","title":"4444444","description":"444444444","thumb":"http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495ad02032_640.jpg","photos":[{"url":"http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495aa721f9_640.jpg","alt":"./UploadFile/2017/1228/5a4495aa721f9_640.jpg"},{"url":"http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495aa8fca5_640.jpg","alt":"./UploadFile/2017/1228/5a4495aa8fca5_640.jpg"},{"url":"http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495aaaed5a_640.jpg","alt":"./UploadFile/2017/1228/5a4495aaaed5a_640.jpg"}],"content":"<p>4444444444<br/><\/p>","time":"1514444206"}]}
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
             * id : 8
             * lid : 1
             * uid : 1
             * title : 4444444
             * description : 444444444
             * thumb : http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495ad02032_640.jpg
             * photos : [{"url":"http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495aa721f9_640.jpg","alt":"./UploadFile/2017/1228/5a4495aa721f9_640.jpg"},{"url":"http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495aa8fca5_640.jpg","alt":"./UploadFile/2017/1228/5a4495aa8fca5_640.jpg"},{"url":"http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495aaaed5a_640.jpg","alt":"./UploadFile/2017/1228/5a4495aaaed5a_640.jpg"}]
             * content : <p>4444444444<br/></p>
             * time : 1514444206
             */

            private String id;
            private String lid;
            private String uid;
            private String title;
            private String description;
            private String thumb;
            private String content;
            private String time;
            private List<PhotosBean> photos;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLid() {
                return lid;
            }

            public void setLid(String lid) {
                this.lid = lid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public List<PhotosBean> getPhotos() {
                return photos;
            }

            public void setPhotos(List<PhotosBean> photos) {
                this.photos = photos;
            }

            public static class PhotosBean {
                /**
                 * url : http://test.025nj.com/dangjian/UploadFile/2017/1228/5a4495aa721f9_640.jpg
                 * alt : ./UploadFile/2017/1228/5a4495aa721f9_640.jpg
                 */

                private String url;
                private String alt;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getAlt() {
                    return alt;
                }

                public void setAlt(String alt) {
                    this.alt = alt;
                }
            }
        }
    }
}
