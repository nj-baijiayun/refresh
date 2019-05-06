package com.nj.baijiayun.smartrv;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe
 */
public interface NxRefreshLayout {

    /**
     * 设置加载更多
     *
     * @param enabled true
     * @return NxRefreshLayout
     */
    NxRefreshLayout setEnableLoadMore(boolean enabled);

    /**
     * 设置刷新可用
     *
     * @param enabled false
     * @return NxRefreshLayout
     */
    NxRefreshLayout setEnableRefresh(boolean enabled);

    /**
     * 设置刷新或者加载更多
     *
     * @param nxOnRefreshListener n
     * @return NxRefreshLayout
     */
    NxRefreshLayout setOnRefreshLoadMoreListener(NxOnRefreshListener nxOnRefreshListener);


    /**
     * 完成刷新
     *
     * @return NxRefreshLayout
     */
    NxRefreshLayout finishRefresh();

    /**
     * 完成加载更多
     *
     * @return NxRefreshLayout
     */
    NxRefreshLayout finishLoadMore();

}
