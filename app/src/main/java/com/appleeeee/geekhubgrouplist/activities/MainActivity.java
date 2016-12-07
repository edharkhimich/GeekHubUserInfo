package com.appleeeee.geekhubgrouplist.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.adapter.RecyclerViewAdapter;
import com.appleeeee.geekhubgrouplist.model.User;
import com.appleeeee.geekhubgrouplist.other.HeadphonesReceiver;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String GITHUB_HOST = "github.com";
    private static final String GOOGLE_HOST = "plus.google.com";
    public static final int PERMS_REQUEST_CODE = 123;
    private boolean findUser;
    private HeadphonesReceiver headphonesReceiver;
    private RecyclerViewActivity recActivity;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recActivity = new RecyclerViewActivity();
        headphonesReceiver = new HeadphonesReceiver();
        intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headphonesReceiver, intentFilter);
        recActivity.addUserList();
        urlHandling();
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

    private void urlHandling() {
        Intent intent = getIntent();
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Intent.ACTION_VIEW)) {
                List<User> userList = RecyclerViewActivity.getList();
                Uri data = intent.getData();
                String url = getString(R.string.https) + data.getHost() + data.getPath();
                if (GITHUB_HOST.equals(data.getHost()) && data.getPath() != null
                        || GOOGLE_HOST.equals(data.getHost()) && data.getPath() != null
                        && String.valueOf(data).startsWith(getString(R.string.http))
                        || String.valueOf(data).startsWith(getString(R.string.https))) {
                    for (User user : userList) {
                        if (url.equals(user.getGitUrl())) {
                            Intent userGitInfoIntent = new Intent(this, UserGitInfoActivity.class);
                            userGitInfoIntent.putExtra(RecyclerViewAdapter.KEY, String.valueOf(data.getPath().substring(1)));
                            userGitInfoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(userGitInfoIntent);
                            findUser = true;
                            break;
                        } else if (url.equals(user.getGoogleUrl())) {
                            Intent userGoogleInfoIntent = new Intent(this, UserGoogleInfoActivity.class);
                            userGoogleInfoIntent.putExtra(RecyclerViewAdapter.KEY, String.valueOf(data.getPath().substring(5)));
                            userGoogleInfoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(userGoogleInfoIntent);
                            findUser = true;
                            break;
                        } else {
                            findUser = false;
                        }
                    }
                }
                if (!findUser)
                    Toast.makeText(this, R.string.no_user_in_list, Toast.LENGTH_LONG).show();
            }
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


