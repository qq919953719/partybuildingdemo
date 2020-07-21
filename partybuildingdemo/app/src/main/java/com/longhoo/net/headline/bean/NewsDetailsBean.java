package com.longhoo.net.headline.bean;

import java.util.List;

/**
 * Created by CK on 2017/7/17.
 */

public class NewsDetailsBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"nid":825,"pic":"http://ngy3.dangjian.025nj.com/upload/20190110/c2b30f80101924c35b2a67934cabd1bc.jpg","title":"通知公告123","digest":"士大夫发的","info":"<p>士大夫发的<br/><\/p>","time":1547104294,"com":0}
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
         * nid : 825
         * pic : http://ngy3.dangjian.025nj.com/upload/20190110/c2b30f80101924c35b2a67934cabd1bc.jpg
         * title : 通知公告123
         * digest : 士大夫发的
         * info : <p>士大夫发的<br/></p>
         * time : 1547104294
         * com : 0
         */

        private int nid;
        private String pic;
        private String title;
        private String digest;
        private String info;
        private int time;
        private int com;
        private int browse;
        private List<String> files;
        private List<String> filename;

        public List<String> getFiles() {
            return files;
        }

        public void setFiles(List<String> files) {
            this.files = files;
        }

        public List<String> getFilename() {
            return filename;
        }

        public void setFilename(List<String> filename) {
            this.filename = filename;
        }

        public int getBrowse() {
            return browse;
        }

        public void setBrowse(int browse) {
            this.browse = browse;
        }

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

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getCom() {
            return com;
        }

        public void setCom(int com) {
            this.com = com;
        }
    }
}
