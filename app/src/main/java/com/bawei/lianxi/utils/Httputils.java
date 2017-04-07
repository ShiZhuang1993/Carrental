package com.bawei.lianxi.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 类用途：
 * 作者：史壮壮
 * 时间：2017/4/7 20:30
 */

public class Httputils {
    public static String getHttp(String urls) throws Exception {
        URL url = new URL(urls);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == 200) {
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(b)) != -1) {
                stream.write(b, 0, len);
            }
            return stream.toString("utf-8");
        }
        return null;
    }
}
