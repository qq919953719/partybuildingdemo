package com.longhoo.net.study.bean;

import java.util.List;

/**
 * Created by ck on 2018/12/9.
 */

public class PartyClassDisplayListBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"aid":12,"title":"用生命丈量壮美河山","videourl":"http://ngy3.dangjian.025nj.com/upload/20190107/c3e27e4eff794835321b2949efc8a501.mp4","content":"　国测一大队用行动让\u201c荣誉、奉献、敬业、责任\u201d这些词变得生动有温度。2015年7月1日，习近平总书记在给国测一大队首次参加珠峰测量的6位老队员的回信中指出，几十年来，国测一大队以及全国测绘战线一代代测绘队员不畏困苦、不怕牺牲，用汗水乃至生命默默丈量着祖国的壮美河山，为祖国发展、人民幸福作出了突出贡献，事迹感人至深。","videoimg":"http://ngy3.dangjian.025nj.com/upload/20190107/a9a19cf9229e47c32fa71a08db111903.png","cid":1,"fab_num":99,"red_num":15,"com_num":1,"is_fab":0}]
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

    public static class DataBean {
        /**
         * aid : 12
         * title : 用生命丈量壮美河山
         * videourl : http://ngy3.dangjian.025nj.com/upload/20190107/c3e27e4eff794835321b2949efc8a501.mp4
         * content : 　国测一大队用行动让“荣誉、奉献、敬业、责任”这些词变得生动有温度。2015年7月1日，习近平总书记在给国测一大队首次参加珠峰测量的6位老队员的回信中指出，几十年来，国测一大队以及全国测绘战线一代代测绘队员不畏困苦、不怕牺牲，用汗水乃至生命默默丈量着祖国的壮美河山，为祖国发展、人民幸福作出了突出贡献，事迹感人至深。
         * videoimg : http://ngy3.dangjian.025nj.com/upload/20190107/a9a19cf9229e47c32fa71a08db111903.png
         * cid : 1
         * fab_num : 99
         * red_num : 15
         * com_num : 1
         * is_fab : 0
         */

        private int aid;
        private String title;
        private String videourl;
        private String content;
        private String videoimg;
        private int cid;
        private int fab_num;
        private int red_num;
        private int com_num;
        private int is_fab;

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        private String point;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getVideoimg() {
            return videoimg;
        }

        public void setVideoimg(String videoimg) {
            this.videoimg = videoimg;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getFab_num() {
            return fab_num;
        }

        public void setFab_num(int fab_num) {
            this.fab_num = fab_num;
        }

        public int getRed_num() {
            return red_num;
        }

        public void setRed_num(int red_num) {
            this.red_num = red_num;
        }

        public int getCom_num() {
            return com_num;
        }

        public void setCom_num(int com_num) {
            this.com_num = com_num;
        }

        public int getIs_fab() {
            return is_fab;
        }

        public void setIs_fab(int is_fab) {
            this.is_fab = is_fab;
        }
    }
}
