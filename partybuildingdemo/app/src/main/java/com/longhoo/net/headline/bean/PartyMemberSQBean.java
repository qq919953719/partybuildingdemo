package com.longhoo.net.headline.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CK on 2018/4/24.
 * Email:910663958@qq.com
 */

public class PartyMemberSQBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"sign":[{"signid":"18","signname":"党员生活"},{"signid":"19","signname":"三会一课"},{"signid":"20","signname":"组织生活会"}]}
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
        private List<SignBean> sign;

        public List<SignBean> getSign() {
            return sign;
        }

        public void setSign(List<SignBean> sign) {
            this.sign = sign;
        }

        public static class SignBean implements Serializable{
            /**
             * signid : 18
             * signname : 党员生活
             */

            private String signid;
            private String signname;

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
    }
}
