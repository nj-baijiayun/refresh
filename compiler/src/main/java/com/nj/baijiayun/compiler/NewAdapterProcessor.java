package com.nj.baijiayun.compiler;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.compiler.model.GroupProcessorModel;
import com.nj.baijiayun.compiler.model.MultipleModel;
import com.nj.baijiayun.compiler.model.NormalModel;
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
import javax.lang.model.type.TypeMirror;

import static com.nj.baijiayun.compiler.utils.Consts.ANNOTATION_TYPE_ADAPTER_CREATE;
import static com.nj.baijiayun.compiler.utils.Consts.ANNOTATION_TYPE_MODEL_MULTIPLE_HOLDER_CREATE;
/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
@SupportedAnnotationTypes({ANNOTATION_TYPE_ADAPTER_CREATE, ANNOTATION_TYPE_MODEL_MULTIPLE_HOLDER_CREATE})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NewAdapterProcessor extends BaseProcessor {
    private static final String DEFAULT_GROUP = "default";
    private static final String RECYCLE_VIEW_PACKAGE = "com.nj.baijiayun.refresh";

    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Map<String, String> options = processingEnvironment.getOptions();
        moduleName = options.get("moduleName");

    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(AdapterCreate.class);
        Map<String, GroupProcessorModel> groupMap = MultipleTypeModelHelper.getHolderClassArray(roundEnvironment);
        for (Element e : elementsAnnotatedWith) {
            //需要按照group 分组
            AdapterCreate annotation = e.getAnnotation(AdapterCreate.class);

            String[] groups = annotation.group();

            for (String group : groups) {

                if (groupMap.get(group) == null) {
                    GroupProcessorModel v = new GroupProcessorModel();
                    v.setGroup(group);
                    groupMap.put(group, v);
                }

                GroupProcessorModel groupModel = groupMap.get(group);
                TypeElement te = (TypeElement) e;
                //获取了当前的类名
                TypeMirror superclass = te.getSuperclass();
                //获取继承的类的范型
                Type.ClassType classType = (Type.ClassType) superclass;
                List<Type> typeArguments = classType.getTypeArguments();


                NormalModel normalModel = new NormalModel(te.getQualifiedName().toString(), typeArguments.get(0).toString());
                groupModel.getNormalModels().add(normalModel);
            }


        }

        if (!isCreate) {

            Map<String, String> adapterGroupMap = new HashMap<>();
            for (String key : groupMap.keySet()) {
                GroupProcessorModel groupModel = groupMap.get(key);
                createAdapter(createFactory(groupModel), groupModel.getGroup());
                adapterGroupMap.put(groupModel.getGroup(), getPackageName() + "." + getAdapterName(groupModel.getGroup()));
            }

            createAdapterHelper(adapterGroupMap);
            isCreate = true;
        }


        return false;
    }

    private void createAdapterHelper(Map<String, String> adapterGropup) {


        StringBuilder mapCode = new StringBuilder();
        for (String key : adapterGropup.keySet()) {
            mapCode.append(String.format("map.put(\"%s\", \"%s\");", key, adapterGropup.get(key)));
        }

        FieldSpec fieldSpec = FieldSpec.builder(Map.class, "map", Modifier.STATIC, Modifier.FINAL).initializer("new java.util.HashMap()", "").build();


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


    private String createFactory(GroupProcessorModel groupModel) {

        String clsName = getAdapterFactoryName(groupModel.getGroup());
        String superClassName = getClsNameByRvPackage("recycleview.MultipleTypeHolderFactory");
        String returnClassName = getClsNameByRvPackage("recycleview.BaseMultipleTypeViewHolder");
        StringBuilder caseCodeStr = new StringBuilder();
        StringBuilder modelCodeStr = new StringBuilder();

        int index = 0;
        //普通model
        for (int i = 0; i < groupModel.getNormalModels().size(); i++) {
            index++;
            String caseCode = String.format("case %s : return new %s(parent);\n", String.valueOf(index), groupModel.getNormalModels().get(i).getHolderClsName());
            String modelCode = String.format("if(object instanceof %s){return %s;}", groupModel.getNormalModels().get(i).getModelClsName(), String.valueOf(index));
            caseCodeStr.append(caseCode);
            modelCodeStr.append(modelCode);
        }

        //多类型的model


        for (int i = 0; i < groupModel.getMultipleModels().size(); i++) {
            MultipleModel multipleModel = groupModel.getMultipleModels().get(i);
            String modelName = multipleModel.getMultipleModelName();

            String changeModel = String.format("%s model=((%s)%s);", modelName, modelName, "object");
            String modelCode = String.format("if(object instanceof %s){%s return %s+factory%s.holderClsArrayIndex(model);}", modelName, changeModel, String.valueOf(index + 1), i);

            for (int j = 0; j < multipleModel.getMultipleModelHolderName().length; j++) {
                index++;
                String caseCode = String.format("case %s : return new %s(parent);\n", String.valueOf(index), multipleModel.getMultipleModelHolderName()[j]);
                caseCodeStr.append(caseCode);
            }
            modelCodeStr.append(modelCode);

        }


        caseCodeStr = new StringBuilder(String.format(" switch(type){%s}  return null", caseCodeStr));
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


        for (int i = 0; i < groupModel.getMultipleModels().size(); i++) {
            String multipleModelFactoryName = groupModel.getMultipleModels().get(i).getMultipleModelFactoryName();
            FieldSpec fieldSpec = FieldSpec.builder(ClassName.bestGuess(multipleModelFactoryName), String.format("factory%s", String.valueOf(i)), Modifier.PRIVATE, Modifier.FINAL)
                    .initializer("new $L()", multipleModelFactoryName).build();
            factoryClsBuilder.addField(fieldSpec);

        }
        TypeSpec factoryCls = factoryClsBuilder
                .addModifiers(Modifier.PUBLIC)
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


    private String getModuleName() {
        return StringUtils.getStrUpperFirst(moduleName);
    }
}
