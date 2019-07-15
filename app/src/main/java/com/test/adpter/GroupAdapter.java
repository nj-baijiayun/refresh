package com.test.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.MultipleTypeHolderFactory;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.adpter
 * @describe
 */
public class GroupAdapter extends BaseMultipleTypeRvAdapter {
    public GroupAdapter(Context context) {
        super(context);
    }

    @Override
    public MultipleTypeHolderFactory createTypeFactory() {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }


}
