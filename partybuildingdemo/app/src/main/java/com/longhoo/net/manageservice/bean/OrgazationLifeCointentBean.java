package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/18.
 */

public class OrgazationLifeCointentBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"details":{"content":"fsdfsdas","meeting_time":"1526006727","meeting_address":"sdfasfda","compere":"黄月霞","recorder":"蓉蓉","attend_memb":"陈凯\\r田申申\\r","desc":"<p>sdfgsdfgsdfgsdfgdsaf<\/p><p>tyjuyukjghsdfh<\/p>","type":"党员大会","sign_memb":"无","meeting_title":"","meeting_content":"<p>sdfasfdasd<\/p><p>dsdfsaddgadfgsdfsdfgsdfg<\/p>","ttype":"2","time":"1526006615","leavestatus":0,"applystatus":0,"leavepeople":[],"joinpeople":[{"aid":"6","uid":"1182","tid":"39","time":"1525919616","realname":"田申申"}]}}
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
        /**
         * details : {"content":"fsdfsdas","meeting_time":"1526006727","meeting_address":"sdfasfda","compere":"黄月霞","recorder":"蓉蓉","attend_memb":"陈凯\\r田申申\\r","desc":"<p>sdfgsdfgsdfgsdfgdsaf<\/p><p>tyjuyukjghsdfh<\/p>","type":"党员大会","sign_memb":"无","meeting_title":"","meeting_content":"<p>sdfasfdasd<\/p><p>dsdfsaddgadfgsdfsdfgsdfg<\/p>","ttype":"2","time":"1526006615","leavestatus":0,"applystatus":0,"leavepeople":[],"joinpeople":[{"aid":"6","uid":"1182","tid":"39","time":"1525919616","realname":"田申申"}]}
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
             * content : fsdfsdas
             * meeting_time : 1526006727
             * meeting_address : sdfasfda
             * compere : 黄月霞
             * recorder : 蓉蓉
             * attend_memb : 陈凯\r田申申\r
             * desc : <p>sdfgsdfgsdfgsdfgdsaf</p><p>tyjuyukjghsdfh</p>
             * type : 党员大会
             * sign_memb : 无
             * meeting_title :
             * meeting_content : <p>sdfasfdasd</p><p>dsdfsaddgadfgsdfsdfgsdfg</p>
             * ttype : 2
             * time : 1526006615
             * leavestatus : 0
             * applystatus : 0
             * leavepeople : []
             * joinpeople : [{"aid":"6","uid":"1182","tid":"39","time":"1525919616","realname":"田申申"}]
             */

            private String content;
            private String meeting_time;
            private String meeting_address;
            private String compere;
            private String recorder;
            private String attend_memb;
            private String desc;
            private String type;
            private String sign_memb;
            private String meeting_title;
            private String meeting_content;
            private String ttype;
            private String time;
            private int leavestatus;
            private int applystatus;
            private List<LeavepeopleBean> leavepeople;
            private List<JoinpeopleBean> joinpeople;

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

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSign_memb() {
                return sign_memb;
            }

            public void setSign_memb(String sign_memb) {
                this.sign_memb = sign_memb;
            }

            public String getMeeting_title() {
                return meeting_title;
            }

            public void setMeeting_title(String meeting_title) {
                this.meeting_title = meeting_title;
            }

            public String getMeeting_content() {
                return meeting_content;
            }

            public void setMeeting_content(String meeting_content) {
                this.meeting_content = meeting_content;
            }

            public String getTtype() {
                return ttype;
            }

            public void setTtype(String ttype) {
                this.ttype = ttype;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getLeavestatus() {
                return leavestatus;
            }

            public void setLeavestatus(int leavestatus) {
                this.leavestatus = leavestatus;
            }

            public int getApplystatus() {
                return applystatus;
            }

            public void setApplystatus(int applystatus) {
                this.applystatus = applystatus;
            }

            public List<LeavepeopleBean> getLeavepeople() {
                return leavepeople;
            }

            public void setLeavepeople(List<LeavepeopleBean> leavepeople) {
                this.leavepeople = leavepeople;
            }

            public List<JoinpeopleBean> getJoinpeople() {
                return joinpeople;
            }

            public void setJoinpeople(List<JoinpeopleBean> joinpeople) {
                this.joinpeople = joinpeople;
            }

            public static class JoinpeopleBean {
                /**
                 * aid : 6
                 * uid : 1182
                 * tid : 39
                 * time : 1525919616
                 * realname : 田申申
                 */

                private String aid;
                private String uid;
                private String tid;
                private String time;
                private String realname;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public String getTid() {
                    return tid;
                }

                public void setTid(String tid) {
                    this.tid = tid;
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

            public static class LeavepeopleBean{
                private String lid;
                private String uid;
                private String type;
                private String tid;
                private String realname;
                private String reason;
                private String time;

                public String getLid() {
                    return lid;
                }

                public void setLid(String lid) {
                    this.lid = lid;
                }

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTid() {
                    return tid;
                }

                public void setTid(String tid) {
                    this.tid = tid;
                }

                public String getRealname() {
                    return realname;
                }

                public void setRealname(String realname) {
                    this.realname = realname;
                }

                public String getReason() {
                    return reason;
                }

                public void setReason(String reason) {
                    this.reason = reason;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }
    }
}
