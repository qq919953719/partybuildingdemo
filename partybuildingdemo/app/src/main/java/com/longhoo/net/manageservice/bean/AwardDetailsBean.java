package com.longhoo.net.manageservice.bean;

import java.util.List;

public class AwardDetailsBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":{"id":180,"title":"获奖名称","address":"","content":null,"files":["http://ngy3.dangjian.025nj.com/upload/cailiao/20190123/b1f93b8fac95046efc19c0b37c00277f.jpg"],"time":"2019-01-23 11:25:27","end_time":null,"status":1,"adminid":18892,"oid":17,"filename":"","put_adminid":"","orgid":"0","type":2,"repo_type":1,"listorder":0,"ispush":0,"push_title":"","push_content":null,"reward_type":1,"reward_name":"获奖名称","reward_company":"单位","reward_level":"三级","reward_time":"2019-01","sid":180,"put_data":[]}}
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
         * data : {"id":180,"title":"获奖名称","address":"","content":null,"files":["http://ngy3.dangjian.025nj.com/upload/cailiao/20190123/b1f93b8fac95046efc19c0b37c00277f.jpg"],"time":"2019-01-23 11:25:27","end_time":null,"status":1,"adminid":18892,"oid":17,"filename":"","put_adminid":"","orgid":"0","type":2,"repo_type":1,"listorder":0,"ispush":0,"push_title":"","push_content":null,"reward_type":1,"reward_name":"获奖名称","reward_company":"单位","reward_level":"三级","reward_time":"2019-01","sid":180,"put_data":[]}
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
             * id : 180
             * title : 获奖名称
             * address :
             * content : null
             * files : ["http://ngy3.dangjian.025nj.com/upload/cailiao/20190123/b1f93b8fac95046efc19c0b37c00277f.jpg"]
             * time : 2019-01-23 11:25:27
             * end_time : null
             * status : 1
             * adminid : 18892
             * oid : 17
             * filename :
             * put_adminid :
             * orgid : 0
             * type : 2
             * repo_type : 1
             * listorder : 0
             * ispush : 0
             * push_title :
             * push_content : null
             * reward_type : 1
             * reward_name : 获奖名称
             * reward_company : 单位
             * reward_level : 三级
             * reward_time : 2019-01
             * sid : 180
             * put_data : []
             */

            private int id;
            private String title;
            private String address;
            private Object content;
            private String time;
            private Object end_time;
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
            private int reward_type;
            private String reward_name;
            private String reward_company;
            private String reward_level;
            private String reward_time;
            private int sid;
            private List<String> files;
            private List<?> put_data;

            public String getBack() {
                return back;
            }

            public void setBack(String back) {
                this.back = back;
            }

            private String back;
            public String getAppendix() {
                return appendix;
            }

            public void setAppendix(String appendix) {
                this.appendix = appendix;
            }


            public String getAppendix_name() {
                return appendix_name;
            }

            public void setAppendix_name(String appendix_name) {
                this.appendix_name = appendix_name;
            }

            String appendix_name;
            String appendix;
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

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public Object getEnd_time() {
                return end_time;
            }

            public void setEnd_time(Object end_time) {
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

            public int getReward_type() {
                return reward_type;
            }

            public void setReward_type(int reward_type) {
                this.reward_type = reward_type;
            }

            public String getReward_name() {
                return reward_name;
            }

            public void setReward_name(String reward_name) {
                this.reward_name = reward_name;
            }

            public String getReward_company() {
                return reward_company;
            }

            public void setReward_company(String reward_company) {
                this.reward_company = reward_company;
            }

            public String getReward_level() {
                return reward_level;
            }

            public void setReward_level(String reward_level) {
                this.reward_level = reward_level;
            }

            public String getReward_time() {
                return reward_time;
            }

            public void setReward_time(String reward_time) {
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

            public List<?> getPut_data() {
                return put_data;
            }

            public void setPut_data(List<?> put_data) {
                this.put_data = put_data;
            }
        }
    }
}
