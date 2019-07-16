### RecycleView 多类型适配器使用

介绍2种可选方案
### 一、使用HolderCreate  

1. 在每个module下引入

```
implementation 'com.nj.baijiayun:annotations:{最新的版本}'
annotationProcessor 'com.nj.baijiayun:compiler:{最新的版本}'

```

2. 创建适配器

```
public class DemoAdapter extends BaseMultipleTypeRvAdapter<Object> {

    public DemoAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewTypeFactory createTypeFactory() {
        return new DemoFactory();
    }
}
```
3. 创建holder

```
public class DemoHolder extends BaseMultipleTypeViewHolder<DemoBean> {
    DemoHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_holder_1;
    }

    @Override
    public void bindData(DemoBean model, int position, BaseRecyclerAdapter adapter) {

    }
}

```
4. holder注册到factory,添加@HolderCreate，@TypeHolder

```
@HolderCreate
public class DemoFactory implements ViewTypeFactory {

    //注册就是将 @TypeHolder(holder = DemoHolder.class) 写在getViewType 方法上
    @TypeHolder(holder = DemoHolder.class)
    @TypeHolder(holder = Demo2Holder.class)
    @Override
    public int getViewType(Object object) {
       return 0;
    }

    @Override
    public BaseMultipleTypeViewHolder createViewHolder(ViewGroup parent, int viewType) {
      return null;
    }
}


```
这时候build一下项目会自动生成 DemoFactoryHolderHelper类

生成的类名规则 factory名称+"HolderHelper"

自己补全代码 DemoFactoryHolderHelper
```
@HolderCreate
public class DemoFactory implements MultipleTypeHolderFactory {

    @MultipleTypeHolder(holder = DemoHolder.class)
    @MultipleTypeHolder(holder = Demo2Holder.class)
    @Override
    public int getViewType(Object object) {
        return DemoFactoryHolderHelper.getViewType(object);
    }

    @Override
    public BaseMultipleTypeViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return DemoFactoryHolderHelper.createViewHolder(parent, viewType);
    }
}



```




5. 使用的时候直接拿定义的adapter

```
DemoAdapter adapter=new DemoAdapter(context);

```





### 二、使用AdapterCreate  更加简洁 （推荐）

1. 添加依赖

```
android {
    defaultConfig {
    ...
    javaCompileOptions {
        annotationProcessorOptions {
        arguments = [ moduleName : project.getName() ]
        }
    }
    }
}
dependencies {
   implementation 'com.nj.baijiayun:annotations:{最新的版本}'
   annotationProcessor 'com.nj.baijiayun:compiler:{最新的版本}'
}

```

2. 给Holder设置注解

```
//group 不写默认为default
//group属性用于归类holder到adapter,存在多少种group,会创建多少adapter,
@AdapterCreate(group="test")
public class DemoHolder extends BaseMultipleTypeViewHolder<DemoBean> {
 
}

```

3.获取adapter

这时候build一下，会生成DemoAdapterHelper类

```
BaseMultipleTypeRvAdapter demoAdapter =DemoAdapterHelper.getAdapter(this);
```


#### 使用AdapterCreate 生成代码的规则

出现多少个group就会生成多少个Adapter跟Factory,
用于holder 归到group对应的Adapter，可以减少一个factory类需要判断太多的model,设置太多的返回类型

- helper类 moduleName+"AdapterHelper"
    工厂方法获取adapter
- adapter类 moduleName+group+"MultipleAdapter"
- 类型factory类   moduleName+group+"MultipleTypeFactory"


