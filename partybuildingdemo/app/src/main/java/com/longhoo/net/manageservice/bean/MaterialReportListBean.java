package com.longhoo.net.manageservice.bean;

import java.util.List;

public class MaterialReportListBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":[{"sid":"11","title":"看了看","time":"2018-12-07 17:02:13","content":"","files":["http://ngy2.dangjian.025nj.com/UploadFile/files/study/20181207/15441733335c0a3715cc360.png"]},{"sid":"12","title":"啊的","time":"2018-12-07 17:12:38","content":"","files":["http://ngy2.dangjian.025nj.com/UploadFile/files/study/20181207/15441733335c0a3715cc360.png","http://ngy2.dangjian.025nj.com/UploadFile/files/study/20181207/15441739585c0a398681b84.jpg"]}]}
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
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * sid : 11
             * title : 看了看
             * time : 2018-12-07 17:02:13
             * content :
             * files : ["http://ngy2.dangjian.025nj.com/UploadFile/files/study/20181207/15441733335c0a3715cc360.png"]
             */

            private String sid;
            private String title;
            private String time;
            private String content;
            private List<String> files;
            private String reward_name;
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getReward_name() {
                return reward_name;
            }

            public void setReward_name(String reward_name) {
                this.reward_name = reward_name;
            }

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<String> getFiles() {
                return files;
            }

            public void setFiles(List<String> files) {
                this.files = files;
            }
        }
    }
}
