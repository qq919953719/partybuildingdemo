package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by ck on 2018/12/9.
 */

public class DataShareingDetailBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":{"id":118,"title":"1111","content":"dddddd","files":["http://ngy3.dangjian.025nj.com/upload/20190227/bbe7847dc250454174ca71628c819a56.xlsx","http://ngy3.dangjian.025nj.com/upload/20190227/222f210a7801456a3593d0b5d0965295.jpg"],"time":"2019-02-27","status":0,"adminid":1,"oid":0,"filename":["孟成真.xlsx","pic10_1920_1080.jpg"],"put_adminid":"","type":1,"orgid":"","push":2,"push_oid":0,"is_check":0,"listorder":0,"sid":118}}
     */

    private String code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : {"id":118,"title":"1111","content":"dddddd","files":["http://ngy3.dangjian.025nj.com/upload/20190227/bbe7847dc250454174ca71628c819a56.xlsx","http://ngy3.dangjian.025nj.com/upload/20190227/222f210a7801456a3593d0b5d0965295.jpg"],"time":"2019-02-27","status":0,"adminid":1,"oid":0,"filename":["孟成真.xlsx","pic10_1920_1080.jpg"],"put_adminid":"","type":1,"orgid":"","push":2,"push_oid":0,"is_check":0,"listorder":0,"sid":118}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 118
             * title : 1111
             * content : dddddd
             * files : ["http://ngy3.dangjian.025nj.com/upload/20190227/bbe7847dc250454174ca71628c819a56.xlsx","http://ngy3.dangjian.025nj.com/upload/20190227/222f210a7801456a3593d0b5d0965295.jpg"]
             * time : 2019-02-27
             * status : 0
             * adminid : 1
             * oid : 0
             * filename : ["孟成真.xlsx","pic10_1920_1080.jpg"]
             * put_adminid :
             * type : 1
             * orgid :
             * push : 2
             * push_oid : 0
             * is_check : 0
             * listorder : 0
             * sid : 118
             */

            private int id;
            private String title;
            private String content;
            private String time;
            private int status;
            private int adminid;
            private int oid;
            private String put_adminid;
            private int type;
            private String orgid;
            private int push;
            private int push_oid;
            private int is_check;
            private int listorder;
            private int sid;
            private List<String> files;
            private List<String> filename;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getAdminid() {
                return adminid;
            }

            public void setAdminid(int adminid) {
                this.adminid = adminid;
            }

            public int getOid() {
                return oid;
            }

            public void setOid(int oid) {
                this.oid = oid;
            }

            public String getPut_adminid() {
                return put_adminid;
            }

            public void setPut_adminid(String put_adminid) {
                this.put_adminid = put_adminid;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOrgid() {
                return orgid;
            }

            public void setOrgid(String orgid) {
                this.orgid = orgid;
            }

            public int getPush() {
                return push;
            }

            public void setPush(int push) {
                this.push = push;
            }

            public int getPush_oid() {
                return push_oid;
            }

            public void setPush_oid(int push_oid) {
                this.push_oid = push_oid;
            }

            public int getIs_check() {
                return is_check;
            }

            public void setIs_check(int is_check) {
                this.is_check = is_check;
            }

            public int getListorder() {
                return listorder;
            }

            public void setListorder(int listorder) {
                this.listorder = listorder;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

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
        }
    }
}
