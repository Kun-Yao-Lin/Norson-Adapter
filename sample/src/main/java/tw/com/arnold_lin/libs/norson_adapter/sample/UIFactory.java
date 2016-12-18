package tw.com.arnold_lin.libs.norson_adapter.sample;

import android.content.Context;

import tw.com.arnold_lin.libs.norson_adapter.R;


/**
 * Created by arnold_lin on 2015/1/8.
 */
public class UIFactory {

    private static float height = 0;
    private static float circleDiameter = 0;
    private static float padding = 0;

    public static int getDefaultHeight(Context context){
        return getDefaultHeight(context, 1f);
    }

    public static int getDefaultHeight(Context context, float rate){
        if(height == 0){
            height = context.getResources().getDimension(R.dimen.height_one_unit);
        }
        int result = (int) (height * rate);
        return result;
    }

    public static int getCircleDiameter(Context context){
        return getCircleDiameter(context,1f);
    }

    public static int getCircleDiameter(Context context,float rate){
        if(circleDiameter == 0){
            circleDiameter = context.getResources().getDimension(R.dimen.diameter);
        }
        int result = (int) (circleDiameter * rate);
        return result;
    }

    public static int getPadding(Context context){
        return getPadding(context,1f);
    }

    public static int getPadding(Context context,float rate){
        if(padding == 0){
            padding = context.getResources().getDimension(R.dimen.list_padding);
        }
        int result = (int) (padding * rate);
        return result;
    }


}
