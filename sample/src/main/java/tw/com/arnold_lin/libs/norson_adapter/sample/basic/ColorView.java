package tw.com.arnold_lin.libs.norson_adapter.sample.basic;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by arnold_lin on 2015/12/18.
 */
public class ColorView extends FrameLayout {

    private TextView number;

    public ColorView(Context context) {
        this(context,null);
    }

    public ColorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
//        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        number = new TextView(context);
        number.setGravity(Gravity.CENTER);
        number.setTypeface(null, Typeface.BOLD);
        number.setTextSize(100);
        number.setTextColor(context.getResources().getColor(android.R.color.white));
//        number.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        number.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(number);
    }

    public void setNumber(int number) {
        this.number.setText(""+number);;
    }
}
