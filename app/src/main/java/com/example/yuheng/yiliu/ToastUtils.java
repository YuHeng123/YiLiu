package com.example.yuheng.yiliu;

import android.widget.Toast;

import org.litepal.LitePalApplication;

public class ToastUtils {

    private static Toast toast;

    public static void showToast(String text){
        if (toast == null){
            toast = Toast.makeText(LitePalApplication.getContext(),text, Toast.LENGTH_LONG);
        }else {
            toast.setText(text);
        }
        toast.show();
    }
}
