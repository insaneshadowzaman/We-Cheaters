package com.learning.insane.wecheaters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        firstNameEditText = (EditText) findViewById(R.id.first_name);
        lastNameEditText = (EditText) findViewById(R.id.last_name);
        passEditText = (EditText) findViewById(R.id.password_edit_text);
        rePassEditText = (EditText) findViewById(R.id.password_retype_edit_text);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        signUpButton = (Button) findViewById(R.id.sign_up_button);

        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = firstNameEditText.getText().toString();
                final String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passEditText.getText().toString();
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
                                    hsmap.put("FirstName", firstName);
                                    hsmap.put("LastName", lastName);
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
