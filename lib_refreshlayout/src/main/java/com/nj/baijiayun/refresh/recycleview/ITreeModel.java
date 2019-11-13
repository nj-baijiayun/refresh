package com.nj.baijiayun.refresh.recycleview;

import java.util.List;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.refresh.recycleview
 * @describe
 */
public interface ITreeModel {


    List<? extends ITreeModel>getChilds();


    TreeItemExpandAttr getTreeItemAttr();
}
