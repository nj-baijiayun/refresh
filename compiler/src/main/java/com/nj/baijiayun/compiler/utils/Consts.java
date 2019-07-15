package com.nj.baijiayun.compiler.utils;

/**
 * @author chengang
 * @date 2019-07-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler.utils
 * @describe
 */
public class Consts {
    // Generate
    public static final String SEPARATOR = "$$";
    public static final String PROJECT = "HolderCreate";
    public static final String TAG = PROJECT + "::";

    // Java type
    private static final String LANG = "java.lang";
    public static final String BYTE = LANG + ".Byte";
    public static final String SHORT = LANG + ".Short";
    public static final String INTEGER = LANG + ".Integer";
    public static final String LONG = LANG + ".Long";
    public static final String FLOAT = LANG + ".Float";
    public static final String DOUBEL = LANG + ".Double";
    public static final String BOOLEAN = LANG + ".Boolean";
    public static final String CHAR = LANG + ".Character";
    public static final String STRING = LANG + ".String";
    public static final String SERIALIZABLE = "java.io.Serializable";
    public static final String PACKAGE = "com.nj.baijiayun.processor";

    // Custom interface
    private static final String FACADE_PACKAGE = "com.alibaba.android.arouter.facade";
    private static final String TEMPLATE_PACKAGE = ".template";
    private static final String SERVICE_PACKAGE = ".service";
    private static final String MODEL_PACKAGE = ".model";

    // Log
    static final String PREFIX_OF_LOGGER = PROJECT + "::Compiler ";

}
