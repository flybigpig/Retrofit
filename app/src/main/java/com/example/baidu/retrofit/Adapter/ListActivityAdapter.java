package com.example.baidu.retrofit.Adapter;

import android.view.SurfaceView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Activity.CustonViewBserActivity;
import com.example.baidu.retrofit.Activity.ImageListActivity;
import com.example.baidu.retrofit.Activity.ImageSwitcherActivity;
import com.example.baidu.retrofit.Activity.RecycleViewFlowActivity;
import com.example.baidu.retrofit.Activity.SurfaceViewActivity;
import com.example.baidu.retrofit.Activity.jpush.JpushChatActivity;
import com.example.baidu.retrofit.Activity.LoadMoreActivity;
import com.example.baidu.retrofit.Activity.wanAndroid.HomePageActivity;
import com.example.baidu.retrofit.Bean.StudyBean;
import com.example.baidu.retrofit.R;
import com.tool.cn.utils.IntentUtils;

import java.util.List;

public class ListActivityAdapter extends BaseQuickAdapter<StudyBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


    public ListActivityAdapter(List<StudyBean> data) {
        super(R.layout.item_main, data);
        setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StudyBean studyBean = (StudyBean) adapter.getData().get(position);
        switch (position) {
            case 0:  // recycleview 加载更多
                IntentUtils.openActivity(mContext, LoadMoreActivity.class);
                break;
            case 1:
                IntentUtils.openActivity(mContext, ImageListActivity.class);
                break;
            case 2:
                IntentUtils.openActivity(mContext, CustonViewBserActivity.class);
                break;
            case 3:
                IntentUtils.openActivity(mContext, ImageSwitcherActivity.class);
                break;
            case 4:
                IntentUtils.openActivity(mContext, HomePageActivity.class);
                break;
            case 5:
//                IntentUtils.openActivity(mContext, ShareCommonActivity.class);
                break;
            case 6:
                IntentUtils.openActivity(mContext, JpushChatActivity.class);
                break;
            case 7:
                IntentUtils.openActivity(mContext, SurfaceViewActivity.class);
                break;
            case 8:
                IntentUtils.openActivity(mContext, RecycleViewFlowActivity.class);
                break;
        }

    }

    @Override
    protected void convert(BaseViewHolder helper, StudyBean item) {
        helper.setText(R.id.tv_item, item.getTitle());
    }
}
