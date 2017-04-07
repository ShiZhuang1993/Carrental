package com.bawei.lianxi.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bawei.lianxi.R;
import com.bawei.lianxi.adapter.QicheAdapter;
import com.bawei.lianxi.bean.QicheBean;
import com.bawei.lianxi.url.Url;
import com.bawei.lianxi.utils.MyAsyncTask;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private QicheAdapter mAdapter;

    private List<QicheBean.ListBean> mList;
    private AlertDialog.Builder mBuilder;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    private void initView() {
        mListView = (ListView) findViewById(R.id.mian_list);
    }

    private void initData() {
        //Gson解析，获取数据
        getAsyuncTask();
        //点击土司和删除方法
        delete();
        //弹框
        alertDialog();



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

}



