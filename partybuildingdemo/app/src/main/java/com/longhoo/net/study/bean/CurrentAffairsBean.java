package com.longhoo.net.study.bean;

import java.util.List;

public class CurrentAffairsBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"id":9812,"title":"身边的榜样 | 从61岁到96岁 她在太行山种下20万棵树","add_time":"2020-01-14 15:17:39","thumbnail":""},{"id":9810,"title":"身边的榜样 | 大四男生为捐造血干细胞推迟毕业答辩","add_time":"2020-01-14 15:17:37","thumbnail":""},{"id":9811,"title":"身边的榜样 | 25岁小伙徒手爬墙破窗灭火救了一楼人","add_time":"2020-01-14 15:17:37","thumbnail":""},{"id":9808,"title":"《榜样4》专题节目\u2014\u2014唐真亚海报","add_time":"2020-01-14 15:17:36","thumbnail":""},{"id":9809,"title":"身边的榜样 | 96岁老人街头心跳骤停 83岁退休医生跪地急救","add_time":"2020-01-14 15:17:36","thumbnail":""},{"id":9807,"title":"《榜样4》专题节目\u2014\u2014黄文秀海报","add_time":"2020-01-14 15:17:35","thumbnail":""},{"id":9806,"title":"《榜样4》专题节目\u2014\u2014李连成海报","add_time":"2020-01-14 15:17:21","thumbnail":""},{"id":9805,"title":"《榜样4》专题节目\u2014\u2014张富清海报","add_time":"2020-01-14 15:17:20","thumbnail":""},{"id":9804,"title":"中共中央办公厅 国务院办公厅印发《脱贫攻坚责任制实施办法》","add_time":"2020-01-14 15:17:20","thumbnail":""},{"id":9803,"title":"中共中央办公厅 国务院办公厅印发《生态文明建设目标评价考核办法》","add_time":"2020-01-14 15:17:18","thumbnail":""}]
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
         * id : 9812
         * title : 身边的榜样 | 从61岁到96岁 她在太行山种下20万棵树
         * add_time : 2020-01-14 15:17:39
         * thumbnail :
         */

        private int id;
        private String title;
        private String add_time;
        private String thumbnail;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        private String url;
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
