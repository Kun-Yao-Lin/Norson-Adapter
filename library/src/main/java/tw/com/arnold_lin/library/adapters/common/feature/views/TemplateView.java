package tw.com.arnold_lin.library.adapters.common.feature.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import tw.com.arnold_lin.library.adapters.common.feature.core.ViewBinderImp;

/**
 * Created by arnold_lin on 2015/12/8.
 */
public class TemplateView extends LinearLayout implements ViewBinderImp<TemplateView.Binder> {

    public TemplateView(Context context) {
        super(context);
        initialize(context);
    }

    public TemplateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {

    }

    @Override
    public void init() {

    }

    @Override
    public void setBinder(TemplateView.Binder binder) {

    }

    @Override
    public View getView() {
        return this;
    }

    public static class Binder extends tw.com.arnold_lin.library.adapters.common.feature.binders.Binder<TemplateView> {
    }
}
