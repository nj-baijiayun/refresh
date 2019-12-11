package com.test.holder;

import android.view.ViewGroup;

import com.baijiayun.R;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.test.bean.DemoBean2;

/**
 * @author chengang
 * @date 2019-07-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
@AdapterCreate
public class TestbindHolder extends BaseMultipleTypeViewHolder<DemoBean2> {
    public TestbindHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_holder_2;}

    @Override
    public void bindData(DemoBean2 model, int position, BaseRecyclerAdapter adapter) {

    }
}
