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
import com.appleeeee.geekhubgrouplist.activities.RecyclerViewActivity;
import com.appleeeee.geekhubgrouplist.activities.UserGitInfoActivity;
import com.appleeeee.geekhubgrouplist.activities.UserGoogleInfoActivity;
import com.appleeeee.geekhubgrouplist.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String KEY = "key";

    private List<User> list;
    private Context context;
    private List<User> userToDelete = new ArrayList<>();


    public RecyclerViewAdapter(Context context, List<User> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void onItemRemove(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView) {
        final int adapterPosition = viewHolder.getAdapterPosition();
        final User mUser = list.get(adapterPosition);
        Snackbar snackbar = Snackbar
                .make(recyclerView, R.string.user_removed, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int mAdapterPosition = viewHolder.getAdapterPosition();
                        list.add(adapterPosition, mUser);
                        notifyItemInserted(adapterPosition);
                        recyclerView.scrollToPosition(mAdapterPosition);
                        userToDelete.remove(mUser);
                    }
                });
        snackbar.show();
        list.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        userToDelete.add(mUser);
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


