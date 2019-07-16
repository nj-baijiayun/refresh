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
    private BaseMultipleTypeRvAdapter baseMultipleTypeRvAdapter;


    public BaseMultipleTypeViewHolder(ViewGroup parent) {
        //临时占位,再替换
        super(parent);
        this.context = parent.getContext();
        setItemView(LayoutInflater.from(parent.getContext()).inflate(bindLayout(), parent, false));
    }


    /**
     * 绑定holder布局
     * @return int 布局id
     */
    public abstract int bindLayout();

    public void setBaseMultipleTypeRvAdapter(BaseMultipleTypeRvAdapter baseMultipleTypeRvAdapter) {
        this.baseMultipleTypeRvAdapter = baseMultipleTypeRvAdapter;
    }


    public BaseMultipleTypeRvAdapter getBaseMultipleTypeRvAdapter() {
        return baseMultipleTypeRvAdapter;
    }

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
        return baseViewHolder.getAdapterPosition();
    }

    public T getClickModel() {
        return (T) getBaseMultipleTypeRvAdapter().getItem(getClickPosition());
    }

    /**
     * 用来回调内部的view 点击，回到适配器设置点击的地方
     *
     * @param v v
     */
    public void itemInnerViewClickCallBack(View v) {
        if (getBaseMultipleTypeRvAdapter().onItemClickListener != null) {
            getBaseMultipleTypeRvAdapter().onItemClickListener.onItemClick(this, getClickPosition(), v, getClickModel());
        }

    }

    public void itemInnerViewLongClickCallBack(View v)
    {
        if (getBaseMultipleTypeRvAdapter().onItemLongClickListener != null) {
            getBaseMultipleTypeRvAdapter().onItemLongClickListener.onItemLongClick(this, getClickPosition(), v, getClickModel());
        }
    }


    public Context getContext() {
        return context;
    }
}
