package com.whx.sms.data;

import android.content.Context;
import android.content.SharedPreferences;

public class DataManager {


    public static String getBaseUrl(Context context){
        SharedPreferences s = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return s.getString("url","http://www.baidu.com");
    }



    public static void updateBaseUrl(Context context, String url){
        SharedPreferences s = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = s.edit();
        edit.putString("url", url);
        edit.commit();

    }
}
