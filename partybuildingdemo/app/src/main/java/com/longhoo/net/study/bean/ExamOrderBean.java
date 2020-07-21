package com.longhoo.net.study.bean;

import java.util.List;

public class ExamOrderBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":"9","oid":"0","userid":"1","name":"学习贯彻党的十九大精神100题","qnum":"100","score":"100","desc":"学习贯彻党的十九大精神100题","starttime":"2018-02-10 15:52:56","endtime":"2020-02-12 15:52:56","examtime":"120","time":"1514965987","status":"2","examid":"9","current":"0","total":false},{"id":"8","oid":"0","userid":"189","name":"十九大报告","qnum":"20","score":"100","desc":"\u201c经过长期努力，中国特色社会主义进入了新时代。\u201d3万多字的十九大报告，涉及就业、扶贫、法治等多个方面。大会主题是什么？全面建成小康社会的决胜期是什么时候？20道题带你了解十九大报告，公考、考研用得上，赶紧收藏！","starttime":"2017-12-28 16:48:58","endtime":"2020-01-31 16:48:58","examtime":"50","time":"1514451331","status":"2","examid":"8","current":"0","total":false}]}
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

        public static class ListBean {
            /**
             * id : 9
             * oid : 0
             * userid : 1
             * name : 学习贯彻党的十九大精神100题
             * qnum : 100
             * score : 100
             * desc : 学习贯彻党的十九大精神100题
             * starttime : 2018-02-10 15:52:56
             * endtime : 2020-02-12 15:52:56
             * examtime : 120
             * time : 1514965987
             * status : 2
             * examid : 9
             * current : 0
             * total : false
             */

            private String id;
            private String oid;
            private String userid;
            private String name;
            private String qnum;
            private String score;
            private String desc;
            private String starttime;
            private String endtime;
            private String examtime;
            private String time;
            private String status;
            private String examid;
            private String current;
            private boolean total;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getQnum() {
                return qnum;
            }

            public void setQnum(String qnum) {
                this.qnum = qnum;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
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

            public String getExamtime() {
                return examtime;
            }

            public void setExamtime(String examtime) {
                this.examtime = examtime;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getExamid() {
                return examid;
            }

            public void setExamid(String examid) {
                this.examid = examid;
            }

            public String getCurrent() {
                return current;
            }

            public void setCurrent(String current) {
                this.current = current;
            }

            public boolean isTotal() {
                return total;
            }

            public void setTotal(boolean total) {
                this.total = total;
            }
        }
    }
}
