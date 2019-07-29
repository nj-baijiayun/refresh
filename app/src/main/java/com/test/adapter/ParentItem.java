package com.test.adapter;

import com.nj.baijiayun.refresh.recycleview.AbstractTreeItemAttr;
import com.nj.baijiayun.refresh.recycleview.ITreeModel;

import java.util.List;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.adapter
 * @describe
 */
public class ParentItem implements ITreeModel {


    @Override
    public List<? extends ITreeModel> getChilds() {
        return null;
    }

    @Override
    public AbstractTreeItemAttr getTreeItemAttr() {
        return null;
    }
}
