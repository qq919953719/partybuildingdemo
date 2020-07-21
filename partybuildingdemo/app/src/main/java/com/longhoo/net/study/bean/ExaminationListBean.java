package com.longhoo.net.study.bean;

import java.util.List;

/**
 * Created by ck on 2017/12/21.
 */

public class ExaminationListBean{


    /**
     * code : 0
     * msg : 成功
     * data : [{"id":3,"name":"测试1","score":100,"desc":"测试","starttime":"2018-12-03 00:00:00","endtime":"2018-12-29 00:00:00","examtime":20},{"id":1,"name":"党支部工作条例知识竞赛","score":100,"desc":"党支部工作条例知识竞赛","starttime":"2019-01-04 09:00:00","endtime":"2019-01-31 15:30:00","examtime":60}]
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
         * name : 测试1
         * score : 100
         * desc : 测试
         * starttime : 2018-12-03 00:00:00
         * endtime : 2018-12-29 00:00:00
         * examtime : 20
         */

        private int id;
        private String name;
        private int score;
        private String desc;
        private String starttime;
        private String endtime;
        private int examtime;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

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

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getExamtime() {
            return examtime;
        }

        public void setExamtime(int examtime) {
            this.examtime = examtime;
        }
    }
}
