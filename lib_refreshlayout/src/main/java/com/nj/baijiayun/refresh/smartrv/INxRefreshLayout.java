package com.nj.baijiayun.refresh.smartrv;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe 刷新view基本的功能
 */
public interface INxRefreshLayout {

    /**
     * 设置加载更多
     *
     * @param enabled true
     * @return NxRefreshLayout
     */
    INxRefreshLayout setEnableLoadMore(boolean enabled);

    /**
     * 设置刷新可用
     *
     * @param enabled false
     * @return NxRefreshLayout
     */
    INxRefreshLayout setEnableRefresh(boolean enabled);

    /**
     * 设置刷新或者加载更多
     *
     * @param nxOnRefreshListener n
     * @return NxRefreshLayout
     */
    INxRefreshLayout setOnRefreshLoadMoreListener(INxOnRefreshListener nxOnRefreshListener);


    /**
     * 完成刷新
     *
     * @return NxRefreshLayout
     */
    INxRefreshLayout finishRefresh();

    /**
     * 完成加载更多
     *
     * @return NxRefreshLayout
     */
    INxRefreshLayout finishLoadMore();

}
