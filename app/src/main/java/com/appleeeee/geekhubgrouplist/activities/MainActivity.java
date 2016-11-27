package com.appleeeee.geekhubgrouplist.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.adapter.RecyclerViewAdapter;
import com.appleeeee.geekhubgrouplist.model.User;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String GITHUB_HOST = "github.com";
    private static final String GOOGLE_HOST = "plus.google.com";
    private static final String STATE = "state";
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
    void contactLickOnClick(){
        Intent contactIntent = new Intent(this, ContactActivity.class);
        startActivity(contactIntent);
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

    private class HeadphonesReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra(STATE, -1);
                switch (state) {
                    case 0:
                        Toast.makeText(context, R.string.headset_is_unplagged, Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(context,
                                R.string.headset_is_plugged, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, R.string.no_idea, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

