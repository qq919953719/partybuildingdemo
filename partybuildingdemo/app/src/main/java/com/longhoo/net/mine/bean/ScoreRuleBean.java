package com.longhoo.net.mine.bean;

public class ScoreRuleBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"id":26,"info":"<p>积分规则：<br/><\/p><p>1111<\/p><p>2222<\/p><p>44444<br/><\/p>","type":3}
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
         * id : 26
         * info : <p>积分规则：<br/></p><p>1111</p><p>2222</p><p>44444<br/></p>
         * type : 3
         */

        private int id;
        private String info;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
