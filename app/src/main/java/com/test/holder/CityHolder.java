package com.test.holder;

import android.view.ViewGroup;

import com.baijiayun.R;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.ExpandHelper;
import com.test.bean.CityBean;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
@AdapterCreate
public class CityHolder extends BaseMultipleTypeViewHolder<CityBean> {
    public CityHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> ExpandHelper.expandOrCollapseTree(getBaseMultipleTypeRvAdapter(),getClickPosition()));
    }

    @Override
    public int bindLayout() {
        return R.layout.item_city;
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }


    @Override
    public void bindData(CityBean model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.tv, model.getTitle());

    }
}

