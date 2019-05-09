package com.nj.baijiayun.refresh.smartrv;

import com.nj.baijiayun.refresh.smartrv.strategy.DefaultExtra;

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
    public static final int TYPE_SMART_REFRESH = 1;
    private int type = TYPE_SMART_REFRESH;


    private NxRefreshConfig() {

    }

    private volatile static NxRefreshConfig instance;

    static NxRefreshConfig get() {
        if (instance == null) {
            synchronized (NxRefreshConfig.class) {
                if (instance == null) {
                    instance = new NxRefreshConfig();
                }
            }
        }
        return instance;
    }


    public static NxRefreshConfig init(DefaultExtra defaultExtra) {
        NxRefreshConfig nxRefreshConfig = get();
        nxRefreshConfig.setDefaultExtra(defaultExtra);
        return instance;
    }

    DefaultExtra getDefaultExtra() {
        return defaultExtra;
    }

    public void setType(int type) {
        this.type = type;
    }

    int getType() {
        return type;
    }

    private void setDefaultExtra(DefaultExtra defaultExtra) {
        this.defaultExtra = defaultExtra;
    }


}
