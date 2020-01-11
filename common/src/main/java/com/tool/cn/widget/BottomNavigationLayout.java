package com.tool.cn.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.tool.cn.R;

/**
 *  2017/4/12  13:54.
 *
 *
 * @version 1.0.0
 * @class BottomNavigationLayout
 * @describe 底部导航栏
 */
public class BottomNavigationLayout extends LinearLayout implements View.OnClickListener {

    private SparseArray<Tab> mTabs;
    private OnTabItemClickListener mOnTabItemClickListener;
    private int mLastSelectedPosition;//记录上一次点击的位置
    private int mTabSelectedTextColor;
    private int mTabUnselectedTextColor;
    private int specialPosition = -1; //特殊的view项

    public BottomNavigationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initAttrs(attrs);
    }

    private void initAttrs(@Nullable AttributeSet attrs) {
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.BottomNavigationLayout);
        mTabSelectedTextColor = typedArray.getColor(R.styleable.BottomNavigationLayout_tab_selected_textColor, getResources().getColor(R.color.colorPrimary));
        mTabUnselectedTextColor = typedArray.getColor(R.styleable.BottomNavigationLayout_tab_unselected_textColor, getResources().getColor(R.color.text_gray_color));
        typedArray.recycle();
    }

    /**
     * 初始化Tab
     */
    private void initTabs() {
        mTabs = new SparseArray<>();
        Tab tab;
        for (int i = 0; i < getChildCount(); i++) {
            tab = new Tab();
            tab.vTabView = getChildAt(i);
            tab.vTabView.setOnClickListener(this);
            tab.vTabView.setTag(i);
            if (specialPosition != i) {
                tab.ivTabSelectedIcon = tab.vTabView.findViewById(R.id.iv_tab_selected_icon);
                tab.ivTabUnselectedIcon = tab.vTabView.findViewById(R.id.iv_tab_unselected_icon);
                tab.badgeView = tab.vTabView.findViewById(R.id.badgeView);
                tab.tvTabName = tab.vTabView.findViewById(R.id.tv_tab_name);
            }
            mTabs.put(i, tab);
        }
        //默认选择第一个
        mLastSelectedPosition = 0;
        refreshSelectedTab();
    }

    /**
     * 添加Tab
     *
     * @param position          Tab的位置
     * @param tabSelectedIcon   Tab的选中状态的icon
     * @param tabUnselectedIcon Tab的未选中状态的icon
     * @param tabName           Tab的名字
     */
    public final void addTab(int position, @DrawableRes int tabSelectedIcon, @DrawableRes int tabUnselectedIcon, String tabName) {
        if (null == mTabs) {
            initTabs();
        }
        Tab tab = mTabs.get(position);
        tab.ivTabSelectedIcon.setImageResource(tabSelectedIcon);
        tab.ivTabUnselectedIcon.setImageResource(tabUnselectedIcon);
        tab.tvTabName.setTextColor(mTabUnselectedTextColor);
        tab.tvTabName.setText(tabName);
    }

    @Override
    public void onClick(View v) {
        boolean isCanChange = true;//是否可以切换，true可以，false不可以。默认可以切换。
        int position = (int) v.getTag();
        if (mLastSelectedPosition == position && specialPosition != position) return;

        if (null != mOnTabItemClickListener) {
            isCanChange = mOnTabItemClickListener.onTabItemClick(position);
        }
        if (isCanChange) {
            mLastSelectedPosition = position;
            refreshSelectedTab();
        }
    }

    /**
     * 获取Tab名字
     *
     * @param position 要获取名字的Tab位置
     * @return Tab名字
     */
    public String getTabName(int position) {
        return mTabs.get(position).tvTabName.getText().toString().trim();
    }

    /**
     * 设置mark角标数量
     *
     * @param markPosition 要设置mark的Tab位置。
     * @param badgeCount   mark 数量。
     */
    public void setTabMarkCount(int markPosition, int badgeCount) {
        Tab tab = mTabs.get(markPosition);
        if (badgeCount > 0) { //数字
            tab.badgeView.refresh(badgeCount);
        } else if (badgeCount == 0) {  //小圆点
            tab.badgeView.setVisibility(VISIBLE);
            tab.badgeView.invalidate();
        } else {
            tab.badgeView.setVisibility(GONE);
            tab.badgeView.invalidate();
        }
    }

    public void setSelectedTab(int selectedTabIndex) {
        mLastSelectedPosition = selectedTabIndex;
        refreshSelectedTab();
        if (null != mOnTabItemClickListener) {
            mOnTabItemClickListener.onTabItemClick(selectedTabIndex);
        }
    }

    /**
     * 刷新选择的Tab
     *
     * @param selectedTabPosition 选中的Tab的角标
     */
    public void refreshSelectedTab(int selectedTabPosition) {
        mLastSelectedPosition = selectedTabPosition;
        refreshSelectedTab();
    }

    /**
     * 刷新选择的Tab
     */
    private void refreshSelectedTab() {
        for (int i = 0; i < mTabs.size(); i++) {
            if (specialPosition != i) {
                Tab tab = mTabs.get(i);
                if (mLastSelectedPosition == i) {
                    tab.ivTabSelectedIcon.setVisibility(View.VISIBLE);
                    tab.ivTabUnselectedIcon.setVisibility(View.GONE);
                    tab.tvTabName.setTextColor(mTabSelectedTextColor);
                } else {
                    tab.ivTabSelectedIcon.setVisibility(View.GONE);
                    tab.ivTabUnselectedIcon.setVisibility(View.VISIBLE);
                    tab.tvTabName.setTextColor(mTabUnselectedTextColor);
                }
            }
        }
    }

    private class Tab {
        private View vTabView;
        private ImageView ivTabSelectedIcon;
        private ImageView ivTabUnselectedIcon;
        private TextView tvTabName;
        private BadgeView badgeView;
    }

    /**
     * BottomNavigation子条目点击事件监听器。
     */
    public interface OnTabItemClickListener {
        /**
         * BottomNavigation子条目点击事件回调。
         *
         * @param clickPosition 点击的子条目的position
         * @return true切换成功，false切换失败(不允许切换)。
         */
        boolean onTabItemClick(int clickPosition);

    }

    /**
     * 设置BottomNavigation子条目点击事件监听器
     *
     * @param onTabItemClickListener BottomNavigation子条目点击事件监听器
     */
    public void setOnTabItemClickListener(OnTabItemClickListener onTabItemClickListener) {
        this.mOnTabItemClickListener = onTabItemClickListener;
    }

    public int getSpecialPosition() {
        return specialPosition;
    }

    public void setSpecialPosition(int specialPosition) {
        this.specialPosition = specialPosition;
    }
}
