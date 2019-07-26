package com.nj.baijiayun.compiler;

import com.nj.baijiayun.annotations.ModelTypeHolderCreate;
import com.nj.baijiayun.compiler.model.GroupProcessorModel;
import com.nj.baijiayun.compiler.model.MultipleModel;
import com.sun.tools.javac.code.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author chengang
 * @date 2019-07-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
public class BestTypeModelHelper {


    static List<String> getHolderClsName(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(ModelTypeHolderCreate.class);

        List<String> result = new ArrayList<>();
        for (Element e : elementsAnnotatedWith) {

            TypeElement te = (TypeElement) e;
            //获取了当前的类名
            TypeMirror superclass = te.getSuperclass();
            if (!isInstanceOf(superclass.toString())) {
                result.add(te.toString());
            }
        }
        return result;
    }

    private static boolean isInstanceOf(String clsName) {
        System.out.println("isInstanceOf-->" + clsName);
        return clsName.contains("BaseMultipleTypeModelHolderFactory");
    }


    static Map<String, GroupProcessorModel> getHolderClassArray(RoundEnvironment roundEnvironment) {
        Map<String, GroupProcessorModel> groupMap = new HashMap<>();
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(ModelTypeHolderCreate.class);

        for (Element e : elementsAnnotatedWith) {

            TypeElement te = (TypeElement) e;
            //获取了当前的类名
            TypeMirror superclass = te.getSuperclass();
            if (!isInstanceOf(superclass.toString())) {
                continue;
            }


            Type.ClassType classType = (Type.ClassType) superclass;
            //获取继承的类的范型

            List<Type> typeArguments = classType.getTypeArguments();
            Type type = typeArguments.get(0);


            ModelTypeHolderCreate annotation = e.getAnnotation(ModelTypeHolderCreate.class);
            String annotationStr = annotation.toString();
            int start = annotationStr.lastIndexOf("=");
            int end = annotationStr.lastIndexOf(")");
            String[] groups = annotation.group();
            String holderClassNameStr = annotationStr.substring(start + 1, end);

            for (String group : groups) {
                if (groupMap.get(group) == null) {
                    GroupProcessorModel groupModel = new GroupProcessorModel();
                    groupModel.setGroup(group);

                    MultipleModel multipleModel = new MultipleModel();

                    multipleModel.setMultipleModelFactoryName(te.toString());
                    multipleModel.setMultipleModelName(type.toString());
                    groupModel.getMultipleModels().add(multipleModel);
                    groupMap.put(group, groupModel);
                } else {
                    MultipleModel multipleModel = new MultipleModel();
                    multipleModel.setMultipleModelFactoryName(te.toString());
                    multipleModel.setMultipleModelName(type.toString());
                    groupMap.get(group).getMultipleModels().add(multipleModel);
                }
            }


        }
        return groupMap;
    }

}
