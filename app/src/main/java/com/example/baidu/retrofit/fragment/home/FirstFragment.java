package com.example.baidu.retrofit.fragment.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Adapter.home.FirstAdapter;
import com.example.baidu.retrofit.Bean.home.GongZhongHao;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.dialog.WaitingDialog;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.RetrofitUtil;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.tool.cn.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author
 * @date 2020/1/3.
 * GitHub：
 * email：
 * description：
 */
public class FirstFragment extends BaseFragment {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private FirstAdapter mHomeAdapter;
    private String REPORT_FRAGMENT_TAG = "home";
    private WaitingDialog mWaitingDialog;
    private int PAGE = 1;
    private int mPageSize = 20;
    private List<String> ListGongZhongHao = new ArrayList<>();

    public FirstFragment getInstance(String type) {
        FirstFragment f = new FirstFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment);
    }


    @Override
    protected void initView() {
        mHomeAdapter = new FirstAdapter(null);

        FlexboxLayoutManager manager = new FlexboxLayoutManager(getActivity(), FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recycleView.setLayoutManager(manager);
//        recycleView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(mHomeAdapter);
        if (mContext == null) {
            mContext = getActivity();
        }
    }

    @Override
    public void getHttpData() {
        getAndroidArtical(mPageSize, PAGE);

    }

    public void getAndroidArtical(int mPageSize, int page) {

        if (mContext != null) {

            RetrofitUtil.getTestService().getGongZhongHao().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<GongZhongHao>>((Rx2Activity) mContext, false, "getAndroidHome") {

                        @Override
                        public void onSuccess(List<GongZhongHao> gongZhongHaos) {

                            mHomeAdapter.setNewData(gongZhongHaos);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                        }
                    });
        }
    }


    private void test() {

//        android.app.FragmentManager manager = getActivity().getFragmentManager();
//        if (manager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {
//            manager.beginTransaction().add(new MainFragment(), REPORT_FRAGMENT_TAG).commit();
//            // Hopefully, we are the first to make a transaction.
//            manager.executePendingTransactions();
//        }

//        RxPermissionsFragment rxPermissionsFragment = findRxPermissionsFragment(fragmentManager);
//        boolean isNewInstance = rxPermissionsFragment == null;
//        if (isNewInstance) {
//            rxPermissionsFragment = new RxPermissionsFragment();
//            fragmentManager
//                    .beginTransaction()
//                    .add(rxPermissionsFragment, TAG)
//                    .commitNow();
//        }
//        return rxPermissionsFragment;
    }


}
