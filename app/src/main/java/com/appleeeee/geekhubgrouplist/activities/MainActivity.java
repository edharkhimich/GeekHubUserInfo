package com.appleeeee.geekhubgrouplist.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.adapter.RecyclerViewAdapter;
import com.appleeeee.geekhubgrouplist.model.User;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "mLogs";
    private static final String GITHUB_HOST = "github.com";
    private static final String GOOGLE_HOST = "plus.google.com";
    private boolean findUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RecyclerViewActivity recActivity = new RecyclerViewActivity();
        recActivity.addUserList();
        urlHandling();


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
                            Log.d(LOG, data.getPath());
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
    }

