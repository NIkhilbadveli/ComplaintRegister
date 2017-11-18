package com.crazy.insane.trial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessActivity extends AppCompatActivity{
    ArrayList<RowItem> rowItems = new ArrayList<>();
    long childrencount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarComp);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mess = FirebaseDatabase.getInstance().getReference().child("/Mess Complaints").getRef();
        final ListView listView = (ListView) findViewById(R.id.comp_mess_list);

        mess.child(user.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                childrencount = dataSnapshot.getChildrenCount();
                if(childrencount!=0) {
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getApplicationContext(), "No Complaints yet!!!", Toast.LENGTH_SHORT).show();
                }
                rowItems.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String subject = dsp.child("/Subject").getValue(String.class);
                    String status = dsp.child("/Status").getValue(String.class);
                    String flag = dsp.child("/Flag").getValue(String.class);

                    if (subject != null && status!=null && flag != null) {
                        if (flag.equals("Sent")) {
                            RowItem item = new RowItem(R.drawable.ic_pending, subject, status);
                            rowItems.add(item);
                        } else if (flag.equals("Pending")) {
                            RowItem item = new RowItem(R.drawable.ic_pending, subject, status);
                            rowItems.add(item);
                        } else if (flag.equals("Finished")) {
                            RowItem item = new RowItem(R.drawable.ic_finished, subject, status);
                            rowItems.add(item);
                        }

                    }
                }
                Collections.reverse(rowItems);
                CustomListViewAdapter adapter = new CustomListViewAdapter(getApplicationContext(),
                        R.layout.list_item, rowItems);
                progressBar.setVisibility(View.GONE);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                String position = String.valueOf(childrencount-pos);

                Intent i = new Intent(MessActivity.this,ComplaintEditing.class);
                i.putExtra("subject",rowItems.get(pos).getSubject());
                i.putExtra("Position",position);
                i.putExtra("Activity","Mess Complaints");
                startActivity(i);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int pos, long id) {
                String position = String.valueOf(childrencount - pos);

                Intent i = new Intent(MessActivity.this, ComplaintDescription.class);
                i.putExtra("subject", rowItems.get(pos).getSubject());
                i.putExtra("Position", position);
                i.putExtra("Activity", "Mess Complaints");
                startActivity(i);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_action_name);
        fab.setBackgroundColor(3218322);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessActivity.this, MessEditingActivity.class);
                startActivity(intent);
            }
        });
    }
}
