package com.test.bean;

import com.nj.baijiayun.refresh.recycleview.IMultipleTypeModel;

/**
 * @author chengang
 * @date 2019-07-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test
 * @describe
 */
public class DemoBean implements IMultipleTypeModel {
    @Override
    public int itemType() {
        return 0;
    }
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
