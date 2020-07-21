package com.longhoo.net.mine.bean;

import java.util.List;

public class MyStudyArchivesMenuBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : ["2019","2018"]
     */

    private int code;
    private String msg;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
