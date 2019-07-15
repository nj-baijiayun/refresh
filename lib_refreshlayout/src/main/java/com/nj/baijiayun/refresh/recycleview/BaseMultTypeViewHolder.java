package com.nj.baijiayun.refresh.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author chengang
 * @date 2019/4/23
 * @describe 实现多了类型的
 */
@Deprecated
public abstract class BaseMultTypeViewHolder<T> extends BaseViewHolder {

    public Context context;
    private BaseMultTypeRvAdapter baseMultTypeRvAdapter;
    private View parent;


    public BaseMultTypeViewHolder(ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        context = parent.getContext();
        this.parent = parent;
    }



    public BaseMultTypeViewHolder(ViewGroup parent, View itemView) {
        super(itemView);
        context = parent.getContext();
        this.parent = parent;
    }



    public void setBaseMutiTypeRvAdapter(BaseMultTypeRvAdapter baseMultTypeRvAdapter) {
        this.baseMultTypeRvAdapter = baseMultTypeRvAdapter;
    }


    public BaseMultTypeRvAdapter getBaseMultTypeRvAdapter() {
        return baseMultTypeRvAdapter;
    }

    /**
     * 绑定数据
     *
     * @param model    m
     * @param position p
     * @param adapter  a
     */
    public abstract void bindData(T model, int position, BaseRecyclerAdapter adapter);


    public View getLayoutView(int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, (ViewGroup) parent, false);
    }

    public int getClickPosition() {
        BaseViewHolder baseViewHolder = (BaseViewHolder) this.convertView.getTag();
        return baseViewHolder.getAdapterPosition();
    }

    public T getClickModel() {
        return (T) getBaseMultTypeRvAdapter().getItem(getClickPosition());
    }

    public void itemInnerViewClick(View v) {
        if (getBaseMultTypeRvAdapter().onItemClickListener != null) {
            getBaseMultTypeRvAdapter().onItemClickListener.onItemClick(this, getClickPosition(), v, getClickModel());
        }

    }

}
