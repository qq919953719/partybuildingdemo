package com.longhoo.net.headline.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CK on 2017/11/24.
 */

public class CustomItemBean implements Serializable{


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"nid":758,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之六","digest":"","time":1532671689,"browse":40,"type":4},{"nid":757,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之七","digest":"","time":1532671668,"browse":31,"type":4},{"nid":756,"pic":"","title":"警钟常鸣\u2014\u2014党员违纪典型案例之八","digest":"","time":1532671642,"browse":28,"type":4},{"nid":755,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之九","digest":"","time":1532671621,"browse":26,"type":4},{"nid":754,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之十","digest":"","time":1532671600,"browse":28,"type":4},{"nid":753,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之十一","digest":"","time":1532671577,"browse":24,"type":4},{"nid":752,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之十二","digest":"","time":1532671550,"browse":26,"type":4},{"nid":751,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之十三","digest":"","time":1532671498,"browse":27,"type":4},{"nid":749,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之十四","digest":"","time":1531895986,"browse":34,"type":4},{"nid":748,"pic":"","title":"警钟常鸣\u2014党员违纪典型案例之十五","digest":"","time":1531895944,"browse":10,"type":4}]
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
         * nid : 758
         * pic :
         * title : 警钟常鸣—党员违纪典型案例之六
         * digest :
         * time : 1532671689
         * browse : 40
         * type : 4
         */

        private int nid;
        private String pic;
        private String title;
        private String digest;
        private int time;
        private int browse;
        private int type;
        private String source;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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
