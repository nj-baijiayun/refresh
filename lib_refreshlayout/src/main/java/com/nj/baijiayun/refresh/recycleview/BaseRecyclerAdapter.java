package com.nj.baijiayun.refresh.recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2018/6/8
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "AbsRecycleAdapter";
    private Context context;
    List<T> mItems;
    private int layoutId;
    /**
     * holder 的item Click回调
     */
    private BaseHolderItemViewOnClickListener holderItemViewOnClickListener;
    /**
     * 外部拿到的的itemClick
     */
    public OnItemClickListener<T> onItemClickListener;
    public OnItemLongClickListener<T> onItemLongClickListener;


    /**
     * 设置item 布局的layout
     */
    protected abstract int attachLayoutRes();


    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        mItems = new ArrayList<>();
        this.context = context;
        this.layoutId = attachLayoutRes();
        holderItemViewOnClickListener = new BaseHolderItemViewOnClickListener() {
            @Override
            public void onClick(BaseViewHolder holder, int position, View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder, position, view, getItem(position));
                }
            }

            @Override
            public void onLongClick(BaseViewHolder holder, int position, View view) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(holder, position, view, getItem(position));
                }
            }
        };
    }


    @Override
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = onCreateDefaultViewHolder(parent, viewType);
        if (baseViewHolder == null) {
            throw new NullPointerException("baseViewHolder not allow empty");
        }
        baseViewHolder.convertView.setTag(baseViewHolder);
        if (baseViewHolder.isNeedClickRootItemViewInHolder()) {
            //这个holderItemHolder只是一个封装而已
            baseViewHolder.getConvertView().setOnClickListener(holderItemViewOnClickListener);
        }
        if(baseViewHolder.isNeedLongClickRootItemViewInHolder())
        {
            baseViewHolder.getConvertView().setOnLongClickListener(holderItemViewOnClickListener);
        }


        return baseViewHolder;
    }


    public BaseViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        bindViewAndData(holder, mItems.get(position), position);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<T> getAllItems() {
        return mItems;
    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<T> items) {
        addAll(items, false);
    }

    public void addAll(List<T> items, boolean clearAll) {
        if (clearAll) {
            mItems.clear();
        }
        if (items != null && items.size() > 0) {
            int size = mItems.size();
            mItems.addAll(items);
            if (!clearAll) {
                notifyItemRangeInserted(size, items.size());
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public void addItem(T item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(mItems.size());
        }
    }

    public void addItem(int postion, T item) {
        if (item != null) {
            this.mItems.add(postion, item);
            notifyItemChanged(mItems.size());
        }
    }

    public void removeAll() {
        this.mItems = null;
        notifyDataSetChanged();
    }

    public final List<T> getItems() {
        return mItems;
    }


    public final T getItem(int position) {
        if (position < 0 || position >= mItems.size()) {
            return null;
        }
        return mItems.get(position);
    }

    public final void removeItem(T item) {
        if (this.mItems.contains(item)) {
            int position = mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    protected final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public final void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }


    /**
     * 绑定数据
     *
     * @param holder   holder
     * @param t        数据源
     * @param position 位置
     */
    protected abstract void bindViewAndData(BaseViewHolder holder, T t, int position);


    protected static abstract class BaseHolderItemViewOnClickListener implements View.OnClickListener, View.OnLongClickListener {
        @Override
        public void onClick(View v) {
            BaseViewHolder holder = (BaseViewHolder) v.getTag();
            if (holder != null) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    onClick(holder, adapterPosition, v);
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            BaseViewHolder holder = (BaseViewHolder) v.getTag();
            if (holder != null) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    onLongClick(holder, adapterPosition, v);
                }
            }
            return true;
        }

        public abstract void onClick(BaseViewHolder holder, int position, View view);

        public abstract void onLongClick(BaseViewHolder holder, int position, View view);
    }


    public interface OnItemClickListener<T> {
        void onItemClick(BaseViewHolder holder, int position, View view, T item);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(BaseViewHolder holder, int position, View view, T item);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BaseRecyclerAdapter getThis() {
        return this;
    }


}

