package com.longhoo.net.partyaffairs.bean;

import java.util.List;

public class OpenPartyNode {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":1,"title":"党务公开","thumb":"http://zhdj.xzcit.cn/upload/20200528/bd68b3c6b50865c1e841a845983ea901.png","addtime":"2020-05-28 16:33:03"}]}
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
             * title : 党务公开
             * thumb : http://zhdj.xzcit.cn/upload/20200528/bd68b3c6b50865c1e841a845983ea901.png
             * addtime : 2020-05-28 16:33:03
             */

            private int id;
            private String title;
            private String thumb;
            private String addtime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
