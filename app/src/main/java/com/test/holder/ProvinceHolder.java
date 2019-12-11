package com.test.holder;

import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.view.ViewGroup;

import com.baijiayun.R;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.ExpandHelper;
import com.test.bean.ProvinceBean;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.holder
 * @describe
 */
@AdapterCreate
public class ProvinceHolder extends BaseMultipleTypeViewHolder<ProvinceBean> {
    public ProvinceHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> ExpandHelper.expandOrCollapseTree(getBaseMultipleTypeRvAdapter(), getClickPosition()));
    }

    @Override
    public int bindLayout() {
        return R.layout.item_province;
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public void bindData(ProvinceBean model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.tv, model.getTitle());

        getConvertView().setBackgroundColor(model.isSelect()? Color.RED: ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));


    }
}
