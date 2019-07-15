package com.nj.baijiayun.compiler;

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
public class GroupModel {

    private String group;
    private List<String> holderClsName;
    private List<String> modelClsName;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getHolderClsName() {
        if(holderClsName==null)
        {
            holderClsName=new ArrayList<>();
        }
        return holderClsName;
    }

    public void setHolderClsName(List<String> holderClsName) {
        this.holderClsName = holderClsName;
    }

    public List<String> getModelClsName() {
        if(modelClsName==null)
        {
            modelClsName=new ArrayList<>();
        }
        return modelClsName;
    }

    public void setModelClsName(List<String> modelClsName) {
        this.modelClsName = modelClsName;
    }
}
