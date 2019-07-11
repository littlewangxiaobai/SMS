package com.whx.sms;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class SMSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "7ab4b184e2", true);
    }
}
