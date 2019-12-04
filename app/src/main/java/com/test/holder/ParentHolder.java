package com.test.holder;

import android.view.ViewGroup;
import android.widget.Toast;

import com.baijiayun.R;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.test.adapter.ExpandHelper;
import com.test.bean.ParentBean;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
public class ParentHolder extends BaseMultipleTypeViewHolder<ParentBean> {
    public ParentHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> {
            Toast.makeText(v.getContext(), getClickPosition()+"---", Toast.LENGTH_SHORT).show();
            ExpandHelper.expandOrCollapseTree(getBaseMultipleTypeRvAdapter(),getClickPosition());
        });
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.item_parent;
    }

    @Override
    public void bindData(ParentBean model, int position, BaseRecyclerAdapter adapter) {


    }


}
