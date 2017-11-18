package com.crazy.insane.trial;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView email = (TextView) findViewById(R.id.email_ad);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Button changePassword = (Button) findViewById(R.id.change_password);

        email.setText(user.getEmail());
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        final boolean connected = cd.isConnectingToInternet();

            changePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (connected) {
                    if (user != null) {
                        auth.sendPasswordResetEmail(user.getEmail())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Snackbar.make(coordinatorLayout, "Password reset email sent", Snackbar.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(UserProfileActivity.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    } else {
                        Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }
}
