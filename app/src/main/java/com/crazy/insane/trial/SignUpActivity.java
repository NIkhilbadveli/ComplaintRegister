package com.crazy.insane.trial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail;
    private ProgressBar progressBar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String random_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance

        Button  btnSignIn,btnSignUp;

        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        inputEmail = (EditText) findViewById(R.id.email_signup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }});

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                boolean connected = cd.isConnectingToInternet();
                        if (connected) {
                            String email = inputEmail.getText().toString();
                            SmailVerifier smailVerifier = new SmailVerifier();
                            if (smailVerifier.verify(email)) {
                                progressBar.setVisibility(View.VISIBLE);
                                //creating user with random password

                                random_password = "Nkdmhbtylrc";
                                auth.createUserWithEmailAndPassword(email, random_password)
                                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                            }
                                        });
                                auth.sendPasswordResetEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                }
                                            }
                                        });
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Password email sent", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                finish();
                            } else {
                                    inputEmail.setError("Enter a valid email address");
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
    }
