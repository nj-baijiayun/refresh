package com.nj.baijiayun.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chengang
 * @date 2019-07-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.annotations
 * @describe
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface HolderCreate {
}
