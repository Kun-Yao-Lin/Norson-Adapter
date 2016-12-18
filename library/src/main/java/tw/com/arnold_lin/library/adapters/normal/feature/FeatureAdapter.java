package tw.com.arnold_lin.library.adapters.normal.feature;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import tw.com.arnold_lin.library.adapters.common.feature.binders.Binder;
import tw.com.arnold_lin.library.adapters.common.feature.core.FeatureBasicLayout;
import tw.com.arnold_lin.library.adapters.common.feature.interfaces.IBinder;


/**
 * Created by arnold_lin on 2015/11/17.
 */
public class FeatureAdapter extends BaseAdapter implements IBinder<Binder> {
    private final String TAG = "FeatureAdapter";
    private final Context context;
    private final List<Binder> binders = new ArrayList<Binder>();

    public FeatureAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return binders.size();
    }

    @Override
    public Object getItem(int position) {
        return binders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void setBinder(int pos, Binder binders) {
        if (binders == null || this.binders.size() <= pos) {
            return;
        }
        this.binders.set(pos, binders);
        notifyDataSetChanged();
    }

    @Override
    public void setBinder(Binder binder) {

    }

    @Override
    public void addBinder(Binder binder) {

    }

    @Override
    public void setBinders(ArrayList<? extends Binder> binders) {
        if (binders == null) {
            return;
        }
        Log.d(TAG,"binders size : "+binders.size());
        this.binders.clear();
        this.binders.addAll(binders);
        notifyDataSetChanged();
    }

    @Override
    public void addBinders(ArrayList<? extends Binder> binders) {
        if (binders == null) {
            return;
        }

        this.binders.addAll(binders);
        notifyDataSetChanged();
    }

    @Override
    public void removeBinders(ArrayList<? extends Binder> binders) {
        // TODO: 2016/8/5  
    }

    @Override
    public void removeBinder(int pos) {
        if(pos >= getCount()){
            return;
        }
        this.binders.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public void removeBinder(Binder binder) {
        if(this.binders.contains(binder) == false){
            return;
        }
        int index = this.binders.indexOf(binder);
        this.binders.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public void insertBinders(int pos, ArrayList<? extends Binder> binders) {
        // TODO: 2016/8/2
    }

    @Override
    public void insertBinder(int pos, Binder binder) {
        // TODO: 2016/8/2
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView");
        ViewHolder holder;
        if (convertView == null) {
            FeatureBasicLayout layout = new FeatureBasicLayout(context);
            holder = new ViewHolder();
            holder.item = layout;
            convertView = layout;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Binder binder = binders.get(position);
        binder.setIndex(position);
        holder.item.setBinder(binder);
        Log.d(TAG, "setBinder binder type : " + binder.getClass().getSimpleName());
        return convertView;
    }

    public static class ViewHolder {
        FeatureBasicLayout item;
    }

}
