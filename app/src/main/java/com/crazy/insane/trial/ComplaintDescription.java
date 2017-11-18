package com.crazy.insane.trial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class ComplaintDescription extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView subject = (TextView) findViewById(R.id.desc_sub);
        final TextView complaintText = (TextView) findViewById(R.id.desc_comp);
        final TextView messageText = (TextView) findViewById(R.id.desc_message);
        final EditText reply = (EditText) findViewById(R.id.reply);
        String subject_name = getIntent().getExtras().getString("subject");
        String act_name = getIntent().getExtras().getString("Activity");
        final String token = getIntent().getExtras().getString("Position");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference random = FirebaseDatabase.getInstance().getReference().child(act_name).getRef();

        random.child(user.getDisplayName()).child(token).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String complaint = dataSnapshot.child("Complaint").getValue(String.class);
                String message = dataSnapshot.child("Message").getValue(String.class);
                complaintText.setText(complaint);
                messageText.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        subject.setText(subject_name);
        Button replyButton = (Button)findViewById(R.id.reply_button);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Reply Sent",Toast.LENGTH_SHORT).show();
                random.child(user.getDisplayName()).child(token).child("UserReply").setValue(reply.getText().toString());
                finish();
            }
        });
    }
}
