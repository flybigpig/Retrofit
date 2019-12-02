package com.example.baidu.retrofit.Study;

import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * bravh 解读源码，分析其上拉加载原理
 * <p>
 * <p>
 * {@link BaseQuickAdapter}
 */
public class BravhCodeReadAdapter  {


    /**
     *
     ViewHolder通常出现在适配器里，为的是listview滚动的时候快速设置值，而不必每次都重新创建很多对象，从而提升性能。
     在android开发中Listview是一个很重要的组件，它以列表的形式根据数据的长自适应展示具体内容,用户可以自由的定义listview每一列的布局，
     但当listview有大量的数据需要加载的时候，会占据大量内存，影响性能，这时候就需要按需填充并重新使用view来减少对象的创建。



     *
     *
     getItemViewType : 根据position返回的一个int值，代表该position下的ViewHolder类型


     onCreateViewHolder ：创建对应ViewType的viewholder


     onBinderViewHolder ：绑定数据到对应的ViewHolder





    /**
     * 主要上拉加载原理分析
     */

    /**
     * This method is because it can lead to crash: always call this method while RecyclerView is computing a layout or scrolling.
     *
     * 滑动时会造成内存泄露
     *
     *
     */

    public void setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {

    }


    /**
     * 新方法
     */
    public interface RequestLoadMoreListener {

        void onLoadMoreRequested();

    }


    /**
     * 自动加载更多，？原理
     *

            private void autoLoadMore(int position) {
                if (getLoadMoreViewCount() == 0) {                     //  当前加载更多视图统计 ，等于0  不加载  返回1，往下走判断其他加载条件
                    return;
                }
                if (position < getItemCount() - mPreLoadNumber) {       //   总数-预加载数目  > 当前位置
                    return;
                }
                if (mLoadMoreView.getLoadMoreStatus() != LoadMoreView.STATUS_DEFAULT) {    // 支支持默认状态下的加载更多，
                    return;
                }
                mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_LOADING);
                if (!mLoading) {
                    mLoading = true;
                    if (getRecyclerView() != null) {
                        getRecyclerView().post(new Runnable() {                // 判断加载更多判断，view.post 加载更多
                            @Override
                            public void run() {
                                mRequestLoadMoreListener.onLoadMoreRequested();
                            }
                        });
                    } else {
                        mRequestLoadMoreListener.onLoadMoreRequested();
                    }
                }
            }


     //

     1. 查询当前加载更多视图，若监听不存在 或者未设置加载更多为true,

     2. mNextLoadEnable   // 是否往下加载  且 加载更多视图已经Gone

     3. 如果数据大小为0

     以上三种都不得加载，返回1

    public int getLoadMoreViewCount() {
        if (mRequestLoadMoreListener == null || !mLoadMoreEnable) {
            return 0;
        }
        if (!mNextLoadEnable && mLoadMoreView.isLoadEndMoreGone()) {
            return 0;
        }
        if (mData.size() == 0) {
            return 0;
        }
        return 1;
    }


     *
     */


    //  至此可以看到实际上交由反射+泛型去创建viewholder的实例


    /**
     * if you want to use subclass of BaseViewHolder in the adapter,
     * you must override the method to create new ViewHolder.
     *
     * @param view view
     * @return new ViewHolder

    @SuppressWarnings("unchecked")
    protected K createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        K k;
        // 泛型擦除会导致z为null
        if (z == null) {
            k = (K) new BaseViewHolder(view);
        } else {
            k = createGenericKInstance(z, view);
        }
        return k != null ? k : (K) new BaseViewHolder(view);
    }

     */

    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view

    @SuppressWarnings("unchecked")
    private K createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (K) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (K) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get generic parameter K
     *
     * @param z

    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }


     * @return
     */

}
