package com.learning.insane.wecheaters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends Activity {

    private EditText firstNameEditText, lastNameEditText, passEditText, rePassEditText, emailEditText;
    private Button signUpButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstNameEditText = findViewById(R.id.first_name);
        passEditText = findViewById(R.id.password_edit_text);
        rePassEditText = findViewById(R.id.password_retype_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        signUpButton = findViewById(R.id.sign_up_button);

        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = firstNameEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                final String password = passEditText.getText().toString();
                String retypePassword = rePassEditText.getText().toString();



                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference()
                                            .child("Users").child(uid);
                                    HashMap<String, String> hsmap = new HashMap<>();
                                    hsmap.put("name", name);
                                    hsmap.put("Mail", email);
                                    hsmap.put("Pass", password);
                                    dbr.setValue(hsmap);
                                    Toast.makeText(getApplicationContext(), "Successful!!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed!!!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });







            }

        });
    }
}
