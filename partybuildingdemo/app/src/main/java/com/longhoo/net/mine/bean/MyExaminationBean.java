package com.longhoo.net.mine.bean;

import java.util.List;

/**
 * Created by ${CC} on 2018/1/3.
 */

public class MyExaminationBean {


    /**
     * code : 0
     * msg : 成功
     * data : [{"id":3,"exam_id":1,"uid":503,"name":"党支部工作条例知识竞赛","uname":"测试2","oname":"计算机与软件学院师生一支部,计算机与软件学院党总支","qnum":20,"right":13,"score":65,"time":"0分34秒","wrong":7}]
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
         * id : 3
         * exam_id : 1
         * uid : 503
         * name : 党支部工作条例知识竞赛
         * uname : 测试2
         * oname : 计算机与软件学院师生一支部,计算机与软件学院党总支
         * qnum : 20
         * right : 13
         * score : 65
         * time : 0分34秒
         * wrong : 7
         */

        private int id;
        private int exam_id;
        private int uid;
        private String name;
        private String uname;
        private String oname;
        private int qnum;
        private int right;
        private int score;
        private String time;
        private int wrong;
        private int euid;

        public int getEuid() {
            return euid;
        }

        public void setEuid(int euid) {
            this.euid = euid;
        }

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

        public int getQnum() {
            return qnum;
        }

        public void setQnum(int qnum) {
            this.qnum = qnum;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
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

        public int getWrong() {
            return wrong;
        }

        public void setWrong(int wrong) {
            this.wrong = wrong;
        }
    }
}
