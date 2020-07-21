package com.longhoo.net.supervision.bean;

import java.util.List;

public class ZhibuOrgBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"oid":1,"name":"机关党总支","pid":0},{"oid":17,"name":"└\u2015机关党政办党支部","pid":1},{"oid":18,"name":"└\u2015机关组宣党支部","pid":1},{"oid":19,"name":"└\u2015机关纪监党支部","pid":1},{"oid":20,"name":"└\u2015机关学工党支部","pid":1},{"oid":21,"name":"└\u2015机关保卫党支部","pid":1},{"oid":22,"name":"└\u2015机关团委党支部","pid":1},{"oid":23,"name":"└\u2015机关工会党支部","pid":1},{"oid":24,"name":"└\u2015机关人事党支部","pid":1},{"oid":25,"name":"└\u2015└\u2015机关计财党支部","pid":24},{"oid":26,"name":"└\u2015机关教务党支部","pid":1},{"oid":27,"name":"└\u2015机关国际处党支部","pid":1},{"oid":28,"name":"└\u2015机关质控党支部","pid":1},{"oid":29,"name":"└\u2015机关科技党支部","pid":1},{"oid":30,"name":"└\u2015机关招就党支部","pid":1},{"oid":31,"name":"└\u2015国际教育学院师生支部","pid":1},{"oid":32,"name":"└\u2015机关资产党支部","pid":1},{"oid":68,"name":"└\u2015机关纪检党支部","pid":1},{"oid":2,"name":"机械工程学院党总支","pid":0},{"oid":33,"name":"└\u2015机械工程学院教工一支部","pid":2},{"oid":35,"name":"└\u2015机械工程学院教工三支部","pid":2},{"oid":36,"name":"└\u2015机械工程学院师生一支部","pid":2},{"oid":37,"name":"└\u2015机械工程学院师生二支部","pid":2},{"oid":38,"name":"└\u2015机械工程学院师生三支部","pid":2},{"oid":34,"name":"└\u2015└\u2015机械工程学院教工二支部","pid":38},{"oid":3,"name":"电气工程学院党总支","pid":0},{"oid":39,"name":"└\u2015电气工程学院教工支部","pid":3},{"oid":40,"name":"└\u2015电气工程学院师生一支部","pid":3},{"oid":41,"name":"└\u2015电气工程学院师生二支部","pid":3},{"oid":42,"name":"└\u2015电气工程学院师生三支部","pid":3},{"oid":4,"name":"航空工程学院党总支","pid":0},{"oid":43,"name":"└\u2015航空工程学院师生一支部","pid":4},{"oid":44,"name":"└\u2015航空工程学院师生二支部","pid":4},{"oid":45,"name":"└\u2015航空工程学院师生三支部","pid":4},{"oid":5,"name":"交通工程学院党总支","pid":0},{"oid":46,"name":"└\u2015交通工程学院师生一支部","pid":5},{"oid":47,"name":"└\u2015交通工程学院师生二支部","pid":5},{"oid":6,"name":"计算机与软件学院党总支","pid":0},{"oid":48,"name":"└\u2015计算机与软件学院师生一支部","pid":6},{"oid":78,"name":"└\u2015└\u2015 测试支部","pid":48},{"oid":49,"name":"└\u2015计算机与软件学院师生二支部","pid":6},{"oid":50,"name":"└\u2015计算机与软件学院师生三支部","pid":6},{"oid":51,"name":"└\u2015计算机与软件学院教工支部","pid":6},{"oid":7,"name":"经济管理学院党总支","pid":0},{"oid":52,"name":"└\u2015经济管理学院教工支部","pid":7},{"oid":53,"name":"└\u2015经济管理学院师生一支部","pid":7},{"oid":54,"name":"└\u2015经济管理学院师生二支部","pid":7},{"oid":8,"name":"商务贸易学院党总支","pid":0},{"oid":55,"name":"└\u2015商务贸易学院师生一支部","pid":8},{"oid":56,"name":"└\u2015商务贸易学院师生二支部","pid":8},{"oid":57,"name":"└\u2015商务贸易学院师生三支部","pid":8},{"oid":58,"name":"└\u2015商务贸易学院师生四支部","pid":8},{"oid":9,"name":"艺术设计学院党总支","pid":0},{"oid":59,"name":"└\u2015艺术设计学院教工支部","pid":9},{"oid":60,"name":"└\u2015艺术设计学院师生一支部","pid":9},{"oid":61,"name":"└\u2015艺术设计学院师生二支部","pid":9},{"oid":10,"name":"国际教学学院党总支","pid":0},{"oid":62,"name":"└\u2015国际教育学院教工支部","pid":10},{"oid":63,"name":"马克思主义学院直属党支部","pid":0},{"oid":12,"name":"└\u2015马克思主义学院直属党支部","pid":63},{"oid":64,"name":"航空党支部","pid":0},{"oid":65,"name":"└\u2015航空党支部","pid":64},{"oid":66,"name":"质量监控与评估处","pid":0},{"oid":67,"name":"└\u2015质量监控与评估处","pid":66},{"oid":69,"name":"公共基础课部直属党支部","pid":0},{"oid":11,"name":"└\u2015公共基础课部直属党支部","pid":69},{"oid":70,"name":"体育部直属党支部","pid":0},{"oid":13,"name":"└\u2015体育部直属党支部","pid":70},{"oid":71,"name":"后勤直属党支部","pid":0},{"oid":14,"name":"└\u2015后勤直属党支部","pid":71},{"oid":72,"name":"图书馆直属党支部","pid":0},{"oid":15,"name":"└\u2015图书馆直属党支部","pid":72},{"oid":73,"name":"继续教育学院直属党支部","pid":0},{"oid":16,"name":"└\u2015继续教育学院直属党支部","pid":73}]
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
         * oid : 1
         * name : 机关党总支
         * pid : 0
         */

        private int oid;
        private String name;
        private int pid;

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
