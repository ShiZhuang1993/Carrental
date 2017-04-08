package com.bawei.lianxi.activity;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lianxi.R;
import com.bawei.lianxi.adapter.QicheAdapter;
import com.bawei.lianxi.bean.QicheBean;
import com.bawei.lianxi.url.Url;
import com.bawei.lianxi.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.List;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {

    private ListView mListView;
    private QicheAdapter mAdapter;

    private List<QicheBean.ListBean> mList;
    private AlertDialog.Builder mBuilder;
    private int page;
    private ImageView mToButton;
    private TextView mTopTextview;
    private int mWidthPixels;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWidthPixels = this.getResources().getDisplayMetrics().widthPixels;
        initView();
        initData();
    }
    private void initView() {
        mListView = (ListView) findViewById(R.id.mian_list);
        mToButton = (ImageView) findViewById(R.id.topButton);
        mTopTextview = (TextView) findViewById(R.id.topTv);
       mToButton.setOnClickListener(this);
    }

    private void initData() {
        //Gson解析，获取数据
        getAsyuncTask();
        //点击土司和删除方法
        delete();
        //弹框
        alertDialog();

        // 设置左侧滑动菜单
        setBehindContentView(R.layout.menu_frame_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new LeftFragment()).commit();
        // 实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置可以左右滑动的菜单
        sm.setMode(SlidingMenu.LEFT);
        // 设置滑动菜单视图的宽度
        sm.setBehindWidth(mWidthPixels/3*2);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(0.35f);
        // 设置触摸屏幕的模式,这里设置为全屏
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置下方视图的在滚动时的缩放比例



        sm.setBehindScrollScale(0.0f);


    }

    private void alertDialog() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setIcon(R.mipmap.timg);
        mBuilder.setTitle("确定删除？");
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mList.remove(mList.get(page));
                mAdapter.notifyDataSetChanged();
            }
        });

        mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mBuilder.create();
    }

    private void delete() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mList.get(position).getId() + "", Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                page = position;
                mBuilder.show();
                return true;
            }
        });
    }

    private void getAsyuncTask() {
        MyAsyncTask asyncTask = new MyAsyncTask() {


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                QicheBean qicheBean = gson.fromJson(s, QicheBean.class);
                mList = qicheBean.getList();
                mAdapter = new QicheAdapter(MainActivity.this, mList);
                mListView.setAdapter(mAdapter);
            }
        };
        asyncTask.execute(Url.url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topButton:
                toggle();
                break;
            default:
                break;
        }
    }
}



