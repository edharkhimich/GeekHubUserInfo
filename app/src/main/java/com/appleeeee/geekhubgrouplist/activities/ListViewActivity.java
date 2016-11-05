package com.appleeeee.geekhubgrouplist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appleeeee.geekhubgrouplist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewActivity extends AppCompatActivity {

    @BindView(R.id.list_view_toolbar)
    Toolbar listViewToolbar;
    @BindView(R.id.list_view)
    ListView listView;

    private ArrayAdapter<String> arrayAdapter;
    private String[] array;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);

        setToolbar();
        array = getResources().getStringArray(R.array.group_list);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_content, array);
        listView.setAdapter(arrayAdapter);
    }

    private void setToolbar() {
        setSupportActionBar(listViewToolbar);
        getSupportActionBar().setTitle(R.string.geekhub_group);
        listViewToolbar.setNavigationIcon(R.drawable.ic_action_back);
        listViewToolbar.setBackgroundColor(getResources().getColor(R.color.lightBlueForBack));
        listViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
