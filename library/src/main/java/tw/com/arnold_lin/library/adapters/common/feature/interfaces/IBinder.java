package tw.com.arnold_lin.library.adapters.common.feature.interfaces;

import java.util.ArrayList;

/**
 * Created by arnold_lin on 2016/4/7.
 */
public interface IBinder<T> {

    void addBinders(ArrayList<? extends T> binders);

    void addBinder(T binder);

    void insertBinders(int pos, ArrayList<? extends T> binders);

    void insertBinder(int pos, T binder);

    void setBinders(ArrayList<? extends T> binders);

    void setBinder(int pos, T binder);

    void setBinder(T binder);

    void removeBinders(ArrayList<? extends T> binders);

    void removeBinder(int pos);

    void removeBinder(T binder);

}
