package com.nj.baijiayun.compiler.utils;

import com.squareup.javapoet.ClassName;
import com.sun.tools.javac.code.Type;

import javax.lang.model.element.TypeElement;

/**
 * @author chengang
 * @date 2019-07-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler.utils
 * @describe
 */
public class TypeUtils {
    public static String getTopSuperClsType(TypeElement te, ClassName className) {
        Type.ClassType classType = (Type.ClassType) te.getSuperclass();

        while (classType != null) {
            if (classType.toString().contains(className.toString())) {
                return classType.getTypeArguments().get(0).toString();
            }
            classType = (Type.ClassType) classType.supertype_field;
        }
        return null;

    }

    public static boolean isSuperClassContains(TypeElement te, ClassName className) {
        Type.ClassType classType = (Type.ClassType) te.getSuperclass();


        while (classType != null) {
            if (classType.toString().contains(className.toString())) {
                return true;
            }
            classType = (Type.ClassType) classType.supertype_field;

        }
        return false;

    }


    public static ClassName getOverride() {
        return ClassName.get("java.lang", "Override");
    }



}
