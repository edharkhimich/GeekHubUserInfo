package com.appleeeee.geekhubgrouplist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.activities.UserGitInfoActivity;
import com.appleeeee.geekhubgrouplist.activities.UserGoogleInfoActivity;
import com.appleeeee.geekhubgrouplist.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

import static com.appleeeee.geekhubgrouplist.util.Constants.KEY;

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<User, RecyclerViewAdapter.ViewHolder> {

    private RealmResults<User> data;
    private Context context;

    public RecyclerViewAdapter(Context context, RealmResults<User> data) {
        super(context, data, true);
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onItemRemove(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView) {
        final int adapterPosition = viewHolder.getAdapterPosition();
        final User deletedUser = data.get(adapterPosition);
        final Realm instance = Realm.getDefaultInstance();
        final User restoreUser = instance.copyFromRealm(deletedUser);
        Snackbar snackbar = Snackbar
                .make(recyclerView, R.string.user_removed, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        instance.beginTransaction();
                        instance.copyToRealm(restoreUser);
                        notifyItemInserted(adapterPosition);
                        instance.commitTransaction();
                    }
                });
        snackbar.show();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                deletedUser.deleteFromRealm();
            }
        });
    }

    public void setData(RealmResults<User> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_rec_view)
        TextView recTv;
        @BindView(R.id.rec_view_btn)
        Button buttonToGit;
        @BindView(R.id.item_layout)
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final User userItem) {
            recTv.setText(userItem.getName());
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent googleIntent = new Intent(context, UserGoogleInfoActivity.class);
                    googleIntent.putExtra(KEY, userItem.getGoogleNickName());
                    googleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(googleIntent);
                }
            });
            buttonToGit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent gitIntent = new Intent(context, UserGitInfoActivity.class);
                    gitIntent.putExtra(KEY, userItem.getGitNickName());
                    gitIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(gitIntent);
                }
            });
        }
    }
}


