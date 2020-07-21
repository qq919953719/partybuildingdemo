package com.longhoo.net.study.bean;

import java.util.List;

public class ExamOrderItemBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"my":{"score":50,"rank":1,"mytime":"12分53秒"},"lists":[{"id":2,"exam_id":1,"uid":503,"name":"党支部工作条例知识竞赛","uname":"测试2","oname":"计算机与软件学院师生一支部,计算机与软件学院党总支","score":50,"time":"12分53秒"}]}
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
         * my : {"score":50,"rank":1,"mytime":"12分53秒"}
         * lists : [{"id":2,"exam_id":1,"uid":503,"name":"党支部工作条例知识竞赛","uname":"测试2","oname":"计算机与软件学院师生一支部,计算机与软件学院党总支","score":50,"time":"12分53秒"}]
         */

        private MyBean my;
        private List<ListsBean> lists;

        public MyBean getMy() {
            return my;
        }

        public void setMy(MyBean my) {
            this.my = my;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class MyBean {
            /**
             * score : 50
             * rank : 1
             * mytime : 12分53秒
             */

            private int score;
            private int rank;
            private String mytime;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getMytime() {
                return mytime;
            }

            public void setMytime(String mytime) {
                this.mytime = mytime;
            }
        }

        public static class ListsBean {
            /**
             * id : 2
             * exam_id : 1
             * uid : 503
             * name : 党支部工作条例知识竞赛
             * uname : 测试2
             * oname : 计算机与软件学院师生一支部,计算机与软件学院党总支
             * score : 50
             * time : 12分53秒
             */

            private int id;
            private int exam_id;
            private int uid;
            private String name;
            private String uname;
            private String oname;
            private int score;
            private String time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getExam_id() {
                return exam_id;
            }

            public void setExam_id(int exam_id) {
                this.exam_id = exam_id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getOname() {
                return oname;
            }

            public void setOname(String oname) {
                this.oname = oname;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
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
