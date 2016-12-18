package tw.com.arnold_lin.library.adapters.recyclerview.wrappers.circular;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import tw.com.arnold_lin.library.adapters.recyclerview.RecyclerViewPositionHelper;
import tw.com.arnold_lin.library.adapters.recyclerview.wrappers.WrapperAdapterImpl;


/**
 * Created by arnold_lin on 15/7/28.
 */
public class CircularAdapter extends WrapperAdapterImpl {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView.Adapter wrapped;
    private ViewGroup parent;
    private int count = 0;
    private int maxVisibleItemCount = 0;
    private int curVisibleItemCount = 0;

    public CircularAdapter(RecyclerView.Adapter wrapped) {
        super(wrapped);
        this.wrapped = wrapped;
    }

    @Override
    public int getItemCount() {
        setCount(wrapped.getItemCount());
        return count;
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
        return wrapped.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder : "+position+""+ position % wrapped.getItemCount());
        super.onBindViewHolder(holder, position % wrapped.getItemCount());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position % wrapped.getItemCount(), payloads);
    }

    @Override
    public long getItemId(int position) {
        return wrapped.getItemId(position % wrapped.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        return wrapped.getItemViewType(position % wrapped.getItemCount());
    }

    public void setMaxVisibleItemCount(int maxVisibleItemCount) {
        if(maxVisibleItemCount > this.maxVisibleItemCount){
            this.maxVisibleItemCount = maxVisibleItemCount;
            Log.d(TAG,"maxVisibleItemCount : "+maxVisibleItemCount);
        }

        if(curVisibleItemCount != maxVisibleItemCount){
            Log.d(TAG,"curVisibleItemCount : "+maxVisibleItemCount);
        }
        curVisibleItemCount = maxVisibleItemCount;
    }

    public void setCount(int count) {
        if(this.count == 0){
            this.count = count;
            Log.d(TAG, "this.count == 0");
        }else if(this.count > maxVisibleItemCount - 1 && maxVisibleItemCount != 0){
            Log.d(TAG, "this.count > maxVisibleItemCount");
            this.count = Integer.MAX_VALUE;
        }
    }

    /**
     * Created by arnold_lin on 2016/4/14.
     */
    public class OnScrollListener extends RecyclerView.OnScrollListener{
        int firstVisibleItem, visibleItemCount, totalItemCount;
        RecyclerViewPositionHelper mRecyclerViewHelper;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mRecyclerViewHelper.getItemCount();
            firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();

            setMaxVisibleItemCount(visibleItemCount);
        }

    }
}
