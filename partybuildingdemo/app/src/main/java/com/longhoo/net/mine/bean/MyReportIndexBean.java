package com.longhoo.net.mine.bean;

import java.util.List;

public class MyReportIndexBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"sameDay_time":0,"count_time":0,"cat_learn_report":[{"type":1,"type_name":"阅读","num":1,"time":0},{"type":2,"type_name":"视频","num":0,"time":0},{"type":3,"type_name":"答题","num":0,"time":0},{"type":4,"type_name":"心得","num":0,"time":0},{"type":5,"type_name":"评论","num":0,"time":0}]}
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
         * sameDay_time : 0
         * count_time : 0
         * cat_learn_report : [{"type":1,"type_name":"阅读","num":1,"time":0},{"type":2,"type_name":"视频","num":0,"time":0},{"type":3,"type_name":"答题","num":0,"time":0},{"type":4,"type_name":"心得","num":0,"time":0},{"type":5,"type_name":"评论","num":0,"time":0}]
         */

        private int sameDay_time;
        private int count_time;
        private List<CatLearnReportBean> cat_learn_report;

        public int getSameDay_time() {
            return sameDay_time;
        }

        public void setSameDay_time(int sameDay_time) {
            this.sameDay_time = sameDay_time;
        }

        public int getCount_time() {
            return count_time;
        }

        public void setCount_time(int count_time) {
            this.count_time = count_time;
        }

        public List<CatLearnReportBean> getCat_learn_report() {
            return cat_learn_report;
        }

        public void setCat_learn_report(List<CatLearnReportBean> cat_learn_report) {
            this.cat_learn_report = cat_learn_report;
        }

        public static class CatLearnReportBean {
            /**
             * type : 1
             * type_name : 阅读
             * num : 1
             * time : 0
             */

            private int type;
            private String type_name;
            private int num;
            private int time;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }
        }
    }
}
