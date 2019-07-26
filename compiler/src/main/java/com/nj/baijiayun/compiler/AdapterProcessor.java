package com.nj.baijiayun.compiler;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.compiler.model.GroupModel;
import com.nj.baijiayun.compiler.processor.BaseProcessor;
import com.nj.baijiayun.compiler.utils.Consts;
import com.nj.baijiayun.compiler.utils.StringUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.util.List;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AdapterProcessor extends BaseProcessor {

    private static final String DEFAULT_GROUP = "default";
    private static final String RECYCLE_VIEW_PACKAGE = "com.nj.baijiayun.refresh";
    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Map<String, String> options = processingEnvironment.getOptions();
        try {
            moduleName = options.get("moduleName");
        } catch (Exception ee) {
            logger.error("You need add javaCompileOptions");
        }

    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(AdapterCreate.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(AdapterCreate.class);
        Map<String, GroupModel> groupMap = new HashMap<>();
        for (Element e : elementsAnnotatedWith) {
            AdapterCreate annotation = e.getAnnotation(AdapterCreate.class);
            String[] groups = annotation.group();
            for (String group : groups) {
                GroupModel groupModel = getGroupModel(groupMap, group);
                TypeElement te = (TypeElement) e;
                //获取当前继承的类名
                TypeMirror superclass = te.getSuperclass();
                //获取继承的类的范型
                Type.ClassType classType = (Type.ClassType) superclass;
                List<Type> typeArguments = classType.getTypeArguments();
                groupModel.getHolderClsName().add(te.getQualifiedName().toString());
                groupModel.getModelClsName().add(typeArguments.get(0).toString());
            }

        }

        if (isFirstRun()) {
            Map<String, String> adapterGroupMap = new HashMap<>();
            for (String key : groupMap.keySet()) {
                GroupModel groupModel = groupMap.get(key);
                adapterGroupMap.put(groupModel.getGroup(), getPackageName() + "." + getAdapterName(groupModel.getGroup()));
                createAdapter(createFactory(groupModel.getHolderClsName(), groupModel.getModelClsName(), groupModel.getGroup()), groupModel.getGroup());
            }
            createAdapterHelper(adapterGroupMap);
        }


        return false;
    }

    private GroupModel getGroupModel(Map<String, GroupModel> groupMap, String group) {
        if (group == null) {
            group = DEFAULT_GROUP;
        }
        if (groupMap.get(group) == null) {
            GroupModel v = new GroupModel();
            v.setGroup(group);
            groupMap.put(group, v);
        }
        return groupMap.get(group);
    }


    private void createAdapterHelper(Map<String, String> adapterGroup) {
        StringBuilder mapCode = new StringBuilder();
        for (String key : adapterGroup.keySet()) {
            mapCode.append(String.format("map.put(\"%s\", \"%s\");", key, adapterGroup.get(key)));
        }
        //申明一个map对象并且初始化
        FieldSpec fieldSpec = FieldSpec.builder(Map.class, "map", Modifier.STATIC, Modifier.FINAL)
                .initializer("new java.util.HashMap()", "")
                .build();

        String getAdapterReturnName = getClsNameByRvPackage("recycleview.BaseMultipleTypeRvAdapter");
        String methodCode = "String key = (String) map.get(group);\n" +
                "        try {\n" +
                "            Class<?> aClass = Class.forName(key);\n" +
                "            java.lang.reflect.Constructor<?> constructor = aClass.getConstructor(Context.class);\n" +
                "            return (BaseMultipleTypeRvAdapter) constructor.newInstance(context);\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "        return null";

        MethodSpec getAdapter = MethodSpec.methodBuilder("getAdapter")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(getAdapterReturnName))
                .addParameter(ClassName.bestGuess("android.content.Context"), "context")
                .addParameter(String.class, "group")
                .addStatement(methodCode, "")
                .build();

        MethodSpec getAdapterNoGroup = MethodSpec.methodBuilder("getAdapter")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(getAdapterReturnName))
                .addParameter(ClassName.bestGuess("android.content.Context"), "context")
                .addStatement("return getAdapter(context,$S)", DEFAULT_GROUP)
                .build();

        TypeSpec adapterHelper = TypeSpec.classBuilder(getAdapterHelperName())
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldSpec)
                .addMethod(getAdapter)
                .addMethod(getAdapterNoGroup)
                .addStaticBlock(CodeBlock.of("$L", mapCode))
                .build();

        JavaFile javaFile = JavaFile.builder(getPackageName(), adapterHelper)
                .build();
        writeToFile(javaFile);


    }

    private String getAdapterHelperName() {
        return getModuleName() + "AdapterHelper";
    }


    private String getAdapterFactoryName(String group) {
        return getModuleName() + getGroupName(group) + "MultipleTypeFactory";
    }


    private String createFactory(java.util.List<String> holderClassName, java.util.List<String> modleClass, String group) {
        String clsName = getAdapterFactoryName(group);
        String superClassName = getClsNameByRvPackage("recycleview.MultipleTypeHolderFactory");
        String returnClassName = getClsNameByRvPackage("recycleview.BaseMultipleTypeViewHolder");
        StringBuilder caseCodeStr = new StringBuilder();
        StringBuilder modelCodeStr = new StringBuilder();
        //size 为1的时候不做if 跟switch判断
        if (holderClassName.size() == 1) {
            String caseCode = String.format("return new %s(parent)", holderClassName.get(0));
            String modelCode = String.format("return %s", String.valueOf(1));
            caseCodeStr.append(caseCode);
            modelCodeStr.append(modelCode);
        } else {
            for (int i = 0; i < holderClassName.size(); i++) {
                String caseCode = String.format("case %s : return new %s(parent);\n", String.valueOf(i + 1), holderClassName.get(i));
                String modelCode = String.format("if(object instanceof %s){return %s;}", modleClass.get(i), String.valueOf(i + 1));
                caseCodeStr.append(caseCode);
                modelCodeStr.append(modelCode);
            }
            caseCodeStr = new StringBuilder(String.format(" switch(type){%s}  return null", caseCodeStr));
            modelCodeStr.append("return 0");
        }
        MethodSpec getViewType = MethodSpec.methodBuilder("getViewType")
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addAnnotation(getOverride())
                .addParameter(Object.class, "object")
                .addStatement("$L", modelCodeStr.toString())
                .build();
        MethodSpec createViewHolder = MethodSpec.methodBuilder("createViewHolder")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(getOverride())
                .returns(ClassName.bestGuess(returnClassName))
                .addParameter(ClassName.bestGuess("android.view.ViewGroup"), "parent")
                .addParameter(int.class, "type")
                .addStatement("$L", caseCodeStr.toString())
                .build();
        TypeSpec factoryCls = TypeSpec.classBuilder(clsName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(getViewType)
                .addMethod(createViewHolder)
                .addSuperinterface(ClassName.bestGuess(superClassName))
                .build();
        JavaFile javaFile = JavaFile.builder(getPackageName(), factoryCls)
                .build();
        writeToFile(javaFile);
        return getPackageName() + "." + clsName;
    }


    private void createAdapter(String factoryName, String group) {

        String clsName = getAdapterName(group);
        String superClassName = getClsNameByRvPackage("recycleview.BaseMultipleTypeRvAdapter");

        MethodSpec constructMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.bestGuess("android.content.Context"), "context")
                .addStatement("super($L)", "context").build();

        String returnName = getClsNameByRvPackage("recycleview.MultipleTypeHolderFactory");
        String methodName = "createTypeFactory";

        MethodSpec createTypeFactoryMethod = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(returnName))
                .addAnnotation(getOverride())
                .addStatement("return new $L()", factoryName).build();


        TypeSpec adapterClass = TypeSpec.classBuilder(clsName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(ClassName.bestGuess(superClassName))
                .addMethod(constructMethod)
                .addMethod(createTypeFactoryMethod)
                .build();

        JavaFile javaFile = JavaFile.builder(getPackageName(), adapterClass)
                .build();
        writeToFile(javaFile);
    }

    private ClassName getOverride() {
        return ClassName.get("java.lang", "Override");
    }

    private String getAdapterName(String group) {
        return getModuleName() + getGroupName(group) + "MultipleAdapter";
    }
    private String getClsNameByRvPackage(String name) {
        return RECYCLE_VIEW_PACKAGE + "." + name;
    }

    private String getPackageName() {
        return Consts.PACKAGE;
    }

    private String getGroupName(String group) {
        return StringUtils.getStrUpperFirst(group);
    }

    @Override
    public String getModuleName() {
        return StringUtils.getStrUpperFirst(moduleName);
    }
}
