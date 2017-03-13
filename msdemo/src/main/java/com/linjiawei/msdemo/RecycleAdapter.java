package com.linjiawei.msdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by : 林嘉伟  Date：2016/10/24
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyHolder>{

    private List<Student> date;
    public RecycleAdapter(List<Student> date) {
        this.date = date;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (holder != null) {
            holder.setUsername(date.get(position).toString());
        }
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        private TextView mUsernameView;

        public MyHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            mUsernameView = (TextView) itemView.findViewById(R.id.recycleItemText);
        }

        public void setUsername(String username) {
            if (null == mUsernameView) return;
            mUsernameView.setText(username);
        }
    }



}
