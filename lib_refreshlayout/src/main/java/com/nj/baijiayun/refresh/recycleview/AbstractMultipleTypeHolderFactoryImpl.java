package com.nj.baijiayun.refresh.recycleview;

import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengang
 * @date 2019-07-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.refresh.recycleview
 * @describe
 */
public abstract class AbstractMultipleTypeHolderFactoryImpl implements MultipleTypeHolderFactory {

    private SparseArray<Class<? extends BaseMultipleTypeViewHolder>> typeHolderArray = new SparseArray<>();

    public Map<Class, Class<? extends BaseMultipleTypeViewHolder>> modelHolderMap;

    public int getHolderClassType(Class<? extends BaseMultipleTypeViewHolder> cls) {
        for (int i = 0; i < typeHolderArray.size(); i++) {
            int key = typeHolderArray.keyAt(i);
            if (typeHolderArray.get(key).equals(cls)) {
                return key;
            }
        }
        int size = typeHolderArray.size();
        typeHolderArray.put(size, cls);
        return size;
    }

    @Override
    public int getViewType(Object object) {

        Class<? extends BaseMultipleTypeViewHolder> aClass = getModelHolderMap().get(object.getClass());
        if (aClass == null) {
            throw new NullPointerException(object.getClass() + "not bind holder");
        }

        return getHolderClassType(aClass);
    }


    @Override
    public BaseMultipleTypeViewHolder createViewHolder(ViewGroup parent, int type) {
        Class<? extends BaseMultipleTypeViewHolder> aClass = typeHolderArray.get(type);
        try {
            System.out.println("createViewHolder--->" + aClass);

            return aClass.getConstructor(ViewGroup.class).newInstance(parent);
        } catch (Exception e) {

            throw new NullPointerException("holder create fail,Please Check your LayoutId or Check Your Constructor Method" + e.getMessage());
        }
    }

    public Map<Class, Class<? extends BaseMultipleTypeViewHolder>> getModelHolderMap() {
        if (modelHolderMap == null) {
            modelHolderMap = new HashMap<>();
            initModelHolderMapData();
        }
        return modelHolderMap;
    }

    public abstract void initModelHolderMapData();
}
