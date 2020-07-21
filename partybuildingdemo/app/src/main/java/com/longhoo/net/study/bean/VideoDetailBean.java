package com.longhoo.net.study.bean;

public class VideoDetailBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"title":"用生命丈量壮美河山","videourl":"http://ngy3.dangjian.025nj.com/upload/20190107/c3e27e4eff794835321b2949efc8a501.mp4","videoimg":"http://ngy3.dangjian.025nj.com/upload/20190107/a9a19cf9229e47c32fa71a08db111903.png","des":"　国测一大队用行动让\u201c荣誉、奉献、敬业、责任\u201d这些词变得生动有温度。2015年7月1日，习近平总书记在给国测一大队首次参加珠峰测量的6位老队员的回信中指出，几十年来，国测一大队以及全国测绘战线一代代测绘队员不畏困苦、不怕牺牲，用汗水乃至生命默默丈量着祖国的壮美河山，为祖国发展、人民幸福作出了突出贡献，事迹感人至深。","red_num":16,"fab_num":100,"is_fab":1}
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
         * title : 用生命丈量壮美河山
         * videourl : http://ngy3.dangjian.025nj.com/upload/20190107/c3e27e4eff794835321b2949efc8a501.mp4
         * videoimg : http://ngy3.dangjian.025nj.com/upload/20190107/a9a19cf9229e47c32fa71a08db111903.png
         * des : 　国测一大队用行动让“荣誉、奉献、敬业、责任”这些词变得生动有温度。2015年7月1日，习近平总书记在给国测一大队首次参加珠峰测量的6位老队员的回信中指出，几十年来，国测一大队以及全国测绘战线一代代测绘队员不畏困苦、不怕牺牲，用汗水乃至生命默默丈量着祖国的壮美河山，为祖国发展、人民幸福作出了突出贡献，事迹感人至深。
         * red_num : 16
         * fab_num : 100
         * is_fab : 1
         */

        private String title;
        private String videourl;
        private String videoimg;
        private String des;
        private int red_num;
        private int fab_num;
        private int is_fab;

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        private String point;
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideourl() {
            return videourl;
        }

        public void setVideourl(String videourl) {
            this.videourl = videourl;
        }

        public String getVideoimg() {
            return videoimg;
        }

        public void setVideoimg(String videoimg) {
            this.videoimg = videoimg;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getRed_num() {
            return red_num;
        }

        public void setRed_num(int red_num) {
            this.red_num = red_num;
        }

        public int getFab_num() {
            return fab_num;
        }

        public void setFab_num(int fab_num) {
            this.fab_num = fab_num;
        }

        public int getIs_fab() {
            return is_fab;
        }

        public void setIs_fab(int is_fab) {
            this.is_fab = is_fab;
        }
    }
}
