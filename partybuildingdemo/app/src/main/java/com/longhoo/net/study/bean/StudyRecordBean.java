package com.longhoo.net.study.bean;

import java.util.List;

public class StudyRecordBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"uid":503,"realname":"测试2","count":72,"courseCount":72,"classCount":0},{"uid":272,"realname":"褚鹤华","count":0,"courseCount":0,"classCount":0},{"uid":271,"realname":"陈吉","count":0,"courseCount":0,"classCount":0},{"uid":270,"realname":"步勇星","count":0,"courseCount":0,"classCount":0},{"uid":269,"realname":"伍洪斌","count":0,"courseCount":0,"classCount":0},{"uid":268,"realname":"石坚","count":0,"courseCount":0,"classCount":0},{"uid":267,"realname":"李建国","count":0,"courseCount":0,"classCount":0},{"uid":266,"realname":"苏晓萍","count":0,"courseCount":0,"classCount":0},{"uid":265,"realname":"茅晓薇","count":0,"courseCount":0,"classCount":0},{"uid":264,"realname":"朱琳","count":0,"courseCount":0,"classCount":0}]
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

    public static class DataBean {
        /**
         * uid : 503
         * realname : 测试2
         * count : 72
         * courseCount : 72
         * classCount : 0
         */

        private int uid;
        private String realname;
        private int count;
        private int courseCount;
        private int classCount;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCourseCount() {
            return courseCount;
        }

        public void setCourseCount(int courseCount) {
            this.courseCount = courseCount;
        }

        public int getClassCount() {
            return classCount;
        }

        public void setClassCount(int classCount) {
            this.classCount = classCount;
        }
    }
}
