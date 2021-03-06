package com.test.bean;

import com.nj.baijiayun.refresh.recycleview.AbstractTreeItemAttr;
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
public class AreaBean implements ITreeModel {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private AbstractTreeItemAttr abstractTreeItem = new AbstractTreeItemAttr(this);

    @Override
    public List<? extends ITreeModel> getChilds() {
        return null;
    }

    @Override
    public AbstractTreeItemAttr getTreeItemAttr() {
        return abstractTreeItem;
    }
}
