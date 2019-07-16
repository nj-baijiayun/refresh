package com.nj.baijiayun.compiler;

import com.nj.baijiayun.compiler.utils.Logger;
import com.squareup.javapoet.JavaFile;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
public class BaseProcessor extends AbstractProcessor {
    Logger logger;
    Filer filer;
    private boolean isFirst = true;

    boolean isFirstRun() {
        if (isFirst) {
            isFirst = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        logger = new Logger(processingEnv.getMessager());


    }

    public void writeToFile(JavaFile javaFile) {
        try {
            javaFile.writeTo(filer);
        } catch (Exception ee) {
            logger.error(ee);
        }

    }


}
