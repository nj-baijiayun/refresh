package com.nj.baijiayun.refresh.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author chengang
 * @date 2019/4/23
 * @describe 快速实现多类型的holder
 */
public abstract class BaseMultipleTypeViewHolder<T> extends BaseViewHolder {

    private Context context;
    private int viewType;

    public BaseMultipleTypeViewHolder(ViewGroup parent) {
        //临时占位,再替换
        super(parent);
        this.context = parent.getContext();
        setItemView(LayoutInflater.from(parent.getContext()).inflate(bindLayout(), parent, false));
    }
    @Deprecated
    public BaseMultipleTypeRvAdapter getBaseMultipleTypeRvAdapter() {
        return (BaseMultipleTypeRvAdapter) getAdapter();
    }


    /**
     * 绑定holder布局
     *
     * @return int 布局id
     */
    public abstract int bindLayout();



    /**
     * 绑定数据
     *
     * @param model    m
     * @param position p
     * @param adapter  a
     */
    public abstract void bindData(T model, int position, BaseRecyclerAdapter adapter);


    public int getClickPosition() {
        BaseViewHolder baseViewHolder = (BaseViewHolder) this.convertView.getTag();
        return baseViewHolder.getAdapterPositionExcludeHeadViewCount();
    }

    public T getClickModel() {
        return (T) getAdapter().getItem(getClickPosition());
    }




    /**
     * 用来回调内部的view 点击，回到适配器设置点击的地方
     *
     * @param v v
     */
    public void itemInnerViewClickCallBack(View v) {
        if (getAdapter().onItemClickListener != null) {
            getAdapter().onItemClickListener.onItemClick(this, getClickPosition(), v, getClickModel());
        }

    }

    public void itemInnerViewLongClickCallBack(View v) {
        if (getAdapter().onItemLongClickListener != null) {
            getAdapter().onItemLongClickListener.onItemLongClick(this, getClickPosition(), v, getClickModel());
        }
    }


    public Context getContext() {
        return context;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
