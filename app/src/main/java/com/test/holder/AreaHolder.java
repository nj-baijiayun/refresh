package com.test.holder;

import android.view.ViewGroup;

import com.baijiayun.R;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.test.bean.AreaBean;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
@AdapterCreate
public class AreaHolder extends BaseMultipleTypeViewHolder<AreaBean> {
    public AreaHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_area;
    }

    @Override
    public void bindData(AreaBean model, int position, BaseRecyclerAdapter adapter) {
            setText(R.id.tv,model.getTitle());
    }
}

