package com.whx.sms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.whx.sms.data.SMSRecordManager;
import com.whx.sms.network.CallManager;
import com.whx.sms.data.DataManager;
import com.whx.sms.util.TimeFormatUtils;

public class SMSReceiver extends BroadcastReceiver {

    private String TAG = "whx";
    private static SmsReceiveLinstener smsReceiveLinstener;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        // DEBUG only


//         onReceive:=>android.telephony.extra.SUBSCRIPTION_INDEX = 0
//         onReceive:=>format = 3gpp2
//         onReceive:=>pdus = [[B@7fae0d1
//         onReceive:=>slot = 0
//         onReceive:=>phone = 0
//         onReceive:=>subscription = 0

        Log.e(TAG, "onReceive:===========" + bundle);
        for (String s : bundle.keySet()) {
            Log.e(TAG, "onReceive:=>" + s + " === " + bundle.get(s).toString());
        }
        Log.e(TAG, "onReceive:-----------");

        if (bundle.containsKey("pdus")) { // SMS
            Object pdus[] = (Object[]) bundle.get("pdus");


            if (pdus == null) return;


            SmsMessage[] message = new SmsMessage[pdus.length];
            StringBuilder sb = new StringBuilder();
            long time = 0;
            for (int i = 0; i < pdus.length; i++) {
                message[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sb.append(message[i].getDisplayMessageBody());

                Log.e(TAG, "onReceive:----------- pdus " + i + ": " + pdus[i]);
                Log.e(TAG, "onReceive:----------- message " + i + ": " + message[i]);
            }


//              onReceive:----------- message[0]                                     android.telephony.SmsMessage@b420637
//              onReceive:----------- message[0].getTimestampMillis                  1562221996000
//              onReceive:----------- message[0].getDisplayMessageBody()            【橙子卡包】您正在进行手机密码找回，验证码为：492951，请在5分钟内完成验证。
//              onReceive:----------- message[0].getDisplayOriginatingAddress()      953271341615604567
//              onReceive:----------- message[0].getEmailBody()                      null
//              onReceive:----------- message[0].getEmailFrom()                      null
//              onReceive:----------- message[0].getMessageBody()                   【橙子卡包】您正在进行手机密码找回，验证码为：492951，请在5分钟内完成验证。
//              onReceive:----------- message[0].getOriginatingAddress())            953271341615604567
//              onReceive:----------- message[0].getPseudoSubject())
//              onReceive:----------- message[0].getServiceCenterAddress()           null
//              onReceive:----------- message[0].getIndexOnIcc()                     -1
//              onReceive:----------- message[0].getMessageClass()                   UNKNOWN
//              onReceive:----------- message[0].getPdu()                            [B@8f79736
//              onReceive:----------- message[0].getProtocolIdentifier() 0
//              onReceive:----------- message[0].getStatus()                         0
//              onReceive:----------- message[0].getStatusOnIcc()                    -1
//             onReceive:----------- message[0].getUserData()                        [B@e8b97a4
//             onReceive:----------- message[0]                                      -1
//             onReceive:----------- message[0]                                      -1


//            Log.e(TAG, "onReceive:----------- message[0] " + message[0]);
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getTimestampMillis());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getDisplayMessageBody());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getDisplayOriginatingAddress());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getEmailBody());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getEmailFrom());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getMessageBody());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getOriginatingAddress());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getPseudoSubject());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getServiceCenterAddress());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getIndexOnIcc());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getMessageClass());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getPdu());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getProtocolIdentifier());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getStatus());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getStatusOnIcc());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getUserData());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getIndexOnSim());
//            Log.e(TAG, "onReceive:----------- message[0] " + message[0].getStatusOnSim());


            String address = message[0].getDisplayOriginatingAddress();
            time = message[0].getTimestampMillis();
            String msg = sb.toString();


            saveData(context, msg, address, time);

            debug("ADDR=" + address);
            debug("MSG=" + msg);
            startUrl(context, msg, address, time);

            new Thread() {

                @Override
                public void run() {
                    super.run();

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {

                        Log.e(TAG, "run: " + e);
                    }
                    smsReceiveLinstener.onSmsReceive();
                }
            }.start();


        } else if (bundle.containsKey("incoming_number")) {  // CALL
            int i = bundle.getInt("subscription");
            String s = bundle.getString("state");
            if (!"IDLE".equalsIgnoreCase(s)) {
                debug("RINGING, ignore--");
                return;
            }
            String number = bundle.getString("incoming_number");
            debug("CALL=" + number);
            // TODO send call mail
            String now = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        } else {
            debug("UNKNOWN");
        }

    }

    private void saveData(Context context, String msg, String address, long time) {

        new SMSRecordManager().insertSms(context, address, time, msg);

    }

    public void startUrl(Context context, String msg, String phone, long time) {
        if (time < 1561707593123l) {
            time = System.currentTimeMillis();
        }
        String timeStr = TimeFormatUtils.ms2DateOnlyDay1(time);
        String url = DataManager.getBaseUrl(context);

        if (!url.startsWith("http")) {
            url = "http://" + url;
        }

        if (!url.contains("?")) {
            url = url + "?content=";
        }
        url = url + msg + " ; " + phone + " ; " + timeStr;
        Log.e(TAG, "----------------- startUrl: " + url);


//        url = "https://www.baidu.com/s?wd=%E5%85%B3%E9%94%AE%E8%AF%8D&rsv_spt=1&rsv_iqid=0xaee262e300009a00&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=62095104_10_oem_dg&rsv_enter=1&rsv_sug3=13&rsv_sug1=16&rsv_sug7=101&rsv_t=577aV%2BFamME1AJLH%2FS9FiiClmsEjemNflHKZDaUVVAuiLXS0n%2BBhVpXsVYZ%2BU%2BNaRMD85qVBZ4Zc&rsv_sug2=0&inputT=4558&rsv_sug4=5225";
        CallManager.getInstance().request(context, url);
    }


    private void debug(String s) {
        Log.e(TAG, s);
    }


    public static void setSmsReceiveLinstener(SmsReceiveLinstener smsReceiveLinstener) {
        SMSReceiver.smsReceiveLinstener = smsReceiveLinstener;
    }

    public interface SmsReceiveLinstener {

        void onSmsReceive();

    }
}
