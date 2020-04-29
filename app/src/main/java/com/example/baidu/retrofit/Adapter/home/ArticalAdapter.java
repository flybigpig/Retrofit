package com.example.baidu.retrofit.Adapter.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Activity.wanAndroid.WebViewActivity;
import com.example.baidu.retrofit.Bean.home.DatasBean;
import com.example.baidu.retrofit.R;

import java.util.List;

/**
 * @author
 * @date 2020/4/3.
 * GitHub：
 * email：
 * description：
 */
public class ArticalAdapter extends BaseQuickAdapter<DatasBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private Context mContext;

    public ArticalAdapter(Context context, @Nullable List<DatasBean> data) {
        super(R.layout.item_artical_history, data);
        this.mContext = context;
        setOnItemClickListener(this);
        setOnItemChildClickListener(this);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        holder.addOnClickListener(R.id.ll_content, R.id.red_icon);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, DatasBean datasBean) {
        String title = replaceDquo(datasBean.getTitle());
        baseViewHolder.setText(R.id.title, title);
        baseViewHolder.setChecked(R.id.red_icon, false);

    }


    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    private String replaceDquo(String str) {
        String temp = str.replaceAll("&ldquo;", "\"");
        return temp.replaceAll("&rdquo;", "\"");
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.ll_content:
                DatasBean datasBean = (DatasBean) adapter.getData().get(position);
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", datasBean.getLink());
                mContext.startActivity(intent);
                break;
            case R.id.red_icon:
                CheckBox checkBox = (CheckBox) view;
                boolean flag = checkBox.isChecked();
                checkBox.setChecked(!flag);
                break;

        }
    }
}
