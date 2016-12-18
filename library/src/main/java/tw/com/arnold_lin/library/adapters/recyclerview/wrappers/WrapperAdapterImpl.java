package tw.com.arnold_lin.library.adapters.recyclerview.wrappers;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by arnold_lin on 15/7/27.
 */
public class WrapperAdapterImpl<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected RecyclerView.Adapter<VH> wrapped;
    public WrapperAdapterImpl(RecyclerView.Adapter wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int getItemViewType(int i) {
        return wrapped.getItemViewType(i);
    }

    @Override
    public int getItemCount() {
        return wrapped.getItemCount();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return wrapped.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        wrapped.onBindViewHolder(holder, position);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        wrapped.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        wrapped.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        wrapped.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        wrapped.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        wrapped.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(VH holder) {
        wrapped.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(VH holder) {
        wrapped.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        wrapped.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public long getItemId(int position) {
        return wrapped.getItemId(position);
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        return wrapped.onFailedToRecycleView(holder);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        wrapped.setHasStableIds(hasStableIds);
    }
}
