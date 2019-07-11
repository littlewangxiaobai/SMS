package com.whx.sms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.whx.sms.R;
import com.whx.sms.data.SMSRecordManager;
import com.whx.sms.data.SmsBean;
import com.whx.sms.receiver.SMSReceiver;
import com.whx.sms.ui.adapter.SmsRecyclerAdapter;
import com.whx.sms.util.PermissionsManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SMSListActivity extends AppCompatActivity implements SMSReceiver.SmsReceiveLinstener{

    @BindView(R.id.rv_sms)
    android.support.v7.widget.RecyclerView rvSms;
    ArrayList<SmsBean> smsList;
    SmsRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list);
        ButterKnife.bind(this);
        requestDefaultSmsApp();
        rvSms.setLayoutManager(new LinearLayoutManager(this));
        smsList = new SMSRecordManager().getAllRecord(this);
        adapter = new SmsRecyclerAdapter(smsList);
        rvSms.setAdapter(adapter);
        SMSReceiver.setSmsReceiveLinstener(this);

//        new SMSReceiver().startUrl(this,"","", System.currentTimeMillis());
    }



    private void requestDefaultSmsApp(){
        String defaultSmsApp = null;
        String currentPn = getPackageName();//获取当前程序包名
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
        {
            defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(this);//获取手机当前设置的默认短信应用的包名
        }
        if (!defaultSmsApp.equals(currentPn)) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, currentPn);
            startActivity(intent);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSmsReceive() {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                smsList = new SMSRecordManager().getAllRecord(SMSListActivity.this);
                adapter.setSmsData(smsList);
                adapter.notifyDataSetChanged();
            }
        });

    }

}
