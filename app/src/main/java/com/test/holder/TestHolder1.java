package com.test.holder;

import android.view.ViewGroup;

import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.test.bean.MultipleTypeModel;

/**
 * @author chengang
 * @date 2019-07-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
public class TestHolder1 extends BaseMultipleTypeViewHolder<MultipleTypeModel> {
    public TestHolder1(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void bindData(MultipleTypeModel model, int position, BaseRecyclerAdapter adapter) {

    }
}
