package com.appleeeee.geekhubgrouplist.activities;

import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.adapter.MyContactAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rec_contact_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.r_view)
    RelativeLayout relativeLayout;

    private final String[] FROM = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER};
    private static final int LOADER_ID = 1;
    private static final String ASC = " ASC";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final String TAG = "mLogs";

    Unbinder unbinder;
    MyContactAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_rec_view);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        adapter = new MyContactAdapter();
        fabOnClick();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ASC;
        return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, FROM, null, null, sort);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        recyclerView.setAdapter(new MyContactAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fabOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialogView = getLayoutInflater().inflate(R.layout.dialog_interface, null);
                new AlertDialog.Builder(ContactActivity.this)
                        .setView(dialogView)
                        .setTitle(R.string.add_contact)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                EditText user = (EditText) dialogView.findViewById(R.id.username);
                                EditText phone = (EditText) dialogView.findViewById(R.id.phone_number);
                                if (!user.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()) {
                                    String username = user.getText().toString().trim();
                                    String phone_number = phone.getText().toString().trim();
                                    addContact(username, phone_number);
                                    adapter.notifyDataSetChanged();
                                    Snackbar snackBar = Snackbar.make(relativeLayout, getString(R.string.call_me) + username + " created", Snackbar.LENGTH_LONG);
                                    snackBar.show();
                                } else {
                                    Toast.makeText(ContactActivity.this, R.string.enter_your_contact, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    public void addContact(String displayName, String number) {

        ArrayList<ContentProviderOperation> contentProv = new ArrayList<>();
        int contactIndex = contentProv.size();

        contentProv.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        contentProv.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName)
                .build());

        contentProv.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProv);
        } catch (OperationApplicationException exp) {
            exp.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
