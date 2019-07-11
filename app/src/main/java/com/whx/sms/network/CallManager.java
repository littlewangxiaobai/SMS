package com.whx.sms.network;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CallManager {


    private CallManager(){

    }

    private static class  Holder {
        static CallManager instance = new CallManager();
    }

    public static  CallManager  getInstance(){
        return Holder.instance;
    }

    public void request(final Context context, String url){

        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call,  IOException e) {
                    Log.e("whx", "onFailure: " + call  + "  with IOException:" + e);
                }

                @Override
                public void onResponse( Call call, Response response) throws IOException {
                    Log.e("whx", "onResponse: " + call + "   with response: " + response);
                }
            });
        }catch (IllegalArgumentException e){
            Toast.makeText(context,"url 有误" ,Toast.LENGTH_LONG).show();
            Log.e("whx", "IllegalArgumentException: " + e);
        }

    }

}
