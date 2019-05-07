package com.nj.baijiayun.smartrv.strategy;

import android.support.annotation.NonNull;
import android.view.View;

import com.nj.baijiayun.SmartRefreshLayout;
import com.nj.baijiayun.api.RefreshLayout;
import com.nj.baijiayun.listener.OnRefreshLoadMoreListener;
import com.nj.baijiayun.smartrv.INxOnRefreshListener;
import com.nj.baijiayun.smartrv.INxRefreshLayout;
import com.nj.baijiayun.smartrv.INxRefreshLayoutStrategy;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe
 */
public class NxSmartRefreshLayoutStrategy implements INxRefreshLayoutStrategy {


    private SmartRefreshLayout smartRefreshLayout;
    private INxRefreshLayout nxRefreshLayout;


    public NxSmartRefreshLayoutStrategy(INxRefreshLayout nxRefreshLayout, View view) {
        this.nxRefreshLayout = nxRefreshLayout;
        this.smartRefreshLayout = (SmartRefreshLayout) view;

    }

    @Override
    public INxRefreshLayout setEnableLoadMore(boolean enable) {
        this.smartRefreshLayout.setEnableLoadMore(enable);
        return this;
    }

    @Override
    public INxRefreshLayout setEnableRefresh(boolean enable) {
        this.smartRefreshLayout.setEnableRefresh(enable);
        return this;

    }

    @Override
    public INxRefreshLayout setOnRefreshLoadMoreListener(final INxOnRefreshListener nxOnRefreshListener) {
        this.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //务必需要回调出去
                nxOnRefreshListener.onLoadMore(nxRefreshLayout);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //务必需要回调出去
                nxOnRefreshListener.onRefresh(nxRefreshLayout);
            }
        });
        return this;

    }

    @Override
    public INxRefreshLayout finishRefresh() {
        this.smartRefreshLayout.finishRefresh();
        return this;

    }

    @Override
    public INxRefreshLayout finishLoadMore() {
        this.smartRefreshLayout.finishLoadMore();
        return this;
    }


}
