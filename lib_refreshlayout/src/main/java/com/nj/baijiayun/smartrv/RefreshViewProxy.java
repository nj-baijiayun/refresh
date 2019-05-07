package com.nj.baijiayun.smartrv;

import android.view.View;


/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe 代理第三方的view
 */
public class RefreshViewProxy implements INxRefreshLayoutStrategy {

    /**
     * 代理对象
     */
    private INxRefreshLayout proxyStrategy;

    private View view;

    View getProxyRefreshView() {
        return view;
    }

    RefreshViewProxy(INxRefreshLayoutStrategy iRefreshLayoutStrategy, View view) throws Exception {
        if (proxyStrategy == null) {
            this.view=view;
            proxyStrategy = iRefreshLayoutStrategy;
        }
    }
    @Override
    public INxRefreshLayout setEnableLoadMore(boolean enabled) {
        if(proxyStrategy!=null) {
            proxyStrategy.setEnableLoadMore(enabled);
        }
        return this;
    }

    @Override
    public INxRefreshLayout setEnableRefresh(boolean enabled) {
        if(proxyStrategy!=null) {
            proxyStrategy.setEnableRefresh(enabled);
        }
        return this;
    }


    @Override
    public INxRefreshLayout setOnRefreshLoadMoreListener(INxOnRefreshListener nxOnRefreshListener) {
        if(proxyStrategy!=null) {
            proxyStrategy.setOnRefreshLoadMoreListener(nxOnRefreshListener);
        }
        return this;
    }

    @Override
    public INxRefreshLayout finishRefresh() {
        if(proxyStrategy!=null) {
            proxyStrategy.finishRefresh();
        }
        return this;
    }

    @Override
    public INxRefreshLayout finishLoadMore() {
        if(proxyStrategy!=null) {
            proxyStrategy.finishLoadMore();
        }
        return this;
    }


}
