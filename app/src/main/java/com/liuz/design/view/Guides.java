package com.liuz.design.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * date: 2018/5/23 11:25
 * author liuzhao
 */
public class Guides implements Parcelable {
    public static final Creator<Guides> CREATOR = new Creator<Guides>() {
        @Override
        public Guides createFromParcel(Parcel source) {
            return new Guides(source);
        }

        @Override
        public Guides[] newArray(int size) {
            return new Guides[size];
        }
    };
    private int bannerRes;
    private int tipsRes;
    private int tipBtnRes;
    private int tipDesRes;

    public Guides(int bannerRes, int tipsRes, int tipBtnRes, int tipDesRes) {
        this.bannerRes = bannerRes;
        this.tipsRes = tipsRes;
        this.tipBtnRes = tipBtnRes;
        this.tipDesRes = tipDesRes;
    }


    public Guides(int bannerRes, int tipBtnRes) {
        this.bannerRes = bannerRes;
        this.tipsRes = tipBtnRes;
        this.tipBtnRes = -1;
    }

    protected Guides(Parcel in) {
        this.bannerRes = in.readInt();
        this.tipsRes = in.readInt();
        this.tipBtnRes = in.readInt();
        this.tipDesRes = in.readInt();
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

    public int getTipBtnRes() {
        return tipBtnRes;
    }

    public void setTipBtnRes(int tipBtnRes) {
        this.tipBtnRes = tipBtnRes;
    }

    public int getTipDesRes() {
        return tipDesRes;
    }

    public void setTipDesRes(int tipDesRes) {
        this.tipDesRes = tipDesRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bannerRes);
        dest.writeInt(this.tipsRes);
        dest.writeInt(this.tipBtnRes);
        dest.writeInt(this.tipDesRes);
    }
}
