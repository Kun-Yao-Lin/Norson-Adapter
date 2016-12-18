package tw.com.arnold_lin.libs.norson_adapter.sample.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import tw.com.arnold_lin.library.adapters.common.feature.core.ViewBinderImp;

/**
 * Created by arnold_lin on 2015/12/8.
 */
public class BannerView extends FrameLayout implements ViewBinderImp<BannerView.Binder> {

    private int imageHeight;

    private ImageAdapter adapter;
    private ViewPager mPager;
    private CirclePageIndicator mIndicator;

    public BannerView(Context context) {
        super(context);
        initialize(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        imageHeight = metrics.heightPixels / 3;

//        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,imageHeight));

        adapter = new ImageAdapter(context);

        mPager = new ViewPager(context);
        mPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,imageHeight));
        mPager.setAdapter(adapter);
        addView(mPager);

        mIndicator = new CirclePageIndicator(context);
        mIndicator.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
        mIndicator.setPadding(10, 10, 10, 10);
        mIndicator.setViewPager(mPager);
        addView(mIndicator);
    }

    @Override
    public void init() {

    }

    @Override
    public void setBinder(Binder binder) {
        adapter.setImageList(binder.getImageUrl());
    }

    @Override
    public View getView() {
        return this;
    }

    public static class Binder extends tw.com.arnold_lin.library.adapters.common.feature.binders.Binder<BannerView>{
        private final List<String> imageUrl;

        public Binder(List<String> imageUrl) {
            this.imageUrl = imageUrl;
        }

        public List<String>  getImageUrl() {
            return imageUrl;
        }

    }
}
