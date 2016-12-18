package tw.com.arnold_lin.libs.norson_adapter.sample.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import tw.com.arnold_lin.library.adapters.common.feature.core.ViewBinderImp;
import tw.com.arnold_lin.libs.norson_adapter.sample.NorsonApplication;

/**
 * Created by arnold_lin on 2015/12/8.
 */
public class UnvImageView extends LinearLayout implements ViewBinderImp<UnvImageView.Binder> {

    private ImageView imageView;
    private ImageLoader loader = ImageLoader.getInstance();

    public UnvImageView(Context context) {
        super(context);
        initialize(context);
    }

    public UnvImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,metrics.heightPixels /2));
        addView(imageView);
    }

    @Override
    public void init() {

    }

    @Override
    public void setBinder(Binder binder) {
        loader.displayImage(binder.getUrl(),imageView, NorsonApplication.defaultOptions);
    }

    @Override
    public View getView() {
        return this;
    }

    public static class Binder extends tw.com.arnold_lin.library.adapters.common.feature.binders.Binder<UnvImageView>{
        private final String url;

        public Binder(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

    }
}
