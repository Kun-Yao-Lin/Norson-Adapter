package tw.com.arnold_lin.libs.norson_adapter.sample.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tw.com.arnold_lin.library.adapters.common.feature.core.ViewBinderImp;
import tw.com.arnold_lin.libs.norson_adapter.sample.UIFactory;

/**
 * Created by arnold_lin on 2016/12/12.
 */

public class TextItemView extends TextView implements ViewBinderImp<TextItemView.Binder> {

    public TextItemView(Context context) {
        super(context);
        initialize(context);
    }

    public TextItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        int padding2 = UIFactory.getPadding(context, 2f);

        setPadding(padding2,padding2,padding2,padding2);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        setBackgroundResource(android.R.color.white);
    }

    @Override
    public void init() {
    }

    @Override
    public void setBinder(TextItemView.Binder binder) {
        setText(binder.getText());
        setOnClickListener(binder.getOnClickListener());
    }

    @Override
    public View getView() {
        return this;
    }

    public static class Binder extends tw.com.arnold_lin.library.adapters.common.feature.binders.Binder<TextItemView>{
        private String text = "";

        public Binder(String text) {
            if(TextUtils.isEmpty(text) == true){
                return;
            }
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
