package com.learning.insane.wecheaters.Util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learning.insane.wecheaters.R;
import com.learning.insane.wecheaters.model.Shortcut;

import java.util.ArrayList;

public class UserShortcutsRecyclerAdapter extends RecyclerView.Adapter<ShortcutViewHolder> {
    private ArrayList<Shortcut> shortcuts;

    public UserShortcutsRecyclerAdapter(ArrayList<Shortcut> shortcuts) {
        this.shortcuts = shortcuts;
    }

    @Override
    public ShortcutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shortcut_view, parent, false);
        return new ShortcutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShortcutViewHolder shortcutViewHolder, int position) {
        if(shortcuts.get(position) != null)
            shortcutViewHolder.setShortcut(shortcuts.get(position));
    }

    @Override
    public int getItemCount() {
        return shortcuts.size();
    }
}
