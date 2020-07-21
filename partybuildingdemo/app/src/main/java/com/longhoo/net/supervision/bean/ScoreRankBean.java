package com.longhoo.net.supervision.bean;

import java.util.List;

public class ScoreRankBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":[{"realname":"蒋凌燕","score":100,"num":"01","order":1},{"realname":"安婧","score":100,"num":"02","order":2},{"realname":"孙扬清","score":100,"num":"03","order":3},{"realname":"李中科","score":100,"num":"04","order":4},{"realname":"赵慧娟","score":100,"num":"05","order":5},{"realname":"刘芳芳","score":100,"num":"06","order":6},{"realname":"许莫淇","score":100,"num":"07","order":7},{"realname":"蒋美云","score":100,"num":"08","order":8},{"realname":"贾利娟","score":100,"num":"09","order":9},{"realname":"孟庆杰","score":100,"num":"010","order":10}]}
     */

    private String code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * realname : 蒋凌燕
             * score : 100
             * num : 01
             * order : 1
             */

            private String realname;
            private int score;
            private String num;
            private int order;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }
        }
    }
}
