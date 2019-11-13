### RecycleView 多类型适配器使用


### 一、添加依赖

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
   implementation 'com.nj.baijiayun:annotations:1.0.2'
   annotationProcessor 'com.nj.baijiayun:compiler:1.0.2'
}

```
### 二、添加注解


1. 一个model对应一种holder  给Holder设置注解


```
//group 不写默认为default
//group属性用于归类holder到adapter,存在多少种group,会创建多少adapter,
@AdapterCreate(group="test")
public class DemoHolder extends BaseMultipleTypeViewHolder<DemoBean> {
 
}

```


2. 一个model对应多个holder

新建Factory 类继承BaseMultipleTypeModelHolderFactory,加上@ModelMultiTypeAdapterCreate，返回的holder不要加注解

```
@ModelMultiTypeAdapterCreate
public class MyModelFactory extends BaseMultipleTypeModelHolderFactory<MultipleTypeModel2> {

    @Override
    public Class<? extends BaseMultipleTypeViewHolder> getMultipleTypeHolderClass(MultipleTypeModel2 model) {
            if(model.getType()==1){
                  return TestHolder1.class;
              }else {
                  return TestHolder2.class;
              }
    }
}

```

3. 获取adapter

这时候build一下，会生成相关AdapterHelper类，分group的adapter 也会生成对应的方法
例如：group="test" 会生成 getTestAdapter()


```
BaseMultipleTypeRvAdapter demoAdapter =DemoAdapterHelper.getDefaultAdapter(this);

```


#### 跨module使用

如果基础module 包含一个holder 或这一个model对应多holder的factory,直接采用继承
使用如下:

```
@AdapterCreate
public class TestExtendHolder extends TestNobindHolder {
    public TestExtendHolder(ViewGroup parent) {
        super(parent);
    }
}

```

```
@ModelMultiTypeAdapterCreate
public class MyModelFactory extends ModelNobindFactory {
    
    
    
}
```



#### 注解生成代码的规则

出现多少个group就会生成多少个Adapter跟Factory,
用于holder 归到group对应的Adapter，可以减少一个factory类需要判断太多的model,设置太多的返回类型

- helper类 moduleName+"AdapterHelper"
    工厂方法获取adapter
- adapter类 moduleName+group+"Adapter"
- 类型factory类   moduleName+group+"Factory"




