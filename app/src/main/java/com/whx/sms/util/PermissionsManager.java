package com.whx.sms.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

public class PermissionsManager {

    private String[] map_permissions = {
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.RECEIVE_SMS,

            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };




    public static final int WRITE_COARSE_LOCATION_REQUEST_CODE =100;

    private ArrayList<String> mPermissionList = new ArrayList<>();


    public boolean requestPermissions(Activity context) {
        mPermissionList.clear();
        for (int i = 0; i < map_permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, map_permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(map_permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            return true;
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(context, permissions, WRITE_COARSE_LOCATION_REQUEST_CODE);
        }
        return false;
    }


}
