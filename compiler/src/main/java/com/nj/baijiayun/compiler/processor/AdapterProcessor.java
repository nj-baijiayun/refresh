package com.nj.baijiayun.compiler.processor;

import com.google.auto.service.AutoService;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.annotations.ModelMultiTypeAdapterCreate;
import com.nj.baijiayun.compiler.model.GroupProcessorModel;
import com.nj.baijiayun.compiler.model.MultipleModel;
import com.nj.baijiayun.compiler.model.NormalModel;
import com.nj.baijiayun.compiler.utils.Consts;
import com.nj.baijiayun.compiler.utils.StringUtils;
import com.nj.baijiayun.compiler.utils.TypeUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static com.nj.baijiayun.compiler.utils.Consts.ANNOTATION_TYPE_ADAPTER_CREATE;
import static com.nj.baijiayun.compiler.utils.Consts.ANNOTATION_TYPE_MODEL_MULTIPLE_HOLDER_CREATE;

/**
 * @author chengang
 * @date 2019-07-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler.processor
 * @describe
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({ANNOTATION_TYPE_MODEL_MULTIPLE_HOLDER_CREATE, ANNOTATION_TYPE_ADAPTER_CREATE})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AdapterProcessor extends BaseProcessor {
    private static final String RECYCLE_VIEW_PACKAGE = "com.nj.baijiayun.refresh.recycleview";
    private static ClassName BASE_MULTI_TYPE_MODEL_FACTORY = ClassName.bestGuess(RECYCLE_VIEW_PACKAGE + ".BaseMultipleTypeModelHolderFactory");
    private static ClassName BASE_MULTI_TYPE_HOLDER = ClassName.bestGuess(RECYCLE_VIEW_PACKAGE + ".BaseMultipleTypeViewHolder");
    private static ClassName BASE_MULTI_TYPE_ADAPTER = ClassName.bestGuess(RECYCLE_VIEW_PACKAGE + ".BaseMultipleTypeRvAdapter");
    private static ClassName MULTI_TYPE_HOLDER_FACTORY_IMPL = ClassName.bestGuess(RECYCLE_VIEW_PACKAGE + ".AbstractMultipleTypeHolderFactoryImpl");
    private static ClassName MULTI_TYPE_MODEL_FACTORY_INTERFACE = ClassName.bestGuess(RECYCLE_VIEW_PACKAGE + ".MultipleTypeHolderFactory");
    private static String PACKAGE_NAME = Consts.PACKAGE;
    private Map<String, GroupProcessorModel> groupMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        logger.info(">>> process, start... <<<");

        if (CollectionUtils.isNotEmpty(annotations)) {
            logger.info(">>> CollectionUtils.isNotEmpty, start... <<<");

            Set<? extends Element> modelMultiTypeElements = roundEnvironment.getElementsAnnotatedWith(ModelMultiTypeAdapterCreate.class);
            logger.info(">>> modelMultiTypeElements, start... <<<");

            Set<? extends Element> holderElements = roundEnvironment.getElementsAnnotatedWith(AdapterCreate.class);
            try {
                logger.info(">>> Found routes, start... <<<");
                this.groupMap.clear();
                this.parseAdapterCreate(holderElements);
                this.parseModelMulti(modelMultiTypeElements);
                this.createCls();

            } catch (Exception e) {
                logger.error(e);
            }
            return true;
        }
        return false;
    }

    private void createCls() {
        logger.info("createClsgroupMap" + groupMap.size());

        Map<String, String> adapterGroupMap = new HashMap<>();
        for (String key : groupMap.keySet()) {
            GroupProcessorModel groupModel = groupMap.get(key);
            logger.info("createClsgroupMap-->" + groupModel.getGroup());

            String adapterClsName = createAdapterCls(createViewTypeFactoryCls(groupModel), groupModel.getGroup());
            adapterGroupMap.put(groupModel.getGroup(), adapterClsName + ".class");
        }

        createAdapterHelper(adapterGroupMap);
    }

    /**
     * 这里主要为了存储了所有的多类型的单model
     */
    private void parseModelMulti(Set<? extends Element> modelMultiTypeElements) {
        Map<String, GroupProcessorModel> groupMap = this.groupMap;
        for (Element e : modelMultiTypeElements) {
            TypeElement te = (TypeElement) e;
            //获取了当前的类名
            if (!TypeUtils.isSuperClassContains(te, BASE_MULTI_TYPE_MODEL_FACTORY)) {
                continue;
            }
            String realClassType = TypeUtils.getTopSuperClsType(te, BASE_MULTI_TYPE_MODEL_FACTORY);
            ModelMultiTypeAdapterCreate annotation = e.getAnnotation(ModelMultiTypeAdapterCreate.class);
            for (String group : annotation.group()) {
                MultipleModel multipleModel = new MultipleModel();
                multipleModel.setMultipleModelFactoryName(te.getQualifiedName().toString());
                multipleModel.setMultipleModelName(realClassType);
                if (groupMap.get(group) == null) {
                    GroupProcessorModel groupModel = new GroupProcessorModel(group);
                    groupModel.getMultipleModels().add(multipleModel);
                    groupMap.put(group, groupModel);
                } else {
                    groupMap.get(group).getMultipleModels().add(multipleModel);
                }
            }


        }

    }


    private void parseAdapterCreate(Set<? extends Element> holderElements) {
        Map<String, GroupProcessorModel> groupMap = this.groupMap;

        for (Element e : holderElements) {
            AdapterCreate annotation = e.getAnnotation(AdapterCreate.class);

            String[] groups = annotation.group();

            for (String group : groups) {
                GroupProcessorModel groupModel = groupMap.get(group);
                if (groupModel == null) {
                    GroupProcessorModel v = new GroupProcessorModel(group);
                    groupMap.put(group, v);
                }
                groupModel = groupMap.get(group);

                //获取了当前的类名
                TypeElement te = (TypeElement) e;
                groupModel.getNormalModels().add(new NormalModel(((TypeElement) e).getQualifiedName().toString(), TypeUtils.getTopSuperClsType(te, BASE_MULTI_TYPE_HOLDER)));
            }
        }
    }

    private String createAdapterCls(String factoryName, String group) {

        String clsName = getClsNameByGroup(group, "Adapter");
        MethodSpec constructMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.bestGuess("android.content.Context"), "context")
                .addStatement("super($L)", "context").build();
        MethodSpec createTypeFactoryMethod = MethodSpec.methodBuilder("createTypeFactory")
                .addModifiers(Modifier.PUBLIC)
                .returns(MULTI_TYPE_MODEL_FACTORY_INTERFACE)
                .addAnnotation(Override.class)
                .addStatement("return new $T()", ClassName.bestGuess(factoryName)).build();

        TypeSpec adapterClass = TypeSpec.classBuilder(clsName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(BASE_MULTI_TYPE_ADAPTER)
                .addMethod(constructMethod)
                .addMethod(createTypeFactoryMethod)
                .build();

        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, adapterClass)
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (Exception ee) {
            logger.error(ee);
        }

        return PACKAGE_NAME + "." + clsName;


    }

    private String createViewTypeFactoryCls(GroupProcessorModel groupProcessorModel) {


        String createFactoryCls = getClsNameByGroup(groupProcessorModel.getGroup(), "Factory");
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(createFactoryCls)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        classBuilder.superclass(MULTI_TYPE_HOLDER_FACTORY_IMPL);

        if (groupProcessorModel.getMultipleModels().size() > 0) {

            MethodSpec.Builder getViewTypeBuilder = MethodSpec.methodBuilder("getViewType");
            getViewTypeBuilder.addModifiers(Modifier.PUBLIC)
                    .returns(int.class)
                    .addAnnotation(Override.class)
                    .addParameter(Object.class, "object");

            //model多类型
            for (int i = 0; i < groupProcessorModel.getMultipleModels().size(); i++) {
                MultipleModel multipleModel = groupProcessorModel.getMultipleModels().get(i);
                String multipleModelFactoryName = multipleModel.getMultipleModelFactoryName();

                //构造factory对象
                FieldSpec fieldSpec = FieldSpec.builder(ClassName.bestGuess(multipleModelFactoryName), "factory" + i, Modifier.PRIVATE, Modifier.FINAL)
                        .initializer("new $T()", ClassName.bestGuess(multipleModelFactoryName)).build();
                classBuilder.addField(fieldSpec);
            }

            for (int i = 0; i < groupProcessorModel.getMultipleModels().size(); i++) {
                MultipleModel multipleModel = groupProcessorModel.getMultipleModels().get(i);
                logger.info("--->" + multipleModel.getMultipleModelName());
                getViewTypeBuilder.addStatement("if(object instanceof $T)\n return getHolderClassType($L.getMultipleTypeHolderClass(($T)object))",
                        ClassName.bestGuess(multipleModel.getMultipleModelName()),
                        "factory" + i,
                        ClassName.bestGuess(multipleModel.getMultipleModelName()));

            }
            //重写getViewType 方法
            getViewTypeBuilder.addStatement(CodeBlock.builder().add("return super.getViewType(object)").build());
            classBuilder.addMethod(getViewTypeBuilder.build());
        }


        //添加初始化代码
        MethodSpec.Builder initHashMapDataBuilder = MethodSpec.methodBuilder("initModelHolderMapData")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addAnnotation(Override.class);
        for (int i = 0; i < groupProcessorModel.getNormalModels().size(); i++) {
            NormalModel normalModel = groupProcessorModel.getNormalModels().get(i);
            //有可能一个group下没有默认的
            if (normalModel.getModelClsName() != null && normalModel.getModelClsName().length() > 0) {
                initHashMapDataBuilder.addStatement("$L"
                        , CodeBlock.of("modelHolderMap.put($T.class,$T.class)"
                                , ClassName.bestGuess(normalModel.getModelClsName())
                                , ClassName.bestGuess(normalModel.getHolderClsName())));
            }
        }

        MethodSpec.Builder getHolderMapDefaultSizeBuilder = MethodSpec.methodBuilder("getHolderMapDefaultSize")
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addAnnotation(Override.class)
                .addStatement("return $L", groupProcessorModel.getNormalModels().size());

        classBuilder.
                addMethod(getHolderMapDefaultSizeBuilder.build())
                .addMethod(initHashMapDataBuilder.build());
        writeToFile(JavaFile.builder(PACKAGE_NAME, classBuilder.build()).build());
        return PACKAGE_NAME + "." + createFactoryCls;

    }


    /**
     * test---> A B C
     * ---> D
     */


    private void createAdapterHelper(Map<String, String> adapterGroupMap) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(getClsName("AdapterHelper"));

        StringBuilder mapCode = new StringBuilder();
        for (String key : adapterGroupMap.keySet()) {
            mapCode.append(String.format("map.put(\"%s\", %s);\n", key, adapterGroupMap.get(key)));
        }


        FieldSpec fieldSpec = FieldSpec.builder(Map.class, "map", Modifier.STATIC, Modifier.FINAL).initializer("new java.util.HashMap<>()", "").build();


        String methodCode = "\n" +
                " try {\n" +
                "   Class<?> cls = (Class<?>) map.get(group);\n" +
                "   java.lang.reflect.Constructor<?> constructor = cls.getConstructor(Context.class);\n" +
                "   return (BaseMultipleTypeRvAdapter) constructor.newInstance(context);\n" +
                " } catch (Exception e) {\n" +
                "     e.printStackTrace();\n" +
                " }\n" +
                "\n" +
                "return null";

        MethodSpec getAdapter = MethodSpec.methodBuilder("getAdapter")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(BASE_MULTI_TYPE_ADAPTER)
                .addParameter(ClassName.bestGuess("android.content.Context"), "context")
                .addParameter(String.class, "group")
                .addStatement(methodCode, "")
                .build();

        for (String key : adapterGroupMap.keySet()) {

            MethodSpec getAdapterNoGroup = MethodSpec.methodBuilder(MessageFormat.format("get{0}Adapter", StringUtils.getStrUpperFirst(key)))
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(BASE_MULTI_TYPE_ADAPTER)
                    .addParameter(ClassName.bestGuess("android.content.Context"), "context")
                    .addStatement("return getAdapter(context,$S)", key)
                    .build();
            classBuilder.addMethod(getAdapterNoGroup);

        }
        TypeSpec adapterHelper = classBuilder
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldSpec)
                .addMethod(getAdapter)
                .addStaticBlock(CodeBlock.of("$L", mapCode))
                .build();

        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, adapterHelper)
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (Exception ee) {
            logger.error(ee);
        }

    }


    private String getClsNameByGroup(String group, String name) {
        return getModuleName() + StringUtils.getStrUpperFirst(group) + name;
    }

    private String getClsName(String name) {
        return getModuleName() + name;
    }


}
