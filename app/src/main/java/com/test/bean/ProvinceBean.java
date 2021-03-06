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
public class ProvinceBean implements ITreeModel {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<CityBean>cityBeans;

    public void setCityBeans(List<CityBean> cityBeans) {
        this.cityBeans = cityBeans;
    }

    private AbstractTreeItemAttr abstractTreeItem = new AbstractTreeItemAttr(this);


    @Override
    public List<? extends ITreeModel> getChilds() {
        return cityBeans;
    }

    @Override
    public AbstractTreeItemAttr getTreeItemAttr() {
        return abstractTreeItem;
    }
}
