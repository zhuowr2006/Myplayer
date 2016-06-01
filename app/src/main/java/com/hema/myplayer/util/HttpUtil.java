package com.hema.myplayer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/3/16.
 */
public class HttpUtil {
    public static final String STATUS_NETWORK = "network_available";
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(1000);
    }

    public static void get(Context context, String urlString, AsyncHttpResponseHandler res) {
        if (!isNetworkConnected(context)){
            Toast.makeText(context, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
            return;
        }
        LogUtils.i("Http", "URL=" + urlString);
        client.get(urlString, res);
    }

    public static void get(Context context, String urlString, RequestParams params,
                           AsyncHttpResponseHandler res) {
        if (!isNetworkConnected(context)){
            Toast.makeText(context, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
            return;
        }
        LogUtils.i("Http", "URL=" + urlString);
        client.get(urlString, params, res);
    }

    public static void get(Context context, String urlString, JsonHttpResponseHandler res)

    {
        if (!isNetworkConnected(context)){
            Toast.makeText(context, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
            return;
        }
        client.get(urlString, res);
    }

    public static void get(Context context, String urlString, RequestParams params,
                           JsonHttpResponseHandler res)

    {
        if (!isNetworkConnected(context)){
            Toast.makeText(context, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
            return;
        }
        client.get(urlString, params, res);
    }

    public static void get(Context context, String uString, BinaryHttpResponseHandler bHandler)

    {
        if (!isNetworkConnected(context)){
            Toast.makeText(context, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
            return;
        }
        client.get(uString, bHandler);
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 从UrlConnection中获取文件名称
    public static String getFileName(String url) {
        String fileName = null;
        boolean isOK = false;
        try {
            URL myURL = new URL(url);

            URLConnection conn = myURL.openConnection();
            if (conn == null) {
                return null;
            }
            Map<String, List<String>> hf = conn.getHeaderFields();
            if (hf == null) {
                return null;
            }
            Set<String> key = hf.keySet();
            if (key == null) {
                return null;
            }

            for (String skey : key) {
                List<String> values = hf.get(skey);
                for (String value : values) {
                    String result;
                    try {
                        result = value;
                        int location = result.indexOf("filename");

                        if (location >= 0) {
                            result = result.substring(location
                                    + "filename".length());
                            result = java.net.URLDecoder
                                    .decode(result, "utf-8");
                            result = result.substring(result.indexOf("\"") + 1,
                                    result.lastIndexOf("\""));

                            fileName = result
                                    .substring(result.indexOf("=") + 1);
                            isOK = true;
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if (isOK) {
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }


}
