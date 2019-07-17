package com.nj.baijiayun.compiler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.compiler
 * @describe
 */
public class GroupProcessorModel {

    private String group;
    private List<NormalModel> normalModels;
    private List<MultipleModel> multipleModels;

    public List<MultipleModel> getMultipleModels() {
        if (multipleModels == null) {
            multipleModels = new ArrayList<>();
        }
        return multipleModels;
    }

    public void setMultipleModels(List<MultipleModel> multipleModels) {
        this.multipleModels = multipleModels;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<NormalModel> getNormalModels() {
        if (normalModels == null) {
            normalModels = new ArrayList<>();
        }
        return normalModels;
    }

    public void setNormalModels(List<NormalModel> normalModels) {
        this.normalModels = normalModels;
    }
}
