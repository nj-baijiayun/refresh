package com.baijiayun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nj.baijiayun.smartrv.INxOnRefreshListener;
import com.nj.baijiayun.smartrv.INxRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> datas = new ArrayList<>();

    MyRefresh nxRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nxRefreshView = findViewById(R.id.nxRv);
        nxRefreshView.setEnableRefresh(true);
        nxRefreshView.setEnableLoadMore(true);
        nxRefreshView.setAdapter(adapter);
        for (int i = 0; i < 100; i++) {
            datas.add(i+"");
        }


        //可选 不设置 默认是LinearLayoutManager Vertical
        nxRefreshView.setLayoutManager(new GridLayoutManager(this,4));
        nxRefreshView.setOnRefreshLoadMoreListener(new INxOnRefreshListener() {

            @Override
            public void onRefresh(INxRefreshLayout nxRefreshLayout) {

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nxRefreshView.finishRefresh();
                    }
                },1000);
            }

            @Override
            public void onLoadMore(INxRefreshLayout nxRefreshLayout) {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nxRefreshView.finishLoadMore();
                    }
                },1000);
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
