package com.bawei.lianxi.utils;

import android.os.AsyncTask;

/**
 * 类用途：
 * 作者：史壮壮
 * 时间：2017/4/7 20:34
 */

public class MyAsyncTask  extends AsyncTask<String,Integer,String>{
    @Override
    protected String doInBackground(String... params) {
        try {
            return Httputils.getHttp(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
