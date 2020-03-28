package com.example.baidu.retrofit.Activity;

import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.View.AutoVerticalScrollImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageSwitcherActivity extends Rx2Activity {


    @BindView(R.id.imageSwitcher1)
    AutoVerticalScrollImageView imageSwitcher;
    @BindView(R.id.viewGroup)
    LinearLayout viewGroup;

    private List<Integer> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);
        ButterKnife.bind(this);
        for (int i = 0; i < 10; i++) {
            imgs.add(R.drawable.ic_placeholer);
        }

        imageSwitcher.setList(imgs);
        imageSwitcher.startAutoScroll();
        imageSwitcher.setImageStillTime(200 + 100);
    }

    @Override
    protected void getHttp() {

    }

    @Override
    protected void nationalizationData(Properties prop) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageSwitcher = null;
    }
}
