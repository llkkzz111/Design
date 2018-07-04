package com.liuz.design.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * date: 2018/7/3 18:44
 * author liuzhao
 */
public class BannerBean implements Parcelable {
    /**
     * desc :
     * id : 6
     * imagePath : http://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png
     * isVisible : 1
     * order : 1
     * title : 我们新增了一个常用导航Tab~
     * type : 0
     * url : http://www.wanandroid.com/navi
     */

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.desc);
        dest.writeInt(this.id);
        dest.writeString(this.imagePath);
        dest.writeInt(this.isVisible);
        dest.writeInt(this.order);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeString(this.url);
    }

    public BannerBean() {
    }

    protected BannerBean(Parcel in) {
        this.desc = in.readString();
        this.id = in.readInt();
        this.imagePath = in.readString();
        this.isVisible = in.readInt();
        this.order = in.readInt();
        this.title = in.readString();
        this.type = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<BannerBean> CREATOR = new Parcelable.Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel source) {
            return new BannerBean(source);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };
}
