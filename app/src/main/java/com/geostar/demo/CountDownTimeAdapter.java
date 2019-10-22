package com.geostar.demo;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.NonNull;

/**
 * @author : linjian
 * @date 2019-10-14 16:10
 * @description :
 */
public class CountDownTimeAdapter extends BaseQuickAdapter<TimerData, BaseViewHolder> {


    public CountDownTimeAdapter() {
        super(R.layout.rv_list_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TimerData item) {
        helper.setText(R.id.tv_title, item.getTitle());
        TextView tvTimer = helper.getView(R.id.tv_timer);
        tvTimer.setText(TimeUtils.getTimerText(item.getDisplayTime()));
    }

}
