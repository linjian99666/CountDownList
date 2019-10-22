package com.geostar.demo;

/**
 * @author : linjian
 * @date 2019-10-21 15:25
 * @description :
 */
public class TimeUtils {

    private static int DAY = 24 * 60 * 60;
    private static int HOUR = 60 * 60;
    private static int MIN = 60;
    private static int SECOND = 1;

    /**
     * 格式化时间
     *
     * @param finishTime
     * @return
     */
    public static String getTimerText(long finishTime) {
        if (finishTime <= 0) {
            return "倒计时结束";
        } else {
            int day = 0, hour = 0, minute = 0, second = 0;
            if (finishTime >= DAY) {
                day = (int) (finishTime / DAY);
                finishTime = finishTime - DAY * day;
            }
            if (finishTime >= HOUR) {
                hour = (int) (finishTime / HOUR);
                finishTime = finishTime - HOUR * hour;
            }
            if (finishTime >= MIN) {
                minute = (int) (finishTime / MIN);
                finishTime = finishTime - MIN * minute;
            }
            if (finishTime >= 0) {
                second = (int) finishTime / SECOND;
            }
            StringBuilder sb = new StringBuilder();
            if (day < 10) {
                sb.append("剩余").append("0").append(day).append("天");
            } else {
                sb.append("剩余").append(day).append("天");
            }
            if (hour < 10) {
                sb.append("0").append(hour).append("时");
            } else {
                sb.append(hour).append("时");
            }
            if (minute < 10) {
                sb.append("0").append(minute).append("分");
            } else {
                sb.append(minute).append("分");
            }
            if (second < 10) {
                sb.append("0").append(second).append("秒");
            } else {
                sb.append(second).append("秒");
            }
            return sb.toString();
        }
    }
}
