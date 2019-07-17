package com.nj.baijiayun.compiler.model;

/**
 * @author chengang
 * @date 2019-07-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
public class MultipleModel {


    private String multipleModelName;
    private String multipleModelFactoryName;
    //hodler的name
    private String[] multipleModelHolderName;

    public String getMultipleModelFactoryName() {
        return multipleModelFactoryName;
    }

    public void setMultipleModelFactoryName(String multipleModelFactoryName) {
        this.multipleModelFactoryName = multipleModelFactoryName;
    }



    public String[] getMultipleModelHolderName() {
        return multipleModelHolderName;
    }

    public void setMultipleModelHolderName(String[] multipleModelHolderName) {
        this.multipleModelHolderName = multipleModelHolderName;
    }

    public String getMultipleModelName() {
        return multipleModelName;
    }

    public void setMultipleModelName(String multipleModelName) {
        this.multipleModelName = multipleModelName;
    }

}
