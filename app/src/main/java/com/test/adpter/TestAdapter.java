package com.test.adpter;

import android.content.Context;

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
public class TestAdapter extends BaseMultipleTypeRvAdapter {
    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public MultipleTypeHolderFactory createTypeFactory() {
        return null;
    }
}
