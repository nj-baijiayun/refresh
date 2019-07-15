package com.nj.baijiayun.refresh.recycleview;

import android.view.ViewGroup;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.refresh.recycleview
 * @describe
 */
@Deprecated
public abstract class AbstractDefaultViewTypeRegisterFactory implements ViewTypeFactory {

    @Override
    public BaseMultTypeViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return  CreateHolderHelper.autoCreateHolder(this.getClass(), viewType, parent);
    }


}
