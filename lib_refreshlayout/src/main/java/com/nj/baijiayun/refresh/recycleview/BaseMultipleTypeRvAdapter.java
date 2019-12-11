package com.nj.baijiayun.refresh.recycleview;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;


/**
 * @author chengang
 * @date 2017/7/13
 */

public abstract class BaseMultipleTypeRvAdapter<T> extends BaseRecyclerAdapter<T> {

    private MultipleTypeHolderFactory mFactory;

    public BaseMultipleTypeRvAdapter(Context context) {
        super(context);
        mFactory = createTypeFactory();
    }

    //占位的
    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    public abstract MultipleTypeHolderFactory createTypeFactory();


    @Override
    public BaseViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return factoryCreateViewHolder(parent, type);
    }

    private BaseMultipleTypeViewHolder factoryCreateViewHolder(ViewGroup parent, int viewType) {
        BaseMultipleTypeViewHolder viewHolder = (BaseMultipleTypeViewHolder) mFactory.createViewHolder(parent, viewType);
        if (viewHolder == null) {
            throw new NullPointerException("viewHolder is Null,Please Check viewHolder bind viewType is " + viewType);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return mFactory.getViewType(mItems.get(position));
    }


    @Override
    protected void bindViewAndData(BaseViewHolder holder, T t, int position) {
        ((BaseMultipleTypeViewHolder) holder).bindData(t, position, this);
    }

    @Override
    public void bindViewAndData(BaseViewHolder holder, T t, int position, List<Object> payloads) {
        ((BaseMultipleTypeViewHolder) holder).bindData(t, position, this, payloads);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
