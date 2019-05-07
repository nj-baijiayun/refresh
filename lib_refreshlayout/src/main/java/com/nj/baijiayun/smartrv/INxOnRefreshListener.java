package com.nj.baijiayun.smartrv;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe 刷新 加载更多
 */
public interface INxOnRefreshListener {

    /**
     * 刷新
     */
    void onRefresh(INxRefreshLayout nxRefreshLayout);

    /**
     * 加载更多
     */
    void onLoadMore(INxRefreshLayout nxRefreshLayout);
}
