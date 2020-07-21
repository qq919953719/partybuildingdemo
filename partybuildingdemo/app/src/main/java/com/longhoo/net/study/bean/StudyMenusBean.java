package com.longhoo.net.study.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ck on 2017/12/12.
 */

public class StudyMenusBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"mid":3,"name":"两学一做"},{"mid":22,"name":"党章"},{"mid":21,"name":"三严三实"},{"mid":23,"name":"党史故事"},{"mid":31,"name":"学习要点"},{"mid":30,"name":"十九大学习"}]
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

    public static class DataBean implements Serializable{
        /**
         * mid : 3
         * name : 两学一做
         */

        private int mid;
        private String name;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
