package com.longhoo.net.manageservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MemberZiXunBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":1,"title":"士大夫","tid":3,"thumbnail":"http://ngy4.dangjian.025nj.com/upload/20200221/117b734489ef43da4bcca991550d8c45.jpg","content":"&lt;p&gt;手动阀手动阀手动阀手动阀&lt;/p&gt;","add_time":"2020-02-21 17:25:35"}]}
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
             * id : 1
             * title : 士大夫
             * tid : 3
             * thumbnail : http://ngy4.dangjian.025nj.com/upload/20200221/117b734489ef43da4bcca991550d8c45.jpg
             * content : &lt;p&gt;手动阀手动阀手动阀手动阀&lt;/p&gt;
             * add_time : 2020-02-21 17:25:35
             */

            private int id;
            private String title;
            private int tid;
            private String thumbnail;
            private String content;
            private String add_time;

            protected ListBean(Parcel in) {
                id = in.readInt();
                title = in.readString();
                tid = in.readInt();
                thumbnail = in.readString();
                content = in.readString();
                add_time = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeString(title);
                dest.writeInt(tid);
                dest.writeString(thumbnail);
                dest.writeString(content);
                dest.writeString(add_time);
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }
    }
}
