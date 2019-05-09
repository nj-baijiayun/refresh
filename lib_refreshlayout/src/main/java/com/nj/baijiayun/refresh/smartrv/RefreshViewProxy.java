package com.nj.baijiayun.refresh.smartrv;

import android.content.Context;
import android.view.View;

import com.nj.baijiayun.refresh.SmartRefreshLayout;
import com.nj.baijiayun.refresh.smartrv.strategy.NxSmartRefreshLayoutStrategy;


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

    RefreshViewProxy(Context context,INxRefreshLayout nxRefreshLayout) throws Exception {
        if (proxyStrategy == null) {
            if(NxRefreshConfig.get().getType()==NxRefreshConfig.TYPE_SMART_REFRESH) {
                SmartRefreshLayout view = new SmartRefreshLayout(context);
                proxyStrategy=new NxSmartRefreshLayoutStrategy(nxRefreshLayout, view);
                this.view=view;
            }
        }
    }


    public void setProxyStrategy(INxRefreshLayout proxyStrategy) {
        this.proxyStrategy = proxyStrategy;
    }

    @Override
    public INxRefreshLayout setEnableLoadMore(boolean enabled) {
        if (proxyStrategy != null) {
            proxyStrategy.setEnableLoadMore(enabled);
        }
        return this;
    }

    @Override
    public INxRefreshLayout setEnableRefresh(boolean enabled) {
        if (proxyStrategy != null) {
            proxyStrategy.setEnableRefresh(enabled);
        }
        return this;
    }


    @Override
    public INxRefreshLayout setOnRefreshLoadMoreListener(INxOnRefreshListener nxOnRefreshListener) {
        if (proxyStrategy != null) {
            proxyStrategy.setOnRefreshLoadMoreListener(nxOnRefreshListener);
        }
        return this;
    }

    @Override
    public INxRefreshLayout finishRefresh() {
        if (proxyStrategy != null) {
            proxyStrategy.finishRefresh();
        }
        return this;
    }

    @Override
    public INxRefreshLayout finishLoadMore() {
        if (proxyStrategy != null) {
            proxyStrategy.finishLoadMore();
        }
        return this;
    }



}
