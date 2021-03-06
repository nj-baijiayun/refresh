package com.nj.baijiayun.refresh.recycleview;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author chengang
 * @date 2019/5/16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.baijiayun.basic.adapter.recycleview2
 * @describe
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    public static final int LINEARLAYOUT = 0;
    public static final int GRIDLAYOUT = 1;
    public static final int STAGGEREDGRIDLAYOUT = 2;

    @IntDef({LINEARLAYOUT, GRIDLAYOUT, STAGGEREDGRIDLAYOUT})
    //表示注解所存活的时间,在运行时,而不会存在. class 文件.
    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutManager {
        public int type() default LINEARLAYOUT;
    }


    private int leftRight;
    private int topBottom;
    /**
     * 头布局个数
     */
    private int headItemCount;
    /**
     * 边距
     */
    private int space;
    /**
     * 时候包含边距 默认包含边缘的边距
     */
    private boolean includeEdge = true;


    /**
     * 烈数
     */
    private int spanCount;

    private @LayoutManager
    int layoutManager;

    private boolean includeFirst = true;

    public SpaceItemDecoration setIncludeFirst(boolean includeFirst) {
        this.includeFirst = includeFirst;
        return this;
    }

    private SpaceItemDecoration() {
    }

    public static SpaceItemDecoration create() {
        return new SpaceItemDecoration();
    }


    public SpaceItemDecoration setLayoutManagerType(int layoutManagerType) {
        this.layoutManager = layoutManagerType;
        return this;
    }

    public SpaceItemDecoration setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        return this;
    }

    public SpaceItemDecoration setHeadItemCount(int headItemCount) {
        this.headItemCount = headItemCount;
        return this;
    }

    public SpaceItemDecoration setIncludeEdge(boolean includeEdge) {
        this.includeEdge = includeEdge;
        return this;
    }

    public SpaceItemDecoration setSpace(int space) {
        this.space = dp2px(space);
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        switch (layoutManager) {
            case LINEARLAYOUT:
                setLinearLayoutSpaceItemDecoration(outRect, view, parent, state);
                break;
            case GRIDLAYOUT:
                GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
                //列数
                spanCount = gridLayoutManager.getSpanCount();
                setNGridLayoutSpaceItemDecoration(outRect, view, parent, state);
                break;
            case STAGGEREDGRIDLAYOUT:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
                //列数
                spanCount = staggeredGridLayoutManager.getSpanCount();
                setNGridLayoutSpaceItemDecoration(outRect, view, parent, state);
                break;
            default:
                break;
        }
    }

    /**
     * LinearLayoutManager spacing
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    private void setLinearLayoutSpaceItemDecoration(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();

        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {

            int position = parent.getChildAdapterPosition(view) - headItemCount;
            if (headItemCount != 0 && position == -headItemCount) {
                return;
            }


            outRect.left = 0;
            outRect.right = 0;

            if (parent.getChildLayoutPosition(view) == (layoutManager.getItemCount() - 1)) {
                if (includeEdge) {
                    outRect.bottom = space;
                } else {
                    outRect.bottom = 0;
                }
            } else {
                outRect.bottom = space;
            }

            if (parent.getChildLayoutPosition(view) == 0) {

                if (includeEdge && includeFirst) {

                    outRect.top = space;
                } else {
                    outRect.top = 0;
                }
            } else {
                outRect.top = 0;
            }
        } else {
            outRect.top = 0;
            outRect.bottom = 0;
//            outRect.right = space;

            if (parent.getChildLayoutPosition(view) == (layoutManager.getItemCount() - 1)) {
                if (includeEdge) {
                    outRect.right = space;
                } else {
                    outRect.right = 0;
                }
            } else {
                outRect.right = space;
            }
            if (parent.getChildLayoutPosition(view) == 0) {
                if (includeEdge && includeFirst) {
                    outRect.left = space;
                } else {
                    outRect.left = 0;
                }
            } else {
                outRect.left = 0;
            }
        }

    }

    /**
     * GridLayoutManager or StaggeredGridLayoutManager spacing
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    private void setNGridLayoutSpaceItemDecoration(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view) - headItemCount;
        if (headItemCount != 0 && position == -headItemCount) {
            return;
        }
        int column = position % spanCount;
        if (includeEdge) {
            outRect.left = space - column * space / spanCount;
            outRect.right = (column + 1) * space / spanCount;
            if (position < spanCount) {
                outRect.top = space;
            }
            outRect.bottom = space;
        } else {
            outRect.left = column * space / spanCount;
            outRect.right = space - (column + 1) * space / spanCount;
            if (position >= spanCount) {
                outRect.top = space;
            }
        }

    }

    /**
     * GridLayoutManager设置间距（此方法最左边和最右边间距为设置的一半）
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    private void setGridLayoutSpaceItemDecoration(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        //判断总的数量是否可以整除
        int totalCount = layoutManager.getItemCount();
        int surplusCount = totalCount % layoutManager.getSpanCount();
        int childPosition = parent.getChildAdapterPosition(view);
        //竖直方向的
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                //后面几项需要bottom
                outRect.bottom = topBottom;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.bottom = topBottom;
            }
            //被整除的需要右边
            if ((childPosition + 1 - headItemCount) % layoutManager.getSpanCount() == 0) {
                //加了右边后最后一列的图就非宽度少一个右边距
                //outRect.right = leftRight;
            }
            outRect.top = topBottom;
            outRect.left = leftRight / 2;
            outRect.right = leftRight / 2;
        } else {
            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                //后面几项需要右边
                outRect.right = leftRight;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.right = leftRight;
            }
            //被整除的需要下边
            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {
                outRect.bottom = topBottom;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
        }
    }

    public static int dp2px(float dpValue) {
        return (int) (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, Resources.getSystem().getDisplayMetrics());
    }


}
