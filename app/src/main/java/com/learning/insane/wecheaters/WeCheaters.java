package com.learning.insane.wecheaters;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class WeCheaters extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
