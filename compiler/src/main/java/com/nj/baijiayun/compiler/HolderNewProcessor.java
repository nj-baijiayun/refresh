package com.nj.baijiayun.compiler;

import com.nj.baijiayun.annotations.HolderCreate;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.util.List;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @author chengang
 * @date 2019-07-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.complier
 * @describe 支持了自动设置type
 */
//@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HolderNewProcessor extends BaseProcessor {


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(HolderCreate.class.getCanonicalName());
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(HolderCreate.class)) {
            Scope membersField = ((Symbol.ClassSymbol) e).members_field;
            Scope.Entry elems = membersField.elems;
            StringBuilder caseCodeTotalStr = new StringBuilder();
            StringBuilder modelTotalStr = new StringBuilder();
            Type returnType = null;
            String packageName = getCurrentClassPackageName(membersField.owner);
            while (elems != null) {
                if (elems.sym.toString().contains("getViewType")) {
                    List<Attribute.Compound> declarationAttributes = elems.sym.getMetadata().getDeclarationAttributes();
                    for (int i = 0; i < declarationAttributes.size(); i++) {
                        if (declarationAttributes.get(i).toString().contains("AuthValidations")) {
                            //获得了AuthValidations 注解了
                            Attribute.Compound compound = declarationAttributes.get(i);
                            //获得AuthValidations 上的所有注解
                            Attribute.Array array = (Attribute.Array) compound.values.get(0).snd;
                            createTypeToHolderAndModelToType(caseCodeTotalStr, modelTotalStr, array);
                        }
                    }

                } else if (elems.sym.type.toString().contains("ViewHolder")) {
                    Type.MethodType methodType = (Type.MethodType) elems.sym.type;
                    returnType = methodType.restype;
                }
                elems = elems.sibling;
            }
            MethodSpec getViewType = MethodSpec.methodBuilder("getViewType")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(int.class)
                    .addParameter(Object.class, "object")
                    .addStatement("$L return 0", modelTotalStr.toString())
                    .build();
            MethodSpec createViewHolder = MethodSpec.methodBuilder("createViewHolder")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(TypeName.get(returnType))
                    .addParameter(ClassName.bestGuess("android.view.ViewGroup"), "parent")
                    .addParameter(int.class, "type")
                    .addStatement(" switch(type){$L}  return null", caseCodeTotalStr.toString())
                    .build();
            TypeSpec holderClass = TypeSpec.classBuilder(e.getSimpleName() + "HolderHelper")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(getViewType)
                    .addMethod(createViewHolder)
                    .build();
            JavaFile javaFile = JavaFile.builder(packageName, holderClass)
                    .build();

            try {
                javaFile.writeTo(filer);
            } catch (Exception ee) {
                logger.error(ee);
            }

        }
        return false;
    }




    private void createTypeToHolderAndModelToType(StringBuilder caseCodeTotalStr, StringBuilder modelTotalStr, Attribute.Array array) {
        for (int j = 0; j < array.values.length; j++) {
            //这个时候才拿到真实的TypeHoder注解
            Attribute.Compound compound1 = (Attribute.Compound) array.values[j];

            if (!compound1.values.get(0).fst.toString().contains("holder")) {
                throw new IllegalArgumentException("TypeHolder first param must be Holder");

            }
            //遍历typeHolder
            Attribute holderAttribute = compound1.values.get(0).snd;

            getSupperType(holderAttribute);
            Object modelStr;
            modelStr = getSupperType(holderAttribute);
            if (compound1.values.size() == 2) {
                modelStr = getClassStr(compound1.values.get(1).snd);
            }

            String caseCode = String.format("case %s : return new %s(parent);\n", String.valueOf(j + 1), getClassStr(holderAttribute));
            String modelCodeStr = String.format("if(object instanceof %s){return %s;}", modelStr, String.valueOf(j + 1));
            caseCodeTotalStr.append(caseCode);
            modelTotalStr.append(modelCodeStr);

        }
    }

    private static String getClassStr(Attribute attribute) {
        return attribute.toString().replace(".class", "");
    }

    private static String getCurrentClassPackageName(Symbol symbol) {
        String s = symbol.toString();
        return s.substring(0, s.lastIndexOf("."));
    }

    private Type getSupperType(Attribute holderAttribute) {
        Type.ClassType classType = (Type.ClassType) holderAttribute.type;
        Type.ClassType classType1 = (Type.ClassType) classType.typarams_field.get(0);
        Type.ClassType supertypeField = (Type.ClassType) classType1.supertype_field;
        return supertypeField.typarams_field.get(0);
    }


}