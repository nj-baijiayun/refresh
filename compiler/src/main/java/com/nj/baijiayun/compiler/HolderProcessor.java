package com.nj.baijiayun.compiler;

import com.nj.baijiayun.annotations.HolderCreate;
import com.nj.baijiayun.compiler.utils.Logger;
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
import com.sun.tools.javac.util.Pair;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;

/**
 * @author chengang
 * @date 2019-07-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.complier
 * @describe
 */
//@AutoService(Processor.class)
@Deprecated
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HolderProcessor extends AbstractProcessor {

    private Logger logger;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(HolderCreate.class.getCanonicalName());
    }

    private Filer filer;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        logger = new Logger(processingEnv.getMessager());


    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(HolderCreate.class)) {
            Scope membersField = ((Symbol.ClassSymbol) e).members_field;
            Scope.Entry elems = membersField.elems;
            StringBuilder caseCodeTotalStr = new StringBuilder();
            StringBuilder modelTotalStr = new StringBuilder();
            Type returnType = null;
            String packageName= getCurrentClassPackageName(membersField.owner);
            while (elems != null) {
                if (elems.sym.type.getKind() == TypeKind.INT) {
                    Symbol.VarSymbol varSymbol = (Symbol.VarSymbol) elems.sym;
                    int constValue = (int) varSymbol.getConstValue();
                    List<Attribute.Compound> declarationAttributes = elems.sym.getMetadata().getDeclarationAttributes();
                    if (declarationAttributes.size() > 0) {
                        Attribute.Compound compound = declarationAttributes.get(0);
                        List<Pair<Symbol.MethodSymbol, Attribute>> values = compound.values;
                        Attribute holderName = values.get(0).snd;
                        String caseCode = String.format("case %s : return new %s(parent);\n", constValue, getClassStr(holderName));
                        caseCodeTotalStr.append(caseCode);
                        if (values.size() >= 2) {
                            Attribute modelType = values.get(1).snd;
                            String modelCodeStr = String.format("if(object instanceof %s){return %s;}", getClassStr(modelType), constValue);
                            modelTotalStr.append(modelCodeStr);
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

    private static String getClassStr(Attribute attribute) {
        return attribute.toString().replace(".class", "");
    }

    private static String getCurrentClassPackageName(Symbol symbol) {
        String s = symbol.toString();
        return s.substring(0,s.lastIndexOf("."));
    }
}