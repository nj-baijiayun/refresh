package com.baijiayun;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nj.baijiayun.SmartRefreshLayout;
import com.nj.baijiayun.smartrv.INxExtra;
import com.nj.baijiayun.smartrv.NxOnRefreshListener;
import com.nj.baijiayun.smartrv.NxRefreshLayout;
import com.nj.baijiayun.smartrv.NxRefreshView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> datas = new ArrayList<>();

    private void addData() {


        for (int i = 0; i < 20; i++) {
            datas.add(i + "");
        }


    }

    NxRefreshView nxRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nxRefreshView = findViewById(R.id.nxRv);
        nxRefreshView.setExtra(new INxExtra() {
            @Override
            public void setExtra(View view) {
                if (view instanceof SmartRefreshLayout) {
                    Context context = view.getContext();
                    ((SmartRefreshLayout)view).setFooterHeight(0);
                }

            }
        });
        addData();

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
        nxRefreshView.setAdapter(adapter);

        nxRefreshView.setOnRefreshLoadMoreListener(new NxOnRefreshListener() {
            @Override
            public void onRefresh(final NxRefreshLayout nxRefreshLayout) {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.clear();
                        addData();
                        nxRefreshView.notifyDataSetChanged();

                        nxRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final NxRefreshLayout nxRefreshLayout) {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addData();
                        nxRefreshView.notifyDataSetChanged();
                        nxRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
    }
}
