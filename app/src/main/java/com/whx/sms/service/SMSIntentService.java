package com.whx.sms.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class SMSIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SMSIntentService() {
        super("SMSIntentService");
    }


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String text, String param2) {
        Intent intent = new Intent(context, SMSIntentService.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
        Log.e("whx", "startActionFoo: 1 " );
    }


    @Override
    protected void onHandleIntent( Intent intent) {
        Uri uri = Uri.parse("https://www.baidu.com");
        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent1);


        Log.e("whx", "startActionFoo: 2 " );


    }
}
