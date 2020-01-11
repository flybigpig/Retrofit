package com.tool.cn.widget;

import android.widget.AbsListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 *  2017/6/2  11:34.
 *
 *
 * @version 1.0.0
 * @class LoadMore
 * @describe 上拉加载框架
 */
public class LoadMore extends RecyclerView.OnScrollListener implements AbsListView.OnScrollListener {

    private OnLoadMoreListener loadMoreListener;
    private boolean isLastPage;
    private boolean isGrid;

    public LoadMore(RecyclerView recyclerView) {
        //noinspection deprecation
        recyclerView.setOnScrollListener(this);
    }

    /**
     * 设置LayoutManager为StaggeredGridLayoutManager、GridLayoutManager等模式时使用该构造方法
     *
     * @param recyclerView
     * @param isGrid       是网格是为true
     */
    public LoadMore(RecyclerView recyclerView, boolean isGrid) {
        this.isGrid = isGrid;
        recyclerView.setOnScrollListener(this);
    }

    public LoadMore(AbsListView absListView) {
        absListView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (isLastPage) {
            return;
        }
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            int lastVisiblePosition;
            if (isGrid) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisible = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
                lastVisiblePosition = lastVisible[lastVisible.length - 1];
            } else {
                lastVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
            if (lastVisiblePosition == recyclerView.getAdapter().getItemCount() - 1) {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isLastPage) {
            return;
        }
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            int lastVisiblePosition = view.getLastVisiblePosition();
            if (lastVisiblePosition == view.getCount() - 1) {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
}
