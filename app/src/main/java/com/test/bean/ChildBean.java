package com.test.bean;

import com.nj.baijiayun.refresh.recycleview.TreeItemExpandAttr;
import com.nj.baijiayun.refresh.recycleview.ITreeModel;

import java.util.List;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.bean
 * @describe
 */
public class ChildBean implements ITreeModel {
    private String title;


    @Override
    public List<? extends ITreeModel> getChilds() {
        return null;
    }


    private TreeItemExpandAttr abstractTreeItem=new TreeItemExpandAttr(this);
    @Override
    public TreeItemExpandAttr getTreeItemAttr() {
        return abstractTreeItem;
    }
}
