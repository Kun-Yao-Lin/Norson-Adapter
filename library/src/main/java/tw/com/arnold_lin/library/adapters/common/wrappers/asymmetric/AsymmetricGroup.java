package tw.com.arnold_lin.library.adapters.common.wrappers.asymmetric;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by arnold_lin on 15/9/6.
 */
public final class AsymmetricGroup {

    public static class Spirit {
        final int defaultGroupHeight;
        final List<Integer> columnSeqs;
        final List<Integer> cutOffIndex;
        final int column ;
        final int orientation;
        final List<LinearLayout.LayoutParams> groupParams;
        final List<LinearLayout.LayoutParams> itemParams;

        public Spirit(int column, List<Integer> columnSeqs, List<Integer> cutOffIndex, int orientation, List<LinearLayout.LayoutParams> groupParams, List<LinearLayout.LayoutParams> itemParams,int defaultGroupHeight) {
            this.column = column;
            this.columnSeqs = columnSeqs;
            this.cutOffIndex = cutOffIndex;
            this.orientation = orientation;
            this.groupParams = groupParams;
            this.itemParams = itemParams;
            this.defaultGroupHeight = defaultGroupHeight;
        }

        public static Spirit create(int minUnit ,int maxUnit ,int columns ,int amount ,int orientation,int defaultGroupHeight){
            List<Integer> cloumnSeqs = new ArrayList<Integer>();
            List<Integer> cutOffIndex = new ArrayList<Integer>();
            List<LinearLayout.LayoutParams> groupParams = new ArrayList<LinearLayout.LayoutParams>();
            List<LinearLayout.LayoutParams> itemParams = new ArrayList<LinearLayout.LayoutParams>();
            int orientation_ = orientation == LinearLayout.HORIZONTAL ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL;
            int count = 0;
            int start_ = 0;
            int end_ = 0;
            for(int i = 0 ;count < amount && end_ != amount; i++){
                int random;
                if(orientation_ == LinearLayout.HORIZONTAL){
                    random = randInt(1,2);
                }else {
                    random = randInt(2,3);
                }
                start_ = count;
                end_ = (count += random);

                if(count > amount){
                    end_ = end_ - (count - amount);
                }

                if(i == columns - 1){
                    end_ = amount;
                }

                int delta = end_ - start_;
                for(int j = 0 ; j < delta; j++){
                    cloumnSeqs.add(i);
                }
                cutOffIndex.add(cloumnSeqs.size()-1);
                itemParams.addAll(createFrameParams(minUnit, maxUnit, delta, orientation_));
                groupParams.add(createGroupParams());
            }

            return new Spirit(columns,cloumnSeqs,cutOffIndex,orientation,groupParams,itemParams,defaultGroupHeight);
        }

        private static LinearLayout.LayoutParams createGroupParams(){
            int weight = randInt(2,4);
            return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, weight);
        }

