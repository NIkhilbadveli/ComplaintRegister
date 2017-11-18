package com.crazy.insane.trial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HostelEditingActivity extends AppCompatActivity {
    static long complaintnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_editing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference hostel = FirebaseDatabase.getInstance().getReference().child("/Hostel Complaints").getRef();
        hostel.child(user.getDisplayName()).addValueEventListener(new ValueEventListener() {
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

        Button submit_button = (Button) findViewById(R.id.edit_hostel_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText complaint = (EditText) findViewById(R.id.edit_hostel_complaint_text);
                EditText subject = (EditText) findViewById(R.id.edit_hostel_subject_text);
                RadioGroup radiogroup = (RadioGroup) findViewById(R.id.edit_hostel_radiogroup);
                EditText roomno = (EditText) findViewById(R.id.edit_hostel_room_text);

                if (subject.getText().toString().isEmpty() || complaint.getText().toString().isEmpty() ||
                        radiogroup.getCheckedRadioButtonId() == -1 || roomno.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT).show();

                } else {
                    int radioButtonID = radiogroup.getCheckedRadioButtonId();
                    View radioButton = radiogroup.findViewById(radioButtonID);
                    int idx = radiogroup.indexOfChild(radioButton);
                    String token = String.valueOf(complaintnum);
                    int num = Integer.parseInt(roomno.getText().toString());
                    if (idx == 0) {
                        if (num >= 100 && num <= 125 || num >= 200 && num <= 225 || num >= 0 && num <= 25) {
                            hostel.child(user.getDisplayName() + "/" + token + "/Room Number").setValue(roomno.getText().toString());
                            hostel.child(user.getDisplayName() + "/" + token + "/Subject").setValue(subject.getText().toString());
                            hostel.child(user.getDisplayName() + "/" + token + "/Complaint").setValue(complaint.getText().toString());
                            hostel.child(user.getDisplayName() + "/" + token + "/Hostel").setValue("Hostel 1");
                            hostel.child(user.getDisplayName()).child(token).child("Status").setValue("Complaint Sent");
                            hostel.child(user.getDisplayName()).child(token).child("Flag").setValue("Sent");
                            Toast.makeText(getApplicationContext(), "Complaint Sent", Toast.LENGTH_SHORT).show();

                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Enter proper room number", Toast.LENGTH_SHORT).show();
                        }
                    } else if (idx == 1) {
                        if (num >= 100 && num <= 130 || num >= 200 && num <= 230) {

                            hostel.child(user.getDisplayName() + "/" + token + "/Room Number").setValue(roomno.getText().toString());
                            hostel.child(user.getDisplayName() + "/" + token + "/Hostel").setValue("Hostel 2");
                            hostel.child(user.getDisplayName() + "/" + token + "/Subject").setValue(subject.getText().toString().toUpperCase());
                            hostel.child(user.getDisplayName() + "/" + token + "/Complaint").setValue(complaint.getText().toString().toUpperCase());
                            hostel.child(user.getDisplayName()).child(token).child("Status").setValue("Complaint Sent");
                            hostel.child(user.getDisplayName()).child(token).child("Flag").setValue("Sent");
                            Toast.makeText(getApplicationContext(), "Complaint Sent", Toast.LENGTH_SHORT).show();

                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Enter proper room number", Toast.LENGTH_SHORT).show();
                        }
                    } else if (idx == 2) {

                        hostel.child(user.getDisplayName() + "/" + token + "/Room Number").setValue(roomno.getText().toString());
                        hostel.child(user.getDisplayName() + "/" + token + "/Subject").setValue(subject.getText().toString().toUpperCase());
                        hostel.child(user.getDisplayName() + "/" + token + "/Complaint").setValue(complaint.getText().toString().toUpperCase());
                        hostel.child(user.getDisplayName()).child(token).child("Status").setValue("Complaint Sent");
                        hostel.child(user.getDisplayName()).child(token).child("Flag").setValue("Sent");
                        hostel.child(user.getDisplayName() + "/" + token + "/Hostel").setValue("Girls Hostel");
                        Toast.makeText(getApplicationContext(), "Complaint Sent", Toast.LENGTH_SHORT).show();

                        finish();

                    }
                    complaintnum++;
                }


            }
        });
    }


}
