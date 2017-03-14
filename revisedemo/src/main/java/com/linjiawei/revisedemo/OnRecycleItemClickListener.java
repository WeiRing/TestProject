package com.linjiawei.revisedemo;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by : 林嘉伟  Date：2017/3/14
 * RecycleView事件监听器
 */
public abstract class OnRecycleItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;//手势探测器
    private RecyclerView recyclerView;

    public OnRecycleItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**
     * 点击事件
     *
     * @param holder
     */
    public abstract void OnItemClick(RecyclerView.ViewHolder holder);

    /**
     * 长按事件
     *
     * @param holder
     */
    public void onLongItemClick(RecyclerView.ViewHolder holder) { }

    ;

    /**
     * 自定义手势探测器
     * SimpleOnGestureListener了两个手势监听器分别是：OnGestureListener，OnDoubleTapListener
     */
    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        //短按监听
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (itemView != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(itemView);
                //实现点击事件的动作
                OnItemClick(holder);
            }
            return true;
        }

        //长按监听
        @Override
        public void onLongPress(MotionEvent e) {
            View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (itemView != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(itemView);
                //实现长按事件的动作
                onLongItemClick(holder);
            }
        }
    }
}


