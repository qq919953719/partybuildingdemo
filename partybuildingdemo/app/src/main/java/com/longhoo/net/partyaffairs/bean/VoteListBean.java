package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by ck on 2017/12/19.
 */

public class VoteListBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"id":12,"name":"十佳青年","addtime":"2019-11-15","status":1,"listorder":3,"zan_num":5,"content":null},{"id":13,"name":"巾帼榜样","addtime":"2019-11-15","status":1,"listorder":2,"zan_num":3,"content":null},{"id":14,"name":"最美紫金人","addtime":"2019-11-15","status":1,"listorder":1,"zan_num":2,"content":null}]
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
         * id : 12
         * name : 十佳青年
         * addtime : 2019-11-15
         * status : 1
         * listorder : 3
         * zan_num : 5
         * content : null
         */

        private int id;
        private String name;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        private String uid;
        private String addtime;
        private int status;
        private int listorder;
        private int zan_num;
        private String content;

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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getListorder() {
            return listorder;
        }

        public void setListorder(int listorder) {
            this.listorder = listorder;
        }

        public int getZan_num() {
            return zan_num;
        }

        public void setZan_num(int zan_num) {
            this.zan_num = zan_num;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
