介绍recycleview适配器跟holder的使用

## Recycleview适配器使用

设置点击事件
```
//

  demoAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, View view, Object item) {
                
            }
        });

```

设置长按

```  
demoAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(BaseViewHolder holder, int position, View view, Object item) {
                
            }
        });

```



## Holder的使用


基本使用就是实现bindLayout()返回一个布局id,
实现bindData方法

```
public class DemoHolder extends BaseMultipleTypeViewHolder<DemoBean> {
    public DemoHolder(ViewGroup parent) {
        super(parent);
        
    }
    @Override
    public int bindLayout() {
        return R.layout.item_holder_1;
    }

    @Override
    public void bindData(DemoBean model, int position, BaseRecyclerAdapter adapter) {
        //内置了很多快捷设置value的方法
        setText(R.id.tv1,model.getTitle());
        //没有内置的方法,则例如这样
        ((TextView)getView(R.id.tv1)).setText(model.getTitle());

    }
}

```

设置点击事件，长按事件，在holder内执行


```
//  尽量把点击事件写在构造方法里面，不要在bindData调用
  public DemoHolder(ViewGroup parent) {
        super(parent);
        setOnClickListener(R.id.tv1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取点击的位置
                int clickPosition = getClickPosition();
                //获取点击位置所在的model
                DemoBean clickModel = getClickModel();


            }
        });
        setOnLongClickListener(R.id.tv1, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //以上同理
                return true;
            }
        });

    }
```



设置点击事件，长按事件回调到adapter

```
//如果点击事件不想在holder处理，可回调到adapter
  setOnClickListener(R.id.tv1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemInnerViewClickCallBack(v);
            }
        });
        setOnLongClickListener(R.id.tv1, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemInnerViewLongClickCallBack(v);
                //以上同理
                return true;
            }
        });
```

在holder设置的话，根据view的Id作为区分

```
   demoAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, View view, Object item) {

                if (view.getId() == R.id.tv1) {
                    //doSomething
                }else {
                    //doElse
                }

            }
        });
```


如果不想item根的点击事件在adapter设置的回调执行，需要在holder里面配置以下属性

```
   
    //长按在holder里面执行
    @Override
    public boolean isNeedLongClickRootItemViewInHolder() {
        return true;
    }

    //点击在holder里面执行
    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

```



