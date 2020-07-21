package com.longhoo.net.partyaffairs.bean;

import java.util.List;

/**
 * Created by ${CC} on 2017/12/28.
 */

public class AssessmentandreviewBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"assess":[{"aid":"1","info":"开展三会一课","score":"1","scorenote":"1分/条","scoresum":"1"},{"aid":"2","info":"开展党员生活会","score":"1","scorenote":"1分/条","scoresum":"1"},{"aid":"3","info":"开展组织生活会","score":"1","scorenote":"1分/条","scoresum":"1"},{"aid":"4","info":"组织成员每期按时交纳党费","score":"5","scorenote":"5分/条","scoresum":"5"},{"aid":"5","info":"发布党风廉政","score":"1","scorenote":"1分/条","scoresum":"3"},{"aid":"6","info":"发布群团工作","score":"1","scorenote":"1分/条","scoresum":"3"},{"aid":"7","info":"组织内党员获得集团党建先锋第一名","score":"5","scorenote":"5分","scoresum":"15"},{"aid":"8","info":"发布一场学习直播","score":"5","scorenote":"5分/条","scoresum":"20"},{"aid":"9","info":"举办一场考试","score":"5","scorenote":"5分/条","scoresum":""}],"score":"49"}
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
         * assess : [{"aid":"1","info":"开展三会一课","score":"1","scorenote":"1分/条","scoresum":"1"},{"aid":"2","info":"开展党员生活会","score":"1","scorenote":"1分/条","scoresum":"1"},{"aid":"3","info":"开展组织生活会","score":"1","scorenote":"1分/条","scoresum":"1"},{"aid":"4","info":"组织成员每期按时交纳党费","score":"5","scorenote":"5分/条","scoresum":"5"},{"aid":"5","info":"发布党风廉政","score":"1","scorenote":"1分/条","scoresum":"3"},{"aid":"6","info":"发布群团工作","score":"1","scorenote":"1分/条","scoresum":"3"},{"aid":"7","info":"组织内党员获得集团党建先锋第一名","score":"5","scorenote":"5分","scoresum":"15"},{"aid":"8","info":"发布一场学习直播","score":"5","scorenote":"5分/条","scoresum":"20"},{"aid":"9","info":"举办一场考试","score":"5","scorenote":"5分/条","scoresum":""}]
         * score : 49
         */

        private String score;
        private List<AssessBean> assess;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public List<AssessBean> getAssess() {
            return assess;
        }

        public void setAssess(List<AssessBean> assess) {
            this.assess = assess;
        }

        public static class AssessBean {
            /**
             * aid : 1
             * info : 开展三会一课
             * score : 1
             * scorenote : 1分/条
             * scoresum : 1
             */

            private String aid;
            private String info;
            private String score;
            private String scorenote;
            private String scoresum;

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getScorenote() {
                return scorenote;
            }

            public void setScorenote(String scorenote) {
                this.scorenote = scorenote;
            }

            public String getScoresum() {
                return scoresum;
            }

            public void setScoresum(String scoresum) {
                this.scoresum = scoresum;
            }
        }
    }
}
