package com.longhoo.net.study.bean;

import java.util.List;

public class SelfAnswerAnalysisBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"list":[{"id":54,"pid":3,"name":"11","type":1,"selections":"11","answershow":"11","answerinfo":"11"},{"id":55,"pid":3,"name":"444","type":1,"selections":"444","answershow":"1","answerinfo":""}]}
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
             * id : 54
             * pid : 3
             * name : 11
             * type : 1
             * selections : 11
             * answershow : 11
             * answerinfo : 11
             */

            private int id;
            private int pid;
            private String name;
            private int type;
            private String selections;
            private String answershow;
            private String answerinfo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getSelections() {
                return selections;
            }

            public void setSelections(String selections) {
                this.selections = selections;
            }

            public String getAnswershow() {
                return answershow;
            }

            public void setAnswershow(String answershow) {
                this.answershow = answershow;
            }

            public String getAnswerinfo() {
                return answerinfo;
            }

            public void setAnswerinfo(String answerinfo) {
                this.answerinfo = answerinfo;
            }
        }
    }
}
