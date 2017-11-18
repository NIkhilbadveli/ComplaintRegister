package com.crazy.insane.trial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessEditingActivity extends AppCompatActivity {
    static long complaintnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_editing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mess = FirebaseDatabase.getInstance().getReference().child("/Mess Complaints").getRef();

        mess.child(user.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long count = dataSnapshot.getChildrenCount();
                complaintnum = count + 1;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        Button submit_button = (Button) findViewById(R.id.edit_mess_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText complaint = (EditText) findViewById(R.id.edit_mess_complaint_text);
                EditText subject = (EditText) findViewById(R.id.edit_mess_subject_text);
                if (subject.getText().toString().isEmpty() || complaint.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT).show();

                } else {
                    String token = String.valueOf(complaintnum);
                    mess.child(user.getDisplayName() + "/" + token + "/Subject").setValue(subject.getText().toString());
                    mess.child(user.getDisplayName() + "/" + token + "/Complaint").setValue(complaint.getText().toString());
                    mess.child(user.getDisplayName()).child(token).child("Status").setValue("Complaint Sent");
                    mess.child(user.getDisplayName()).child(token).child("Flag").setValue("Sent");
                    complaintnum++;
                    Toast.makeText(getApplicationContext(), "Complaint Sent", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

    }
}
