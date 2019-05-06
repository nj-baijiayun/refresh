package com.nj.baijiayun.smartrv;

import android.view.View;

import com.nj.baijiayun.smartrv.strategy.NxSmartRefreshLayoutStrategy;


/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe
 */
public class RefreshViewProxy implements NxRefreshLayoutStrategy {

    //代理对象
    private NxRefreshLayoutStrategy proxyStrategy;

    private View view;

    public View getProxyRefreshView() {
        return view;
    }

    public RefreshViewProxy(NxRefreshLayout nxRefreshLayout, View view) throws Exception {
        if (proxyStrategy == null) {
            this.view=view;
            proxyStrategy = new NxSmartRefreshLayoutStrategy(nxRefreshLayout, view);
        }
    }



    @Override
    public NxRefreshLayout setEnableLoadMore(boolean enabled) {
        proxyStrategy.setEnableLoadMore(enabled);
        return this;
    }

    @Override
    public NxRefreshLayout setEnableRefresh(boolean enabled) {
        proxyStrategy.setEnableRefresh(enabled);
        return this;
    }


    @Override
    public NxRefreshLayout setOnRefreshLoadMoreListener(NxOnRefreshListener nxOnRefreshListener) {
        proxyStrategy.setOnRefreshLoadMoreListener(nxOnRefreshListener);
        return this;
    }

    @Override
    public NxRefreshLayout finishRefresh() {
        proxyStrategy.finishRefresh();
        return this;
    }

    @Override
    public NxRefreshLayout finishLoadMore() {
        proxyStrategy.finishLoadMore();
        return this;
    }


}
