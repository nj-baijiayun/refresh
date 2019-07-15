package com.nj.baijiayun.refresh.recycleview;

import android.content.Context;
import android.view.ViewGroup;


/**
 * @author chengang
 * @date 2017/7/13
 */

@Deprecated
public abstract class BaseMultTypeRvAdapter<T extends Object> extends BaseRecyclerAdapter<T> {

    private ViewTypeFactory mFactory;

    public BaseMultTypeRvAdapter(Context context) {
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

    private BaseMultTypeViewHolder factoryCreateViewHolder(ViewGroup parent, int viewType) {
        BaseMultTypeViewHolder viewHolder = mFactory.createViewHolder(parent, viewType);
        if (viewHolder == null) {
            throw new NullPointerException("viewHolder is Null,Please Check viewHolder bind viewType is " + viewType);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {


        int viewType = mFactory.getViewType(mItems.get(position));
        if (viewType == 0) {
            throw new IllegalArgumentException("Please check Model " + mItems.get(position) + " unbind type");
        }
        return viewType;
    }


    @Override
    protected void bindViewAndData(BaseViewHolder holder, T t, int position) {
        ((BaseMultTypeViewHolder) holder).setBaseMutiTypeRvAdapter(this);
        ((BaseMultTypeViewHolder) holder).bindData(t, position, this);

    }

}
