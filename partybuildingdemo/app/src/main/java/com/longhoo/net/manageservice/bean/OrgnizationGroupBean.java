package com.longhoo.net.manageservice.bean;

import java.util.List;

/**
 * Created by admin on 2017/11/28.
 */

public class OrgnizationGroupBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"fid":"1","parentid":"3","fname":"党员社区","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa69e72bcb.png","type":"1","time":"1511865583","noread":"1"},{"fid":"2","parentid":"3","fname":"明主生活会","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa69303781.png","type":"2","time":"1511865597","noread":"1"},{"fid":"3","parentid":"3","fname":"组织生活会","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa68bc54fc.png","type":"3","time":"1511865618","noread":"1"},{"fid":"4","parentid":"3","fname":"三会一课","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa6813abf9.png","type":"4","time":"1511865644","noread":"1"}],"myorganization":{"oid":"2","name":"龙虎网党小组"},"organizationList":[{"oid":"2","name":"龙虎网党小组","member":[{"uid":"989","nickname":"全球人生"},{"uid":"986","nickname":"无霜"},{"uid":"1","nickname":"文明南京"},{"uid":"3","nickname":"陈凯陈"},{"uid":"2","nickname":"崔狗狗"}]},{"oid":"8","name":"龙虎网党小组2","member":[{"uid":"987","nickname":"name1"},{"uid":"988","nickname":"可怜啦咯了哈哈哈哈哈"}]}]}
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
         * list : [{"fid":"1","parentid":"3","fname":"党员社区","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa69e72bcb.png","type":"1","time":"1511865583","noread":"1"},{"fid":"2","parentid":"3","fname":"明主生活会","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa69303781.png","type":"2","time":"1511865597","noread":"1"},{"fid":"3","parentid":"3","fname":"组织生活会","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa68bc54fc.png","type":"3","time":"1511865618","noread":"1"},{"fid":"4","parentid":"3","fname":"三会一课","thumb":"http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa6813abf9.png","type":"4","time":"1511865644","noread":"1"}]
         * myorganization : {"oid":"2","name":"龙虎网党小组"}
         * organizationList : [{"oid":"2","name":"龙虎网党小组","member":[{"uid":"989","nickname":"全球人生"},{"uid":"986","nickname":"无霜"},{"uid":"1","nickname":"文明南京"},{"uid":"3","nickname":"陈凯陈"},{"uid":"2","nickname":"崔狗狗"}]},{"oid":"8","name":"龙虎网党小组2","member":[{"uid":"987","nickname":"name1"},{"uid":"988","nickname":"可怜啦咯了哈哈哈哈哈"}]}]
         */

        private MyorganizationBean myorganization;
        private List<ListBean> list;
        private List<OrganizationListBean> organizationList;

        public MyorganizationBean getMyorganization() {
            return myorganization;
        }

        public void setMyorganization(MyorganizationBean myorganization) {
            this.myorganization = myorganization;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<OrganizationListBean> getOrganizationList() {
            return organizationList;
        }

        public void setOrganizationList(List<OrganizationListBean> organizationList) {
            this.organizationList = organizationList;
        }

        public static class MyorganizationBean {
            /**
             * oid : 2
             * name : 龙虎网党小组
             */

            private String oid;
            private String name;

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ListBean {
            /**
             * fid : 1
             * parentid : 3
             * fname : 党员社区
             * thumb : http://test.025nj.com/dangjian//UploadFile/organizatioo/2017/1130/5a1fa69e72bcb.png
             * type : 1
             * time : 1511865583
             * noread : 1
             */

            private String fid;
            private String parentid;
            private String fname;
            private String thumb;
            private String type;
            private String time;
            private String noread;

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getFname() {
                return fname;
            }

            public void setFname(String fname) {
                this.fname = fname;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getNoread() {
                return noread;
            }

            public void setNoread(String noread) {
                this.noread = noread;
            }
        }

        public static class OrganizationListBean {
            /**
             * oid : 2
             * name : 龙虎网党小组
             * member : [{"uid":"989","nickname":"全球人生"},{"uid":"986","nickname":"无霜"},{"uid":"1","nickname":"文明南京"},{"uid":"3","nickname":"陈凯陈"},{"uid":"2","nickname":"崔狗狗"}]
             */

            private String oid;
            private String name;
            private List<MemberBean> member;

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<MemberBean> getMember() {
                return member;
            }

            public void setMember(List<MemberBean> member) {
                this.member = member;
            }

            public static class MemberBean {
                /**
                 * uid : 989
                 * nickname : 全球人生
                 */

                private String uid;

                public String getRealname() {
                    return realname;
                }
                public String getClick() {
                    return click;
                }

                public void setClick(String click) {
                    this.click = click;
                }

                private String click;//1可点击，0不可点击
                public void setRealname(String realname) {
                    this.realname = realname;
                }

                private String realname;

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }


            }
        }
    }
}
