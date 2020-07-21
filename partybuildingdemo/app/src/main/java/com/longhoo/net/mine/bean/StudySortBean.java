package com.longhoo.net.mine.bean;

import java.util.List;

public class StudySortBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"list":{"my_rank":1,"my_study_time":2,"rank_list":[{"ctime":"8140","uid":10106,"realname":"孟成真","orderlist":1},{"ctime":"14","uid":10107,"realname":"mcz","orderlist":2}]}}
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
         * list : {"my_rank":1,"my_study_time":2,"rank_list":[{"ctime":"8140","uid":10106,"realname":"孟成真","orderlist":1},{"ctime":"14","uid":10107,"realname":"mcz","orderlist":2}]}
         */

        private ListBean list;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * my_rank : 1
             * my_study_time : 2
             * rank_list : [{"ctime":"8140","uid":10106,"realname":"孟成真","orderlist":1},{"ctime":"14","uid":10107,"realname":"mcz","orderlist":2}]
             */

            private int my_rank;
            private int my_study_time;
            private List<RankListBean> rank_list;

            public int getMy_rank() {
                return my_rank;
            }

            public void setMy_rank(int my_rank) {
                this.my_rank = my_rank;
            }

            public int getMy_study_time() {
                return my_study_time;
            }

            public void setMy_study_time(int my_study_time) {
                this.my_study_time = my_study_time;
            }

            public List<RankListBean> getRank_list() {
                return rank_list;
            }

            public void setRank_list(List<RankListBean> rank_list) {
                this.rank_list = rank_list;
            }

            public static class RankListBean {
                /**
                 * ctime : 8140
                 * uid : 10106
                 * realname : 孟成真
                 * orderlist : 1
                 */

                private String ctime;
                private int uid;
                private String realname;
                private int orderlist;

                public String getCtime() {
                    return ctime;
                }

                public void setCtime(String ctime) {
                    this.ctime = ctime;
                }

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public String getRealname() {
                    return realname;
                }

                public void setRealname(String realname) {
                    this.realname = realname;
                }

                public int getOrderlist() {
                    return orderlist;
                }

                public void setOrderlist(int orderlist) {
                    this.orderlist = orderlist;
                }
            }
        }
    }
}
