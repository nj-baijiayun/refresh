package com.nj.baijiayun.smartrv;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name www.baijiayun.module_common.widget.smartrv
 * @describe recycleview 的常用方法
 */
public interface IRecycleViewInterface {

    void setAdapter(RecyclerView.Adapter adaper);

    void setLayoutManager(RecyclerView.LayoutManager layout);

    void addItemDecoration(RecyclerView.ItemDecoration decor);

    void setItemAnimator(RecyclerView.ItemAnimator animator);

    void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup);


}
