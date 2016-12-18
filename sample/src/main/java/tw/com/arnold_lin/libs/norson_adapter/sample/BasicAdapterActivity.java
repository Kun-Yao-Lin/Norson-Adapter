package tw.com.arnold_lin.libs.norson_adapter.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import tw.com.arnold_lin.library.adapters.common.feature.interfaces.IBinder;
import tw.com.arnold_lin.libs.norson_adapter.R;


/**
 * Created by arnold_lin on 2015/12/18.
 */
public abstract class BasicAdapterActivity<T> extends Activity implements View.OnClickListener {

    protected abstract RecyclerView.Adapter getAdapter(Context context);
    protected abstract ArrayList<T> getInitData();
    protected abstract T getSingleData();
    protected abstract RecyclerView.LayoutManager getLayoutManager();

    private LinearLayout linearLayout ;
    private RecyclerView listView ;
    private Button button;

    private RecyclerView.Adapter adapter;
    private IBinder imp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 11){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
        linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);
        listView = new RecyclerView(this);
        RecyclerView.LayoutManager manager = getLayoutManager();
        if(manager == null){
            manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        listView.setLayoutManager(manager);
        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
        linearLayout.addView(listView);

        button = new Button(this);
        button.setText(R.string.add);
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(this);

        linearLayout.addView(button);

        adapter = getAdapter(this);
        listView.setAdapter(adapter);

        //初始化
        if(adapter instanceof IBinder){
            imp =  ((IBinder) adapter);
            imp.setBinders(getInitData());
        }

    }

    @Override
    public void onClick(View v) {
        if(imp != null){
            imp.addBinder(getSingleData());
        }
        listView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }
}
