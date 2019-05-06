package com.nj.baijiayun.smartrv;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nj.baijiayun.SmartRefreshLayout;


/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe
 */
public class NxRefreshView extends RelativeLayout implements NxRefreshLayout, IRecycleViewInterface {

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

    public void initView() throws Exception {
        SmartRefreshLayout mSmartRefreshLayout = new SmartRefreshLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSmartRefreshLayout.setLayoutParams(params);
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutParams(params);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addView(mSmartRefreshLayout);
        mSmartRefreshLayout.addView(mRecyclerView);
        mProxy = new RefreshViewProxy(this, mSmartRefreshLayout);
        setEnableRefresh(true);

        NxRefreshConfig nxRefreshConfig = NxRefreshConfig.get();
        if (nxRefreshConfig != null) {
            setExtra(nxRefreshConfig.getDefaultExtra());
        }

    }


    @Override
    public NxRefreshLayout setEnableLoadMore(boolean enabled) {
        return mProxy.setEnableLoadMore(enabled);
    }

    @Override
    public NxRefreshLayout setEnableRefresh(boolean enabled) {
        return mProxy.setEnableRefresh(enabled);
    }

    @Override
    public NxRefreshLayout setOnRefreshLoadMoreListener(NxOnRefreshListener nxOnRefreshListener) {
        return mProxy.setOnRefreshLoadMoreListener(nxOnRefreshListener);
    }

    @Override
    public NxRefreshLayout finishRefresh() {
        return mProxy.finishRefresh();
    }

    @Override
    public NxRefreshLayout finishLoadMore() {
        return mProxy.finishLoadMore();
    }


    public RecyclerView getmRecyclerView() {
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

    public void notifyDataSetChanged() {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanSizeLookup(spanSizeLookup);
        }
    }
}