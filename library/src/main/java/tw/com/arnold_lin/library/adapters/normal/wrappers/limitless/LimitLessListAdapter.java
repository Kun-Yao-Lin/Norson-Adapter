package tw.com.arnold_lin.library.adapters.normal.wrappers.limitless;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import tw.com.arnold_lin.library.adapters.normal.wrappers.WrapperBaseAdapterImpl;


/**
 * Created by arnold_lin on 15/7/28.
 */
public class LimitLessListAdapter extends WrapperBaseAdapterImpl implements AbsListView.OnScrollListener {

    public static interface LoadMoreListener {
        public void onLoadMore();
    }

    private final String TAG = getClass().getSimpleName();
    private int dynamicCount = 0;
    private LoadMoreListener loadMoreListener;
    private Context context;
    private ViewGroup parent;
    private boolean isLoading = false;

    private int maxVisibleItemCount = 0;

    public LimitLessListAdapter(Context context, BaseAdapter wrapped) {
        super(wrapped);
        this.context = context;
    }

    @Override
    public int getCount() {
        if(dynamicCount > wrapped.getCount() || dynamicCount <= maxVisibleItemCount){
            Log.d("addDynamicCount","dynamicCount > wrapped.getCount() || dynamicCount <= maxVisibleItemCount");
            Log.d("addDynamicCount","getCount : "+dynamicCount);
            dynamicCount = wrapped.getCount();
        }
        return dynamicCount;
    }

    private void addDynamicCount(int number){

        int maxAmount =  super.getCount();
        Log.d("addDynamicCount", "DYNAMIC_COUNT " + dynamicCount);
        Log.d("addDynamicCount","maxAmount : "+maxAmount);
        Log.d("addDynamicCount","number : "+number);

        if(dynamicCount == maxAmount){
            Log.d("addDynamicCount","DYNAMIC_COUNT == maxAmount");
            return;
        }else if(dynamicCount > maxAmount){
            Log.d("addDynamicCount","DYNAMIC_COUNT > maxAmount");
            dynamicCount = maxAmount;
            return;
        }
        int afterAdd = dynamicCount + number;
        if(afterAdd > maxAmount){
            Log.d("addDynamicCount","afterAdd > maxAmount");
            dynamicCount = maxAmount;
            notifyDataSetChanged();
            return;
        }else {
            if(dynamicCount == afterAdd){
                Log.d("addDynamicCount","afterAdd = maxAmount");
                return;
            }
            Log.d("addDynamicCount","afterAdd != maxAmount");
            dynamicCount = afterAdd;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(this.parent != viewGroup && this.parent == null) {
            this.parent = viewGroup;
            if (this.parent instanceof AbsListView) {
                if(this.parent instanceof ListView){
                    ListView listView = ((ListView) this.parent);

                }
                AbsListView absListView = ((AbsListView) parent);
                absListView.setOnScrollListener(this);
            }
        }
        return wrapped.getView(position, view, viewGroup);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        setMaxVisibleItemCount(visibleItemCount);
        Log.d("AbsListView Listener", "visibleItemCount : " + visibleItemCount);
        Log.d("AbsListView Listener", "firstVisibleItem : " + firstVisibleItem);
        boolean loadMore = /* maybe add a padding */
                firstVisibleItem + visibleItemCount >= totalItemCount;

        Log.d("AbsListView Listener",""+loadMore);

        if(loadMore) {
            Log.d("AbsListView Listener", "loadMore1");
            addDynamicCount(this.maxVisibleItemCount);
            if(loadMoreListener != null){
                loadMoreListener.onLoadMore();
            }
            Log.d("AbsListView Listener", "loadMore2");
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setDynamicCount(int dynamicCount) {
        if(super.getCount() >= dynamicCount) {
            this.dynamicCount = dynamicCount;
        }else {
            Log.w(TAG,"wrapper adapter have to bigger than dynamicCount");
        }

    }

    public void setMaxVisibleItemCount(int maxVisibleItemCount) {
        if(maxVisibleItemCount > this.maxVisibleItemCount){
            this.maxVisibleItemCount = maxVisibleItemCount;
        }
    }
}
