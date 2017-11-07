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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.learning.insane.wecheaters.R;
import com.learning.insane.wecheaters.Util.Constants;
import com.learning.insane.wecheaters.Util.ShortcutViewHolder;
import com.learning.insane.wecheaters.model.Shortcut;
import com.learning.insane.wecheaters.model.User;

public class MyShortcutsFragment extends Fragment {

    private DatabaseReference shortcutsDb;
    private FirebaseRecyclerAdapter<String, ShortcutViewHolder> mFirebaseRecyclerAdapter;

    public MyShortcutsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_my_shortcuts, container, false);
        recyclerView.setHasFixedSize(true);

//        Toast.makeText(getActivity(), "Created Fragment", Toast.LENGTH_LONG).show();

        shortcutsDb = FirebaseDatabase.getInstance().getReference().child(Constants.SHORTCUTS);
        Query mQuery = FirebaseDatabase.getInstance().getReference()
                .child(Constants.USERS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(User.SHORTCUTS).orderByKey().limitToFirst(50);

        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(mQuery, String.class).build();

        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<String, ShortcutViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final ShortcutViewHolder shortcutViewHolder, int position, String id) {
                shortcutsDb.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Shortcut shortcut = dataSnapshot.getValue(Shortcut.class);
                        shortcutViewHolder.setShortcut(shortcut);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public ShortcutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shortcut_view, parent, false);
                return new ShortcutViewHolder(v);
            }
        };

        recyclerView.setAdapter(mFirebaseRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));

        return recyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        mFirebaseRecyclerAdapter.stopListening();
        super.onStop();

    }
}
