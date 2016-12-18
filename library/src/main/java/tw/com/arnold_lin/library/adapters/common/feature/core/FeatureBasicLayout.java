package tw.com.arnold_lin.library.adapters.common.feature.core;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

import tw.com.arnold_lin.library.adapters.common.feature.binders.Binder;


/**
 * Created by arnold_lin on 2015/12/3.
 */
public class FeatureBasicLayout extends FrameLayout implements ViewBinderImp<Binder> {
    private static final String TAG = "FeatureBasicLayout";
    private SparseArray<View> viewBinders = new SparseArray<>();

//    private Map<Class<? extends ViewBinderImp>, View> viewBinders = new HashMap<Class<? extends ViewBinderImp>, View>();

    private Class<? extends ViewBinderImp> currViewBinderCls = null;
    private View currBinderView = null;

    public FeatureBasicLayout(Context context) {
        this(context, null);
    }

    public FeatureBasicLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeatureBasicLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        //在 BaseAdapter 中可能會因 android.widget.LinearLayout$LayoutParams cannot be cast to android.widget.AbsListView$LayoutParams 而 crash
//        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void init() {

    }

    @Override
    public void setBinder(Binder binder) {
        setBinderView(binder);
        if (currBinderView instanceof ViewBinderImp) {
            ((ViewBinderImp) currBinderView).init();
            ((ViewBinderImp) currBinderView).setBinder(binder);
        }
    }

    @Override
    public View getView() {
        return currBinderView;
    }

    private void setBinderView(Binder binder) {

        if (binder == null) {
            throw new NullPointerException("Binder cannot be null.");
        }

        Class<? extends ViewBinderImp> viewBinderImpCls = binder.getViewBinderCls();
        if (viewBinderImpCls == null) {
            throw new NullPointerException("ViewBinderImp cannot be null.");
        }

        if (viewBinders.get(viewBinderImpCls.hashCode(), null) == null) {
            //沒有就加入此畫面
            addBinderView(FeatureViewFactory.createViewBinder(getContext(),binder));
            Log.d(TAG, "viewBinderImpCls ("+viewBinderImpCls+") is add to FeatureBasicLayout ("+ hashCode() +")!");
        }

        if (currViewBinderCls == viewBinderImpCls) {
            Log.d(TAG, "currViewBinderCls == viewBinderImpCls");
            return;
        }

        currViewBinderCls = viewBinderImpCls;

        if (currBinderView != null) {
            currBinderView.setVisibility(GONE);
        }

        currBinderView = viewBinders.get(currViewBinderCls.hashCode());
        currBinderView.setVisibility(VISIBLE);
    }

    public void addBinderView(ViewBinderImp binder) {
        if (binder == null) {
            return;
        }

        View view = binder.getView();
        view.setVisibility(GONE);
        addView(view);

        viewBinders.put(binder.getClass().hashCode(), view);
    }

    public View getCurrBinderView() {
        return currBinderView;
    }
}
