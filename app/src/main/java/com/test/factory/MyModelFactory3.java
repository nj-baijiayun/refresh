package com.test.factory;

import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeModelHolderFactory;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.test.bean.MultipleTypeModel;
import com.test.holder.TestHolder1;
import com.test.holder.TestHolder2;

/**
 * @author chengang
 * @date 2019-07-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.factory
 * @describe
 */
public class MyModelFactory3 extends BaseMultipleTypeModelHolderFactory<MultipleTypeModel> {

    @Override
    public Class<? extends BaseMultipleTypeViewHolder> getMultipleTypeHolderClass(MultipleTypeModel model) {
       if(model.getType()==1){
           return TestHolder1.class;
       }else {
           return TestHolder2.class;
       }
    }
}
