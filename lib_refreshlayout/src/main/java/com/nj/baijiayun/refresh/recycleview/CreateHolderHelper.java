package com.nj.baijiayun.refresh.recycleview;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author chengang
 * @date 2019/4/24
 * @describe desc
 */
@Deprecated
public class CreateHolderHelper {

    private static final HashMap<String, SoftReference> PACKAGE_MAP = new HashMap<>();

    /**
     * 反射根据type 自动创建对应的holder
     *
     * @param cls  class
     * @param type 布局的类型
     * @param viewParent viewParent
     * @return holder
     */
    @SuppressLint("UseSparseArrays")
    public static BaseMultTypeViewHolder autoCreateHolder(Class<?> cls, int type, ViewGroup viewParent) {


        SoftReference<HashMap<Integer, Constructor<? extends BaseMultTypeViewHolder>>>softReference = PACKAGE_MAP.get(cls.getName());
        if (softReference == null||softReference.get()==null) {
            softReference = new SoftReference<HashMap<Integer, Constructor<? extends BaseMultTypeViewHolder>>>(new HashMap());
            PACKAGE_MAP.put(cls.getName(),softReference);
        }


        Constructor<? extends BaseMultTypeViewHolder> constructor = softReference.get().get(type);
        if (constructor != null) {
            try {
                return constructor.newInstance(viewParent);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        for (Field m : cls.getDeclaredFields()) {
            m.setAccessible(true);

            try {
                if (m.get(cls) == null) {
                    continue;
                }
                if (type == (int) m.get(cls)) {
                    TypeHolder annotation = m.getAnnotation(TypeHolder.class);
                    Class<? extends BaseMultTypeViewHolder> value = annotation.value();
                    Constructor<? extends BaseMultTypeViewHolder> valueConstructor = value.getConstructor(ViewGroup.class);
                    softReference.get().put(type, valueConstructor);
                    return valueConstructor.newInstance(viewParent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;

    }

}
