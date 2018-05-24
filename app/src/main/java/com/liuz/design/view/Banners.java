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
    public static final Creator<Banners> CREATOR = new Creator<Banners>() {
        @Override
        public Banners createFromParcel(Parcel source) {
            return new Banners(source);
        }

        @Override
        public Banners[] newArray(int size) {
            return new Banners[size];
        }
    };
    private int tipHintRes;

    public Banners(int bannerRes, int tipsRes) {
        this.bannerRes = bannerRes;
        this.tipsRes = tipsRes;
        this.tipHintRes = -1;
    }

    public Banners(int bannerRes, int tipsRes, int tipHintRes) {
        this.bannerRes = bannerRes;
        this.tipsRes = tipsRes;
        this.tipHintRes = tipHintRes;
    }

    protected Banners(Parcel in) {
        this.bannerRes = in.readInt();
        this.tipsRes = in.readInt();
        this.tipHintRes = in.readInt();
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

    public int getTipHintRes() {
        return tipHintRes;
    }

    public void setTipHintRes(int tipHintRes) {
        this.tipHintRes = tipHintRes;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bannerRes);
        dest.writeInt(this.tipsRes);
        dest.writeInt(this.tipHintRes);
    }
}
