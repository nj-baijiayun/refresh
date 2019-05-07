使用统一的刷新控件,屏蔽第三方刷新控件，可以自由替换第三方刷新控件

## 快速集成
在工程目标的`build.gradle`文件添加仓库地址
```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = 'http://172.20.2.114:8081/repository/maven-releases/'
        }
    }
}

```
在dependencies下添加依赖
```
dependencies {
    //当前最新版本为1.0.0
    implementation 'com.nj.baijiayun:refresh:1.0.0'
}
```
当前最新版本为1.0.0 [版本说明](./changelog.md)



## 方法说明

```
    //设置加载更多可用
    INxRefreshLayout setEnableLoadMore(boolean enabled)
    //设置刷新可用
    INxRefreshLayout setEnableRefresh(boolean enabled);
     //设置刷新或者加载更多
    INxRefreshLayout setOnRefreshLoadMoreListener(INxOnRefreshListener nxOnRefreshListener);
    //完成刷新
    INxRefreshLayout finishRefresh();
    //完成加载更多
    INxRefreshLayout finishLoadMore();
    //获得Recylview
    getRecyclerView()
    //设置适配器 以下都是Recycleview相关方法
    void setAdapter(RecyclerView.Adapter adaper);
    void setLayoutManager(RecyclerView.LayoutManager layout);
    void addItemDecoration(RecyclerView.ItemDecoration decor);
    void setItemAnimator(RecyclerView.ItemAnimator animator);
    void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup);
    void notifyDataSetChanged();

```



## 使用说明


### 选择配置第三方刷新控件的默认属性

由于本控件是屏蔽第三方的刷新控件，所以初始化配置第三方的额外属性，这个配置设置完，会让所有的地方生效

```java
NxRefreshConfig.init(new DefaultExtra() {
            @Override
            public void setExtra(View view) {
                if (view instanceof SmartRefreshLayout) {
                    Context context = view.getContext();
                    ((SmartRefreshLayout) view).setRefreshHeader(new WaterDropHeader(view.getContext()));
                    ((SmartRefreshLayout) view).setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
                    ((SmartRefreshLayout) view).setEnableFooterTranslationContent(true);
                    ((SmartRefreshLayout) view).setEnableHeaderTranslationContent(true);
                    ((SmartRefreshLayout) view).setPrimaryColorsId(android.R.color.black);
                }
            }
        });
```
### 基本使用


```
//适配器为RecycleView的适配器 默认使用LinearLayoutManager Vertical
 nxRefreshView.setAdapter(adapter);
 nxRefreshView.setOnRefreshLoadMoreListener(new NxOnRefreshListener() {
            @Override
            public void onRefresh(final NxRefreshLayout nxRefreshLayout) {
                nxRefreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(final NxRefreshLayout nxRefreshLayout) {

            }
        });
```


### 如何替换第三方的


继承 NxRefreshView 实现创建刷新view 跟刷新适配的接口
```
public class MyRefresh extends NxRefreshView {
    @Override
    public void initExtra() {
        super.initExtra();
    }

    @Override
    public ViewGroup createRefreshLayoutView() {
        return new SmartRefreshLayout(getContext());
    }

    //实现刷新模板方法 实现传入的刷新类 实现真正的刷新
    @Override
    public INxRefreshLayoutStrategy createStrategy(INxRefreshLayout iNxRefreshLayout, View refreshView) {
        //refreshView 例如smartRefreshLayout
        return new MyStrategy(iNxRefreshLayout,refreshView);
    }
}


```


Strategy 例子

```
public class NxSmartRefreshLayoutStrategy implements INxRefreshLayoutStrategy {


    private SmartRefreshLayout smartRefreshLayout;
    private INxRefreshLayout nxRefreshLayout;


    public NxSmartRefreshLayoutStrategy(INxRefreshLayout nxRefreshLayout, View view) {
        this.nxRefreshLayout = nxRefreshLayout;
        this.smartRefreshLayout = (SmartRefreshLayout) view;

    }

    @Override
    public INxRefreshLayout setEnableLoadMore(boolean enable) {
        this.smartRefreshLayout.setEnableLoadMore(enable);
        return this;
    }

    @Override
    public INxRefreshLayout setEnableRefresh(boolean enable) {
        this.smartRefreshLayout.setEnableRefresh(enable);
        return this;

    }

    @Override
    public INxRefreshLayout setOnRefreshLoadMoreListener(final INxOnRefreshListener nxOnRefreshListener) {
        this.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //务必需要回调出去
                nxOnRefreshListener.onLoadMore(nxRefreshLayout);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //务必需要回调出去
                nxOnRefreshListener.onRefresh(nxRefreshLayout);
            }
        });
        return this;

    }

    @Override
    public INxRefreshLayout finishRefresh() {
        this.smartRefreshLayout.finishRefresh();
        return this;

    }

    @Override
    public INxRefreshLayout finishLoadMore() {
        this.smartRefreshLayout.finishLoadMore();
        return this;
    }


}

```




