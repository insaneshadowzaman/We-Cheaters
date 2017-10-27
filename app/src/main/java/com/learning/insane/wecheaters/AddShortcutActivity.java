package com.learning.insane.wecheaters;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.learning.insane.wecheaters.model.Shortcut;

import net.cachapa.expandablelayout.ExpandableLayout;

public class AddShortcutActivity extends Activity {

    private class ExpandableTextView implements View.OnClickListener {

        ExpandableLayout e;

        public ExpandableTextView(ExpandableLayout e) {
            this.e = e;
        }

        @Override
        public void onClick(View view) {
            e.toggle();
        }
    }

//    Toolbar toolbar;
    Button previouslyPressed;
    String keySelected;
    Shortcut shortcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shortcut);

        shortcut = new Shortcut();
        shortcut.setOwner(FirebaseAuth.getInstance().getCurrentUser().getUid());

        initHoldKeyboard();
        initKeyboards();
        initExpanders();
        initUploadButton();
    }

    private void initUploadButton() {
        Button b = findViewById(R.id.button_upload);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shortcut.setName(getName());
                shortcut.setDescription(getDesciption());
                shortcut.setKey(keySelected);
                shortcut.setTimeStamp(ServerValue.TIMESTAMP);
                shortcut.previousRequiredShortcutId = "Test prev ID";

                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Shortcuts");
                String id = db.push().getKey();
                shortcut.setId(id);
                db.child(id).setValue(shortcut);
//                finish();
            }

            private String getName() {
                EditText e = findViewById(R.id.add_shortcut_name_edittext);
                return e.getText().toString();
            }
            private String getDesciption() {
                EditText e = findViewById(R.id.add_shortcut_description_edittext);
                return e.getText().toString();
            }
        });
    }


    private void initHoldKeyboard() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(Color.GRAY);
                switch (view.getId()) {
                    case R.id.keyboard_control_button_left_ctrl:
                        shortcut.leftCtrl = !shortcut.leftCtrl;
                        break;
                    case R.id.keyboard_control_button_right_ctrl:
                        shortcut.rightCtrl = !shortcut.rightCtrl;
                        break;
                    case R.id.keyboard_control_button_left_alt:
                        shortcut.leftAlt = !shortcut.leftAlt;
                        break;
                    case R.id.keyboard_control_button_right_alt:
                        shortcut.rightAlt = !shortcut.rightAlt;
                        break;
                    case R.id.keyboard_control_button_tab:
                        shortcut.tab = !shortcut.tab;
                        break;
                    case R.id.keyboard_control_button_caps:
                        shortcut.caps = !shortcut.caps;
                        break;
                    case R.id.keyboard_control_button_left_shift:
                        shortcut.leftShift = !shortcut.leftShift;
                        break;
                    case R.id.keyboard_control_button_right_shift:
                        shortcut.rightShift = !shortcut.rightShift;
                        break;
                }
            }
        };
        LinearLayout container = findViewById(R.id.hold_key_container);
        for(int i = 0; i<container.getChildCount(); i++) {

            LinearLayout l = (LinearLayout) container.getChildAt(i);

            for(int j = 0; j<l.getChildCount(); j++) {
                Button b = (Button) l.getChildAt(j);
                b.setOnClickListener(listener);
            }
        }
    }

    private void initExpanders() {
        TextView text = findViewById(R.id.letters_and_numbers_textView);
        ExpandableLayout expandableLayout = findViewById(R.id.letters_and_numbers_expander);
        text.setOnClickListener(new ExpandableTextView(expandableLayout));

        text = findViewById(R.id.function_keys_textView);
        expandableLayout = findViewById(R.id.fundtion_keys_expander);
        text.setOnClickListener(new ExpandableTextView(expandableLayout));

        text = findViewById(R.id.special_key_textview);
        expandableLayout = findViewById(R.id.special_keyboard_expander);
        text.setOnClickListener(new ExpandableTextView(expandableLayout));
    }

    private void initKeyboards() {

        View.OnClickListener keyboardButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                keySelected = b.getText().toString();
                b.setBackgroundColor(getResources().getColor(R.color.secondaryColor));

                if(previouslyPressed != null) {
                    previouslyPressed.setBackgroundColor(Color.GRAY);
                }
                previouslyPressed = b;
            }
        };

        for(char i = 'A'; i <= 'Z'; i++) {
            int buttonResId = getResources().getIdentifier("keyboard_button_" + i, "id", getPackageName());
            Button b = findViewById(buttonResId);
            b.setOnClickListener(keyboardButtonClickListener);
        }

        for(int i = 1; i<=12; i++) {
            int buttonResId = getResources().getIdentifier("keyboard_button_F" + i, "id", getPackageName());
            Button b = findViewById(buttonResId);
            b.setOnClickListener(keyboardButtonClickListener);

        }

        for(int i = 48; i<=57; i++) {
            int buttonResId = getResources().getIdentifier("keyboard_button_" + i, "id", getPackageName());
            Button b = findViewById(buttonResId);
            b.setOnClickListener(keyboardButtonClickListener);
        }

        for(int i = 33; i<=46; i++) {
            int buttonResId = getResources().getIdentifier("keyboard_button_special_" + i, "id", getPackageName());
            Button b = findViewById(buttonResId);
            b.setOnClickListener(keyboardButtonClickListener);
        }
        for(int i = 58; i<=64; i++) {
            int buttonResId = getResources().getIdentifier("keyboard_button_special_" + i, "id", getPackageName());
            Button b = findViewById(buttonResId);
            b.setOnClickListener(keyboardButtonClickListener);
        }

    }


}
