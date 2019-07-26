package com.test.holder;

import android.view.ViewGroup;

import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-07-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
public class TestNobindHolder extends BaseMultipleTypeViewHolder<Integer> {
    public TestNobindHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void bindData(Integer model, int position, BaseRecyclerAdapter adapter) {

    }
}
