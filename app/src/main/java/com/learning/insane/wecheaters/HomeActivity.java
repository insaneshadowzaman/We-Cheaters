package com.learning.insane.wecheaters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.insane.wecheaters.fragments.FavouritesFragment;
import com.learning.insane.wecheaters.fragments.RecentFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends Activity {

    private Toolbar toolbar;
    private Button addButton;
    private Integer currentFragment;


    public static final String RECENT = "Recent", POPULAR = "Popular", ME = "Me";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        addButton = findViewById(R.id.addShortcutButton);

        setActionBar(toolbar);

        getFragmentManager().beginTransaction().add(R.id.contentContainer, new RecentFragment()).commit();
        currentFragment = 0;

        BottomBar bottomBar = findViewById(R.id.bottom_navigation_bar);
        bottomBar.setActiveTabColor(getResources().getColor(android.R.color.holo_red_dark));
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment;
                switch (tabId) {
                    case R.id.item_recent:
                        if(currentFragment != 0) {
                            toolbar.setSubtitle(RECENT);
                            fragment = new RecentFragment();
                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).commit();
                            currentFragment = 0;
                        }
                        break;
                    case R.id.item_popular:
                        if(currentFragment != 1) {
                            toolbar.setSubtitle(POPULAR);
                            fragment = new FavouritesFragment();
                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).commit();
                            currentFragment = 1;
                        }
                        break;
                    case R.id.item_me:
                        if(currentFragment != 2) {
//                            currentFragment = 2;
//                            toolbar.setSubtitle(ME);
                            //TODO
//                        RecentFragment recentFragment = new RecentFragment();
//                        getFragmentManager().beginTransaction().replace(R.id.contentContainer, recentFragment).commit();
                        }
                        break;
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddShortcutActivity.class);
                startActivity(intent);
            }
        });
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
}
