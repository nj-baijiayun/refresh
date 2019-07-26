package com.test.holder;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.ModelTypeHolderCreate;
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
@ModelTypeHolderCreate
public class TestHolder2 extends BaseMultipleTypeViewHolder<MultipleTypeModel> {
    public TestHolder2(ViewGroup parent) {
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
