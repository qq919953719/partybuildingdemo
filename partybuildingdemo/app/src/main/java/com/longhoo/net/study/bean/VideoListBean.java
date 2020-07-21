package com.longhoo.net.study.bean;

import java.util.List;

/**
 * Created by ck on 2017/12/14.
 */

public class VideoListBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":"1","name":"图文直播1","description":"图文直播1","listorder":"0","showtype":"1","status":"1","type":"0","starttime":"2017-12-12 14:00:22","endtime":"2017-12-12 14:00:22","istart":"0","isend":"0","hits":"0","add_hits":"0","roomid":"0","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1212/5a2f707edcca6.jpg","storestart":"2017-12-12 14:00:22","storeend":"2017-12-12 14:00:22","userid":"1","ispush":"2","sharethumb":"","save_url":"","save_time":"0","sumtime":"0","time":"1513058432"},{"id":"2","name":"视频直播1","description":"视频直播1","listorder":"0","showtype":"1","status":"1","type":"0","starttime":"2017-12-12 14:02:19","endtime":"2017-12-12 14:02:19","istart":"0","isend":"0","hits":"0","add_hits":"0","roomid":"dj-roomid-1","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1212/5a2f70f5bace4.jpg","storestart":"2017-12-12 14:02:19","storeend":"2017-12-12 14:02:19","userid":"1","ispush":"2","sharethumb":"","save_url":"","save_time":"0","sumtime":"0","time":"1513058553","zburl":"http://pili-live-hls.025nj.com.hlsv3.pilidns.com/njcast/dj-roomid-1.m3u8"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * name : 图文直播1
             * description : 图文直播1
             * listorder : 0
             * showtype : 1
             * status : 1
             * type : 0
             * starttime : 2017-12-12 14:00:22
             * endtime : 2017-12-12 14:00:22
             * istart : 0
             * isend : 0
             * hits : 0
             * add_hits : 0
             * roomid : 0
             *
             * thumb : http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1212/5a2f707edcca6.jpg
             * storestart : 2017-12-12 14:00:22
             * storeend : 2017-12-12 14:00:22
             * userid : 1
             * ispush : 2
             * sharethumb :
             * save_url :
             * save_time : 0
             * sumtime : 0
             * time : 1513058432
             * zburl : http://pili-live-hls.025nj.com.hlsv3.pilidns.com/njcast/dj-roomid-1.m3u8
             */

            private String id;
            private String name;
            private String description;
            private String listorder;
            private String showtype;
            private String status;
            private String type;
            private String starttime;
            private String endtime;
            private String istart;
            private String isend;
            private String hits;
            private String add_hits;
            private String roomid;
            private String thumb;
            private String storestart;
            private String storeend;
            private String userid;
            private String ispush;
            private String sharethumb;
            private String save_url;
            private String save_time;
            private String sumtime;
            private String time;
            private String zburl;
            private String score;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getListorder() {
                return listorder;
            }

            public void setListorder(String listorder) {
                this.listorder = listorder;
            }

            public String getShowtype() {
                return showtype;
            }

            public void setShowtype(String showtype) {
                this.showtype = showtype;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStarttime() {
                return starttime;
            }

            public void setStarttime(String starttime) {
                this.starttime = starttime;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getIstart() {
                return istart;
            }

            public void setIstart(String istart) {
                this.istart = istart;
            }

            public String getIsend() {
                return isend;
            }

            public void setIsend(String isend) {
                this.isend = isend;
            }

            public String getHits() {
                return hits;
            }

            public void setHits(String hits) {
                this.hits = hits;
            }

            public String getAdd_hits() {
                return add_hits;
            }

            public void setAdd_hits(String add_hits) {
                this.add_hits = add_hits;
            }

            public String getRoomid() {
                return roomid;
            }

            public void setRoomid(String roomid) {
                this.roomid = roomid;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getStorestart() {
                return storestart;
            }

            public void setStorestart(String storestart) {
                this.storestart = storestart;
            }

            public String getStoreend() {
                return storeend;
            }

            public void setStoreend(String storeend) {
                this.storeend = storeend;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getIspush() {
                return ispush;
            }

            public void setIspush(String ispush) {
                this.ispush = ispush;
            }

            public String getSharethumb() {
                return sharethumb;
            }

            public void setSharethumb(String sharethumb) {
                this.sharethumb = sharethumb;
            }

            public String getSave_url() {
                return save_url;
            }

            public void setSave_url(String save_url) {
                this.save_url = save_url;
            }

            public String getSave_time() {
                return save_time;
            }

            public void setSave_time(String save_time) {
                this.save_time = save_time;
            }

            public String getSumtime() {
                return sumtime;
            }

            public void setSumtime(String sumtime) {
                this.sumtime = sumtime;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getZburl() {
                return zburl;
            }

            public void setZburl(String zburl) {
                this.zburl = zburl;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }
        }
    }
}
