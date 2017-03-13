package com.linjiawei.msdemo;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyFragment extends Fragment implements View.OnClickListener {

    private Button button;
    private RecyclerView mRecycleView;
    private RecycleAdapter recycleAdapter;
    private List<Student> data;

    private DbManager dbManager;
    private int count;

    public static RecyFragment newInstance() {
        Bundle args = new Bundle();
        RecyFragment fragment = new RecyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recy, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            data.add(new Student(i,"姓名"+i,i%2==0?"男":"女",i));
        }

//        try {
//            dbManager = x.getDb(MyApp.getDaoConfig());
//            data.addAll(dbManager.selector(Student.class).findAll());
//        } catch (DbException e) {
//            e.printStackTrace();
//        }


        recycleAdapter = new RecycleAdapter(data);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        button = (Button) view.findViewById(R.id.addBtn);
        button.setOnClickListener(this);
        mRecycleView.addItemDecoration(new RecycleViewUniversalDivider(getActivity()));
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecycleView.setAdapter(recycleAdapter);
        mRecycleView.addOnItemTouchListener(new OnRecycleItemClickListener(mRecycleView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {
                Toast.makeText(getActivity(),data.get(holder.getAdapterPosition()).toString(),Toast.LENGTH_SHORT).show();
                /*try {
                    dbManager.deleteById(Student.class,data.get(holder.getAdapterPosition()).getId());
                    data.remove(holder.getAdapterPosition());
                    recycleAdapter.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }*/
            }
        });



    }

    @Override
    public void onClick(View v) {
        if (data.size() > 0) {
            count = data.get(data.size() - 1).getId() + 1;
        } else {
            count = 1;
        }
        try {
            dbManager.saveOrUpdate(new Student(count, "学生" + count, "男女", count));
            data.clear();
            data.addAll(dbManager.findAll(Student.class));
            recycleAdapter.notifyDataSetChanged();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
