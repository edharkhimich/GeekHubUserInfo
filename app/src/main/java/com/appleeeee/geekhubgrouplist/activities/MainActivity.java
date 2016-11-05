package com.appleeeee.geekhubgrouplist.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appleeeee.geekhubgrouplist.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_list_view)
    void listViewOnClick() {
        Intent listViewIntent = new Intent(getApplicationContext(), ListViewActivity.class);
        startActivity(listViewIntent);
    }

    @OnClick(R.id.button_recycler_view)
    void recyclerViewOnClick() {
        Intent recViewintent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
        startActivity(recViewintent);
    }
}
