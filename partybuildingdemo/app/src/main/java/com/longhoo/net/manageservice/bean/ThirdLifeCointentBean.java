package com.longhoo.net.manageservice.bean;

/**
 * Created by ${CC} on 2017/12/18.
 */

public class ThirdLifeCointentBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"details":{"content":"ceshi","meeting_time":"1513067547","meeting_address":"报业","compere":"全球人生","recorder":"全球人生","attend_memb":"全球人生&nbsp;&nbsp;","sign_memb":"","meeting_spirit":"<p>测试<br/><\/p>"}}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
        /**
         * details : {"content":"ceshi","meeting_time":"1513067547","meeting_address":"报业","compere":"全球人生","recorder":"全球人生","attend_memb":"全球人生&nbsp;&nbsp;","sign_memb":"","meeting_spirit":"<p>测试<br/><\/p>"}
         */

        private DetailsBean details;

        public DetailsBean getDetails() {
            return details;
        }

        public void setDetails(DetailsBean details) {
            this.details = details;
        }

        public static class DetailsBean {
            /**
             * content : ceshi
             * meeting_time : 1513067547
             * meeting_address : 报业
             * compere : 全球人生
             * recorder : 全球人生
             * attend_memb : 全球人生&nbsp;&nbsp;
             * sign_memb :
             * meeting_spirit : <p>测试<br/></p>
             */

            private String content;
            private String meeting_time;
            private String meeting_address;
            private String compere;
            private String recorder;
            private String attend_memb;
            private String sign_memb;
            private String meeting_spirit;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getMeeting_time() {
                return meeting_time;
            }

            public void setMeeting_time(String meeting_time) {
                this.meeting_time = meeting_time;
            }

            public String getMeeting_address() {
                return meeting_address;
            }

            public void setMeeting_address(String meeting_address) {
                this.meeting_address = meeting_address;
            }

            public String getCompere() {
                return compere;
            }

            public void setCompere(String compere) {
                this.compere = compere;
            }

            public String getRecorder() {
                return recorder;
            }

            public void setRecorder(String recorder) {
                this.recorder = recorder;
            }

            public String getAttend_memb() {
                return attend_memb;
            }

            public void setAttend_memb(String attend_memb) {
                this.attend_memb = attend_memb;
            }

            public String getSign_memb() {
                return sign_memb;
            }

            public void setSign_memb(String sign_memb) {
                this.sign_memb = sign_memb;
            }

            public String getMeeting_spirit() {
                return meeting_spirit;
            }

            public void setMeeting_spirit(String meeting_spirit) {
                this.meeting_spirit = meeting_spirit;
            }
        }
    }
}
