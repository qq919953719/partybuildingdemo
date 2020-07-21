package com.longhoo.net.mine.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/27.
 */

public class SignBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"type":2,"addtime":"2018-12-27 10:48","aid":1697,"title":"是否","time":"2018-12-25 09:00"}]
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
         * type : 2
         * addtime : 2018-12-27 10:48
         * aid : 1697
         * title : 是否
         * time : 2018-12-25 09:00
         */

        private int type;
        private String addtime;
        private int aid;
        private String title;
        private String time;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
