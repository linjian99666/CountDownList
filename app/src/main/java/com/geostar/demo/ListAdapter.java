package com.geostar.demo;

import android.os.CountDownTimer;
import android.util.SparseArray;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.NonNull;

/**
 * @author : linjian
 * @date 2019-10-14 16:10
 * @description :
 */
public class ListAdapter extends BaseQuickAdapter<TimerData, BaseViewHolder> {

    private static int DAY = 24 * 60 * 60 * 1000;
    private static int HOUR = 60 * 60 * 1000;
    private static int MIN = 60 * 1000;
    private static int SECOND = 1000;
    /**
     * 用于退出activity,避免countdown，造成资源浪费。
     */
    private SparseArray<CountDownTimer> mCountDownTimerMap;

    public ListAdapter() {
        super(R.layout.rv_list_item);
        mCountDownTimerMap = new SparseArray<>();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TimerData item) {
        helper.setText(R.id.tv_title, item.getTitle());
        long time = item.getEndTime();
        final TextView tvTimer = helper.getView(R.id.tv_timer);
        CountDownTimer downTimer = mCountDownTimerMap.get(tvTimer.hashCode());
        if (downTimer != null) {
            downTimer.cancel();
        }
        time = time - System.currentTimeMillis();
        if (time > 0) {
            CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(getTimerText(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    tvTimer.setText("剩余0天0时0分0秒");
                }
            };
            mCountDownTimerMap.put(tvTimer.hashCode(), countDownTimer);
            countDownTimer.start();
        } else {
            tvTimer.setText("剩余0天0时0分0秒");
        }
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (mCountDownTimerMap == null) {
            return;
        }
        for (int i = 0, length = mCountDownTimerMap.size(); i < length; i++) {
            CountDownTimer cdt = mCountDownTimerMap.get(mCountDownTimerMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    /**
     * 格式化时间
     *
     * @param finishTime
     * @return
     */
    private String getTimerText(long finishTime) {
        if (finishTime <= 0) {
            return "剩余0天0时0分0秒";
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
