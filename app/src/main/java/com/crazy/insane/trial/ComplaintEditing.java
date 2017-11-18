package com.crazy.insane.trial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crazy.insane.trial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ComplaintEditing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_editing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String subject_name = getIntent().getExtras().getString("subject");
        String act_name = getIntent().getExtras().getString("Activity");
        final String token = getIntent().getExtras().getString("Position");

        final EditText subject = (EditText) findViewById(R.id.sub_complaint);
        final EditText complaintText = (EditText) findViewById(R.id.complaint_edit_text);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference random = FirebaseDatabase.getInstance().getReference().child(act_name).getRef();

        random.child(user.getDisplayName()).child(token).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String complaint = dataSnapshot.child("Complaint").getValue(String.class);
                complaintText.setText(complaint);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        Button submit = (Button) findViewById(R.id.complaint_edit_button);
        subject.setText(subject_name);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ComplaintEditing","token"+ token);
                random.child(user.getDisplayName()).child(token).child("Subject").setValue(subject.getText().toString());
                random.child(user.getDisplayName()).child(token).child("Complaint").setValue(complaintText.getText().toString());
                finish();
            }
        });
    }
}