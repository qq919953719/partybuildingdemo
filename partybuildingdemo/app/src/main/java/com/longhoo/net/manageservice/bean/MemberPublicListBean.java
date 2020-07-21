package com.longhoo.net.manageservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MemberPublicListBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":28,"name":"李亚东","mobile":"18251979735","id_number":"411111111111111111","reason":"可口可乐了了了","oid":1,"recommender":"李亚东","company":"龙虎网","addr":"龙虎网","thumb":["http://ngy4.dangjian.025nj.com/upload/said/20200318/97e1558214913a39a588b01ecf5c91de.jpeg","http://ngy4.dangjian.025nj.com/upload/said/20200318/e506a177a365d8b1b9a7f39d58c9c4fe.jpeg"],"file_url":"http://ngy4.dangjian.025nj.com/upload/said/20200318/e0337d162132e63b762e7deefd4f6ead.xlsx","file_name":"副本 总表（7.19）.xlsx","tid":1,"check_status":0,"check_remarks":"","addtime":"2020-03-18 09:55:59","uid":10106,"oname":"党委","check_status_name":"审核中"}]}
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

        public static class ListBean implements Parcelable {
            /**
             * id : 28
             * name : 李亚东
             * mobile : 18251979735
             * id_number : 411111111111111111
             * reason : 可口可乐了了了
             * oid : 1
             * recommender : 李亚东
             * company : 龙虎网
             * addr : 龙虎网
             * thumb : ["http://ngy4.dangjian.025nj.com/upload/said/20200318/97e1558214913a39a588b01ecf5c91de.jpeg","http://ngy4.dangjian.025nj.com/upload/said/20200318/e506a177a365d8b1b9a7f39d58c9c4fe.jpeg"]
             * file_url : http://ngy4.dangjian.025nj.com/upload/said/20200318/e0337d162132e63b762e7deefd4f6ead.xlsx
             * file_name : 副本 总表（7.19）.xlsx
             * tid : 1
             * check_status : 0
             * check_remarks :
             * addtime : 2020-03-18 09:55:59
             * uid : 10106
             * oname : 党委
             * check_status_name : 审核中
             */

            private int id;
            private String name;
            private String mobile;
            private String id_number;
            private String reason;
            private int oid;
            private String recommender;
            private String company;
            private String addr;
            private String file_url;
            private String file_name;
            private int tid;
            private String check_status;
            private String check_remarks;
            private String addtime;
            private int uid;
            private String oname;
            private String check_status_name;
            private List<String> thumb;
            private String birthday;//  出生年月
            private String in_party_time;//  入党申请日期
            private String confirm_activist_time;//  确定积极分子日期
            private String confirm_obj_time;// 确定发展对象日期

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getIn_party_time() {
                return in_party_time;
            }

            public void setIn_party_time(String in_party_time) {
                this.in_party_time = in_party_time;
            }

            public String getConfirm_activist_time() {
                return confirm_activist_time;
            }

            public void setConfirm_activist_time(String confirm_activist_time) {
                this.confirm_activist_time = confirm_activist_time;
            }

            public String getConfirm_obj_time() {
                return confirm_obj_time;
            }

            public void setConfirm_obj_time(String confirm_obj_time) {
                this.confirm_obj_time = confirm_obj_time;
            }

            public String getConfirm_ready_time() {
                return confirm_ready_time;
            }

            public void setConfirm_ready_time(String confirm_ready_time) {
                this.confirm_ready_time = confirm_ready_time;
            }

            private String confirm_ready_time;// 成为预备党员日期

            protected ListBean(Parcel in) {
                id = in.readInt();
                name = in.readString();
                mobile = in.readString();
                id_number = in.readString();
                reason = in.readString();
                oid = in.readInt();
                recommender = in.readString();
                company = in.readString();
                addr = in.readString();
                file_url = in.readString();
                file_name = in.readString();
                tid = in.readInt();
                check_status = in.readString();
                check_remarks = in.readString();
                addtime = in.readString();
                uid = in.readInt();
                oname = in.readString();
                check_status_name = in.readString();
                thumb = in.createStringArrayList();
                birthday = in.readString();
                in_party_time = in.readString();
                confirm_activist_time = in.readString();
                confirm_obj_time = in.readString();
                confirm_ready_time = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeString(name);
                dest.writeString(mobile);
                dest.writeString(id_number);
                dest.writeString(reason);
                dest.writeInt(oid);
                dest.writeString(recommender);
                dest.writeString(company);
                dest.writeString(addr);
                dest.writeString(file_url);
                dest.writeString(file_name);
                dest.writeInt(tid);
                dest.writeString(check_status);
                dest.writeString(check_remarks);
                dest.writeString(addtime);
                dest.writeInt(uid);
                dest.writeString(oname);
                dest.writeString(check_status_name);
                dest.writeStringList(thumb);
                dest.writeString(birthday);
                dest.writeString(in_party_time);
                dest.writeString(confirm_activist_time);
                dest.writeString(confirm_obj_time);
                dest.writeString(confirm_ready_time);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getId_number() {
                return id_number;
            }

            public void setId_number(String id_number) {
                this.id_number = id_number;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public int getOid() {
                return oid;
            }

            public void setOid(int oid) {
                this.oid = oid;
            }

            public String getRecommender() {
                return recommender;
            }

            public void setRecommender(String recommender) {
                this.recommender = recommender;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getFile_url() {
                return file_url;
            }

            public void setFile_url(String file_url) {
                this.file_url = file_url;
            }

            public String getFile_name() {
                return file_name;
            }

            public void setFile_name(String file_name) {
                this.file_name = file_name;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }

            public String getCheck_status() {
                return check_status;
            }

            public void setCheck_status(String check_status) {
                this.check_status = check_status;
            }

            public String getCheck_remarks() {
                return check_remarks;
            }

            public void setCheck_remarks(String check_remarks) {
                this.check_remarks = check_remarks;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getOname() {
                return oname;
            }

            public void setOname(String oname) {
                this.oname = oname;
            }

            public String getCheck_status_name() {
                return check_status_name;
            }

            public void setCheck_status_name(String check_status_name) {
                this.check_status_name = check_status_name;
            }

            public List<String> getThumb() {
                return thumb;
            }

            public void setThumb(List<String> thumb) {
                this.thumb = thumb;
            }
        }
    }
}
