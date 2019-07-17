package com.test.factory;

import com.nj.baijiayun.annotations.ModelMultipleHolderCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeModelFactroy;
import com.test.bean.MultipleTypeModel;
import com.test.holder.TestHolder1;
import com.test.holder.TestHolder2;
import com.test.holder.TestHolder3;

/**
 * @author chengang
 * @date 2019-07-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.auto_create.demo.model
 * @describe
 */

//解析出需要 插入代码的model,
//需要把这段代码插入到判断里面去  其实返回一个model holder对应的列表
@ModelMultipleHolderCreate(holder = {TestHolder1.class, TestHolder2.class, TestHolder3.class},group = {"default","demo"})
public class MyModelFactory extends BaseMultipleTypeModelFactroy<MultipleTypeModel> {


    @Override
    public int holderClsArrayIndex(MultipleTypeModel model) {
        if (model.getType() == 1) {
            return 1;
        }else if(model.getType()==2)
        {
            return 2;
        }
        return 0;
    }

}
