package com.test.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.adapter
 * @describe
 */
public abstract class TreeAdapterItem {


    /**
     * 是否展开
     */
    protected boolean isExpand;


    public TreeAdapterItem() {

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
    public List<TreeAdapterItem> getAllChilds() {

        ArrayList<TreeAdapterItem> treeAdapterItems = new ArrayList<>();

        for (int i = 0; i < getChilds().size(); i++) {

            TreeAdapterItem treeAdapterItem = getChilds().get(i);

            treeAdapterItems.add(treeAdapterItem);

            if (treeAdapterItem.isParent()) {

                List list = treeAdapterItem.getAllChilds();

                if (list != null && list.size() > 0) {

                    treeAdapterItems.addAll(list);
                }
            }
        }
        return treeAdapterItems;
    }

    public List<? extends TreeAdapterItem> getChilds() {
        return null;
    }

    /**
     * 是否持有子数据
     *
     * @return
     */
    public boolean isParent() {
        return getChilds() != null && getChilds().size() > 0;
    }


}
