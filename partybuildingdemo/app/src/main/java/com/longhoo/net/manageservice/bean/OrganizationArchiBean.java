package com.longhoo.net.manageservice.bean;

import com.longhoo.net.widget.OrganizationLevelView;

import java.util.List;

/**
 * Created by CK on 2018/4/27.
 * Email:910663958@qq.com
 */

public class OrganizationArchiBean {


    /**
     * code : 0
     * msg : 成功
     * data : [{"oid":1,"name":"机关党总支","pid":0,"type":0,"tag":""},{"oid":2,"name":"机械工程学院党总支","pid":0,"type":0,"tag":""},{"oid":3,"name":"电气工程学院党总支","pid":0,"type":0,"tag":""},{"oid":4,"name":"航空工程学院党总支","pid":0,"type":0,"tag":""},{"oid":5,"name":"交通工程学院党总支","pid":0,"type":0,"tag":""},{"oid":6,"name":"计算机与软件学院党总支","pid":0,"type":0,"tag":""},{"oid":7,"name":"经济管理学院党总支","pid":0,"type":0,"tag":""},{"oid":8,"name":"商务贸易学院党总支","pid":0,"type":0,"tag":""},{"oid":9,"name":"艺术设计学院党总支","pid":0,"type":0,"tag":""},{"oid":10,"name":"国际教学学院党总支","pid":0,"type":0,"tag":""},{"oid":63,"name":"马克思主义学院直属党支部","pid":0,"type":0,"tag":""},{"oid":64,"name":"航空党支部","pid":0,"type":0,"tag":""},{"oid":66,"name":"质量监控与评估处","pid":0,"type":0,"tag":""},{"oid":69,"name":"公共基础课部直属党支部","pid":0,"type":0,"tag":""},{"oid":70,"name":"体育部直属党支部","pid":0,"type":0,"tag":""},{"oid":71,"name":"后勤直属党支部","pid":0,"type":0,"tag":""},{"oid":72,"name":"图书馆直属党支部","pid":0,"type":0,"tag":""},{"oid":73,"name":"继续教育学院直属党支部","pid":0,"type":0,"tag":""}]
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

    public static class DataBean extends OrganizationLevelView.BaseDataBean{
        /**
         * oid : 1
         * name : 机关党总支
         * pid : 0
         * type : 0
         * tag :
         */

        private int oid;
        private int pid;
        private int type;
        private int person;
        private int hide;
        private int uid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getHide() {
            return hide;
        }

        public void setHide(int hide) {
            this.hide = hide;
        }

        public int getPerson() {
            return person;
        }

        public void setPerson(int person) {
            this.person = person;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }


        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

    }
}
