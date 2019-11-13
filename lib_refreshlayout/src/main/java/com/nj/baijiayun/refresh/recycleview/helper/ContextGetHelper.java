package com.nj.baijiayun.refresh.recycleview.helper;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

/**
 * @author chengang
 * @date 2019-09-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.refresh.recycleview.helper
 * @describe
 */
public class ContextGetHelper {
    public static Activity getActivityFromView(View view) {
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }
}
