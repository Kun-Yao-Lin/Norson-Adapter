package tw.com.arnold_lin.library.adapters.recyclerview.wrappers.limitless;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import tw.com.arnold_lin.library.adapters.recyclerview.RecyclerViewPositionHelper;
import tw.com.arnold_lin.library.adapters.recyclerview.wrappers.WrapperAdapterImpl;


/**
 * Created by arnold_lin on 15/7/28.
 */
public class LimitLessListAdapter extends WrapperAdapterImpl {

    public static interface LoadMoreListener {
        public void onLoadMore();
    }

    private final String TAG = getClass().getSimpleName();
    private int dynamicCount = 0;
    private LoadMoreListener loadMoreListener;
    private Context context;
    private ViewGroup parent;

    private int maxVisibleItemCount = 1;

    public LimitLessListAdapter(Context context, RecyclerView.Adapter wrapped) {
        super(wrapped);
        this.context = context;
    }

    @Override
    public int getItemCount() {
//        if(dynamicCount >= wrapped.getItemCount() || dynamicCount <= maxVisibleItemCount){
//            Log.d("getItemCount","dynamicCount >= wrapped.getItemCount() || dynamicCount <= maxVisibleItemCount");
//            Log.d("getItemCount","dynamicCount : "+dynamicCount);
//            dynamicCount = wrapped.getItemCount();
//        }
//        Log.d("getItemCount","getItemCount : "+dynamicCount);
//        return dynamicCount;
        return wrapped.getItemCount();
    }

    private synchronized void addDynamicCount(int number){

        Log.d("addDynamicCount", "-------------------------");
        int maxAmount = super.getItemCount();
        Log.d("addDynamicCount", "DYNAMIC_COUNT " + dynamicCount);
        Log.d("addDynamicCount","maxAmount : "+maxAmount);
        Log.d("addDynamicCount","number : "+number);

        if(dynamicCount == maxAmount){
            Log.d("addDynamicCount","DYNAMIC_COUNT == maxAmount");
//            notifyDataSetChanged();
            return;
        }else if(dynamicCount > maxAmount){
            Log.d("addDynamicCount","DYNAMIC_COUNT > maxAmount");
            dynamicCount = maxAmount;
            return;
        }

        int beforeAdd = dynamicCount;
        int afterAdd = dynamicCount + number;
        if(afterAdd >= maxAmount){
            Log.d("addDynamicCount","afterAdd >= maxAmount");
            dynamicCount = maxAmount;
        }else {
            Log.d("addDynamicCount","afterAdd < maxAmount");
            dynamicCount = afterAdd;
        }
        notifyItemRangeChanged(beforeAdd, dynamicCount);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(this.parent != parent && this.parent == null) {
            this.parent = parent;
            if (this.parent instanceof RecyclerView) {
                RecyclerView recyclerView = ((RecyclerView) this.parent);
                recyclerView.addOnScrollListener(new OnScrollListener());
            }
        }
        return super.onCreateViewHolder(parent, viewType);
    }


    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setDynamicCount(int dynamicCount) {
        if(super.getItemCount() >= dynamicCount) {
            this.dynamicCount = dynamicCount;
        }else {
            Log.w(TAG,"wrapper adapter have to bigger than dynamicCount");
        }

    }

    public void setMaxVisibleItemCount(int maxVisibleItemCount) {
        maxVisibleItemCount++;
        if(maxVisibleItemCount > this.maxVisibleItemCount){
            this.maxVisibleItemCount = maxVisibleItemCount;
        }
        Log.d("addDynamicCount","maxVisibleItemCount :"+this.maxVisibleItemCount);

    }

    /**
     * Created by arnold_lin on 2016/4/14.
     */
    public class OnScrollListener extends RecyclerView.OnScrollListener{
        public  String TAG = "LimitLessRecyclerOnScrollListener";
        int firstVisibleItem, visibleItemCount, totalItemCount;

        RecyclerViewPositionHelper mRecyclerViewHelper;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
            totalItemCount = mRecyclerViewHelper.getItemCount();
            firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
            visibleItemCount = recyclerView.getChildCount();

            setMaxVisibleItemCount(visibleItemCount);
            addDynamicCount(maxVisibleItemCount);

            boolean loadMore = (firstVisibleItem + visibleItemCount + 1) >= (totalItemCount - 2);
            Log.d(TAG,"loadMore :"+loadMore);
            if(loadMore == true && loadMoreListener != null){
                loadMoreListener.onLoadMore();
            }
        }

    }
}
