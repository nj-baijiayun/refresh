package com.test.adpter;

import android.content.Context;

import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.ViewTypeFactory;

/**
 * @author chengang
 * @date 2019-07-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test
 * @describe
 */
public class DemoAdapter extends BaseMultipleTypeRvAdapter<Object> {

    public DemoAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewTypeFactory createTypeFactory() {
        return new DemoFactory();
    }
}
