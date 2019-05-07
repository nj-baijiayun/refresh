package com.nj.baijiayun.smartrv;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nj.baijiayun.SmartRefreshLayout;
import com.nj.baijiayun.smartrv.strategy.DefaultExtra;
import com.nj.baijiayun.smartrv.strategy.NxSmartRefreshLayoutStrategy;


/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe 刷新View
 */
public class NxRefreshView extends FrameLayout implements INxRefreshLayout, IRecycleViewInterface, INxRefreshInterface {

    private RecyclerView mRecyclerView;
    private RefreshViewProxy mProxy;
    private RecyclerView.Adapter mAdapter;

    public NxRefreshView(Context context) throws Exception {
        super(context);
        initView();
    }

    public NxRefreshView(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        initView();

    }

    public NxRefreshView(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        initView();


    }

    private void initView() throws Exception {

        ViewGroup refreshLayoutView = createRefreshLayoutView();
        mProxy = new RefreshViewProxy(createStrategy(this, refreshLayoutView), refreshLayoutView);
        LayoutParams params = new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        refreshLayoutView.setLayoutParams(params);
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutParams(params);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addView(refreshLayoutView);
        refreshLayoutView.addView(mRecyclerView);
        initExtra();


    }


    @Override
    public INxRefreshLayout setEnableLoadMore(boolean enabled) {
        return mProxy.setEnableLoadMore(enabled);
    }

    @Override
    public INxRefreshLayout setEnableRefresh(boolean enabled) {
        return mProxy.setEnableRefresh(enabled);
    }

    @Override
    public INxRefreshLayout setOnRefreshLoadMoreListener(INxOnRefreshListener nxOnRefreshListener) {
        return mProxy.setOnRefreshLoadMoreListener(nxOnRefreshListener);
    }

    @Override
    public INxRefreshLayout finishRefresh() {
        return mProxy.finishRefresh();
    }

    @Override
    public INxRefreshLayout finishLoadMore() {
        return mProxy.finishLoadMore();
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    public void setExtra(INxExtra nxFactory) {
        nxFactory.setExtra(mProxy.getProxyRefreshView());
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(layout);
    }

    @Override
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    @Override
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    @Override
    public void notifyDataSetChanged() {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanSizeLookup(spanSizeLookup);
        }
    }

    @Override
    public ViewGroup createRefreshLayoutView() {
        return new SmartRefreshLayout(getContext());
    }

    @Override
    public INxRefreshLayoutStrategy createStrategy(INxRefreshLayout iNxRefreshLayout, View refreshView) {
        return new NxSmartRefreshLayoutStrategy(iNxRefreshLayout, refreshView);
    }


    @Override
    public void initExtra() {
        setEnableRefresh(true);
        NxRefreshConfig nxRefreshConfig = NxRefreshConfig.get();
        if (nxRefreshConfig != null) {
            setExtra(nxRefreshConfig.getDefaultExtra());
        } else {
            setExtra(new DefaultExtra());
        }


    }
}