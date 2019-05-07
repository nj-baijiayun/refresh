package com.baijiayun;

import android.view.View;

import com.nj.baijiayun.smartrv.INxOnRefreshListener;
import com.nj.baijiayun.smartrv.INxRefreshLayout;
import com.nj.baijiayun.smartrv.INxRefreshLayoutStrategy;

/**
 * @author chengang
 * @date 2019/5/7
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.baijiayun
 * @describe
 */
public class MyStrategy implements INxRefreshLayoutStrategy {

    INxRefreshLayout iNxRefreshLayout;

    public MyStrategy(INxRefreshLayout iNxRefreshLayout,View view)
    {

    }

    @Override
    public INxRefreshLayout setEnableLoadMore(boolean enabled) {
        return null;
    }

    @Override
    public INxRefreshLayout setEnableRefresh(boolean enabled) {
        return null;
    }

    @Override
    public INxRefreshLayout setOnRefreshLoadMoreListener(INxOnRefreshListener nxOnRefreshListener) {
        return null;
    }

    @Override
    public INxRefreshLayout finishRefresh() {
        return null;
    }

    @Override
    public INxRefreshLayout finishLoadMore() {
        return null;
    }


}
