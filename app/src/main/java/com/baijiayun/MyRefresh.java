package com.baijiayun;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.nj.baijiayun.SmartRefreshLayout;
import com.nj.baijiayun.smartrv.INxRefreshLayout;
import com.nj.baijiayun.smartrv.INxRefreshLayoutStrategy;
import com.nj.baijiayun.smartrv.NxRefreshView;
import com.nj.baijiayun.smartrv.strategy.NxSmartRefreshLayoutStrategy;

/**
 * @author chengang
 * @date 2019/5/7
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.baijiayun
 * @describe
 */
public class MyRefresh extends NxRefreshView {


    public MyRefresh(Context context) throws Exception {
        super(context);
    }

    public MyRefresh(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
    }

    public MyRefresh(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public INxRefreshLayoutStrategy createStrategy(INxRefreshLayout iNxRefreshLayout, View refreshView) {
        return new NxSmartRefreshLayoutStrategy(iNxRefreshLayout, refreshView);
    }

    @Override
    public ViewGroup createRefreshLayoutView() {
        return new SmartRefreshLayout(getContext());
    }
}
