package com.learning.insane.wecheaters;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toolbar;

public class AddShortcutActivity extends Activity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shortcut);
        initToolBar(toolbar);
    }


    private void initToolBar(Toolbar toolbar) {

    }
}
