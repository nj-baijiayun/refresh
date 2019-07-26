package com.test.adpter;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.HolderCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.MultipleTypeHolder;
import com.nj.baijiayun.refresh.recycleview.MultipleTypeHolderFactory;

/**
 * @author chengang
 * @date 2019-07-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test
 * @describe
 */
@HolderCreate
public class DemoFactory implements MultipleTypeHolderFactory {

    @MultipleTypeHolder(holder = DemoHolder.class)
    @MultipleTypeHolder(holder = Demo2Holder.class)
    @Override
    public int getViewType(Object object) {
        return 0;
    }

    @Override
    public BaseMultipleTypeViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
