package com.nj.baijiayun.smartrv;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author chengang
 * @date 2019/5/7
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.smartrv
 * @describe 刷新view 需要的接口
 */
public interface INxRefreshInterface {
    /**
     * 创建包裹的View
     *
     * @return viewGroup
     */
    ViewGroup createRefreshLayoutView();

    /**
     * 配置第三方刷新实现类
     *
     * @param iNxRefreshLayout i
     * @param refreshView      v
     * @return IRefreshLayoutStrategy
     */
    INxRefreshLayoutStrategy createStrategy(INxRefreshLayout iNxRefreshLayout, View refreshView);


    /**
     * 初始化属性
     */
    void initExtra();



}
