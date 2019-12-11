package com.test.holder;

import android.view.ViewGroup;

import com.baijiayun.R;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.test.bean.ChildBean;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
@AdapterCreate
public class ChildHolder extends BaseMultipleTypeViewHolder<ChildBean> {
    public ChildHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_child;
    }

    @Override
    public void bindData(ChildBean model, int position, BaseRecyclerAdapter adapter) {
    }
}
