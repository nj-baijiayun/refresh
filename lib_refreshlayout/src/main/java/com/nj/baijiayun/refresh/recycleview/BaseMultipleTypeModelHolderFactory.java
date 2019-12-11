package com.nj.baijiayun.refresh.recycleview;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-07-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.auto_create.demo
 * @describe
 */


public abstract class BaseMultipleTypeModelHolderFactory<T> {


    public abstract Class<? extends RecyclerView.ViewHolder> getMultipleTypeHolderClass(T model);


}
