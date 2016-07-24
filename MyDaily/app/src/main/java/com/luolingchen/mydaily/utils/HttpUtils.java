package com.luolingchen.mydaily.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by luolingchen on 2016-07-23.
 */
public class HttpUtils {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, ResponseHandlerInterface responseHandler){
        client.get(url,responseHandler);
    }



    public static boolean isNetworkConnected(Context context) {
        if(context != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
