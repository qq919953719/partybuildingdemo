package com.longhoo.net.mine.bean;

import java.util.List;

public class ScoreSortBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"info":[{"realname":"孟成真","score":35},{"realname":"组织部","score":0},{"realname":"mcz","score":0}],"score":35,"rank":1}
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
         * info : [{"realname":"孟成真","score":35},{"realname":"组织部","score":0},{"realname":"mcz","score":0}]
         * score : 35
         * rank : 1
         */

        private int score;
        private int rank;
        private List<InfoBean> info;

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

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * realname : 孟成真
             * score : 35
             */

            private String realname;
            private int score;

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
        }
    }
}
