package com.longhoo.net.manageservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ActsEncrollDetailBean implements Parcelable {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"aid":1764,"addtime":"2019-05-22 14:48","status":2,"ptitle":"cdgdfgdfg","meetingobj":"fdgdfg","uidstr":"177,178,179,180,181,183,184,182","ounit":"dfgdfg","cdate":"2019-05-24 00:00","enddate":"2019-05-25 00:00","stime":"2019-05-22 14:49","etime":"2019-05-23 00:00","addr":"dfgdfg","info":"<p>dfgdfg<br/><\/p>","uploads":"","uid":182,"cmr":"Qqqqqq","isEnroll":2,"signUser":"","leavestatus":1,"leavereason":"Fffffffff","leavepeople":[{"lid":144,"uid":182,"type":4,"tid":1764,"reason":"Fffffffff","time":"2019-05-22 15:06:28","realname":"于小娟"}],"is_join":2}
     */

    private String code;
    private String msg;
    private DataBean data;

    protected ActsEncrollDetailBean(Parcel in) {
        code = in.readString();
        msg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(msg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActsEncrollDetailBean> CREATOR = new Creator<ActsEncrollDetailBean>() {
        @Override
        public ActsEncrollDetailBean createFromParcel(Parcel in) {
            return new ActsEncrollDetailBean(in);
        }

        @Override
        public ActsEncrollDetailBean[] newArray(int size) {
            return new ActsEncrollDetailBean[size];
        }
    };

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

    public static class DataBean implements Parcelable{
        /**
         * aid : 1764
         * addtime : 2019-05-22 14:48
         * status : 2
         * ptitle : cdgdfgdfg
         * meetingobj : fdgdfg
         * uidstr : 177,178,179,180,181,183,184,182
         * ounit : dfgdfg
         * cdate : 2019-05-24 00:00
         * enddate : 2019-05-25 00:00
         * stime : 2019-05-22 14:49
         * etime : 2019-05-23 00:00
         * addr : dfgdfg
         * info : <p>dfgdfg<br/></p>
         * uploads :
         * uid : 182
         * cmr : Qqqqqq
         * isEnroll : 2
         * signUser :
         * leavestatus : 1
         * leavereason : Fffffffff
         * leavepeople : [{"lid":144,"uid":182,"type":4,"tid":1764,"reason":"Fffffffff","time":"2019-05-22 15:06:28","realname":"于小娟"}]
         * is_join : 2
         */

        private int aid;
        private String addtime;
        private int status;
        private String ptitle;
        private String meetingobj;
        private String uidstr;
        private String ounit;
        private String cdate;
        private String enddate;
        private String stime;
        private String etime;
        private String addr;
        private String info;
        private String uidNameStr;

        public String getOidstr() {
            return oidstr;
        }

        public void setOidstr(String oidstr) {
            this.oidstr = oidstr;
        }

        private String oidstr;

        public String getUidNameStr() {
            return uidNameStr;
        }

        public void setUidNameStr(String uidNameStr) {
            this.uidNameStr = uidNameStr;
        }

        public String getEnrollNameStr() {
            return enrollNameStr;
        }

        public void setEnrollNameStr(String enrollNameStr) {
            this.enrollNameStr = enrollNameStr;
        }

        private String enrollNameStr;
        private String uploads;

        protected DataBean(Parcel in) {
            aid = in.readInt();
            addtime = in.readString();
            status = in.readInt();
            ptitle = in.readString();
            meetingobj = in.readString();
            uidstr = in.readString();
            ounit = in.readString();
            cdate = in.readString();
            enddate = in.readString();
            stime = in.readString();
            etime = in.readString();
            addr = in.readString();
            info = in.readString();
            uploads = in.readString();
            unback = in.readString();
            uid = in.readInt();
            cmr = in.readString();
            isEnroll = in.readInt();
            signUser = in.readString();
            leavestatus = in.readInt();
            leavereason = in.readString();
            is_join = in.readInt();
            is_sign = in.readInt();
            uname_str = in.readString();
            uidNameStr = in.readString();
            enrollNameStr = in.readString();

            oidstr = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(aid);
            dest.writeString(addtime);
            dest.writeInt(status);
            dest.writeString(ptitle);
            dest.writeString(meetingobj);
            dest.writeString(uidstr);
            dest.writeString(ounit);
            dest.writeString(cdate);
            dest.writeString(enddate);
            dest.writeString(stime);
            dest.writeString(etime);
            dest.writeString(addr);
            dest.writeString(info);
            dest.writeString(uploads);
            dest.writeString(unback);
            dest.writeInt(uid);
            dest.writeString(cmr);
            dest.writeInt(isEnroll);
            dest.writeString(signUser);
            dest.writeInt(leavestatus);
            dest.writeString(leavereason);
            dest.writeInt(is_join);
            dest.writeInt(is_sign);
            dest.writeString(uname_str);
            dest.writeString(uidNameStr);
            dest.writeString(enrollNameStr);
            dest.writeString(oidstr);

        }

        @Override
        public int describeContents() {
            return 0;
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

        public String getUnback() {
            return unback;
        }

        public void setUnback(String unback) {
            this.unback = unback;
        }

        private String unback;
        private int uid;
        private String cmr;
        private int isEnroll;
        private String signUser;
        private int leavestatus;
        private String leavereason;
        private int is_join;

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }

        private int   is_sign;
        private List<LeavepeopleBean> leavepeople;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPtitle() {
            return ptitle;
        }

        public String getUname_str() {
            return uname_str;
        }

        public void setUname_str(String uname_str) {
            this.uname_str = uname_str;
        }

        private String uname_str;
        public void setPtitle(String ptitle) {
            this.ptitle = ptitle;
        }

        public String getMeetingobj() {
            return meetingobj;
        }

        public void setMeetingobj(String meetingobj) {
            this.meetingobj = meetingobj;
        }

        public String getUidstr() {
            return uidstr;
        }

        public void setUidstr(String uidstr) {
            this.uidstr = uidstr;
        }

        public String getOunit() {
            return ounit;
        }

        public void setOunit(String ounit) {
            this.ounit = ounit;
        }

        public String getCdate() {
            return cdate;
        }

        public void setCdate(String cdate) {
            this.cdate = cdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getUploads() {
            return uploads;
        }

        public void setUploads(String uploads) {
            this.uploads = uploads;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getCmr() {
            return cmr;
        }

        public void setCmr(String cmr) {
            this.cmr = cmr;
        }

        public int getIsEnroll() {
            return isEnroll;
        }

        public void setIsEnroll(int isEnroll) {
            this.isEnroll = isEnroll;
        }

        public String getSignUser() {
            return signUser;
        }

        public void setSignUser(String signUser) {
            this.signUser = signUser;
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

        public int getIs_join() {
            return is_join;
        }

        public void setIs_join(int is_join) {
            this.is_join = is_join;
        }

        public List<LeavepeopleBean> getLeavepeople() {
            return leavepeople;
        }

        public void setLeavepeople(List<LeavepeopleBean> leavepeople) {
            this.leavepeople = leavepeople;
        }

        public static class LeavepeopleBean {
            /**
             * lid : 144
             * uid : 182
             * type : 4
             * tid : 1764
             * reason : Fffffffff
             * time : 2019-05-22 15:06:28
             * realname : 于小娟
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
