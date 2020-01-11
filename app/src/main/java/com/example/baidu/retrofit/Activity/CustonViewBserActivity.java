package com.example.baidu.retrofit.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.Study.MyView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustonViewBserActivity extends AppCompatActivity {

    @BindView(R.id.view_bser)
    MyView viewBser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custon_view_bser);
        ButterKnife.bind(this);
    }
}
