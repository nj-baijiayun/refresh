package com.nj.baijiayun.compiler.utils;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler.utils
 * @describe
 */
public class StringUtils {
    public static String getStrUpperFirst(String str) {
        return str.substring(0, 1).toUpperCase().concat(str.substring(1).toLowerCase());

    }
}
