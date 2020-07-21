package com.longhoo.net.supervision.bean;

import java.util.List;

public class SecretReportContentBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":{"id":30,"title":"1222","address":"2222","content":"<p>safasfdasfd<br/><\/p>","files":"","time":"2019-01-05 15:43:03","status":0,"adminid":503,"oid":48,"filename":"","put_adminid":"","starttime":"2019-01-08","endtime":"2019-01-15","start_read_time":"2019-01-08","end_read_time":"2019-01-20","ispush":null,"push_title":null,"push_content":null,"type":2,"listorder":0,"sid":30,"put_data":[{"id":18,"sid":30,"uid":503,"title":"阿斯顿发","content":"<p>阿斯顿发<br/><\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-05 16:04:29","realname":"测试2"},{"id":20,"sid":30,"uid":503,"title":"徐旻上报","content":"<p>徐敏上报<\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-07 15:16:49","realname":"测试2"},{"id":21,"sid":30,"uid":503,"title":"测绘2","content":"<p>测绘2<br/><\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-07 17:02:53","realname":"测试2"}]}}
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
         * data : {"id":30,"title":"1222","address":"2222","content":"<p>safasfdasfd<br/><\/p>","files":"","time":"2019-01-05 15:43:03","status":0,"adminid":503,"oid":48,"filename":"","put_adminid":"","starttime":"2019-01-08","endtime":"2019-01-15","start_read_time":"2019-01-08","end_read_time":"2019-01-20","ispush":null,"push_title":null,"push_content":null,"type":2,"listorder":0,"sid":30,"put_data":[{"id":18,"sid":30,"uid":503,"title":"阿斯顿发","content":"<p>阿斯顿发<br/><\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-05 16:04:29","realname":"测试2"},{"id":20,"sid":30,"uid":503,"title":"徐旻上报","content":"<p>徐敏上报<\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-07 15:16:49","realname":"测试2"},{"id":21,"sid":30,"uid":503,"title":"测绘2","content":"<p>测绘2<br/><\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-07 17:02:53","realname":"测试2"}]}
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
             * id : 30
             * title : 1222
             * address : 2222
             * content : <p>safasfdasfd<br/></p>
             * files :
             * time : 2019-01-05 15:43:03
             * status : 0
             * adminid : 503
             * oid : 48
             * filename :
             * put_adminid :
             * starttime : 2019-01-08
             * endtime : 2019-01-15
             * start_read_time : 2019-01-08
             * end_read_time : 2019-01-20
             * ispush : null
             * push_title : null
             * push_content : null
             * type : 2
             * listorder : 0
             * sid : 30
             * put_data : [{"id":18,"sid":30,"uid":503,"title":"阿斯顿发","content":"<p>阿斯顿发<br/><\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-05 16:04:29","realname":"测试2"},{"id":20,"sid":30,"uid":503,"title":"徐旻上报","content":"<p>徐敏上报<\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-07 15:16:49","realname":"测试2"},{"id":21,"sid":30,"uid":503,"title":"测绘2","content":"<p>测绘2<br/><\/p>","put_file":"","put_filename":"","status":0,"time":"2019-01-07 17:02:53","realname":"测试2"}]
             */

            private int id;
            private String title;
            private String address;
            private String content;
            private String files;
            private String time;
            private int status;
            private int adminid;
            private int oid;
            private String filename;
            private String put_adminid;
            private String starttime;
            private String endtime;
            private String start_read_time;
            private String end_read_time;
            private Object ispush;
            private Object push_title;
            private Object push_content;
            private int type;
            private int listorder;
            private int sid;
            private String typename;
            private List<PutDataBean> put_data;

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getFiles() {
                return files;
            }

            public void setFiles(String files) {
                this.files = files;
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

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getPut_adminid() {
                return put_adminid;
            }

            public void setPut_adminid(String put_adminid) {
                this.put_adminid = put_adminid;
            }

            public String getStarttime() {
                return starttime;
            }

            public void setStarttime(String starttime) {
                this.starttime = starttime;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getStart_read_time() {
                return start_read_time;
            }

            public void setStart_read_time(String start_read_time) {
                this.start_read_time = start_read_time;
            }

            public String getEnd_read_time() {
                return end_read_time;
            }

            public void setEnd_read_time(String end_read_time) {
                this.end_read_time = end_read_time;
            }

            public Object getIspush() {
                return ispush;
            }

            public void setIspush(Object ispush) {
                this.ispush = ispush;
            }

            public Object getPush_title() {
                return push_title;
            }

            public void setPush_title(Object push_title) {
                this.push_title = push_title;
            }

            public Object getPush_content() {
                return push_content;
            }

            public void setPush_content(Object push_content) {
                this.push_content = push_content;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public List<PutDataBean> getPut_data() {
                return put_data;
            }

            public void setPut_data(List<PutDataBean> put_data) {
                this.put_data = put_data;
            }

            public static class PutDataBean {
                /**
                 * id : 18
                 * sid : 30
                 * uid : 503
                 * title : 阿斯顿发
                 * content : <p>阿斯顿发<br/></p>
                 * put_file :
                 * put_filename :
                 * status : 0
                 * time : 2019-01-05 16:04:29
                 * realname : 测试2
                 */

                private int id;
                private int sid;
                private int uid;
                private String title;
                private String content;
                private String put_file;
                private String put_filename;
                private int status;
                private String time;
                private String realname;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSid() {
                    return sid;
                }

                public void setSid(int sid) {
                    this.sid = sid;
                }

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
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

                public String getPut_file() {
                    return put_file;
                }

                public void setPut_file(String put_file) {
                    this.put_file = put_file;
                }

                public String getPut_filename() {
                    return put_filename;
                }

                public void setPut_filename(String put_filename) {
                    this.put_filename = put_filename;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getRealname() {
                    return realname;
                }

                public void setRealname(String realname) {
                    this.realname = realname;
                }
            }
        }
    }
}
