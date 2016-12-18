package tw.com.arnold_lin.library.adapters.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import tw.com.arnold_lin.library.adapters.common.feature.binders.Binder;
import tw.com.arnold_lin.library.adapters.common.feature.core.FeatureBasicLayout;
import tw.com.arnold_lin.library.adapters.common.feature.interfaces.IBinder;


/**
 * Created by arnold_lin on 2015/11/17.
 */
public class GenericFeatureAdapter<T extends GenericFeatureAdapter.ViewHolder> extends RecyclerView.Adapter<T> implements IBinder<Binder> {
    private final String TAG = "FeatureAdapter";
    private final Context context;
    private final List<Binder> binders = new ArrayList<Binder>();
    private final Class<T> cls;
    public GenericFeatureAdapter(Context context) {
        this.context = context;
        cls = (Class<T>)
                ((ParameterizedType) getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    @Override
    public int getItemCount() {
        return binders.size();
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        FeatureBasicLayout layout = new FeatureBasicLayout(context);
        T holder;
        try {
            Constructor<T> constructor = cls.getConstructor(View.class);
            holder = constructor.newInstance(layout);
        } catch (Exception e) {
            throw new RuntimeException("cls : " + cls.getSimpleName() + " is create failed", e);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        Binder binder = binders.get(position);
        binder.setIndex(position);
        holder.item.setBinder(binder);
    }

    @Override
    public void setBinder(int pos, Binder binders) {
        if (binders == null || this.binders.size() <= pos) {
            return;
        }
        this.binders.set(pos, binders);
        notifyDataSetChanged();
//        notifyItemChanged(pos, binders);
    }

    @Override
    public void addBinder(Binder binder) {
        int index = this.binders.size();
        this.binders.add(binder);
        notifyDataSetChanged();
//        notifyItemInserted(index);
    }

    @Override
    public void setBinders(ArrayList<? extends Binder> binders) {
        if (binders == null) {
            return;
        }
        this.binders.clear();
        this.binders.addAll(binders);
        notifyDataSetChanged();
    }

    @Override
    public void setBinder(Binder binder) {
        if (binders == null) {
            return;
        }
        int index = this.binders.indexOf(binder);
        if(index > -1){
            this.binders.set(index, binder);
            notifyDataSetChanged();
        }
    }

    @Override
    public void addBinders(ArrayList<? extends Binder> binders) {
        if (binders == null) {
            return;
        }
        int startPos = this.binders.size();
        this.binders.addAll(binders);
        notifyItemRangeChanged(startPos , binders.size());
    }

    @Override
    public void insertBinder(int pos, Binder binder) {
        if (binder == null || pos < 0 || pos >= this.binders.size()) {
            return;
        }
        this.binders.add(pos, binder);
        notifyDataSetChanged();
    }

    @Override
    public void insertBinders(int pos, ArrayList<? extends Binder> binders) {
        if (binders == null || pos < 0 || pos >= this.binders.size()) {
            return;
        }
        this.binders.addAll(pos, binders);
        Log.d(TAG,"size :"+this.binders.size());
        notifyDataSetChanged();
    }

    @Override
    public void removeBinders(ArrayList<? extends Binder> binders) {
        int startPos = this.binders.indexOf(binders);
        if(startPos <= -1){
            return;
        }
        this.binders.removeAll(binders);
        notifyItemRangeRemoved(startPos, getItemCount());
    }

    @Override
    public void removeBinder(int pos) {
        if(pos >= getItemCount()){
            return;
        }
        this.binders.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, getItemCount());
    }

    @Override
    public void removeBinder(Binder binder) {
        if(this.binders.contains(binder) == false){
            return;
        }
        int index = this.binders.indexOf(binder);
        this.binders.remove(index);
        notifyDataSetChanged();
//        notifyItemRemoved(index);
//        notifyItemRangeChanged(index, getItemCount());
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FeatureBasicLayout item;
        public ViewHolder(View itemView) {
            super(itemView);
            item = (FeatureBasicLayout) itemView;
        }
    }

    public List<Binder> getBinders() {
        return binders;
    }

    public Binder getBinder(int position) {
        return binders.get(position);
    }

}
