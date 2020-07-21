package com.longhoo.net.manageservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OrganizationLifeDetailBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"tid":189,"content":"我是鑫鑫123","meeting_time":1548259200,"meeting_address":"我是鑫鑫","compere":"陈薇","recorder":"陈薇","attend_memb":"陈青\\r陈薇\\r陈敏\\r","desc":"<p>我是鑫鑫<\/p>","type":"支部委员会","sign_memb":"无","meeting_title":"我是鑫鑫","meeting_content":"<p>我是鑫鑫<br/><\/p>","ttype":1,"time":1548328789,"summaryInfo":null,"summaryimg":null,"userid":119,"is_attend":1,"leavestatus":1,"leavereason":"77777777777777777777777777777777777777777777777777777778888888","leavepeople":[{"lid":78,"uid":119,"type":2,"tid":189,"reason":"77777777777777777777777777777777777777777777777777777778888888","time":"2019-01-24 19:23:06","realname":"陈敏"}]}
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

    public static class DataBean implements Parcelable {
        /**
         * tid : 189
         * content : 我是鑫鑫123
         * meeting_time : 1548259200
         * meeting_address : 我是鑫鑫
         * compere : 陈薇
         * recorder : 陈薇
         * attend_memb : 陈青\r陈薇\r陈敏\r
         * desc : <p>我是鑫鑫</p>
         * type : 支部委员会
         * sign_memb : 无
         * meeting_title : 我是鑫鑫
         * meeting_content : <p>我是鑫鑫<br/></p>
         * ttype : 1
         * time : 1548328789
         * summaryInfo : null
         * summaryimg : null
         * userid : 119
         * is_attend : 1
         * leavestatus : 1
         * leavereason : 77777777777777777777777777777777777777777777777777777778888888
         * leavepeople : [{"lid":78,"uid":119,"type":2,"tid":189,"reason":"77777777777777777777777777777777777777777777777777777778888888","time":"2019-01-24 19:23:06","realname":"陈敏"}]
         */

        private int tid;
        private String content;
        private int meeting_time;
        private String meeting_address;
        private String compere;

        public String getCompere1() {
            return compere1;
        }

        public void setCompere1(String compere1) {
            this.compere1 = compere1;
        }

        private String compere1;
        private String recorder;
        private String attend_memb;

        protected DataBean(Parcel in) {
            tid = in.readInt();
            content = in.readString();
            meeting_time = in.readInt();
            meeting_address = in.readString();
            compere = in.readString();
            recorder = in.readString();
            attend_memb = in.readString();
            attend_memb1 = in.readString();
            desc = in.readString();
            typeId = in.readInt();
            orgid = in.readString();
            type = in.readString();
            sign_memb = in.readString();
            meeting_title = in.readString();
            meeting_content = in.readString();
            ttype = in.readInt();
            time = in.readInt();
            summaryInfo = in.readString();
            summaryimg = in.readString();
            userid = in.readInt();
            is_attend = in.readInt();
            leavestatus = in.readInt();
            unback = in.readString();
            uname_str = in.readString();
            sum_show = in.readInt();
            leavereason = in.readString();
            recorder1 = in.readInt();
            is_sign = in.readInt();
            videourl = in.readString();
            videoimg = in.readString();
            cmr = in.readString();
            is_join = in.readInt();
            compere1= in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(tid);
            dest.writeString(content);
            dest.writeInt(meeting_time);
            dest.writeString(meeting_address);
            dest.writeString(compere);
            dest.writeString(recorder);
            dest.writeString(attend_memb);
            dest.writeString(attend_memb1);
            dest.writeString(desc);
            dest.writeInt(typeId);
            dest.writeString(orgid);
            dest.writeString(type);
            dest.writeString(sign_memb);
            dest.writeString(meeting_title);
            dest.writeString(meeting_content);
            dest.writeInt(ttype);
            dest.writeInt(time);
            dest.writeString(summaryInfo);
            dest.writeString(summaryimg);
            dest.writeInt(userid);
            dest.writeInt(is_attend);
            dest.writeInt(leavestatus);
            dest.writeString(unback);
            dest.writeString(uname_str);
            dest.writeInt(sum_show);
            dest.writeString(leavereason);
            dest.writeInt(recorder1);
            dest.writeInt(is_sign);
            dest.writeString(videourl);
            dest.writeString(videoimg);
            dest.writeString(cmr);
            dest.writeInt(is_join);
            dest.writeString(compere1);

        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getAttend_memb1() {
            return attend_memb1;
        }

        public void setAttend_memb1(String attend_memb1) {
            this.attend_memb1 = attend_memb1;
        }

        private String attend_memb1;
        private String desc;


        public int getTypeid() {
            return typeId;
        }

        public void setTypeid(int typeid) {
            this.typeId = typeid;
        }

        private int typeId;
        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        private String orgid;

        private String type;
        private String sign_memb;
        private String meeting_title;
        private String meeting_content;
        private int ttype;
        private int time;
        private String summaryInfo;
        private String summaryimg;
        private int userid;
        private int is_attend;
        private int leavestatus;




        @Override
        public int describeContents() {
            return 0;
        }



        public String getUnback() {
            return unback;
        }

        public void setUnback(String unback) {
            this.unback = unback;
        }

        private String unback;

        public String getUname_str() {
            return uname_str;
        }

        public void setUname_str(String uname_str) {
            this.uname_str = uname_str;
        }

        private String uname_str;

        public int getSum_show() {
            return sum_show;
        }

        public void setSum_show(int sum_show) {
            this.sum_show = sum_show;
        }

        private int sum_show;

        private String leavereason;
        private List<LeavepeopleBean> leavepeople;
        private int recorder1;
        //1已确认参加2未确认参加
        private int is_sign;

        public String getVideourl() {
            return videourl;
        }

        public void setVideourl(String videourl) {
            this.videourl = videourl;
        }

        private String videourl;

        public String getVideoimg() {
            return videoimg;
        }

        public void setVideoimg(String videoimg) {
            this.videoimg = videoimg;
        }

        private String videoimg;


        public String getCmr() {
            return cmr;
        }

        public void setCmr(String cmr) {
            this.cmr = cmr;
        }

        private String cmr;

        public int getIs_join() {
            return is_join;
        }

        public void setIs_join(int is_join) {
            this.is_join = is_join;
        }

        private int is_join;


        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }

        public int getRecorder1() {
            return recorder1;
        }

        public void setRecorder1(int recorder1) {
            this.recorder1 = recorder1;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMeeting_time() {
            return meeting_time;
        }

        public void setMeeting_time(int meeting_time) {
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

        public int getTtype() {
            return ttype;
        }

        public void setTtype(int ttype) {
            this.ttype = ttype;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getSummaryInfo() {
            return summaryInfo;
        }

        public void setSummaryInfo(String summaryInfo) {
            this.summaryInfo = summaryInfo;
        }

        public String getSummaryimg() {
            return summaryimg;
        }

        public void setSummaryimg(String summaryimg) {
            this.summaryimg = summaryimg;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getIs_attend() {
            return is_attend;
        }

        public void setIs_attend(int is_attend) {
            this.is_attend = is_attend;
        }

        public int getLeavestatus() {
            return leavestatus;
        }

        public void setLeavestatus(int leavestatus) {
            this.leavestatus = leavestatus;
        }

        public String getLeavereason() {
            return leavereason;
        }

        public void setLeavereason(String leavereason) {
            this.leavereason = leavereason;
        }

        public List<LeavepeopleBean> getLeavepeople() {
            return leavepeople;
        }

        public void setLeavepeople(List<LeavepeopleBean> leavepeople) {
            this.leavepeople = leavepeople;
        }

        public static class LeavepeopleBean {
            /**
             * lid : 78
             * uid : 119
             * type : 2
             * tid : 189
             * reason : 77777777777777777777777777777777777777777777777777777778888888
             * time : 2019-01-24 19:23:06
             * realname : 陈敏
             */

            private int lid;
            private int uid;
            private int type;
            private int tid;
            private String reason;
            private String time;
            private String realname;

            public int getLid() {
                return lid;
            }

            public void setLid(int lid) {
                this.lid = lid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
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

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }
        }
    }
}
