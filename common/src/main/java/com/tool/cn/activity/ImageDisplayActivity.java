package com.tool.cn.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tool.cn.activity.BaseActivity;
import com.tool.cn.utils.GlideImageManager;
import com.tool.cn.utils.IntentUtils;
import com.tool.cn.utils.StatusBarUtils;
import com.tool.cn.widget.image.TouchImageView;

import java.util.ArrayList;

/**
 *  2016/5/16 9:56
 *
 *
 * @version 1.0.0
 * @class ImageDisplayActivity
 * @describe 查看大图
 */
public class ImageDisplayActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    public static final String pickers = "pickers"; //图片数组
    public static final String picker = "picker"; //单张图片
    public static final String position = "position"; //初始化位置
    private ViewPager vp;
    private LinearLayout ll_point;
    private int mOriginalPosition;
    private int length;
    private ArrayList<ImageView> imageList = new ArrayList<>();
    private ArrayList<View> points = new ArrayList<>();
    private ArrayList<String> uriList;
    private String pickerPach;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setTransparent(this);
        setContentView(com.tool.cn.R.layout.activity_image_display);
    }

    @Override
    protected void getHttp() {

    }

    @Override
    protected void init() {
        StatusBarUtils.StatusBarDarkMode(this, 3);
        vp = (ViewPager) findViewById(com.tool.cn.R.id.vp_imageDisplayAty);
        ll_point = (LinearLayout) findViewById(com.tool.cn.R.id.ll_imageDisplayAty_points);
        bundle = getIntent().getExtras();
        uriList = bundle.getStringArrayList(pickers);
        pickerPach = bundle.getString(picker);
        bundle.clear();
        if (pickerPach != null) {
            uriList = new ArrayList<>();
            uriList.add(pickerPach);
        }
        if (uriList != null && uriList.size() > 0) {
            length = uriList.size();
        }
        mOriginalPosition = getIntent().getExtras().getInt(position, -1);
        initImageSource();// 加载图片
        initPoints();// 加载小圆点

        vp.setAdapter(new MyPagerAdapter());
        if (length == 1) {
            vp.setCurrentItem(0);
        } else {
            vp.setCurrentItem(mOriginalPosition);
        }
        vp.setOnPageChangeListener(this);
    }

    public void initImageSource() {
        if (length == 1) {
            if (uriList.get(0).startsWith("http://")) {
                setTouchImageViewWithDrawable(Uri.parse(uriList.get(0)).toString());
            } else {
                setTouchImageViewWithDrawable(uriList.get(0));
            }

            ll_point.setVisibility(View.GONE);
            vp.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    return true;
                }
            });
        } else {
            for (int i = 0; i < length; i++) {
                if (uriList.get(i).startsWith("http://")) {
                    setTouchImageViewWithDrawable(Uri.parse(uriList.get(i)).toString());
                } else {
//                    setTouchImageViewWithDrawable(Uri.parse(ServerURL.SERVER_CBL_API_URL + uriList.get(i)));
                }
            }
        }
    }

    public void initPoints() {
        for (int i = 0; i < length; i++) {
            View v = new View(this);
            v.setBackgroundResource(com.tool.cn.R.drawable.icon_zhishiqi_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.setMargins(5, 0, 5, 0);
            v.setLayoutParams(params);
            ll_point.addView(v);
            points.add(v);
        }
        if (mOriginalPosition > -1) {
            points.get(mOriginalPosition).setBackgroundResource(com.tool.cn.R.drawable.icon_zhishiqi_white_solid);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < length; i++) {
            points.get(i).setBackgroundResource(com.tool.cn.R.drawable.icon_zhishiqi_gray);
        }
        points.get(position).setBackgroundResource(com.tool.cn.R.drawable.icon_zhishiqi_white_solid);
        mOriginalPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * viewPager适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                container.addView(imageList.get(position), 0);
            } catch (Exception e) {
                //handler something
            }
            return imageList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageList.get(position));
        }
    }

    /**
     * 给TouchImageView添加Drawable图片
     *
     * @param pickerPath
     */
    private void setTouchImageViewWithDrawable(String pickerPath) {
        ImageView iv = new ImageView(this);

        RequestOptions options = new RequestOptions()
                .override(800)
                .circleCrop()
                .dontAnimate();

        //设置图片
        Glide.with(mContext).load(pickerPath)
                .apply(options)
                .into(iv);
        imageList.add(iv);
    }

}
