package com.learning.insane.wecheaters;

import android.app.Activity;
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
import com.learning.insane.wecheaters.fragments.MyShortcutsFragment;
import com.learning.insane.wecheaters.fragments.RecentFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends Activity {

    private Toolbar toolbar;
    private Integer currentFragment;

    public static final String RECENT = "Recent", FAVOURITES = "Favourites", ME = "Me";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        Button addButton = findViewById(R.id.addShortcutButton);

        setActionBar(toolbar);

        final RecentFragment recentFragment = new RecentFragment();
        final FavouritesFragment favouritesFragment = new FavouritesFragment();
        final MyShortcutsFragment myShortcutsFragment = new MyShortcutsFragment();

        getFragmentManager().beginTransaction().add(R.id.contentContainer, recentFragment).commit();
        currentFragment = 0;

        BottomBar bottomBar = findViewById(R.id.bottom_navigation_bar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.item_recent:
                        if(currentFragment != 0) {
                            toolbar.setSubtitle(RECENT);
                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, recentFragment).commit();
                            currentFragment = 0;
                        }
                        break;
                    case R.id.item_popular:
                        if(currentFragment != 1) {
                            toolbar.setSubtitle(FAVOURITES);
                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, favouritesFragment).commit();
                            currentFragment = 1;
                        }
                        break;
                    case R.id.item_me:
                        if(currentFragment != 2) {
                            toolbar.setSubtitle(ME);
                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, myShortcutsFragment).commit();
                            currentFragment = 2;
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
