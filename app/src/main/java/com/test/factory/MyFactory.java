package com.test.factory;

import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeModelHolderFactroy;
import com.test.bean.MultipleTypeModel;
import com.test.holder.TestHolder1;

/**
 * @author houyi QQ:1007362137
 * @project android_lib_refresh
 * @class name：com.test.factory
 * @time 2019-07-17 18:10
 * @describe
 */
public class MyFactory extends BaseMultipleTypeModelHolderFactroy<MultipleTypeModel> {
    @Override
    public Class getHolderCls(MultipleTypeModel model) {
        if(model.getType()==1)
        {
            return TestHolder1.class;
        }
        return null;
    }
}
