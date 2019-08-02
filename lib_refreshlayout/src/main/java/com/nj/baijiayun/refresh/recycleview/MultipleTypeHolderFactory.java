package com.nj.baijiayun.refresh.recycleview;

import android.view.ViewGroup;

/**
 * @author chengang
 * @date 2019/4/23
 * @describe 类型工厂
 */

public interface MultipleTypeHolderFactory {

    /**
     * 根据
     * @param parent RV的onCreateViewHolder回调的parent
     * @param viewType 类型id
     * @return  ViewHolder
     */
    BaseMultipleTypeViewHolder createViewHolder(ViewGroup parent, int viewType);

    /**
     * 根据model获得类型
     * @param object model
     * @return model的类型
     */
    int getViewType(Object object);
}
