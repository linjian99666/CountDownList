package com.geostar.demo;

/**
 * @author : linjian
 * @date 2019-10-14 16:46
 * @description :
 */
public class TimerData {
    private String title;
    private long endTime;
    private long displayTime;

    public TimerData(String title, long endTime, long displayTime) {
        this.title = title;
        this.endTime = endTime;
        this.displayTime = displayTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(long displayTime) {
        this.displayTime = displayTime;
    }
}
