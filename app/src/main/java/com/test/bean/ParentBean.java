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
public class ParentBean implements ITreeModel {
    private String title;
    private boolean isExpand;
    private TreeItemExpandAttr abstractTreeItem=new TreeItemExpandAttr(this);


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<ChildBean> childBeans;

    public List<ChildBean> getChildBeans() {
        return childBeans;
    }

    public void setChildBeans(List<ChildBean> childBeans) {
        this.childBeans = childBeans;
    }


    @Override
    public List<? extends ITreeModel> getChilds() {
        return childBeans;
    }


    @Override
    public TreeItemExpandAttr getTreeItemAttr() {
        return abstractTreeItem;
    }
}
