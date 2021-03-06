package com.test.factory;

import com.nj.baijiayun.annotations.ModelMultiTypeAdapterCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeModelHolderFactory;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.test.bean.MultipleTypeModel2;
import com.test.holder.TestHolder4;

import static com.nj.baijiayun.annotations.Consts.DEFAULT_GROUP;

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
@ModelMultiTypeAdapterCreate(group = {"test",DEFAULT_GROUP})
public class MyModelFactory2 extends BaseMultipleTypeModelHolderFactory<MultipleTypeModel2> {

    @Override
    public Class<? extends BaseMultipleTypeViewHolder> getMultipleTypeHolderClass(MultipleTypeModel2 model) {
        return TestHolder4.class;
    }
}
