package com.test.adpter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baijiayun.R;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.test.bean.DemoBean;

/**
 * @author chengang
 * @date 2019-07-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.test
 * @describe
 */
@AdapterCreate(group = {"demo","default"})
public class DemoHolder extends BaseMultipleTypeViewHolder<DemoBean> {
    public DemoHolder(ViewGroup parent) {
        super(parent);
        setOnClickListener(R.id.tv1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemInnerViewClickCallBack(v);
//                Toast.makeText(v.getContext(), "111", Toast.LENGTH_SHORT).show();
            }
        });
        setOnLongClickListener(R.id.tv1, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "222", Toast.LENGTH_SHORT).show();

                //以上同理
                return true;
            }
        });

        setOnClickListener(R.id.ll_root, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "666666", Toast.LENGTH_SHORT).show();
            }
        });

        setOnLongClickListener(R.id.ll_root, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "7777", Toast.LENGTH_SHORT).show();

                return true;
            }
        });


    }
//
//    @Override
//    public boolean isNeedClickRootItemViewInHolder() {
//        return true;
//    }

    @Override
    public boolean isNeedLongClickRootItemViewInHolder() {
        return super.isNeedLongClickRootItemViewInHolder();
    }

    @Override
    public int bindLayout() {
        return R.layout.item_holder_1;
    }

    @Override
    public void bindData(DemoBean model, int position, BaseRecyclerAdapter adapter) {
//        setText(R.id.tv1,model.getTitle());
    }
}
