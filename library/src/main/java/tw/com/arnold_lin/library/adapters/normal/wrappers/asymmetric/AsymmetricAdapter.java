package tw.com.arnold_lin.library.adapters.normal.wrappers.asymmetric;

import android.content.Context;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.arnold_lin.library.adapters.common.wrappers.asymmetric.AsymmetricGroup;
import tw.com.arnold_lin.library.adapters.normal.wrappers.WrapperBaseAdapterImpl;


/**
 * Created by arnold_lin on 15/9/2.
 */
public class AsymmetricAdapter extends WrapperBaseAdapterImpl {
    private static final String TAG = "AsymmetricAdapter";
    private final Map<ViewGroup,ArrayList<View>> groupItem = new HashMap<ViewGroup, ArrayList<View>>();

    private final List<AsymmetricGroup.Spirit> spirits = new ArrayList<AsymmetricGroup.Spirit>();
    private final Map<ViewGroup,AsymmetricGroup> asymmetricGroupMap = new HashMap<ViewGroup, AsymmetricGroup>();
    private final Map<ViewGroup,AsyncTask> groupTask = new HashMap<ViewGroup, AsyncTask>();

    private final Context context;

    private int unit = 0;
    private int minUnit = 0;
    private int maxUnit = 0;

    private DisplayMetrics metrics;
    private int p = 100;
    private int column = 2;

    private int itemCount = 5;
    private int styleCount = 5;
    private int orientation = LinearLayout.HORIZONTAL;

    public AsymmetricAdapter(Context context,BaseAdapter wrapped) {
        super(wrapped);
        this.context = context;
        metrics = context.getResources().getDisplayMetrics();
        refreshUnit();
        notifyStyleCountChanged();
    }

    @Override
    public int getCount() {
        int itemCount = super.getCount();
        int count =  (itemCount / this.itemCount);
        if(itemCount % this.itemCount > 0){
            count++;
        }
        Log.d(TAG,"getCount : "+ count);
        return count;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LinearLayout group;
//        int orientation = LinearLayout.HORIZONTAL;
        if(view == null){
            group = new LinearLayout(context);
            asymmetricGroupMap.put(group, new AsymmetricGroup());
            view = group;
        }else {
            group = (LinearLayout) view;
        }

        int start = position * itemCount; //餘數
        int end = start + itemCount;
        int end_ = start + itemCount;
        int delta = super.getCount() - end;
        if(delta < 0){
            end = super.getCount();
        }

        Log.d(TAG, start + "~" + end);
        ArrayList<View> views = new ArrayList<View>();
        boolean isRequest = true;
        if(groupItem.containsKey(group) == true){
            views = groupItem.get(group);
            isRequest = false;
        }

        for(int i = start ; i < end_ ; i ++) {
            int index = i % itemCount;
            Log.d(TAG,"isRequest : "+isRequest);
            Log.d(TAG,"views.size() > index  : "+ (views.size() > index) );
            View oldView = isRequest == true ? null : views.size() > index ?  views.get(index) : null;
            View newView = null;
            if(i < end){
                newView = super.getView(i, oldView, viewGroup);
            }

            if(oldView == null && newView != null){
                Log.d(TAG,"oldView == null && newView != null");
                views.add(newView);
            }else if(oldView != null && newView == null){
                Log.d(TAG,"oldView != null && newView == null");
                oldView.setVisibility(View.GONE);
            }else if(oldView != null){
                Log.d(TAG,"oldView != null");
                oldView.setVisibility(View.VISIBLE);
            }
        }

        ArrayList<View> viewsTemp = (ArrayList) views.clone();
        int d = viewsTemp.size() - (end - start);
        for(int j = 0 ; j < d; j++) {
            View v =  views.get(viewsTemp.size() -1);
            ViewParent parent = v.getParent();
            if(parent instanceof ViewGroup){
                ((ViewGroup) parent).removeView(v);
            }

            viewsTemp.remove(viewsTemp.size() -1);
        }

        Log.d(TAG,"viewsTemp size : "+viewsTemp.size());

        AsyncTask asyncTask = groupTask.get(group);
        if(asyncTask != null){
            asyncTask.cancel(true);
        }
        asyncTask = new LoadStyleAsyncTask(group,position,viewsTemp);
        groupTask.put(group,asyncTask);
        asyncTask.execute();

        if(isRequest == true){
            groupItem.put(group,views);
        }
//        groupItem.put(group,views);
        return view;
    }

    private void refreshUnit(){
        int average = metrics.widthPixels / column;
        unit = metrics.widthPixels / p;
        minUnit = average / 2 ;
        maxUnit = average + minUnit;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        notifyDataSetChanged();
    }

    public void setColumn(int column) {
        this.column = column;
        refreshUnit();
        notifyDataSetChanged();
    }

    public void setStyleCount(int styleCount){
        this.styleCount = styleCount;
        notifyStyleCountChanged();
    }

    public void notifyStyleCountChanged() {
        for(int i = spirits.size() ; i < styleCount; i++){
            spirits.add(AsymmetricGroup.Spirit.create(
                    minUnit,
                    maxUnit,
                    column,
                    itemCount,
                    orientation,
                    metrics.heightPixels

            ));
        }
    }


    private class LoadStyleAsyncTask extends AsyncTask {
        private ViewGroup viewGroup;
        private AsymmetricGroup.Spirit spirit;
        private AsymmetricGroup asymmetricGroup;
        private ArrayList<View> views;

        public LoadStyleAsyncTask(ViewGroup viewGroup , int position, ArrayList<View> views) {
            this.asymmetricGroup = asymmetricGroupMap.get(viewGroup);
            this.viewGroup = viewGroup;
            notifyStyleCountChanged();
            this.spirit = spirits.get(position % styleCount);
            this.views = views;
        }

        @Override
        protected Object doInBackground(Object[] params) {
//            try {
//                //waiting...
//                Thread.sleep(15);
//            }catch (InterruptedException e){
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Object param) {
            super.onPostExecute(param);
            if(asymmetricGroup != null && spirit != null && views != null && viewGroup != null){
                asymmetricGroup.bindView(context, spirit, views, viewGroup);
            }
            groupTask.put(viewGroup,null);
            int count = 0;
            for(int i = 0 ; i < viewGroup.getChildCount(); i++){
                View v = viewGroup.getChildAt(i);
                if(v instanceof ViewGroup){
                    Log.d("LoadStyleAsyncTask","viewGroup"+i+" : "+((ViewGroup) v).getChildCount());
                    count += ((ViewGroup) v).getChildCount();
                }
            }
            Log.d("LoadStyleAsyncTask","viewGroup children total : "+count);
            Log.d("LoadStyleAsyncTask","views size : "+views.size());
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            asymmetricGroup = null;
            spirit = null;
            views = null;
            viewGroup = null;
            groupTask.put(viewGroup,null);
        }

    }

}
