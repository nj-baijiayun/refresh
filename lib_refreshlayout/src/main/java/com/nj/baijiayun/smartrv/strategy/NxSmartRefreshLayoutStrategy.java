package com.nj.baijiayun.smartrv.strategy;

import android.support.annotation.NonNull;
import android.view.View;

import com.nj.baijiayun.SmartRefreshLayout;
import com.nj.baijiayun.api.RefreshLayout;
import com.nj.baijiayun.listener.OnRefreshLoadMoreListener;
import com.nj.baijiayun.smartrv.NxOnRefreshListener;
import com.nj.baijiayun.smartrv.NxRefreshLayout;
import com.nj.baijiayun.smartrv.NxRefreshLayoutStrategy;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe
 */
public class NxSmartRefreshLayoutStrategy implements NxRefreshLayoutStrategy {


    private SmartRefreshLayout smartRefreshLayout;
    private NxRefreshLayout nxRefreshLayout;


    public NxSmartRefreshLayoutStrategy(NxRefreshLayout nxRefreshLayout, View view) throws Exception {
        this.nxRefreshLayout = nxRefreshLayout;
        if (view instanceof SmartRefreshLayout) {
            this.smartRefreshLayout = (SmartRefreshLayout) view;
        } else {
            throw new Exception("Please check your View is SmartRefreshLayout");

        }

    }

    @Override
    public NxRefreshLayout setEnableLoadMore(boolean enable) {
        this.smartRefreshLayout.setEnableLoadMore(enable);
        return this;
    }

    @Override
    public NxRefreshLayout setEnableRefresh(boolean enable) {
        this.smartRefreshLayout.setEnableRefresh(enable);
        return this;

    }


    @Override
    public NxRefreshLayout setOnRefreshLoadMoreListener(final NxOnRefreshListener nxOnRefreshListener) {
        this.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                nxOnRefreshListener.onLoadMore(nxRefreshLayout);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                nxOnRefreshListener.onRefresh(nxRefreshLayout);
            }
        });
        return this;

    }

    @Override
    public NxRefreshLayout finishRefresh() {
        this.smartRefreshLayout.finishRefresh();
        return this;

    }

    @Override
    public NxRefreshLayout finishLoadMore() {
        this.smartRefreshLayout.finishLoadMore();
        return this;
    }


}
