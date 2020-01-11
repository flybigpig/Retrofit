package com.example.baidu.retrofit.Activity.jpush;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.R;
import com.tool.cn.utils.GlideImageManager;

import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

public class ChatUIActivity extends Rx2Activity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.menu)
    TextView menu;
    @BindView(R.id.drawlayout)
    DrawerLayout drawlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_ui);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        super.init();
        setUserInfo();

    }

    private void setUserInfo() {
        UserInfo userInfo = JMessageClient.getMyInfo();
        GlideImageManager.loadCircleImage(mContext, userInfo.getAvatar(), userImage);
        userName.setText(userInfo.getUserName());
    }

    @Override
    protected void getHttp() {

    }

    @Override
    protected void nationalizationData(Properties prop) {

    }
}
