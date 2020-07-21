package com.longhoo.net.study.bean;

public class ExamInfoBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"id":1,"name":"党支部工作条例知识竞赛","times":100,"qnum":20,"score":100,"examtime":60,"left_times":99}
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
         * id : 1
         * name : 党支部工作条例知识竞赛
         * times : 100
         * qnum : 20
         * score : 100
         * examtime : 60
         * left_times : 99
         */

        private int id;
        private String name;
        private int times;
        private int qnum;
        private int score;
        private int examtime;
        private int left_times;
        private String desc;

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

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public int getQnum() {
            return qnum;
        }

        public void setQnum(int qnum) {
            this.qnum = qnum;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getExamtime() {
            return examtime;
        }

        public void setExamtime(int examtime) {
            this.examtime = examtime;
        }

        public int getLeft_times() {
            return left_times;
        }

        public void setLeft_times(int left_times) {
            this.left_times = left_times;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
