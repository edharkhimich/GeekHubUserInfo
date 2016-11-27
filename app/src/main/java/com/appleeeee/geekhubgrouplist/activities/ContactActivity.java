package com.appleeeee.geekhubgrouplist.activities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.adapter.MyContactAdapter;
import com.appleeeee.geekhubgrouplist.model.Contact;

import java.util.ArrayList;

import butterknife.BindView;

public class ContactActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rec_contact_view)
    RecyclerView recyclerView;

    private final String[] FROM = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER};
    private static final int LOADER_ID = 1;
    MyContactAdapter adapter;
    private String name;
    private String number;
    Cursor data;

    private static final Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    //
//    private final String[] FROM = new String[]{DISPLAY_NAME, NUMBER};
    private static final String TAG = "mLogs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_rec_view);
        Log.d(TAG, "onCreate: ");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, FROM, null, null, sort);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        recyclerView.setAdapter(new MyContactAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}