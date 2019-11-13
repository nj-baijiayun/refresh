package com.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baijiayun.R;
import com.nj.baijiayun.processor.DemoAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.recycleview.extend.HeaderAndFooterRecyclerViewAdapter;
import com.nj.baijiayun.refresh.smartrv.INxOnRefreshListener;
import com.nj.baijiayun.refresh.smartrv.INxRefreshLayout;
import com.nj.baijiayun.refresh.smartrv.NxRefreshConfig;
import com.nj.baijiayun.refresh.smartrv.NxRefreshView;
import com.nj.baijiayun.refresh.smartrv.strategy.DefaultExtra;
import com.test.adapter.ExpandHelper;
import com.test.bean.AreaBean;
import com.test.bean.CityBean;
import com.test.bean.DemoBean;
import com.test.bean.DemoBean2;
import com.test.bean.MultipleTypeModel;
import com.test.bean.ProvinceBean;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

//import com.nj.baijiayun.processor.DemoAdapterHelper;

public class MainActivity extends AppCompatActivity {

    List<Object> datas = new ArrayList<>();

    NxRefreshView nxRefreshView;

    private BaseMultipleTypeRvAdapter demoAdapter;

    RecyclerView getRv() {
        return nxRefreshView.getRecyclerView();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nxRefreshView = findViewById(R.id.nxRv);
        nxRefreshView.setEnableRefresh(true);
        nxRefreshView.setEnableLoadMore(true);
        demoAdapter = DemoAdapterHelper.getDefaultAdapter(this);

//        addPCA();

        demoAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, View view, Object item) {
                if (view.getId() == R.id.tv1) {
                    Toast.makeText(MainActivity.this, "inner" + position, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "DemoBean", Toast.LENGTH_SHORT).show();

            }
        });


        demoAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(BaseViewHolder holder, int position, View view, Object item) {
                Toast.makeText(MainActivity.this, "DemoBeanLong", Toast.LENGTH_SHORT).show();

            }
        });

        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter();
        headerAndFooterRecyclerViewAdapter.setAdapter(demoAdapter);

        nxRefreshView.setAdapter(headerAndFooterRecyclerViewAdapter);


        addNormalData();
        demoAdapter.addAll(datas);
//        RecyclerViewUtils.setHeaderView(nxRefreshView.getRecyclerView(), new Button(this));


        NxRefreshConfig.init(new DefaultExtra());

        //可选 不设置 默认是LinearLayoutManager Vertical
//        nxRefreshView.setLayoutManager(new GridLayoutManager(this, 4));
        nxRefreshView.setOnRefreshLoadMoreListener(new INxOnRefreshListener() {

            @Override
            public void onRefresh(INxRefreshLayout nxRefreshLayout) {

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nxRefreshView.finishRefresh();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(INxRefreshLayout nxRefreshLayout) {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nxRefreshView.finishLoadMore();
                    }
                }, 1000);
            }
        });

        nxRefreshView.notifyDataSetChanged();
    }

    private void addNormalData() {
        for (int i = 0; i < 40; i++) {
            MultipleTypeModel e = new MultipleTypeModel();

            if (i % 2 == 0) {
                e.setType(1);
            } else {
                e.setType(2);
            }
            datas.add(e);
        }
        for (int i = 0; i < 40; i++) {
            if (i % 2 == 0) {
                datas.add(new DemoBean());
            } else {
                datas.add(new DemoBean2());

            }
        }
    }


    private void addPCA() {
//        RecyclerView.LayoutManager
        List<AreaBean> datas = new ArrayList<>();
        for (int i = 0; i < 110; i++) {
            AreaBean areaBean = new AreaBean();
            areaBean.setTitle(i + "区");
            datas.add(areaBean);
        }

        List<CityBean> cityBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CityBean cityBean = new CityBean();
            cityBean.setTitle(i + "市");
            cityBean.setAreaBeans(datas.subList(i * 10, (i + 1) * 10));
            cityBeans.add(cityBean);
        }
        List<ProvinceBean> provinceBeans = new ArrayList<>();
        ProvinceBean provinceBean = new ProvinceBean();
        provinceBean.setTitle("0省");
        provinceBean.getTreeItemAttr().onExpand();
        provinceBean.setCityBeans(cityBeans.subList(0, 5));

        provinceBean.getChilds().get(0).getTreeItemAttr().onExpand();

        ProvinceBean provinceBean2 = new ProvinceBean();
        provinceBean2.setTitle("1省");
        provinceBean2.getTreeItemAttr().onExpand();
        provinceBean2.setCityBeans(cityBeans.subList(5, 10));
//        provinceBean2.getChilds().get(2).getTreeItemAttr().onExpand();

        demoAdapter.addItem(provinceBean);
        demoAdapter.addItem(provinceBean2);

        ExpandHelper.initExpand(demoAdapter);


    }

    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new MyViewHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            View item = ((MyViewHolder) holder).getItem();
            if (item instanceof TextView) {
                ((TextView) item).setText(String.valueOf(position));
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private View item;

            public View getItem() {
                return item;
            }

            public MyViewHolder(View itemView) {
                super(itemView);
                this.item = itemView;
            }
        }

    };

    private static int i = 2;

    public void add(View view) {


        LinearLayoutManager layoutManager = (LinearLayoutManager) getRv().getLayoutManager();

        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        //获取与该view的顶部的偏移量
        int offset = topView.getTop();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        ProvinceBean item = new ProvinceBean();
        i++;
        item.setTitle(MessageFormat.format("{0}省",i));

        demoAdapter.getItems().add(0, item);
        demoAdapter.notifyItemChanged(0,firstVisibleItemPosition+1);
        layoutManager.scrollToPositionWithOffset(firstVisibleItemPosition, offset);

    }
}
