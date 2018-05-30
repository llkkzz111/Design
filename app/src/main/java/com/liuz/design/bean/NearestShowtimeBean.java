package com.liuz.design.bean;

/**
 * date: 2018/5/30 18:29
 * author liuzhao
 */
public class NearestShowtimeBean {
    /**
     * isTicket : true
     * nearestCinemaCount : 145
     * nearestShowDay : 1527667200
     * nearestShowtimeCount : 774
     */

    private boolean isTicket;
    private int nearestCinemaCount;
    private int nearestShowDay;
    private int nearestShowtimeCount;

    public boolean isIsTicket() {
        return isTicket;
    }

    public void setIsTicket(boolean isTicket) {
        this.isTicket = isTicket;
    }

    public int getNearestCinemaCount() {
        return nearestCinemaCount;
    }

    public void setNearestCinemaCount(int nearestCinemaCount) {
        this.nearestCinemaCount = nearestCinemaCount;
    }

    public int getNearestShowDay() {
        return nearestShowDay;
    }

    public void setNearestShowDay(int nearestShowDay) {
        this.nearestShowDay = nearestShowDay;
    }

    public int getNearestShowtimeCount() {
        return nearestShowtimeCount;
    }

    public void setNearestShowtimeCount(int nearestShowtimeCount) {
        this.nearestShowtimeCount = nearestShowtimeCount;
    }
}