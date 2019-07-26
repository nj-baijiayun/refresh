package com.nj.baijiayun.compiler;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.compiler.model.GroupProcessorModel;
import com.nj.baijiayun.compiler.model.MultipleModel;
import com.nj.baijiayun.compiler.model.NormalModel;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static com.nj.baijiayun.compiler.utils.Consts.ANNOTATION_TYPE_ADAPTER_CREATE;
import static com.nj.baijiayun.compiler.utils.Consts.ANNOTATION_TYPE_MODEL_MULTIPLE_HOLDER_CREATE;
import static com.nj.baijiayun.compiler.utils.Consts.ANNOTATION_TYPE_MODEL_TYPE_HOLDER_CREATE;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
@SupportedAnnotationTypes({ANNOTATION_TYPE_ADAPTER_CREATE, ANNOTATION_TYPE_MODEL_MULTIPLE_HOLDER_CREATE, ANNOTATION_TYPE_MODEL_TYPE_HOLDER_CREATE})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BestAdapterProcessor extends BaseProcessor {
    private static final String DEFAULT_GROUP = "default";
    private static final String RECYCLE_VIEW_PACKAGE = "com.nj.baijiayun.refresh.recycleview";


    private ClassName baseMultipleTypeViewHolder=ClassName.bestGuess(RECYCLE_VIEW_PACKAGE+".BaseMultipleTypeViewHolder");

    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Map<String, String> options = processingEnvironment.getOptions();
        moduleName = options.get("moduleName");

    }


    public static String getRealClassType(TypeElement te,ClassName className) {
        Type.ClassType classType = (Type.ClassType) te.getSuperclass();

        while (classType != null && !classType.toString().contains(className.toString())) {
            classType = (Type.ClassType) classType.supertype_field;

        }
        return classType.getTypeArguments().get(0).toString();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Map<String, GroupProcessorModel> groupMap = BestTypeModelHelper.getHolderClassArray(roundEnvironment);
        java.util.List<String> holderClsName = BestTypeModelHelper.getModelTypeHolderClsName(roundEnvironment);

        int multipleTypeSize = holderClsName.size();
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(AdapterCreate.class);

        for (Element e : elementsAnnotatedWith) {
            //需要按照group 分组
            AdapterCreate annotation = e.getAnnotation(AdapterCreate.class);

            String[] groups = annotation.group();

            for (String group : groups) {

                if (groupMap.get(group) == null) {
                    GroupProcessorModel v = new GroupProcessorModel(group);
//                    v.setGroup(group);
                    groupMap.put(group, v);
                }

                GroupProcessorModel groupModel = groupMap.get(group);
                //获取了当前的类名
                TypeElement te = (TypeElement) e;




                holderClsName.add(te.getQualifiedName().toString());
                NormalModel normalModel = new NormalModel(te.getQualifiedName().toString(),getRealClassType(te,baseMultipleTypeViewHolder));
                groupModel.getNormalModels().add(normalModel);
            }


        }

        if (!isCreate) {
            String modelHolderRegisterFactory = createModelHolderRegisterFactory(holderClsName, multipleTypeSize);

            Map<String, String> adapterGroupMap = new HashMap<>();
            for (String key : groupMap.keySet()) {
                GroupProcessorModel groupModel = groupMap.get(key);
                createAdapter(createViewTypeFactory(groupModel, holderClsName, multipleTypeSize, modelHolderRegisterFactory), groupModel.getGroup());
                adapterGroupMap.put(groupModel.getGroup(), getPackageName() + "." + getAdapterName(groupModel.getGroup())+".class");
            }

            createAdapterHelper(adapterGroupMap);
            isCreate = true;
        }


        return false;
    }

    private void createAdapterHelper(Map<String, String> adapterGropup) {


        StringBuilder mapCode = new StringBuilder();
        for (String key : adapterGropup.keySet()) {
            mapCode.append(String.format("map.put(\"%s\", %s);", key, adapterGropup.get(key)));
        }

        FieldSpec fieldSpec = FieldSpec.builder(Map.class, "map", Modifier.STATIC, Modifier.FINAL).initializer("new java.util.HashMap()", "").build();


        String getAdapterReturnName = getClsNameByRvPackage("BaseMultipleTypeRvAdapter");


        String methodCode = "\n" +
                "        try {\n" +
                "            Class<?> aClass = (Class<?>) map.get(group);\n" +
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
                .addStatement("return getAdapter(context,$S)", "default")
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
        try {
            javaFile.writeTo(filer);
        } catch (Exception ee) {
            logger.error(ee);
        }

    }

    private void writeToFile(TypeSpec typeSpec) {
        JavaFile javaFile = JavaFile.builder(getPackageName(), typeSpec)
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (Exception ee) {
            logger.error(ee);
        }
    }

    private String createViewTypeFactory(GroupProcessorModel groupModel, java.util.List<String> holderClsNames, int size, String modelHolderRegisterFactory) {

        String clsName = getAdapterFactoryName(groupModel.getGroup());
        String superClassName = getClsNameByRvPackage("MultipleTypeHolderFactory");
        String returnClassName = getClsNameByRvPackage("BaseMultipleTypeViewHolder");
        StringBuilder caseCodeStr = new StringBuilder();
        StringBuilder modelCodeStr = new StringBuilder();


        for (int i = 0; i < holderClsNames.size(); i++) {
            System.out.println("all--->" + holderClsNames.get(i));
        }
        //普通model
        for (int i = 0; i < groupModel.getNormalModels().size(); i++) {

            System.out.println("holderClsNames--->" + groupModel.getNormalModels().get(i).getHolderClsName());
            int type = holderClsNames.indexOf(groupModel.getNormalModels().get(i).getHolderClsName()) + 1;
            String caseCode = String.format("case %s : return new %s(parent);\n", String.valueOf(type), groupModel.getNormalModels().get(i).getHolderClsName());
            String modelCode = String.format("if(object instanceof %s){return %s;}", groupModel.getNormalModels().get(i).getModelClsName(), String.valueOf(type));
            caseCodeStr.append(caseCode);
            modelCodeStr.append(modelCode);
        }

        //多类型的model

        //switch 只针对单个绑定的
        caseCodeStr = new StringBuilder(String.format(" switch(type){%s}", caseCodeStr));


        for (int i = 0; i < groupModel.getMultipleModels().size(); i++) {
            MultipleModel multipleModel = groupModel.getMultipleModels().get(i);
            String modelName = multipleModel.getMultipleModelName();
            String changeModel = String.format("%s model=((%s)%s);", modelName, modelName, "object");
            String modelCode = String.format("if(object instanceof %s){%s return %s.modelMultipleHolderTypeMap.get(factory%s.getMultipleTypeHolderClass(model));}", modelName, changeModel, modelHolderRegisterFactory, i);
            modelCodeStr.append(modelCode);

        }
        if (groupModel.getMultipleModels().size() > 0) {
            String caseCode = String.format(" try {\n" +
                    "            Class<? extends BaseMultipleTypeViewHolder> cls  = %s.modelMultipleTypeHolderMap.get(type);\n" +
                    "            return cls.getConstructor(parent.getClass()).newInstance(parent);\n" +
                    "        } catch (Exception e) {\n" +
                    "            e.printStackTrace();\n" +
                    "        }", modelHolderRegisterFactory);
            caseCodeStr.append(caseCode);

        }


        caseCodeStr.append("return null");

        modelCodeStr.append("return 0");


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
        TypeSpec.Builder factoryClsBuilder = TypeSpec.classBuilder(clsName);


        //构建factory
        for (int i = 0; i < groupModel.getMultipleModels().size(); i++) {
            String multipleModelFactoryName = groupModel.getMultipleModels().get(i).getMultipleModelFactoryName();
            FieldSpec fieldSpec = FieldSpec.builder(ClassName.bestGuess(multipleModelFactoryName), String.format("factory%s", String.valueOf(i)), Modifier.PRIVATE, Modifier.FINAL)
                    .initializer("new $L()", multipleModelFactoryName).build();
            factoryClsBuilder.addField(fieldSpec);

        }


        TypeSpec.Builder factoryBuilder = factoryClsBuilder
                .addModifiers(Modifier.PUBLIC);

        TypeSpec factoryCls = factoryBuilder
                .addMethod(getViewType)
                .addMethod(createViewHolder)
                .addSuperinterface(ClassName.bestGuess(superClassName))
                .build();
        JavaFile javaFile = JavaFile.builder(getPackageName(), factoryCls)
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (Exception ee) {
            logger.error(ee);
        }
        return getPackageName() + "." + clsName;


    }

    private boolean isCreate = false;


    private ClassName getOverride() {
        return ClassName.get("java.lang", "Override");
    }

    private String getAdapterName(String group) {
        return getModuleName() + getGroupName(group) + "MultipleAdapter";
    }

    private String getClsName(String name) {
        return getModuleName() + name;
    }


    private String createModelHolderRegisterFactory(java.util.List<String> holderClsNames, int multipleTypeSize) {

        TypeSpec.Builder factoryClsBuilder = TypeSpec.classBuilder(getClsName("ModelHolderRegisterFactory"));

        TypeSpec.Builder factoryBuilder = factoryClsBuilder
                .addModifiers(Modifier.PUBLIC);
        if (multipleTypeSize > 0) {
            FieldSpec modelMultipleTypeHolderMap = FieldSpec.builder(ClassName.bestGuess("Map <Integer, Class<? extends BaseMultipleTypeViewHolder>>"),
                    "modelMultipleTypeHolderMap", Modifier.STATIC, Modifier.FINAL).initializer("new java.util.HashMap()", "").build();

            FieldSpec modelMultipleHolderTypeMap = FieldSpec.builder(ClassName.bestGuess("Map<Class<? extends BaseMultipleTypeViewHolder>, Integer>"),
                    "modelMultipleHolderTypeMap", Modifier.STATIC, Modifier.FINAL).initializer("new java.util.HashMap()", "").build();

            //占位
            factoryClsBuilder.addField(FieldSpec.builder(Map.class, "map").build()).addJavadoc("$L", "map用来占位导包，还不知道如何直接写带有范型map申明的导包，所以用一个map占位导包");
            factoryClsBuilder.addField(modelMultipleTypeHolderMap);
            factoryClsBuilder.addField(FieldSpec.builder(ClassName.bestGuess("com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder"), "test").build());
            factoryClsBuilder.addField(modelMultipleHolderTypeMap);
        } else {
            return "";
        }

        StringBuilder staticBlockStr = new StringBuilder();

        for (int i = 0; i < multipleTypeSize; i++) {
            //添加modelMultipleTypeHolderMap
            staticBlockStr.append(String.format("modelMultipleTypeHolderMap.put(%s, %s);\n", String.valueOf(i + 1), holderClsNames.get(i) + ".class"));
            //add modelMultipleHolderTypeMap
            staticBlockStr.append(String.format("modelMultipleHolderTypeMap.put(%s, %s);\n", holderClsNames.get(i) + ".class", String.valueOf(i + 1)));

        }
        factoryBuilder.addStaticBlock(CodeBlock.of("$L", staticBlockStr));

        TypeSpec factoryCls = factoryBuilder.build();
        writeToFile(factoryCls);

        return getPackageName() + "." + getClsName("ModelHolderRegisterFactory");

    }

    private void createAdapter(String factoryName, String group) {

        String clsName = getAdapterName(group);
        String superClassName = getClsNameByRvPackage("BaseMultipleTypeRvAdapter");

        MethodSpec constructMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.bestGuess("android.content.Context"), "context")
                .addStatement("super($L)", "context").build();

        String returnName = getClsNameByRvPackage("MultipleTypeHolderFactory");

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
        try {
            javaFile.writeTo(filer);
        } catch (Exception ee) {
            logger.error(ee);
        }

    }

    private String getClsNameByRvPackage(String name) {
        return RECYCLE_VIEW_PACKAGE + "." + name;
    }


    private String getAdapterHelperName() {
        return getModuleName() + "AdapterHelper";
    }


    private String getAdapterFactoryName(String group) {
        return getModuleName() + getGroupName(group) + "MultipleTypeFactory";
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
