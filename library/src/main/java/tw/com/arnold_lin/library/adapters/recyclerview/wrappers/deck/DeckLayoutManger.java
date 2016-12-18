package tw.com.arnold_lin.library.adapters.recyclerview.wrappers.deck;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by arnold_lin on 2016/4/21.
 */
public class DeckLayoutManger extends RecyclerView.LayoutManager {
    private final String TAG = "DeckLayoutManger";
    public DeckLayoutManger() {
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        //如果 Adapter 中沒有任何物件，則清理
        int itemCount = getItemCount();
        if(itemCount == 0){
            detachAndScrapAttachedViews(recycler);
            return;
        }

        int childCount = getChildCount();
        if (childCount == 0 && state.isPreLayout()) {
            //Nothing to do during prelayout when empty
            return;
        }

        Rect childFrame = new Rect();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            childFrame.left = getDecoratedLeft(child);
            childFrame.top = getDecoratedTop(child);
            childFrame.right = getDecoratedRight(child);
            childFrame.bottom = getDecoratedBottom(child);

//            if (!Rect.intersects(displayFrame, childFrame)) {
//                self.getState().itemsAttached.put(self.getPosition(child), false);
//                removeAndRecycleView(child, recycler);
//            }
        }

        final int h = 300;
        final int w = 300;
        int accumulationW = 0;
        int accumulationH = 0;
        for(int i = 0 ; i < itemCount ; i ++){
            View scrap = recycler.getViewForPosition(i);
            scrap.getMeasuredHeight();
            int unit = (i + 1);
            int accumulationW_ = w * unit;
            int accumulationH_ = h * unit;

            measureChildWithMargins(scrap, 0, 0);
            addView(scrap);


            layoutDecorated(scrap,
                    0,
                    0,
                    scrap.getMeasuredWidth(),
                    scrap.getMeasuredHeight()); // Important！布局到RecyclerView容器中，所有的计算都是为了得出任意position的item的边界来布局

            accumulationH = accumulationH_;
            accumulationW = accumulationW_;
        }

    }

}
