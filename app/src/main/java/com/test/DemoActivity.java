package com.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.baijiayun.R;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test
 * @describe
 */
public class DemoActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_demo_add_top);
        rv = findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter(this) {
            @Override
            protected int attachLayoutRes() {
                return R.layout.course_item_assemble;
            }

            @Override
            public int getItemCount() {
                return Integer.MAX_VALUE;
            }


            @Override
            protected void bindViewAndData(BaseViewHolder holder, Object o, int position) {

                holder.setText(R.id.tv_name,position+"");
                holder.setOnClickListener(R.id.iv_head, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DemoActivity.this, "555", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position % 3);
            }

            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List payloads) {
                super.onBindViewHolder(holder, position, payloads);
            }


        };
        adapter.getItems().add(new Object());
        adapter.getItems().add(new Object());
        adapter.getItems().add(new Object());
        rv.setAdapter(adapter);
        smoothScroller=  new LinearSmoothScroller(rv.getContext()) {
            // 返回：滑过1px时经历的时间(ms)。
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 150.0F / (float)displayMetrics.densityDpi;
            }
        };
        handler.sendEmptyMessageDelayed(1,1000);
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    LinearSmoothScroller smoothScroller;



    int count=0;
    private android.os.Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count+=1;

            smoothScroller.setTargetPosition(count);
            ((LinearLayoutManager) rv.getLayoutManager()).startSmoothScroll(smoothScroller);
            handler.sendEmptyMessageDelayed(1,2000);
        }
    };



}
