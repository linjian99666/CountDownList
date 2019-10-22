package com.geostar.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private CountDownTimeAdapter listAdapter;

    /**
     * 倒计时消息管理器
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            notifyVisiableItem();
        }
    };

    /**
     * 倒计时任务
     */
    private final Runnable mPlayTask = new Runnable() {

        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            mHandler.postDelayed(mPlayTask, 1000);
        }
    };
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("主页");

        mRecyclerView = findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        listAdapter = new CountDownTimeAdapter();
        mRecyclerView.setAdapter(listAdapter);

        listAdapter.setNewData(getTimerItemList());

        // 设置完数据就可以开始更新任务
        mHandler.removeCallbacks(mPlayTask);
        mHandler.postDelayed(mPlayTask, 1000);
    }

    /**
     * 获取时间条目列表
     *
     * @return
     */
    public static List<TimerData> getTimerItemList() {
        List<TimerData> lstTimerItems = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            int randomTime = new Random().nextInt(100) + 1;
            lstTimerItems.add(new TimerData("倒计时标题" + i, currentTimeMillis + randomTime, randomTime));
        }
        return lstTimerItems;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacks(mPlayTask);
            mHandler = null;
        }
    }

    /**
     * 更新可见条目
     */
    private void notifyVisiableItem() {
        if (mRecyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取第一个可见view的位置
            int firstItemPosition = linearManager.findFirstVisibleItemPosition();
            //获取最后一个可见view的位置
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            if (firstItemPosition < lastItemPosition) {
                for (int i = firstItemPosition; i <= lastItemPosition; i++) {
                    TimerData item = listAdapter.getItem(i);
                    long endTime = item.getEndTime();
                    long currentTimeMillis = System.currentTimeMillis() / 1000;
                    if (endTime >= currentTimeMillis) {
                        long displayTime = endTime - currentTimeMillis;
                        item.setDisplayTime(displayTime);
                    } else {
                        item.setDisplayTime(0);
                    }
                }
                listAdapter.notifyItemRangeChanged(firstItemPosition, lastItemPosition - firstItemPosition + 1);
            } else {
                TimerData item = listAdapter.getItem(firstItemPosition);
                long endTime = item.getEndTime();
                long currentTimeMillis = System.currentTimeMillis() / 1000;
                if (endTime >= currentTimeMillis) {
                    long displayTime = endTime - currentTimeMillis;
                    item.setDisplayTime(displayTime);
                }
                listAdapter.notifyItemChanged(firstItemPosition);
            }
        }
    }
}
