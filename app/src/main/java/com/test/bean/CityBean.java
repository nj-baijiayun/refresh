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
public class CityBean implements ITreeModel {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<AreaBean>areaBeans;

    public void setAreaBeans(List<AreaBean> areaBeans) {
        this.areaBeans = areaBeans;
    }

    private TreeItemExpandAttr abstractTreeItem= new TreeItemExpandAttr(this);


    @Override
    public List<? extends ITreeModel> getChilds() {
        return areaBeans;
    }

    @Override
    public TreeItemExpandAttr getTreeItemAttr() {
        return abstractTreeItem;
    }
}
