package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by CK on 2017/12/26.
 * Email:910663958@qq.com
 */

public class LibraryBean {
    private String code;
    private String msg;
    private List<LibraryBean.DataBean> data;

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

    public List<LibraryBean.DataBean> getData() {
        return data;
    }

    public void setData(List<LibraryBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String gid;
        private String gname;
        private String thumb;
        private String score;
        private String type;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
