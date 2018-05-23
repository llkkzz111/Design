package com.liuz.design.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * date: 2018/5/23 11:25
 * author liuzhao
 */
public class Banners implements Parcelable {
    private int bannerRes;
    private int tipsRes;

    public Banners(int bannerRes, int tipsRes) {
        this.bannerRes = bannerRes;
        this.tipsRes = tipsRes;
    }

    public int getBannerRes() {
        return bannerRes;
    }

    public void setBannerRes(int bannerRes) {
        this.bannerRes = bannerRes;
    }

    public int getTipsRes() {
        return tipsRes;
    }

    public void setTipsRes(int tipsRes) {
        this.tipsRes = tipsRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bannerRes);
        dest.writeInt(this.tipsRes);
    }

    protected Banners(Parcel in) {
        this.bannerRes = in.readInt();
        this.tipsRes = in.readInt();
    }

    public static final Parcelable.Creator<Banners> CREATOR = new Parcelable.Creator<Banners>() {
        @Override
        public Banners createFromParcel(Parcel source) {
            return new Banners(source);
        }

        @Override
        public Banners[] newArray(int size) {
            return new Banners[size];
        }
    };
}
