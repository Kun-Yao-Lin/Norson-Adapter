package tw.com.arnold_lin.libs.norson_adapter.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.arnold_lin.library.adapters.OnItemClickListener;
import tw.com.arnold_lin.library.adapters.common.feature.binders.Binder;
import tw.com.arnold_lin.library.adapters.recyclerview.feature.FeatureAdapter;
import tw.com.arnold_lin.libs.norson_adapter.sample.views.TextItemView;

/**
 * Created by arnold_lin on 2016/8/28.
 */
public class SampleListActivity extends Activity {

    private RecyclerView recyclerView;
    private FeatureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FeatureAdapter(this);
        recyclerView.setAdapter(adapter);
        setContentView(recyclerView);

        setBinders();
    }

    public void setBinders(){

        Intent intent = getIntent();
        String path = intent.getStringExtra("com.example.android.apis.Path");

        if (path == null) {
            path = "";
        }
        List<Map> list = getData(path);
        ArrayList<Binder> binders = new ArrayList<>();

        for(Map map :list){
            binders.add(createBinder(map));
        }

        adapter.setBinders(binders);
    }

    private Binder createBinder(final Map map){
        String text = (String) map.get("title");
        TextItemView.Binder binder = new TextItemView.Binder(text);
        binder.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, Binder binder) {
                Intent intent = (Intent) map.get("intent");
                startActivity(intent);
            }
        });
        return binder;
    }

    protected List getData(String prefix) {
        List<Map> myData = new ArrayList<Map>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("tw.com.arnold_lin.libs.norson_adapter.sample");

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;

            if (prefix.length() == 0 || label.startsWith(prefix)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    private final static Comparator<Map> sDisplayNameComparator = new Comparator<Map>() {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(Map map1, Map map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, getClass());
        result.putExtra("com.example.android.apis.Path", path);
        return result;
    }

    protected void addItem(List<Map> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

}
