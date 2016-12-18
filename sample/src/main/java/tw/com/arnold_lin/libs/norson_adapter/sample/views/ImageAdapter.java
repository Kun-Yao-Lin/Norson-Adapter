package tw.com.arnold_lin.libs.norson_adapter.sample.views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import tw.com.arnold_lin.libs.norson_adapter.sample.NorsonApplication;

/**
 * Created by arnold_lin on 2015/12/4.
 */
public class ImageAdapter extends PagerAdapter {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<String> uri = new ArrayList<String>();
    private final Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return uri.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(context, null);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageLoader.displayImage(this.uri.get(position), view, NorsonApplication.defaultOptions);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //提出bitmap把他回收
//        ImageView iv = (ImageView) object;
//        Drawable drawable = iv.getDrawable();
//        if (drawable instanceof BitmapDrawable) {
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//            Bitmap bitmap = bitmapDrawable.getBitmap();
//            bitmap.recycle();
//            System.gc(); //提醒系統及時回收
//        }
//        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        // TODO 自動產生的方法 Stub
        return view == (View) obj;
    }

    public void setImageList(List<String> uri) {
        this.uri = uri;
        if (uri == null) {
            uri = new ArrayList<String>();
        }

        if (uri.size() == 0) {
            uri.add("");
        }
        notifyDataSetChanged();
    }

}
