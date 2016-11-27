package com.appleeeee.geekhubgrouplist.adapter;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appleeeee.geekhubgrouplist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.ContactViewHolder> {

    private Cursor mCursor;
    private int mNameColIdx, mIdColIdx;

    private static final String DISPLAY_NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    private static final String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

    public MyContactAdapter() {
    }

    public MyContactAdapter(Cursor cursor) {
        mCursor = cursor;
        mNameColIdx = cursor.getColumnIndex(DISPLAY_NAME);
        mIdColIdx = cursor.getColumnIndex(NUMBER);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View listItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_list_item, parent, false);
        return new ContactViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int pos) {
        mCursor.moveToPosition(pos);
        String contactName = mCursor.getString(mNameColIdx);
        String contactNum = mCursor.getString(mIdColIdx);

        contactViewHolder.name.setText(contactName);
        contactViewHolder.number.setText(contactNum);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView name;
        @BindView(R.id.tvNumber)
        TextView number;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}