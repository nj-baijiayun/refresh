package com.nj.baijiayun.smartrv;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe
 */
public interface NxOnRefreshListener {

    /**
     * 刷新
     */
    void onRefresh(NxRefreshLayout nxRefreshLayout);

    /**
     * 加载更多
     */
    void onLoadMore(NxRefreshLayout nxRefreshLayout);
}
