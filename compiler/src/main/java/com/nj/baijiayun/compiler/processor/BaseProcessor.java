package com.nj.baijiayun.compiler.processor;

import com.nj.baijiayun.compiler.utils.Logger;
import com.nj.baijiayun.compiler.utils.StringUtils;
import com.squareup.javapoet.JavaFile;

import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import static com.nj.baijiayun.compiler.utils.Consts.KEY_MODULE_NAME;
import static com.nj.baijiayun.compiler.utils.Consts.NO_MODULE_NAME_TIPS;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
public class BaseProcessor extends AbstractProcessor {
    public Logger logger;
    public Filer filer;
    private boolean isFirst = true;
    String moduleName = null;

    public boolean isFirstRun() {
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
        Map<String, String> options = processingEnv.getOptions();
        if (MapUtils.isNotEmpty(options)) {
            moduleName = options.get(KEY_MODULE_NAME);
        }
        if (moduleName != null && moduleName.length() > 0) {
            moduleName = moduleName.replaceAll("[^0-9a-zA-Z_]+", "");

            logger.info("The user has configuration the module name, it was [" + moduleName + "]");
        } else {
            logger.error(NO_MODULE_NAME_TIPS);
            throw new RuntimeException("AdapterCreate::Compiler >>> No module name, for more information, look at gradle log.");
        }

    }


    public void writeToFile(JavaFile javaFile) {
        try {
            javaFile.writeTo(filer);
        } catch (Exception ee) {
            logger.error(ee);
        }

    }

    public String getModuleName() {
        return StringUtils.getStrUpperFirst(moduleName);
    }


}
