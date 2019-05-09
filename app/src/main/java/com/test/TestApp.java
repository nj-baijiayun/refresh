package com.test;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.nj.baijiayun.refresh.SmartRefreshLayout;
import com.nj.baijiayun.refresh.constant.SpinnerStyle;
import com.nj.baijiayun.refresh.footer.BallPulseFooter;
import com.nj.baijiayun.refresh.header.WaterDropHeader;
import com.nj.baijiayun.refresh.smartrv.NxRefreshConfig;
import com.nj.baijiayun.refresh.smartrv.strategy.DefaultExtra;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.baijiayun
 * @describe
 */
public class TestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NxRefreshConfig.init(new DefaultExtra() {
            @Override
            public void setExtra(View view) {
                if (view instanceof SmartRefreshLayout) {
                    Context context = view.getContext();
                    ((SmartRefreshLayout) view).setRefreshHeader(new WaterDropHeader(view.getContext()));
                    ((SmartRefreshLayout) view).setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
                    ((SmartRefreshLayout) view).setEnableFooterTranslationContent(true);
                    ((SmartRefreshLayout) view).setEnableHeaderTranslationContent(true);
                    ((SmartRefreshLayout) view).setPrimaryColorsId(android.R.color.black);
                }
            }
        }).setType(NxRefreshConfig.TYPE_SMART_REFRESH);
    }
}
