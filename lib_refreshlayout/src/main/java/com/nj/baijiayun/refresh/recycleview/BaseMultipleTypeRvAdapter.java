package com.nj.baijiayun.refresh.recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;


/**
 * @author chengang
 * @date 2017/7/13
 */

public abstract class BaseMultipleTypeRvAdapter<T> extends BaseRecyclerAdapter<T> {

    private ViewTypeFactory mFactory;

    public BaseMultipleTypeRvAdapter(Context context) {
        super(context);
        mFactory = createTypeFactory();
    }

    //占位的
    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    public abstract ViewTypeFactory createTypeFactory();


    @Override
    public BaseViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return factoryCreateViewHolder(parent, type);
    }

    private BaseMultipleTypeViewHolder factoryCreateViewHolder(ViewGroup parent, int viewType) {
        BaseMultipleTypeViewHolder viewHolder = mFactory.createViewHolder(parent, viewType);
        if (viewHolder == null) {
            throw new NullPointerException("viewHolder is Null,Please Check viewHolder bind viewType is " + viewType);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = mFactory.getViewType(mItems.get(position));
        if (viewType == 0) {
            throw new IllegalArgumentException("Please Check getViewType(Object object) Method, Model " + mItems.get(position).getClass() + " unbind type");
        }
        return viewType;
    }


    @Override
    protected void bindViewAndData(BaseViewHolder holder, T t, int position) {
        ((BaseMultipleTypeViewHolder) holder).setBaseMultipleTypeRvAdapter(this);
        ((BaseMultipleTypeViewHolder) holder).bindData(t, position, this);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
