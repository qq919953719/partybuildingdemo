package com.longhoo.net.study.bean;

import java.util.List;

public class VideoConListBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":4,"name":"南京市鼓楼区第二届人民代表大会第二次会议","description":"南京市鼓楼区第二届人民代表大会第二次会议将于2018年1月2日09:00在江苏省委党校召开，龙虎网将进行图文直播。","starttime":"2018-01-03 10:34:17","endtime":"2018-01-03 12:26:17","roomid":"ngy-2","thumb":"http://ngy3.dangjian.025nj.com/upload/20190107/505a74a2e5b5a20136cb23354a78c99d.jpg","istart":1,"isend":0,"hits":5,"zan":-4,"zburl":"http://pili-live-hls.025nj.com.hlsv3.pilidns.com/njcast/ngy-2.m3u8"},{"id":5,"name":"基层在线访谈走进秦淮区 畅谈\u201c群众身边的微幸福\u201d","description":"2017年\u201c互联网+改革\u201d之基层在线访谈，看秦淮如何打造幸福工程","starttime":"2018-01-25 10:06:24","endtime":"2018-01-25 16:41:24","roomid":"ngy-1","thumb":"http://ngy3.dangjian.025nj.com/upload/20190107/69c45b569fc3a729f3ce387ce8d1b79f.jpg","istart":1,"isend":0,"hits":0,"zan":0,"zburl":"http://pili-live-hls.025nj.com.hlsv3.pilidns.com/njcast/ngy-1.m3u8"}]}
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
             * id : 4
             * name : 南京市鼓楼区第二届人民代表大会第二次会议
             * description : 南京市鼓楼区第二届人民代表大会第二次会议将于2018年1月2日09:00在江苏省委党校召开，龙虎网将进行图文直播。
             * starttime : 2018-01-03 10:34:17
             * endtime : 2018-01-03 12:26:17
             * roomid : ngy-2
             * thumb : http://ngy3.dangjian.025nj.com/upload/20190107/505a74a2e5b5a20136cb23354a78c99d.jpg
             * istart : 1
             * isend : 0
             * hits : 5
             * zan : -4
             * zburl : http://pili-live-hls.025nj.com.hlsv3.pilidns.com/njcast/ngy-2.m3u8
             */

            private int id;
            private String name;
            private String description;
            private String starttime;
            private String endtime;
            private String roomid;
            private String thumb;
            private int istart;
            private int isend;
            private int hits;
            private int zan;
            private String zburl;
            private int comments;
            private String is_zan;

            public String getIs_zan() {
                return is_zan;
            }

            public void setIs_zan(String is_zan) {
                this.is_zan = is_zan;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public int getIstart() {
                return istart;
            }

            public void setIstart(int istart) {
                this.istart = istart;
            }

            public int getIsend() {
                return isend;
            }

            public void setIsend(int isend) {
                this.isend = isend;
            }

            public int getHits() {
                return hits;
            }

            public void setHits(int hits) {
                this.hits = hits;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public String getZburl() {
                return zburl;
            }

            public void setZburl(String zburl) {
                this.zburl = zburl;
            }
        }
    }
}
