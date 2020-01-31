package com.nj.baijiayun.refresh.recycleview;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.refresh.recycleview
 * @describe
 */
public class TreeItemExpandAttr {

    private ITreeModel iTreeModel;
    /**
     * 是否展开
     */
    private boolean isExpand;


    public TreeItemExpandAttr(ITreeModel iTreeModel) {
        this.iTreeModel = iTreeModel;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 展开
     */
    public void onExpand() {
        isExpand = true;
    }

    /**
     * 折叠
     */
    public void onCollapse() {
        isExpand = false;
    }

    /**
     * 递归遍历所有的子数据，包括子数据的子数据
     *
     * @return List<TreeAdapterItem>
     */
    public List<ITreeModel> getAllChilds() {
        ArrayList<ITreeModel> result = new ArrayList<>();
        for (int i = 0; i < iTreeModel.getChilds().size(); i++) {
            ITreeModel treeAdapterItem = iTreeModel.getChilds().get(i);
            result.add(treeAdapterItem);
            if (treeAdapterItem.getTreeItemAttr().isParent()) {
                List list = treeAdapterItem.getTreeItemAttr().getAllChilds();
                if (list != null && list.size() > 0) {
                    result.addAll(list);
                }
            }
        }
        return result;
    }

    public List<ITreeModel> getExpandChilds() {
        ArrayList<ITreeModel> result = new ArrayList<>();

        for (int i = 0; i < iTreeModel.getChilds().size(); i++) {
            ITreeModel iTreeModel = this.iTreeModel.getChilds().get(i);
            result.add(iTreeModel);
            if (iTreeModel.getTreeItemAttr().isParent() && iTreeModel.getTreeItemAttr().isExpand()) {
                List list = iTreeModel.getTreeItemAttr().getExpandChilds();
                if (list != null && list.size() > 0) {
                    result.addAll(list);
                }
            }
        }

        return result;
    }

    public boolean isParent() {
        return iTreeModel.getChilds() != null && iTreeModel.getChilds().size() > 0;
    }


}
