package com.test.holder;

import android.view.ViewGroup;

import com.baijiayun.R;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.test.bean.DemoBean;

/**
 * @author chengang
 * @date 2019-07-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
public class TestNobindHolder extends BaseMultipleTypeViewHolder<DemoBean> {
    public TestNobindHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_holder_1;
    }

    @Override
    public void bindData(DemoBean model, int position, BaseRecyclerAdapter adapter) {

    }
}
