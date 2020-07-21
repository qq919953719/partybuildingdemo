package com.longhoo.net.headline.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CK on 2017/11/24.
 */

public class HeadlineBean implements Serializable{

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"nid":825,"pic":"http://ngy3.dangjian.025nj.com/upload/20190110/c2b30f80101924c35b2a67934cabd1bc.jpg","title":"通知公告123","digest":"","time":1547104294,"browse":0,"type":5},{"nid":822,"pic":"http://ngy3.dangjian.025nj.com/upload/20190107/63709fd0d78bb430ebcb94cafa005596.jpg","title":"qwerer","digest":"","time":1546845230,"browse":0,"type":3},{"nid":819,"pic":"http://ngy3.dangjian.025nj.com/upload/20181227/0da824b0b17db6c9a2e48567ffbbfc00.jpg","title":"ceshi","digest":"","time":1545909819,"browse":0,"type":2},{"nid":807,"pic":"http://ngy3.dangjian.025nj.com/upload/20190110/b74660f0046c70ad5414d72a35e36984.png","title":"通知公告sss","digest":"","time":1544366792,"browse":8,"type":5},{"nid":758,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之六","digest":"","time":1532671689,"browse":40,"type":4},{"nid":757,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之七","digest":"","time":1532671668,"browse":31,"type":4},{"nid":756,"pic":"","title":"警钟常鸣\u2014\u2014党员违纪典型案例之八","digest":"","time":1532671642,"browse":28,"type":4},{"nid":755,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之九","digest":"","time":1532671621,"browse":26,"type":4},{"nid":754,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之十","digest":"","time":1532671600,"browse":28,"type":4},{"nid":753,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之十一","digest":"","time":1532671577,"browse":24,"type":4}]
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
         * nid : 825
         * pic : http://ngy3.dangjian.025nj.com/upload/20190110/c2b30f80101924c35b2a67934cabd1bc.jpg
         * title : 通知公告123
         * digest :
         * time : 1547104294
         * browse : 0
         * type : 5
         */

        private int nid;
        private String pic;
        private String title;
        private String digest;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        private String url;

        private int time;
        private int browse;
        private int type;
        private int is_top;

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
