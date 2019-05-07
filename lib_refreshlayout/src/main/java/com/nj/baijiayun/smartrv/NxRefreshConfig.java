package com.nj.baijiayun.smartrv;

import com.nj.baijiayun.smartrv.strategy.DefaultExtra;

/**
 * @author chengang
 * @date 2019/5/6
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name www.baijiayun.module_common.widget.smartrv
 * @describe 配置
 */
public class NxRefreshConfig {

    private DefaultExtra defaultExtra;

    private NxRefreshConfig(DefaultExtra defaultExtra) {
        this.defaultExtra = defaultExtra;
    }

    private volatile static NxRefreshConfig instance;

    static NxRefreshConfig get() {
        return instance;
    }

    DefaultExtra getDefaultExtra() {
        return defaultExtra;
    }

    public static NxRefreshConfig init(DefaultExtra defaultExtra) {
        if (instance == null) {
            synchronized (NxRefreshConfig.class) {
                if (instance == null) {
                    instance = new NxRefreshConfig(defaultExtra);
                }
            }
        }
        return instance;
    }



}
