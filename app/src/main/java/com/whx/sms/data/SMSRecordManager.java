package com.whx.sms.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

public class SMSRecordManager {
    private static String TAG = "whx";


    public ArrayList<SmsBean> getAllRecord(Context context) {

        ArrayList<SmsBean> SMSList = new ArrayList<SmsBean>();

        Cursor cursor = null;
//        cursor = context.getContentResolver()
//                .query(Uri.parse("content://sms"),
//                        new String[]{"_id", "address", "read", "body", "date"},
//                        "read = ? ",
//                        new String[]{"0"},
//                        "date desc");
        cursor = context.getContentResolver().query(
                Uri.parse("content://sms"),
                new String[]{"_id", "address", "read", "body", "date"},
                "",
                new String[]{},
                "date desc");
        if (null == cursor ) {
            return null;
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        Log.i(TAG, "m cursor count is " + cursor.getCount());
        Log.i(TAG, "m first is " + cursor.moveToFirst());

        SmsBean smsBean = new SmsBean(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        SMSList.add(smsBean);
        Log.e(TAG, "getAllRecord:  " + smsBean);
//        Log.i(TAG,"m first is "+cursor.moveToFirst());
//        cursor.getString(0);
        while (cursor.moveToNext()) {
            SmsBean temp = new SmsBean(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));
            SMSList.add(temp);
            Log.e(TAG, "getAllRecord:  " + temp);
        }


        cursor.close();
        return SMSList;
    }


    public boolean insertSms(Context context, String address, long time, String body) {
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("address", address);
        values.put("type", 1);
        values.put("date", time);
        values.put("body", body);
        cr.insert(Uri.parse("content://sms"), values);

        return true;
    }
}
