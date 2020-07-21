package com.longhoo.net.mine.bean;

import java.util.List;

public class SharePeopleListBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"people":[{"uid":10106,"name":"孟成真"}]}
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
        private List<PeopleBean> people;

        public List<PeopleBean> getPeople() {
            return people;
        }

        public void setPeople(List<PeopleBean> people) {
            this.people = people;
        }

        public static class PeopleBean {
            /**
             * uid : 10106
             * name : 孟成真
             */

            private int uid;
            private String name;

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
        }
    }
}
