package com.example.baidu.retrofit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidu.retrofit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/1/17.
 * GitHub：
 * email：
 * description：
 */
public class TestRecycleViewAdapter extends RecyclerView.Adapter<TestRecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mList;
    private List<Integer> heights;

    public TestRecycleViewAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
        randomHeight();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycleview_test, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // holder 存在复用问题 ，需要防止混乱

        ViewGroup.LayoutParams lp = holder.mTextView.getLayoutParams();

        lp.height = heights.get(position);

        holder.mTextView.setLayoutParams(lp);

        holder.mTextView.setText(mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textview);
        }
    }

    private List randomHeight() {
        heights = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            heights.add((int) ((int) 100 + Math.random() * 300));
        }
        return heights;
    }


}
