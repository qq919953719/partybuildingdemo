package com.longhoo.net.widget.treelist;

import java.util.List;

public class MultiLevelBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":[{"oid":1,"name":"机关党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":2,"name":"机械工程学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":3,"name":"电气工程学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":4,"name":"航空工程学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":5,"name":"交通工程学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":6,"name":"计算机与软件学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":7,"name":"经济管理学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":8,"name":"商务贸易学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":9,"name":"艺术设计学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":10,"name":"国际教学学院党总支","uid":0,"uname":"","pid":0,"level":0},{"oid":11,"name":"公共基础课部直属党支部","uid":0,"uname":"","pid":69,"level":0},{"oid":12,"name":"马克思主义学院直属党支部","uid":0,"uname":"","pid":63,"level":0},{"oid":13,"name":"体育部直属党支部","uid":0,"uname":"","pid":70,"level":0},{"oid":14,"name":"后勤直属党支部","uid":0,"uname":"","pid":71,"level":0},{"oid":15,"name":"图书馆直属党支部","uid":0,"uname":"","pid":72,"level":0},{"oid":16,"name":"继续教育学院直属党支部","uid":0,"uname":"","pid":73,"level":0},{"oid":17,"name":"机关党政办党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":18,"name":"机关组宣党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":19,"name":"机关纪监党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":20,"name":"机关学工党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":21,"name":"机关保卫党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":22,"name":"机关团委党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":23,"name":"机关工会党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":24,"name":"机关人事党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":25,"name":"机关计财党支部","uid":0,"uname":"","pid":24,"level":0},{"oid":26,"name":"机关教务党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":27,"name":"机关国际处党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":28,"name":"机关质控党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":29,"name":"机关科技党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":30,"name":"机关招就党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":31,"name":"国际教育学院师生支部","uid":0,"uname":"","pid":1,"level":0},{"oid":32,"name":"机关资产党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":33,"name":"机械工程学院教工一支部","uid":0,"uname":"","pid":2,"level":0},{"oid":34,"name":"机械工程学院教工二支部","uid":0,"uname":"","pid":38,"level":0},{"oid":35,"name":"机械工程学院教工三支部","uid":0,"uname":"","pid":2,"level":0},{"oid":36,"name":"机械工程学院师生一支部","uid":0,"uname":"","pid":2,"level":0},{"oid":37,"name":"机械工程学院师生二支部","uid":0,"uname":"","pid":2,"level":0},{"oid":38,"name":"机械工程学院师生三支部","uid":0,"uname":"","pid":2,"level":0},{"oid":39,"name":"电气工程学院教工支部","uid":0,"uname":"","pid":3,"level":0},{"oid":40,"name":"电气工程学院师生一支部","uid":0,"uname":"","pid":3,"level":0},{"oid":41,"name":"电气工程学院师生二支部","uid":0,"uname":"","pid":3,"level":0},{"oid":42,"name":"电气工程学院师生三支部","uid":0,"uname":"","pid":3,"level":0},{"oid":43,"name":"航空工程学院师生一支部","uid":0,"uname":"","pid":4,"level":0},{"oid":44,"name":"航空工程学院师生二支部","uid":0,"uname":"","pid":4,"level":0},{"oid":45,"name":"航空工程学院师生三支部","uid":0,"uname":"","pid":4,"level":0},{"oid":46,"name":"交通工程学院师生一支部","uid":0,"uname":"","pid":5,"level":0},{"oid":47,"name":"交通工程学院师生二支部","uid":0,"uname":"","pid":5,"level":0},{"oid":48,"name":"计算机与软件学院师生一支部","uid":0,"uname":"","pid":6,"level":1},{"oid":49,"name":"计算机与软件学院师生二支部","uid":0,"uname":"","pid":6,"level":0},{"oid":50,"name":"计算机与软件学院师生三支部","uid":0,"uname":"","pid":6,"level":0},{"oid":51,"name":"计算机与软件学院教工支部","uid":0,"uname":"","pid":6,"level":0},{"oid":52,"name":"经济管理学院教工支部","uid":0,"uname":"","pid":7,"level":0},{"oid":53,"name":"经济管理学院师生一支部","uid":0,"uname":"","pid":7,"level":0},{"oid":54,"name":"经济管理学院师生二支部","uid":0,"uname":"","pid":7,"level":0},{"oid":55,"name":"商务贸易学院师生一支部","uid":0,"uname":"","pid":8,"level":0},{"oid":56,"name":"商务贸易学院师生二支部","uid":0,"uname":"","pid":8,"level":0},{"oid":57,"name":"商务贸易学院师生三支部","uid":0,"uname":"","pid":8,"level":0},{"oid":58,"name":"商务贸易学院师生四支部","uid":0,"uname":"","pid":8,"level":0},{"oid":59,"name":"艺术设计学院教工支部","uid":0,"uname":"","pid":9,"level":0},{"oid":60,"name":"艺术设计学院师生一支部","uid":0,"uname":"","pid":9,"level":0},{"oid":61,"name":"艺术设计学院师生二支部","uid":0,"uname":"","pid":9,"level":0},{"oid":62,"name":"国际教育学院教工支部","uid":0,"uname":"","pid":10,"level":0},{"oid":63,"name":"马克思主义学院直属党支部","uid":0,"uname":"","pid":0,"level":0},{"oid":64,"name":"航空党支部","uid":0,"uname":"","pid":0,"level":0},{"oid":65,"name":"航空党支部","uid":0,"uname":"","pid":64,"level":0},{"oid":66,"name":"质量监控与评估处","uid":0,"uname":"","pid":0,"level":0},{"oid":67,"name":"质量监控与评估处","uid":0,"uname":"","pid":66,"level":0},{"oid":68,"name":"机关纪检党支部","uid":0,"uname":"","pid":1,"level":0},{"oid":69,"name":"公共基础课部直属党支部","uid":0,"uname":"","pid":0,"level":0},{"oid":70,"name":"体育部直属党支部","uid":0,"uname":"","pid":0,"level":0},{"oid":71,"name":"后勤直属党支部","uid":0,"uname":"","pid":0,"level":0},{"oid":72,"name":"图书馆直属党支部","uid":0,"uname":"","pid":0,"level":0},{"oid":73,"name":"继续教育学院直属党支部","uid":0,"uname":"","pid":0,"level":0},{"oid":74,"name":"","uid":12,"uname":"张三","pid":48,"level":2},{"oid":75,"name":"","uid":13,"uname":"李四","pid":48,"level":2},{"oid":76,"name":"测试部门","uid":0,"uname":"","pid":48,"level":2},{"oid":122,"name":"222","uid":0,"uname":"","pid":68,"level":0},{"oid":123,"name":"","uid":12,"uname":"王五","pid":76,"level":3}]}
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
             * oid : 1
             * name : 机关党总支
             * uid : 0
             * uname :
             * pid : 0
             * level : 0
             */

            private int oid;
            private String name;
            private int uid;
            private String uname;
            private int pid;
            private int level;

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

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }
        }
    }
}
