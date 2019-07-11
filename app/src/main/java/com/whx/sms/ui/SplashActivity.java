package com.whx.sms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.whx.sms.util.PermissionsManager;

public class SplashActivity extends AppCompatActivity {

    int maxTryTime = 2;
    int tryTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }

    private void requestPermissions() {
        tryTime++;
        if (new PermissionsManager().requestPermissions(this)) {
            jumpToListActivity();
        }
    }

    private void jumpToListActivity() {
        this.startActivity(new Intent(SplashActivity.this, SMSListActivity.class));
        this.finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsManager.WRITE_COARSE_LOCATION_REQUEST_CODE) {
            boolean haveAllPermission = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    haveAllPermission = false;
                    break;
                }
            }

            if (haveAllPermission) {
                requestPermissions();
            } else if (tryTime < maxTryTime) {
                requestPermissions();
            } else {
                Toast.makeText(SplashActivity.this, "未获得权限,请先设置权限", Toast.LENGTH_LONG).show();
                this.finish();
            }

        }
    }

}
