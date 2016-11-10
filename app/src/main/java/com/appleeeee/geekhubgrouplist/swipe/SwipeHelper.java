package com.appleeeee.geekhubgrouplist.swipe;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.appleeeee.geekhubgrouplist.adapter.RecyclerViewAdapter;

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    public SwipeHelper(RecyclerViewAdapter adapter, RecyclerView recyclerView) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemRemove(viewHolder, recyclerView);
    }
}
