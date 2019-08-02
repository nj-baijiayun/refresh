package com.test.adapter;

import android.view.ViewGroup;

import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.adapter
 * @describe
 */
public class ParentHolder extends BaseMultipleTypeViewHolder<Object> {
    public ParentHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void bindData(Object model, int position, BaseRecyclerAdapter adapter) {

    }


}
