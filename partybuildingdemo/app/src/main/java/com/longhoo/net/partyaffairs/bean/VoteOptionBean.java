package com.longhoo.net.partyaffairs.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by cc on 2017/12/19.
 */

public class VoteOptionBean {


    /**
     * code : 0
     * msg : <p>活动介绍</p>
     * data : [{"id":13,"title":"纪虹","thumbnail":"http://ngy4.dangjian.025nj.com//zjnh/public/upload/20191210/0714bdb5503e3b3785872c47e32f407c.jpg","catid":12}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 13
         * title : 纪虹
         * thumbnail : http://ngy4.dangjian.025nj.com//zjnh/public/upload/20191210/0714bdb5503e3b3785872c47e32f407c.jpg
         * catid : 12
         */

        private int id;
        private String title;
        private String thumbnail;

        protected DataBean(Parcel in) {
            id = in.readInt();
            title = in.readString();
            thumbnail = in.readString();
            num = in.readString();
            catid = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(title);
            dest.writeString(thumbnail);
            dest.writeString(num);
            dest.writeInt(catid);
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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        private String num;

        public String getIs_zan() {
            return is_zan;
        }

        public void setIs_zan(String is_zan) {
            this.is_zan = is_zan;
        }

        private String is_zan;

        private int catid;

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

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getCatid() {
            return catid;
        }

        public void setCatid(int catid) {
            this.catid = catid;
        }
    }
}
