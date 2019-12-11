package com.nj.baijiayun.refresh.recycleview;

import androidx.recyclerview.widget.RecyclerView;
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

    /**
     * 存type  holder 对应关系
     */
    private SparseArray<Class<? extends RecyclerView.ViewHolder>> typeHolderArray = new SparseArray<>();

    /**
     * bean clsss 对于的holder
     */

    public Map<Class, Class<? extends RecyclerView.ViewHolder>> modelHolderMap;

    /**
     * 根据holder的class取 type
     */
    public int getHolderClassType(Class<? extends RecyclerView.ViewHolder> cls) {
        for (int i = 0; i < typeHolderArray.size(); i++) {
            if (typeHolderArray.get(typeHolderArray.keyAt(i)).equals(cls)) {
                return typeHolderArray.keyAt(i);
            }
        }
        int size = typeHolderArray.size();
        typeHolderArray.put(size, cls);
        return size;
    }

    @Override
    public int getViewType(Object object) {
        //取到model的类 取出对应的holder
        Class<? extends RecyclerView.ViewHolder> aClass = getModelHolderMap().get(object.getClass());
        if (aClass == null) {
            throw new NullPointerException(object.getClass() + " not bind holder");
        }
        return getHolderClassType(aClass);
    }


    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int type) {
        //取出holder class
        Class<? extends RecyclerView.ViewHolder> aClass = typeHolderArray.get(type);
        try {
            return aClass.getConstructor(ViewGroup.class).newInstance(parent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("holder create fail,Please Check your LayoutId or Check Your Constructor Method" + e.getMessage());
        }
    }


    public Map<Class, Class<? extends RecyclerView.ViewHolder>> getModelHolderMap() {
        if (modelHolderMap == null) {
            modelHolderMap = new HashMap<>(getHolderMapDefaultSize() > 0 ? getHolderMapDefaultSize() : 1 << 2);
            initModelHolderMapData();
        }
        return modelHolderMap;
    }

    public abstract int getHolderMapDefaultSize();

    public abstract void initModelHolderMapData();
}