        private static List<LinearLayout.LayoutParams> createFrameParams(int minUnit ,int maxUnit ,int count ,int orientation){
            List<LinearLayout.LayoutParams> params = new ArrayList<LinearLayout.LayoutParams>();
            LinearLayout.LayoutParams param;
            switch (orientation){
                case LinearLayout.HORIZONTAL:
                    for(int i = 0 ; i < count - 1; i++){
                        int limit = randInt(minUnit,maxUnit);
                        param = new LinearLayout.LayoutParams(limit,ViewGroup.LayoutParams.MATCH_PARENT);
                        params.add(param);
                    }
                    param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.add(param);

                    break;
                default:
                    //VERTICAL
                    for(int i = 0 ; i < count - 1; i++){
                        int limit = randInt(minUnit,maxUnit);
                        param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,limit);
                        params.add(param);
                    }
                    param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.add(param);
                    break;
            }
            return params;
        }

    }

    private final List<LinearLayout> layouts = new ArrayList<LinearLayout>();
    private List<View> views;
    public AsymmetricGroup() {
    }

    private void init(Context context , AsymmetricGroup.Spirit spirit, List<View> views){
//        if(this.views != null) {
//            if(this.views.size() > views.size()){
//                int start = views.size() - 1 ;
//                for (int i = start; i < this.views.size() ; i++){
//                    this.views.get(i).setVisibility(View.GONE);
//                }
//            }
//        }
        this.views = views;

        List< LinearLayout.LayoutParams> groupParams = spirit.groupParams;
        int orientation = spirit.orientation == LinearLayout.HORIZONTAL ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL;
        //檢查是否與 Params 數量匹配
        int groupCount = groupParams.size();
        int layoutCount = layouts.size();

        if(layoutCount < groupCount){
            //缺少補上
            int delta = groupCount - layoutCount;
            for(int i = 0 ; i < delta; i++){
                LinearLayout layout = new LinearLayout(context);
                layout.setWillNotDraw(false);
                layouts.add(layout);
            }
        }else{
            //layoutCount >= groupCount，多出的部分設為 GONE
            for(int i = groupCount ;i < layoutCount; i++){
                LinearLayout layout = layouts.get(i);
                layout.setVisibility(View.GONE);
            }

        }

        //調整 layout Params
        for(int i = 0 ; i < groupCount; i++){
            LinearLayout.LayoutParams params = groupParams.get(i);

            LinearLayout layout = layouts.get(i);
            layout.setOrientation(orientation);
            layout.setLayoutParams(params);
        }
    }

    public synchronized void bindView(Context context, AsymmetricGroup.Spirit spirit, List<View> views, ViewGroup rootView){
        Log.d("AsymmetricGroup", "views.size " + views.size());
        init(context,spirit,views);

        int orientation = spirit.orientation;
        List<LinearLayout.LayoutParams> itemParams = spirit.itemParams;
        List<Integer> columnSeqs = spirit.columnSeqs;

        if(rootView instanceof LinearLayout && ((LinearLayout) rootView).getOrientation() != orientation){
            ((LinearLayout) rootView).setOrientation(orientation);
        }
        rootView.removeAllViews();

        for(LinearLayout layout : layouts){
            rootView.addView(layout);
        }

        int lastSeq = 0;
        int index = 0;
        Log.d("AsymmetricGroup", "-----------------------------");
        Log.d("AsymmetricGroup", "views.size() " + views.size());
        LinearLayout lastLayout = null;
        for(int i = 0; i < columnSeqs.size(); i++){
            int columnSeq = columnSeqs.get(i);
            LinearLayout layout = layouts.get(columnSeq);
            if(i < views.size()) {
                if (lastSeq != columnSeq) {
                    lastSeq = columnSeq;
                    index = 0;
                } else {
                    index++;
                }
                View view = views.get(i);
                view.setLayoutParams(itemParams.get(i));

                //檢查 view 是否有 layout parent
                ViewGroup group = null;
                if (view.getParent() != null && view.getParent() instanceof ViewGroup) {
                    group = (ViewGroup) view.getParent();
                }

                if (group == null) {
                    Log.d("AsymmetricGroup", "group = null");
                    layout.addView(view);
                } else {
                    Log.d("AsymmetricGroup", "group != null");
                    if (layout.equals(group) == false) {
                        Log.d("AsymmetricGroup", "not equals");
                        group.removeView(view);
                        //index 以後都刪除
                        int count = layout.getChildCount();
                        for (int j = index; j < count; j++) {
                            //index 以後都刪除
                            layout.removeViewAt(index);
                        }

                        //指派不相同
                        layout.addView(view);

                    }
                }
                layout.setVisibility(View.VISIBLE);

                ViewGroup.LayoutParams params = layout.getLayoutParams();
                if(spirit.cutOffIndex.contains(i) == true){
                    Log.d("AsymmetricGroup", "spirit.cutOffIndex.contains("+i+") ");
                    //在節點上
                    params.height = spirit.defaultGroupHeight;
                }else {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                layout.setLayoutParams(params);

            }else {
                if(layout.equals(lastLayout) == false) {
                    layout.setVisibility(View.GONE);
                }
            }
            lastLayout = layout;
        }
    }

    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
