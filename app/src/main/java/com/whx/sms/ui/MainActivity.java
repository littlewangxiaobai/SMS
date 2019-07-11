package com.whx.sms.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.whx.sms.data.DataManager;
import com.whx.sms.R;
import com.whx.sms.receiver.SMSReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String url = DataManager.getBaseUrl(this);

        if(!TextUtils.isEmpty(url)){
            et.setText(url);
        }
    }

    @OnClick({R.id.btn_save})
    void onClickView(View view){
        switch (view.getId()){
            case R.id.btn_save:
                saveUrl();
                break;
        }
    }

    private void saveUrl() {
        String urlStr = et.getText().toString();

        urlStr = urlStr.replace(" ","");
        urlStr = urlStr.replace("\n","");
        urlStr = urlStr.replace("\r","");

        if(!TextUtils.isEmpty(urlStr)){
            if(!urlStr.startsWith("http")){
                urlStr = "http://" + urlStr;
            }
            DataManager.updateBaseUrl(this,urlStr);
            Toast.makeText(this,"保存成功" + urlStr,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"请填写url",Toast.LENGTH_LONG).show();
        }

//        testCall();
    }


    private void testCall(){
        new SMSReceiver().startUrl(this, "123", "133388884518" , 1561707593123l);
    }
}
