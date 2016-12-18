package tw.com.arnold_lin.library.adapters;

import android.view.View;

import tw.com.arnold_lin.library.adapters.common.feature.binders.Binder;

/**
 * Created by arnold_lin on 2016/4/22.
 */
public interface OnItemClickListener<T extends Binder> {
    void onItemClick(View v, T binder);
}
