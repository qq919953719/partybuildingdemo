package com.longhoo.net.widget.banner;

/**
 * Created by hcc on 16/8/24 21:37
 * 100332338@qq.com
 * <p>
 * Banner模型类
 */
public class BannerEntity {
    public String id;
    public String title;
    public String img;
    public String type;


    public BannerEntity(String id, String title, String img, String type) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.type = type;
    }
}
