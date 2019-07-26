package com.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.nj.baijiayun.refresh.smartrv.INxOnRefreshListener;
import com.nj.baijiayun.refresh.smartrv.INxRefreshLayout;
import com.nj.baijiayun.refresh.smartrv.NxRefreshConfig;
import com.nj.baijiayun.refresh.smartrv.NxRefreshView;
import com.nj.baijiayun.refresh.smartrv.strategy.DefaultExtra;
import com.test.adpter.DemoHolder;
import com.test.bean.DemoBean;
import com.test.bean.DemoBean2;
import com.test.bean.MultipleTypeModel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

//import com.nj.baijiayun.processor.DemoAdapterHelper;

public class MainActivity extends AppCompatActivity {

    List<Object> datas = new ArrayList<>();

    NxRefreshView nxRefreshView;

    private BaseMultipleTypeRvAdapter demoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nxRefreshView = findViewById(R.id.nxRv);
        nxRefreshView.setEnableRefresh(true);
        nxRefreshView.setEnableLoadMore(true);
        demoAdapter = DemoAdapterHelper.getDefaultAdapter(this);

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
        try {
            DemoHolder demoHolder = DemoHolder.class.getConstructor(ViewGroup.class).newInstance(nxRefreshView);
            System.out.println("demoHolder---->" + demoHolder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        demoAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(BaseViewHolder holder, int position, View view, Object item) {
                Toast.makeText(MainActivity.this, "DemoBeanLong", Toast.LENGTH_SHORT).show();

            }
        });

        nxRefreshView.setAdapter(demoAdapter);



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
        demoAdapter.addAll(datas);

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
}
