package com.longhoo.net.partyaffairs.bean;

import java.util.List;

public class OpenPartyContentNode {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":1,"addtime":"2020-05-28 16:33:03","title":"党务公开","thumb":"http://zhdj.xzcit.cn/upload/20200528/bd68b3c6b50865c1e841a845983ea901.png","content":"<p>党务公开<br/><\/p>","uid":1,"status":"","description":"","filed":"","filedname":"","listorder":2,"uname":"组织部"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * addtime : 2020-05-28 16:33:03
             * title : 党务公开
             * thumb : http://zhdj.xzcit.cn/upload/20200528/bd68b3c6b50865c1e841a845983ea901.png
             * content : <p>党务公开<br/></p>
             * uid : 1
             * status :
             * description :
             * filed :
             * filedname :
             * listorder : 2
             * uname : 组织部
             */

            private int id;
            private String addtime;
            private String title;
            private String thumb;
            private String content;
            private int uid;
            private String status;
            private String description;
            private String filed;
            private String filedname;
            private int listorder;
            private String uname;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getFiled() {
                return filed;
            }

            public void setFiled(String filed) {
                this.filed = filed;
            }

            public String getFiledname() {
                return filedname;
            }

            public void setFiledname(String filedname) {
                this.filedname = filedname;
            }

            public int getListorder() {
                return listorder;
            }

            public void setListorder(int listorder) {
                this.listorder = listorder;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }
        }
    }
}
