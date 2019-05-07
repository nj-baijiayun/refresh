package com.nj.baijiayun.smartrv.strategy;

import android.content.Context;
import android.view.View;

import com.nj.baijiayun.SmartRefreshLayout;
import com.nj.baijiayun.constant.SpinnerStyle;
import com.nj.baijiayun.footer.BallPulseFooter;
import com.nj.baijiayun.header.WaterDropHeader;
import com.nj.baijiayun.smartrv.INxExtra;


/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.helper
 * @describe 默认额外属性接口
 */
public class DefaultExtra implements INxExtra {

    @Override
    public void setExtra(View refreshLayoutView) {
        if (refreshLayoutView instanceof SmartRefreshLayout) {
            Context context = refreshLayoutView.getContext();
            ((SmartRefreshLayout) refreshLayoutView).setRefreshHeader(new WaterDropHeader(refreshLayoutView.getContext()));
            ((SmartRefreshLayout) refreshLayoutView).setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
            ((SmartRefreshLayout) refreshLayoutView).setEnableFooterTranslationContent(true);
            ((SmartRefreshLayout) refreshLayoutView).setEnableHeaderTranslationContent(true);
            ((SmartRefreshLayout) refreshLayoutView).setPrimaryColorsId(android.R.color.black);
        }
    }
}
