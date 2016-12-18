package tw.com.arnold_lin.library.adapters.common.feature.core;

import android.view.View;

import tw.com.arnold_lin.library.adapters.common.feature.binders.Binder;


/**
 * Created by arnold_lin on 2015/12/2.
 */
public interface ViewBinderImp<T extends Binder> {
    public void init();

    public void setBinder(T binder);

    public View getView();
}
