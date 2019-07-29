package com.nj.baijiayun.compiler;

import com.nj.baijiayun.annotations.ModelMultiTypeAdapterCreate;
import com.nj.baijiayun.compiler.model.GroupProcessorModel;
import com.nj.baijiayun.compiler.model.MultipleModel;
import com.nj.baijiayun.compiler.utils.TypeUtils;
import com.squareup.javapoet.ClassName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * @author chengang
 * @date 2019-07-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
@Deprecated
public class HolderTypeModelHelper {


    static List<String> getModelTypeHolderClsName(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(ModelMultiTypeAdapterCreate.class);

        List<String> result = new ArrayList<>();
        for (Element e : elementsAnnotatedWith) {

            TypeElement te = (TypeElement) e;


            //获取了当前的类名
//            TypeMirror superclass = te.getSuperclass();
            if (TypeUtils.isSuperClassContains(te,ClassName.bestGuess("com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder"))) {
                result.add(te.toString());
            }
        }
        return result;
    }

    private static boolean isInstanceOf(String clsName) {
        System.out.println("isInstanceOf-->" + clsName);
        return clsName.contains("BaseMultipleTypeModelHolderFactory");
    }


    public static ClassName baseFactoryName=ClassName.bestGuess("com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeModelHolderFactory");

    static Map<String, GroupProcessorModel> getHolderClassArray(RoundEnvironment roundEnvironment) {
        Map<String, GroupProcessorModel> groupMap = new HashMap<>();
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(ModelMultiTypeAdapterCreate.class);

        for (Element e : elementsAnnotatedWith) {

            TypeElement te = (TypeElement) e;
            //获取了当前的类名
            if (!TypeUtils.isSuperClassContains(te,baseFactoryName)) {
                continue;
            }
            String realClassType = HolderAdapterProcessor.getRealClassType(te, baseFactoryName);
            ModelMultiTypeAdapterCreate annotation = e.getAnnotation(ModelMultiTypeAdapterCreate.class);
            String[] groups = annotation.group();

            for (String group : groups) {
                if (groupMap.get(group) == null) {
                    GroupProcessorModel groupModel = new GroupProcessorModel(group);

                    MultipleModel multipleModel = new MultipleModel();

                    multipleModel.setMultipleModelFactoryName(te.toString());
                    multipleModel.setMultipleModelName(realClassType);
                    groupModel.getMultipleModels().add(multipleModel);
                    groupMap.put(group, groupModel);
                } else {
                    MultipleModel multipleModel = new MultipleModel();
                    multipleModel.setMultipleModelFactoryName(te.toString());
                    multipleModel.setMultipleModelName(realClassType);
                    groupMap.get(group).getMultipleModels().add(multipleModel);
                }
            }


        }
        return groupMap;
    }

}
