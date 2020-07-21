package com.longhoo.net.study.bean;

public class CurrentAffairsDetailBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"id":9805,"catid":4,"key":"ARTI1571655152802406","title":"《榜样4》专题节目\u2014\u2014张富清海报","description":"","keywords":"","thumbnail":"","content":"<p style=\" word-wrap: break-word; text-align: center;\"><img width='100%' src=\"http://ngy4.dangjian.025nj.com/upload/downimg/2019102118552549164.jpg\" alt=\"\" width=\"800\" /><\/p>\r\n<p style=\" word-wrap: break-word; text-align: center;\"><img width='100%' src=\"http://ngy4.dangjian.025nj.com/upload/downimg/2019102118552918324.jpg\" alt=\"\" /><\/p>\r\n<p style=\" word-wrap: break-word; text-align: center;\"><img width='100%' src=\"http://ngy4.dangjian.025nj.com/upload/downimg/2019102118553632481.jpg\" alt=\"\" /><\/p>\r\n<p style=\" word-wrap: break-word; text-align: center;\"><img width='100%' src=\"http://ngy4.dangjian.025nj.com/upload/downimg/2019102118554056844.jpg\" alt=\"\" /><\/p>","add_time":"2020-01-14 15:17:20","status":"","reason":"","is_top":""}
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
         * id : 9805
         * catid : 4
         * key : ARTI1571655152802406
         * title : 《榜样4》专题节目——张富清海报
         * description :
         * keywords :
         * thumbnail :
         * content : <p style=" word-wrap: break-word; text-align: center;"><img width='100%' src="http://ngy4.dangjian.025nj.com/upload/downimg/2019102118552549164.jpg" alt="" width="800" /></p>
         <p style=" word-wrap: break-word; text-align: center;"><img width='100%' src="http://ngy4.dangjian.025nj.com/upload/downimg/2019102118552918324.jpg" alt="" /></p>
         <p style=" word-wrap: break-word; text-align: center;"><img width='100%' src="http://ngy4.dangjian.025nj.com/upload/downimg/2019102118553632481.jpg" alt="" /></p>
         <p style=" word-wrap: break-word; text-align: center;"><img width='100%' src="http://ngy4.dangjian.025nj.com/upload/downimg/2019102118554056844.jpg" alt="" /></p>
         * add_time : 2020-01-14 15:17:20
         * status :
         * reason :
         * is_top :
         */

        private int id;
        private int catid;
        private String key;
        private String title;
        private String description;
        private String keywords;
        private String thumbnail;
        private String content;
        private String add_time;
        private String status;
        private String reason;
        private String is_top;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCatid() {
            return catid;
        }

        public void setCatid(int catid) {
            this.catid = catid;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
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

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getIs_top() {
            return is_top;
        }

        public void setIs_top(String is_top) {
            this.is_top = is_top;
        }
    }
}
