package com.longhoo.net.manageservice.bean;

/**
 * Created by CK on 2017/7/25.
 */

public class SignBan {
    private String signid;
    private String signname;

    public SignBan(String signid, String signname) {
        this.signid = signid;
        this.signname = signname;
    }

    public String getSignid() {
        return signid;
    }

    public void setSignid(String signid) {
        this.signid = signid;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }
}
