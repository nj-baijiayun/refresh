package com.nj.baijiayun.compiler.model;

/**
 * @author chengang
 * @date 2019-07-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
public class NormalModel {

    private String holderClsName;
    private String modelClsName;

    public NormalModel(String holderClsName, String modelClsName) {
        this.holderClsName = holderClsName;
        this.modelClsName = modelClsName;
    }

    public String getHolderClsName() {
        return holderClsName;
    }

    public void setHolderClsName(String holderClsName) {
        this.holderClsName = holderClsName;
    }

    public String getModelClsName() {
        return modelClsName;
    }

    public void setModelClsName(String modelClsName) {
        this.modelClsName = modelClsName;
    }
}
