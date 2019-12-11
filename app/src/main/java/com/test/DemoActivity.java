package com.test;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.baijiayun.R;
import com.caverock.androidsvg.RenderOptions;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.io.ByteArrayInputStream;
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
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private RecyclerView rv;
    private LinearLayoutManager layoutManager;


    String svgData = "<svg t=\"1575279104573\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2571\" width=\"128\" height=\"128\"><path d=\"M422.234 848.913l20.686-1.419c178.271-12.24 317.917-161.906 317.917-340.729 0-188.335-153.22-341.557-341.553-341.557-188.338 0-341.563 153.222-341.563 341.557 0 15.677 1.107 31.586 3.29 47.29l3.513 25.265h-63.116l-2.551-19.295c-2.332-17.625-3.515-35.545-3.515-53.26 0-222.732 181.207-403.937 403.942-403.937 222.731 0 403.935 181.206 403.935 403.937 0 25.712-2.469 51.503-7.343 76.725l199.638 116.514-39.599 67.843-183.27-106.964c-26.698 64.534-69.974 121.329-125.5 164.603-64.006 49.886-140.515 79.045-221.259 84.324l-23.653 1.548v-62.445zM377.279 779.883v-130.251h-100.628v-55.074h100.628v-65.101h-99.069v-55.075h80.093l-121.381-188.663h71.893l101.996 160.9 102.002-160.9h74.372l-123.51 188.663h82.855v55.075h-102.18v65.101h100.627v55.074h-100.627v130.251z\" p-id=\"2572\"></path></svg>";

    String res = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_demo_add_top);
        rv = findViewById(R.id.rv);
        RenderOptions renderOptions = new RenderOptions();
        SVG svg = null;
        try {
            svg = SVG.getFromString(svgData);
//            renderOptions.css(String.format("path{fill:%s;}", getHexString(filterColor)));
            svg.setDocumentWidth(svg.getDocumentWidth() * 20 / svg.getDocumentHeight());
            svg.setDocumentHeight(20);

            PictureDrawable pictureDrawable = new PictureDrawable(svg.renderToPicture(renderOptions));
            pictureDrawable.setBounds(0, 0, 20, 20);

            AppCompatImageView viewById = findViewById(R.id.test);
            viewById.setImageDrawable(pictureDrawable);
        } catch (SVGParseException e) {
            e.printStackTrace();
        }


//        SVGParserRenderer drawable = new SVGParserRenderer(context, String svgContent);


//        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.ic_android_black_24dp, getTheme());
        VectorDrawableCompat vectorDrawableCompat = (VectorDrawableCompat) VectorDrawableCompat.createFromStream(new ByteArrayInputStream(res.getBytes()), "ppp");
        Log.d("TAG", "vectorDrawable===>" + vectorDrawableCompat);


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

                holder.setText(R.id.tv_name, position + "");
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
        smoothScroller = new LinearSmoothScroller(rv.getContext()) {
            // 返回：滑过1px时经历的时间(ms)。
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 150.0F / (float) displayMetrics.densityDpi;
            }
        };
        handler.sendEmptyMessageDelayed(1, 1000);
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    LinearSmoothScroller smoothScroller;


    int count = 0;
    private android.os.Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count += 1;

            smoothScroller.setTargetPosition(count);
            ((LinearLayoutManager) rv.getLayoutManager()).startSmoothScroll(smoothScroller);
            handler.sendEmptyMessageDelayed(1, 2000);
        }
    };


}
