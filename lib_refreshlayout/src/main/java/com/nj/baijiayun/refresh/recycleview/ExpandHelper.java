package com.nj.baijiayun.refresh.recycleview;

import java.util.List;

/**
 * @author chengang
 * @date 2019-07-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test.adapter
 * @describe
 */
public class ExpandHelper {


    public static void initExpand(BaseRecyclerAdapter baseRecyclerAdapter) {
        int itemCount = baseRecyclerAdapter.getItemCount();
        for (int i = itemCount; i >= 0; i--) {
            if ((baseRecyclerAdapter.getItem(i) instanceof ITreeModel) &&
                    ((ITreeModel) baseRecyclerAdapter.getItem(i)).getTreeItemAttr().isExpand()) {
                TreeItemExpandAttr treeItemAttr = ((ITreeModel) baseRecyclerAdapter.getItem(i)).getTreeItemAttr();
                List<ITreeModel> lastExpandChilds = treeItemAttr.getExpandChilds();
                if (lastExpandChilds != null && lastExpandChilds.size() > 0) {
                    baseRecyclerAdapter.getItems().addAll(i + 1, lastExpandChilds);
                }
            }
        }
        baseRecyclerAdapter.notifyDataSetChanged();

    }


    public static void expandOrCollapseTree(BaseRecyclerAdapter baseRecyclerAdapter, int position) {
        if (position < 0) {
            return;
        }
        //获取当前点击的条目
        ITreeModel treeAdapterItem = (ITreeModel) baseRecyclerAdapter.getItems().get(position);
        TreeItemExpandAttr treeItemAttr = treeAdapterItem.getTreeItemAttr();
        //判断点击的条目有没有下一级
        if (treeItemAttr == null || !treeItemAttr.isParent()) {
            return;
        }
        //判断是否展开
        boolean expand = treeItemAttr.isExpand();
        if (expand) {
            //获取所有的子数据.
            List allChilds = treeItemAttr.getAllChilds();
            baseRecyclerAdapter.getItems().removeAll(allChilds);
            baseRecyclerAdapter.notifyItemRangeRemoved(position + 1, allChilds.size());
            baseRecyclerAdapter.notifyItemChanged(position + 1, allChilds.size());
            treeItemAttr.onCollapse();
            //告诉item,折叠
        } else {
            //获取下一级的数据
            List<ITreeModel> lastExpandChilds = treeAdapterItem.getTreeItemAttr().getExpandChilds();
            baseRecyclerAdapter.getItems().addAll(position + 1, lastExpandChilds);
            baseRecyclerAdapter.notifyItemRangeInserted(position + 1, lastExpandChilds.size());
            baseRecyclerAdapter.notifyItemChanged(position + 1, lastExpandChilds.size());
            treeItemAttr.onExpand();
        }

    }


}
