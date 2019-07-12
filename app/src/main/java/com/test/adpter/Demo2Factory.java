package com.test.adpter;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.HolderCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.TypeHolder;
import com.nj.baijiayun.refresh.recycleview.ViewTypeFactory;
import com.test.bean.DemoBean;
import com.test.bean.DemoBean2;

/**
 * @author chengang
 * @date 2019-07-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test
 * @describe
 */
@HolderCreate
public class Demo2Factory implements ViewTypeFactory {

    @TypeHolder(holder = DemoHolder.class, model = DemoBean.class)
    @TypeHolder(holder = Demo2Holder.class, model = DemoBean2.class)
    @Override
    public int getViewType(Object object) {
        return DemoFactoryHolderHelper.getViewType(object);
    }

    @Override
    public BaseMultipleTypeViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return DemoFactoryHolderHelper.createViewHolder(parent, viewType);
    }
}