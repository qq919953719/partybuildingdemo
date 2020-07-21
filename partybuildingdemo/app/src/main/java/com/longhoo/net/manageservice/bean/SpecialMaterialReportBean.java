package com.longhoo.net.manageservice.bean;

import java.util.List;

public class SpecialMaterialReportBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":{"id":179,"title":"测试性能","address":"","content":"阿斯顿发斯蒂芬","files":["http://ngy3.dangjian.025nj.com/upload/20190123/f0398589dbb3e19cbd0de5d3a69b495c.jpg"],"time":"2019-01-23 11:11:56","end_time":"2019-01-26 00:00:00","status":1,"adminid":503,"oid":48,"filename":"gh_e9eadf3745b6_258.jpg","put_adminid":"李四,李镇,张丽芳,徐旻","orgid":"","type":1,"repo_type":1,"listorder":0,"ispush":0,"push_title":"","push_content":null,"reward_type":null,"reward_name":null,"reward_company":null,"reward_level":null,"reward_time":null,"sid":179,"put_data":[{"id":20,"sid":76,"uid":503,"title":"阿斯顿发","content":"撒旦法","put_file":"/upload/20181229/9a618e74258cc9d14bba0956ae3baa06.jpg","put_filename":"111.jpg","status":0,"time":"2018-12-29 09:26:46","listorder":0,"realname":"测试2"}]}}
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
         * data : {"id":179,"title":"测试性能","address":"","content":"阿斯顿发斯蒂芬","files":["http://ngy3.dangjian.025nj.com/upload/20190123/f0398589dbb3e19cbd0de5d3a69b495c.jpg"],"time":"2019-01-23 11:11:56","end_time":"2019-01-26 00:00:00","status":1,"adminid":503,"oid":48,"filename":"gh_e9eadf3745b6_258.jpg","put_adminid":"李四,李镇,张丽芳,徐旻","orgid":"","type":1,"repo_type":1,"listorder":0,"ispush":0,"push_title":"","push_content":null,"reward_type":null,"reward_name":null,"reward_company":null,"reward_level":null,"reward_time":null,"sid":179,"put_data":[{"id":20,"sid":76,"uid":503,"title":"阿斯顿发","content":"撒旦法","put_file":"/upload/20181229/9a618e74258cc9d14bba0956ae3baa06.jpg","put_filename":"111.jpg","status":0,"time":"2018-12-29 09:26:46","listorder":0,"realname":"测试2"}]}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            public String getRep_obj() {
                return rep_obj;
            }

            public void setRep_obj(String rep_obj) {
                this.rep_obj = rep_obj;
            }

            /**
             * id : 179
             * title : 测试性能
             * address :
             * content : 阿斯顿发斯蒂芬
             * files : ["http://ngy3.dangjian.025nj.com/upload/20190123/f0398589dbb3e19cbd0de5d3a69b495c.jpg"]
             * time : 2019-01-23 11:11:56
             * end_time : 2019-01-26 00:00:00
             * status : 1
             * adminid : 503
             * oid : 48
             * filename : gh_e9eadf3745b6_258.jpg
             * put_adminid : 李四,李镇,张丽芳,徐旻
             * orgid :
             * type : 1
             * repo_type : 1
             * listorder : 0
             * ispush : 0
             * push_title :
             * push_content : null
             * reward_type : null
             * reward_name : null
             * reward_company : null
             * reward_level : null
             * reward_time : null
             * sid : 179
             * put_data : [{"id":20,"sid":76,"uid":503,"title":"阿斯顿发","content":"撒旦法","put_file":"/upload/20181229/9a618e74258cc9d14bba0956ae3baa06.jpg","put_filename":"111.jpg","status":0,"time":"2018-12-29 09:26:46","listorder":0,"realname":"测试2"}]
             */
            String rep_obj;
            String videourl;

            public String getVideourl() {
                return videourl;
            }

            public void setVideourl(String videourl) {
                this.videourl = videourl;
            }

            public String getVideoimg() {
                return videoimg;
            }

            public void setVideoimg(String videoimg) {
                this.videoimg = videoimg;
            }

            String videoimg;
            private int id;
            private String title;
            private String address;
            private String content;
            private String time;
            private String end_time;
            private int status;
            private int adminid;
            private int oid;
            private String filename;
            private String put_adminid;
            private String orgid;
            private int type;
            private int repo_type;
            private int listorder;
            private int ispush;
            private String push_title;
            private Object push_content;
            private Object reward_type;
            private Object reward_name;
            private Object reward_company;
            private Object reward_level;
            private Object reward_time;
            private int sid;
            private List<String> files;
            private List<PutDataBean> put_data;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
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

            public String getBack() {
                return back;
            }

            public void setBack(String back) {
                this.back = back;
            }

            private String back;

            public String getUnback() {
                return unback;
            }

            public void setUnback(String unback) {
                this.unback = unback;
            }

            private String unback;

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

            public String getOrgid() {
                return orgid;
            }

            public void setOrgid(String orgid) {
                this.orgid = orgid;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getRepo_type() {
                return repo_type;
            }

            public void setRepo_type(int repo_type) {
                this.repo_type = repo_type;
            }

            public int getListorder() {
                return listorder;
            }

            public void setListorder(int listorder) {
                this.listorder = listorder;
            }

            public int getIspush() {
                return ispush;
            }

            public void setIspush(int ispush) {
                this.ispush = ispush;
            }

            public String getPush_title() {
                return push_title;
            }

            public void setPush_title(String push_title) {
                this.push_title = push_title;
            }

            public Object getPush_content() {
                return push_content;
            }

            public void setPush_content(Object push_content) {
                this.push_content = push_content;
            }

            public Object getReward_type() {
                return reward_type;
            }

            public void setReward_type(Object reward_type) {
                this.reward_type = reward_type;
            }

            public Object getReward_name() {
                return reward_name;
            }

            public void setReward_name(Object reward_name) {
                this.reward_name = reward_name;
            }

            public Object getReward_company() {
                return reward_company;
            }

            public void setReward_company(Object reward_company) {
                this.reward_company = reward_company;
            }

            public Object getReward_level() {
                return reward_level;
            }

            public void setReward_level(Object reward_level) {
                this.reward_level = reward_level;
            }

            public Object getReward_time() {
                return reward_time;
            }

            public void setReward_time(Object reward_time) {
                this.reward_time = reward_time;
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

            public List<PutDataBean> getPut_data() {
                return put_data;
            }

            public void setPut_data(List<PutDataBean> put_data) {
                this.put_data = put_data;
            }

            public static class PutDataBean {
                /**
                 * id : 20
                 * sid : 76
                 * uid : 503
                 * title : 阿斯顿发
                 * content : 撒旦法
                 * put_file : /upload/20181229/9a618e74258cc9d14bba0956ae3baa06.jpg
                 * put_filename : 111.jpg
                 * status : 0
                 * time : 2018-12-29 09:26:46
                 * listorder : 0
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
                private int listorder;
                private String realname;
                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

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

                public int getListorder() {
                    return listorder;
                }

                public void setListorder(int listorder) {
                    this.listorder = listorder;
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
