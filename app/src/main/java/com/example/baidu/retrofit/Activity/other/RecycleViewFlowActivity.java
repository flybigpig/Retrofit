package com.example.baidu.retrofit.Activity.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Adapter.TestRecycleViewAdapter;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RecycleViewFlowActivity extends Rx2Activity {

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_flow);
    }

    @Override
    protected void init() {
        super.init();
        list = new ArrayList<>();
        forList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        TestRecycleViewAdapter adapter = new TestRecycleViewAdapter(this, list);
        recyclerView.setAdapter(adapter);

    }

    private void forList() {
        for (int i = 0; i < 20; i++) {
            list.add("asda");
        }
    }

    @Override
    protected void nationalizationData(Properties prop) {

    }
}
