package com.nj.baijiayun.refresh.listener;

import android.support.annotation.NonNull;

import com.nj.baijiayun.refresh.api.RefreshLayout;

/**
 * 加载更多监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
