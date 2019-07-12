package com.nj.baijiayun.refresh.recycleview;


import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chengang
 * @describe 用于适配器item的类型 注解
 */
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AuthValidations.class)
public @interface TypeHolder {
    Class<? extends BaseMultipleTypeViewHolder> holder();

    Class model() default String.class;

}