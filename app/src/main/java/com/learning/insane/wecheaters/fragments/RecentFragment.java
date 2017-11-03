package com.learning.insane.wecheaters.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.learning.insane.wecheaters.R;
import com.learning.insane.wecheaters.Util.Constants;
import com.learning.insane.wecheaters.Util.ShortcutViewHolder;
import com.learning.insane.wecheaters.model.Shortcut;

public class RecentFragment extends Fragment {

    private FirebaseRecyclerAdapter<Shortcut, ShortcutViewHolder> firebaseRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private Query mDatabaseQuery;

    public RecentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mRecyclerView =  (RecyclerView) inflater.inflate(R.layout.fragment_recent, container, false);
        mRecyclerView.setHasFixedSize(true);
        mDatabaseQuery = FirebaseDatabase.getInstance().getReference().child(Constants.SHORTCUTS).orderByKey().limitToFirst(50);
        getActivity().getActionBar().setTitle("Favourites");

        //RecyclerAdapter Code
        FirebaseRecyclerOptions<Shortcut> options = new FirebaseRecyclerOptions.Builder<Shortcut>()
                .setQuery(mDatabaseQuery, Shortcut.class).build();

        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Shortcut, ShortcutViewHolder>(options) {

                    @Override
                    public ShortcutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.shortcut_view, parent, false
                        );
                        return new ShortcutViewHolder(v);
                    }

                    @Override
                    protected void onBindViewHolder(ShortcutViewHolder shortcutView, int position, Shortcut shortcut) {
                        shortcutView.setShortcut(shortcut);
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));

        return mRecyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        firebaseRecyclerAdapter.stopListening();
        super.onStop();
    }


}