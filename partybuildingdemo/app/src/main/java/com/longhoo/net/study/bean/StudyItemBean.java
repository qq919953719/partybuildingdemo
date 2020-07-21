package com.longhoo.net.study.bean;

import java.util.List;

public class StudyItemBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"nid":397,"pic":"http://ngy3.dangjian.025nj.com/upload/20190108/854e27541a1d9cc37888f14dc2d860d0.png","title":"努力为人类作出新的更大贡献\u2014\u2014八论学习贯彻党的十九大精神","filename":"","files":"","time":1514441996,"browse":32,"tag":"","score":2},{"nid":398,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144423925a448e98122d6.png","title":"坚持走中国特色强军之路\u2014\u2014七论学习贯彻党的十九大精神","filename":"","files":"","time":1514442404,"browse":31,"tag":"","score":2},{"nid":400,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144427755a449017d0c98.png","title":"统筹推进新时代\u201c五位一体\u201d总体布局\u2014\u2014六论学习贯彻党的十九大精神","filename":"","files":"","time":1514442781,"browse":24,"tag":"","score":2},{"nid":401,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144430085a4491005e439.png","title":"开启全面建设社会主义现代化国家新征程\u2014\u2014五论学习贯彻党的十九大精神","filename":"","files":"","time":1514443011,"browse":35,"tag":"","score":2},{"nid":402,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144434815a4492d9327cb.png","title":"深入领会习近平新时代中国特色社会主义思想\u2014\u2014四论学习贯彻党的十九大精神","filename":"","files":"","time":1514443484,"browse":31,"tag":"","score":2},{"nid":403,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144436495a449381c3a64.png","title":"牢牢把握新时代中国共产党的历史使命\u2014\u2014三论学习贯彻党的十九大精神","filename":"","files":"","time":1514443652,"browse":35,"tag":"","score":2},{"nid":404,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144437495a4493e549777.png","title":"阔步走进中国特色社会主义新时代\u2014\u2014二论学习贯彻党的十九大精神","filename":"","files":"","time":1514443831,"browse":49,"tag":"","score":2},{"nid":405,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144439515a4494af03f42.png","title":"让中国特色社会主义展现更强大的生命力 \u2014\u2014一论学习贯彻党的十九大精神","filename":"","files":"","time":1514443953,"browse":60,"tag":"","score":2},{"nid":406,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144450835a44991bc9715.png","title":"\u201c两学一做\u201d重在知行合一","filename":"","files":"","time":1514445090,"browse":43,"tag":"","score":2},{"nid":408,"pic":"http://ngy3.dangjian.025nj.comnews/20171228/thumb_15144464005a449e4095abc.png","title":"\u201c两学一做\u201d贵在真学重在实做","filename":"","files":"","time":1514446402,"browse":30,"tag":"","score":2}]
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
         * nid : 397
         * pic : http://ngy3.dangjian.025nj.com/upload/20190108/854e27541a1d9cc37888f14dc2d860d0.png
         * title : 努力为人类作出新的更大贡献——八论学习贯彻党的十九大精神
         * filename :
         * files :
         * time : 1514441996
         * browse : 32
         * tag :
         * score : 2
         */

        private int nid;
        private String pic;
        private String title;
        private String filename;
        private String files;
        private int time;
        private int browse;
        private String tag;
        private int score;

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
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

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getBrowse() {
            return browse;
        }

        public void setBrowse(int browse) {
            this.browse = browse;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
