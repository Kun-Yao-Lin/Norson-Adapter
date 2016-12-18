package tw.com.arnold_lin.libs.norson_adapter.sample;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import tw.com.arnold_lin.libs.norson_adapter.R;

/**
 * Created by arnold_lin on 2015/12/11.
 */
public class NorsonApplication extends android.app.Application {

    public static final DisplayImageOptions defaultOptions  = new DisplayImageOptions.Builder()
            .showImageOnFail(R.mipmap.ic_launcher)
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build();

    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize(){
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        if(!imageLoader.isInited()){
            imageLoader.init(configuration);
        }
    }
}
