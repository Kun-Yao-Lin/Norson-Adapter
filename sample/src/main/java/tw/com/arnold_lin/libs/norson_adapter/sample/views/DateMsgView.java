package tw.com.arnold_lin.libs.norson_adapter.sample.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

import tw.com.arnold_lin.library.adapters.common.feature.core.ViewBinderImp;


/**
 * Created by arnold_lin on 2015/12/8.
 */
public class DateMsgView extends FrameLayout implements ViewBinderImp<DateMsgView.Binder> {

    private int height_2;
    private int padding;
    private int padding3;
    private TextView number;

    public DateMsgView(Context context) {
        super(context);
        initialize(context);
    }

    public DateMsgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {

        height_2 = 50;
        padding = 10;
        padding3 = padding * 3;

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(padding,padding,padding,padding);
        number = new TextView(context);
        number.setLayoutParams(layoutParams);
        number.setTypeface(null,Typeface.BOLD);
        number.setTextColor(getResources().getColor(android.R.color.white));
        number.setTextSize(20f);
        number.setGravity(Gravity.CENTER);
        addView(number);
    }


    @Override
    public void init() {
    }

    @Override
    public void setBinder(Binder binder) {
        number.setText(binder.getCount());
        number.setBackgroundColor(binder.getColor());
    }

    @Override
    public View getView() {
        return this;
    }

    public static class Binder extends tw.com.arnold_lin.library.adapters.common.feature.binders.Binder<DateMsgView> {

        private final String count;
        private int color;

        public Binder(String count) {
            this.count = count;
            Random rnd = new Random();
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        }

        public String getCount() {
            return count;
        }

        public int getColor() {
            return color;
        }
    }
}
