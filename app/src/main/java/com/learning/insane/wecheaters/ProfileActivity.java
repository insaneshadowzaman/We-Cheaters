package com.learning.insane.wecheaters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.insane.wecheaters.Util.Constants;
import com.learning.insane.wecheaters.Util.UserShortcutsRecyclerAdapter;
import com.learning.insane.wecheaters.model.Shortcut;
import com.learning.insane.wecheaters.model.User;

import java.util.ArrayList;

public class ProfileActivity extends Activity {

    User user;
    RecyclerView mRecyclerView;
    UserShortcutsRecyclerAdapter adapter;

    TextView userNameTextView;
    TextView shortcutCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameTextView = findViewById(R.id.profile_user_name);
        shortcutCountTextView = findViewById(R.id.profile_shortcut_count);

        user = new User(getIntent().getStringExtra(User.ID));
        mRecyclerView = findViewById(R.id.profile_recycler_view);

        final ArrayList<Shortcut> shortcuts = new ArrayList<>();
        final ArrayList<Shortcut> favouriteShortcuts = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("/"+Constants.USERS+"/"+user.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user.setName(dataSnapshot.child(User.NAME).getValue(String.class));
                        user.setEmail(dataSnapshot.child(User.MAIL).getValue(String.class));
                        user.setShortcutCount(dataSnapshot.child(User.SHORTCUT_COUNT).getValue(Integer.class));
                        user.setShortcuts(new ArrayList<String>());
                        user.setFavShortcuts(new ArrayList<String>());

                        userNameTextView.setText(user.getName());
                        shortcutCountTextView.setText(String.valueOf(user.getShortcutCount()));

                        for(DataSnapshot snapshot : dataSnapshot.child(User.SHORTCUTS).getChildren()) {
                            user.getShortcuts().add(snapshot.getValue(String.class));
                        }
                        for(DataSnapshot snapshot : dataSnapshot.child(User.FAV_SHORTCUTS).getChildren()) {
                            user.getFavShortcuts().add(snapshot.getValue(String.class));
                        }

                        DatabaseReference shortcutIdDb = FirebaseDatabase.getInstance().getReference(
                                "/"+Constants.SHORTCUTS);
                        for(String shortcutId : user.getShortcuts()) {
                            shortcutIdDb.child(shortcutId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Shortcut shortcut = dataSnapshot.getValue(Shortcut.class);
                                    shortcuts.add(shortcut);
                                    adapter.notifyItemInserted(shortcuts.size());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        for(String shortcutId : user.getFavShortcuts()) {
                            shortcutIdDb.child(shortcutId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Shortcut shortcut = dataSnapshot.getValue(Shortcut.class);
                                    favouriteShortcuts.add(shortcut);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new UserShortcutsRecyclerAdapter(shortcuts);
        mRecyclerView.setAdapter(adapter);
    }
}
