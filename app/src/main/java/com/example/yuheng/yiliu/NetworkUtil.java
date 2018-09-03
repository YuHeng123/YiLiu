package com.example.yuheng.yiliu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

/**
 * 判断当前网络是否可用
 */
public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context){
        //ConnectivityManager 网络连接管理
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return false;
        }
        //getActiveNetworkInfo:获取当前激活的网络连接信息
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()){
            return false;
        }
        return true;
    }
}
