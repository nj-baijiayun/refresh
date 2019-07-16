package com.test.adpter;

import android.view.ViewGroup;

import com.baijiayun.R;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.test.bean.DemoBean2;

/**
 * @author chengang
 * @date 2019-07-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test
 * @describe
 */
@AdapterCreate
public class Demo2Holder extends BaseMultipleTypeViewHolder<DemoBean2> {
    public Demo2Holder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_holder_2;
    }

    @Override
    public void bindData(DemoBean2 model, int position, BaseRecyclerAdapter adapter) {

    }

    @Override
    public boolean isNeedLongClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }
}
