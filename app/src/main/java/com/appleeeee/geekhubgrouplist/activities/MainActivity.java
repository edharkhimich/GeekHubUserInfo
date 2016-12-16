package com.appleeeee.geekhubgrouplist.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.other.HeadphonesReceiver;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final int PERMS_REQUEST_CODE = 123;
    private HeadphonesReceiver headphonesReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        headphonesReceiver = new HeadphonesReceiver();
        intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headphonesReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        if (headphonesReceiver != null) {
            unregisterReceiver(headphonesReceiver);
            headphonesReceiver = null;
            super.onDestroy();
        }
    }

    @OnClick(R.id.button_list_view)
    void listViewOnClick() {
        Intent listViewIntent = new Intent(this, ListViewActivity.class);
        startActivity(listViewIntent);
    }

    @OnClick(R.id.button_recycler_view)
    void recyclerViewOnClick() {
        Intent recViewintent = new Intent(this, RecyclerViewActivity.class);
        startActivity(recViewintent);
    }

    @OnClick(R.id.button_contact_list)
    void contactLickOnClick() {
        if(hasPermission()) {
            Intent contactIntent = new Intent(this, ContactActivity.class);
            startActivity(contactIntent);
        } else {
            requestPerms();
        }
    }

    private boolean hasPermission(){
        int res = 0;

        String[] permissions = new String[]{Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CONTACTS};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CONTACTS};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMS_REQUEST_CODE:
                for (int res : grantResults){
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed = false;
                break;
        }

        if (allowed){
            Intent contactIntent = new Intent(this, ContactActivity.class);
            startActivity(contactIntent);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, R.string.storage_denied, Toast.LENGTH_SHORT).show();
                }
                else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                    Toast.makeText(this, R.string.storage_denied, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


