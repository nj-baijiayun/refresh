package com.nj.baijiayun.refresh.recycleview;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.refresh.recycleview
 * @describe
 */
@Deprecated
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeHolder {
    Class<? extends BaseMultTypeViewHolder> value();
}
