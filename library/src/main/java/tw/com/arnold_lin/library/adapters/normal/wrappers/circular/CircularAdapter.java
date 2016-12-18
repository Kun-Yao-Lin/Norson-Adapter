package tw.com.arnold_lin.library.adapters.normal.wrappers.circular;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import tw.com.arnold_lin.library.adapters.normal.wrappers.WrapperBaseAdapterImpl;


/**
 * Created by arnold_lin on 15/7/28.
 */
public class CircularAdapter extends WrapperBaseAdapterImpl implements AbsListView.OnScrollListener {

    private final String TAG = getClass().getSimpleName();
    private BaseAdapter wrapped;
    private ViewGroup parent;
    private int count = 0;
    private int maxVisibleItemCount = 0;
    private int curVisibleItemCount = 0;

    public CircularAdapter(BaseAdapter wrapped) {
        super(wrapped);
        this.wrapped = wrapped;
    }

    @Override
    public int getCount() {
        setCount(wrapped.getCount());
        return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Log.d(TAG,"getView position : "+position);
        if(this.parent != viewGroup && this.parent == null) {
            this.parent = viewGroup;
            if (this.parent instanceof AbsListView) {
                AbsListView absListView = ((AbsListView) parent);
                absListView.setOnScrollListener(this);
            }
        }
        return wrapped.getView(position % wrapped.getCount(), convertView, viewGroup);
    }

    @Override
    public Object getItem(int position) {
        return wrapped.getItem(position % wrapped.getCount());
    }

    @Override
    public long getItemId(int position) {
        return wrapped.getItemId(position % wrapped.getCount());
    }

    @Override
    public int getItemViewType(int position) {
        return wrapped.getItemViewType(position % wrapped.getCount());
    }

    @Override
    public boolean isEnabled(int position) {
        return wrapped.isEnabled(position % wrapped.getCount());
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG,"getDropDownView position : "+position);
        return wrapped.getDropDownView(position % wrapped.getCount(),
                convertView, parent);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        setMaxVisibleItemCount(visibleItemCount);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

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
}
