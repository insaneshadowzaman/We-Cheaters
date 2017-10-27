package com.learning.insane.wecheaters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends Activity {

    Toolbar toolbar;
    Button addButton;


    //Test main RecyclerView
//    RecyclerView mRecyclerView;
//    Query mQuery;
//    FirebaseRecyclerAdapter<Shortcut, ViewHolder> firebaseRecyclerAdapter;

    public static final String RECENT = "Recent", POPULAR = "Popular", ME = "Me";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        addButton = findViewById(R.id.addShortcutButton);

        setActionBar(toolbar);

        RecentFragment recentFragment = new RecentFragment();
        getFragmentManager().beginTransaction().add(R.id.contentContainer, recentFragment).commit();

//        BottomBar bottomBar = findViewById(R.id.bottom_navigation_bar);
//        bottomBar.setActiveTabColor(getResources().getColor(android.R.color.holo_red_dark));
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                switch (tabId) {
//                    case R.id.item_recent:
//                        toolbar.setSubtitle(RECENT);
////                        RecentFragment recentFragment = new RecentFragment();
////                        getFragmentManager().beginTransaction().replace(R.id.contentContainer, recentFragment).commit();
//                        break;
//                    case R.id.item_popular:
//                        toolbar.setSubtitle(POPULAR);
////                        RecentFragment recentFragment = new RecentFragment();
////                        getFragmentManager().beginTransaction().replace(R.id.contentContainer, recentFragment).commit();
//                        // TODO:
//                        break;
//                    case R.id.item_me:
//                        toolbar.setSubtitle(ME);
////                        RecentFragment recentFragment = new RecentFragment();
////                        getFragmentManager().beginTransaction().replace(R.id.contentContainer, recentFragment).commit();
//                        // TODO:
//                        break;
//                }
//            }
//        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddShortcutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        firebaseRecyclerAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, SplashScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }



    //Test Viewholder
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView name;
//        TextView description;
//        TextView vote;
//        TextView uploader;
//        TextView timestamp;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            name = (TextView) itemView.findViewById(R.id.shortcut_name);
//            description = (TextView) itemView.findViewById(R.id.shortcut_description);
//            vote = (TextView) itemView.findViewById(R.id.shortcut_vote_count);
//            uploader = (TextView) itemView.findViewById(R.id.shortcut_uploader);
//            timestamp = (TextView) itemView.findViewById(R.id.shortcut_timestamp);
//        }
//        public void setName(String name) {
//            this.name.setText(name);
//        }
//        public void setDescription(String description) {
//            this.description.setText(description);
//        }
//        public void setVote(int votes) {
//            this.vote.setText(votes);
//        }
//        public void setUploader(String uploader) {
//            this.uploader.setText(uploader);
//        }
//        public void setTimestamp(int timestamp) {
//            this.timestamp.setText(timestamp);
//        }
//    }
}
