package tw.com.arnold_lin.library.adapters.common.feature.core;

import android.content.Context;

import java.lang.reflect.Constructor;

import tw.com.arnold_lin.library.adapters.common.feature.binders.BinderImp;

/**
 * Created by arnold_lin on 2015/12/2.
 */
public class FeatureViewFactory {
    private static String TAG = "FeatureViewFactory";

//    private static final Map<Class<? extends FeatureTypeImp>, Class<? extends View>> map = new HashMap<Class<? extends FeatureTypeImp>, Class<? extends View>>();
//
//    public static void register(FeatureTypeImp typeImpl) {
//        if (typeImpl == null) {
//            throw new NullPointerException("typeImpl cannot be null.");
//        }
//
//        Class<? extends View> viewCls = typeImpl.getViewCls();
//        if (viewCls == null) {
//            throw new NullPointerException("ViewCls cannot be null.");
//        }
//
//        if (map.containsKey(typeImpl) == true) {
//            throw new RuntimeException("FeatureTypeImpl : " + typeImpl.getClass().getSimpleName() + " is exist.");
//        }
//
//        Log.d(TAG,"register : "+typeImpl.getClass().getName());
//        map.put(typeImpl.getClass(), viewCls);
//    }
//
//    public static void register(FeatureTypeImp... typeImpls) {
//        for (FeatureTypeImp typeImpl : typeImpls) {
//            register(typeImpl);
//        }
//    }

    public static FeatureBasicLayout create(Context context, BinderImp... binderImps) {
        FeatureBasicLayout layout = new FeatureBasicLayout(context);

//        for (BinderImp binderImp : binderImps) {
//            Log.d(TAG,"binderImp : "+binderImp.getClass().getName());
//            ViewBinderImp viewBinder = createViewBinder(context, binderImp);
//            if (viewBinder != null) {
//                layout.addBinderView(viewBinder);
//            }
//        }
        return layout;
    }

    public static ViewBinderImp createViewBinder(Context context, BinderImp binderImp) {
        ViewBinderImp viewBinder;
        Class cls = null;
        try {
            cls = binderImp.getViewBinderCls();
            Constructor<ViewBinderImp> constructor = cls.getConstructor(Context.class);
            viewBinder = constructor.newInstance(context);
        } catch (Exception e) {
            throw new RuntimeException("cls : " + cls.getSimpleName() + " is create failed", e);
        }
        return viewBinder;
    }

}
