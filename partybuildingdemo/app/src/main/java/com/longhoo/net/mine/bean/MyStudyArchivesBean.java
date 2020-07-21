package com.longhoo.net.mine.bean;

import java.util.List;

public class MyStudyArchivesBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"data":{"count":20100,"courseCount":100,"classCount":20000,"list":[{"id":96,"addtime":"2019-01-24 15:33:46","host_unit":"123","content":"123","title":"鑫鑫","train_starttime":"2019-01-24 15:33","train_endtime":"2019-01-25 15:33","type":"自主上报","class_hour":"100"},{"id":94,"addtime":"2019-01-24 15:14:38","host_unit":"","content":"123123123","title":"鑫鑫测试","train_starttime":"2019-01-24 15:13","train_endtime":"2019-01-26 00:00","type":"培训班报名","class_hour":"10000"},{"id":93,"addtime":"2019-01-24 15:14:38","host_unit":"","content":"123123123","title":"鑫鑫测试","train_starttime":"2019-01-24 15:13","train_endtime":"2019-01-26 00:00","type":"培训班报名","class_hour":"10000"}]}}
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
        /**
         * data : {"count":20100,"courseCount":100,"classCount":20000,"list":[{"id":96,"addtime":"2019-01-24 15:33:46","host_unit":"123","content":"123","title":"鑫鑫","train_starttime":"2019-01-24 15:33","train_endtime":"2019-01-25 15:33","type":"自主上报","class_hour":"100"},{"id":94,"addtime":"2019-01-24 15:14:38","host_unit":"","content":"123123123","title":"鑫鑫测试","train_starttime":"2019-01-24 15:13","train_endtime":"2019-01-26 00:00","type":"培训班报名","class_hour":"10000"},{"id":93,"addtime":"2019-01-24 15:14:38","host_unit":"","content":"123123123","title":"鑫鑫测试","train_starttime":"2019-01-24 15:13","train_endtime":"2019-01-26 00:00","type":"培训班报名","class_hour":"10000"}]}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * count : 20100
             * courseCount : 100
             * classCount : 20000
             * list : [{"id":96,"addtime":"2019-01-24 15:33:46","host_unit":"123","content":"123","title":"鑫鑫","train_starttime":"2019-01-24 15:33","train_endtime":"2019-01-25 15:33","type":"自主上报","class_hour":"100"},{"id":94,"addtime":"2019-01-24 15:14:38","host_unit":"","content":"123123123","title":"鑫鑫测试","train_starttime":"2019-01-24 15:13","train_endtime":"2019-01-26 00:00","type":"培训班报名","class_hour":"10000"},{"id":93,"addtime":"2019-01-24 15:14:38","host_unit":"","content":"123123123","title":"鑫鑫测试","train_starttime":"2019-01-24 15:13","train_endtime":"2019-01-26 00:00","type":"培训班报名","class_hour":"10000"}]
             */

            private int count;
            private int courseCount;
            private int classCount;
            private List<ListBean> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getCourseCount() {
                return courseCount;
            }

            public void setCourseCount(int courseCount) {
                this.courseCount = courseCount;
            }

            public int getClassCount() {
                return classCount;
            }

            public void setClassCount(int classCount) {
                this.classCount = classCount;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : 96
                 * addtime : 2019-01-24 15:33:46
                 * host_unit : 123
                 * content : 123
                 * title : 鑫鑫
                 * train_starttime : 2019-01-24 15:33
                 * train_endtime : 2019-01-25 15:33
                 * type : 自主上报
                 * class_hour : 100
                 */

                private int id;
                private int tid;
                private String addtime;
                private String host_unit;
                private String content;
                private String title;
                private String train_starttime;
                private String train_endtime;
                private String type;
                private String class_hour;
                private String status;


                public int getTid() {
                    return tid;
                }

                public void setTid(int tid) {
                    this.tid = tid;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getAddtime() {
                    return addtime;
                }

                public void setAddtime(String addtime) {
                    this.addtime = addtime;
                }

                public String getHost_unit() {
                    return host_unit;
                }

                public void setHost_unit(String host_unit) {
                    this.host_unit = host_unit;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getTrain_starttime() {
                    return train_starttime;
                }

                public void setTrain_starttime(String train_starttime) {
                    this.train_starttime = train_starttime;
                }

                public String getTrain_endtime() {
                    return train_endtime;
                }

                public void setTrain_endtime(String train_endtime) {
                    this.train_endtime = train_endtime;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getClass_hour() {
                    return class_hour;
                }

                public void setClass_hour(String class_hour) {
                    this.class_hour = class_hour;
                }
            }
        }
    }
}
