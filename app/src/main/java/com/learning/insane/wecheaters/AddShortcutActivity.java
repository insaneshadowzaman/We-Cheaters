package com.learning.insane.wecheaters;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.learning.insane.wecheaters.Util.Constants;
import com.learning.insane.wecheaters.model.Shortcut;
import com.learning.insane.wecheaters.model.User;

import net.cachapa.expandablelayout.ExpandableLayout;


public class AddShortcutActivity extends Activity {

    private class ExpandableTextView implements View.OnClickListener {

        ExpandableLayout e;

        ExpandableTextView(ExpandableLayout e) {
            this.e = e;
        }

        @Override
        public void onClick(View view) {
            e.toggle();
        }
    }

    Button previouslyPressed;
    String keySelected;
    Shortcut shortcut;
    boolean[] controlButtons;
    String uid;

    DatabaseReference shortcutDatabase;
    DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shortcut);

        controlButtons = new boolean[8];

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        shortcutDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.SHORTCUTS);
        userDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(uid);

        initHoldKeyboard();
        initKeyboards();
        initExpander();
        initUploadButton();
    }

    private void initUploadButton() {
        Button b = findViewById(R.id.button_upload);
        b.setOnClickListener(new View.OnClickListener() {
            String id;
            @Override
            public void onClick(View view) {

                shortcut = new Shortcut(
                        getName(),
                        getDesciption(),
                        uid,
                        getAppName(),
                        controlButtons[0],
                        controlButtons[1],
                        controlButtons[2],
                        controlButtons[3],
                        controlButtons[4],
                        controlButtons[5],
                        controlButtons[6],
                        keySelected,
                        "Test Prev"
                );

                id = shortcutDatabase.push().getKey();
                shortcut.setId(id);


                shortcutDatabase.child(id).setValue(shortcut).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        userDatabase.child(User.SHORTCUT_COUNT).runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                Long count = mutableData.getValue(Long.class);
                                if(count == null) mutableData.setValue(1);
                                else mutableData.setValue(count+1);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                Log.d("TRANSACTION : ", "Firebase Transaction done!!!");
                            }
                        });
                        userDatabase.child(Constants.SHORTCUTS).child(id).setValue(shortcut.getId());
                        finish();
                    }

                });
            }

            private String getName() {
                EditText e = findViewById(R.id.add_shortcut_name_edittext);
                return e.getText().toString();
            }
            private String getDesciption() {
                EditText e = findViewById(R.id.add_shortcut_description_edittext);
                return e.getText().toString();
            }
            private String getAppName() {
                EditText e = findViewById(R.id.add_shortcut_app_name_edittext);
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
                        controlButtons[0] = !controlButtons[0];
                        break;
                    case R.id.keyboard_control_button_left_alt:
                        controlButtons[1] = !controlButtons[1];
                        break;
                    case R.id.keyboard_control_button_left_shift:
                        controlButtons[2] = !controlButtons[2];
                        break;
                    case R.id.keyboard_control_button_right_ctrl:
                        controlButtons[3] = !controlButtons[3];
                        break;
                    case R.id.keyboard_control_button_right_alt:
                        controlButtons[4] = !controlButtons[4];
                        break;
                    case R.id.keyboard_control_button_right_shift:
                        controlButtons[5] = !controlButtons[5];
                        break;
                    case R.id.keyboard_control_button_caps:
                        controlButtons[6] = !controlButtons[6];
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

    private void initExpander() {
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
