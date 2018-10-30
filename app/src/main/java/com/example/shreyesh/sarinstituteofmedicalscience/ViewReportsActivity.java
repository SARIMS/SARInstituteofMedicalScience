package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewReportsActivity extends AppCompatActivity {

    private TextView staffName, title, body, patient;
    private Toolbar viewReportsToolbar;
    private DatabaseReference reportRef;
    private String reportid, pname, pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        viewReportsToolbar = (Toolbar) findViewById(R.id.viewReportsToolbar);
        setSupportActionBar(viewReportsToolbar);
        staffName = (TextView) findViewById(R.id.viewReportsStaffName);
        title = (TextView) findViewById(R.id.viewReportsTitle);
        body = (TextView) findViewById(R.id.viewReportsBody);
        patient = (TextView) findViewById(R.id.viewReportsPatientName);

        reportid = getIntent().getStringExtra("reportid");
        pname = getIntent().getStringExtra("name");
        pid = getIntent().getStringExtra("pid");

        reportRef = FirebaseDatabase.getInstance().getReference().child("reports").child(pid).child(reportid);

        reportRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String t = dataSnapshot.child("title").getValue().toString();
                String b = dataSnapshot.child("body").getValue().toString();
                String s = dataSnapshot.child("doctor").getValue().toString();
                String p = dataSnapshot.child("patient").getValue().toString();

                staffName.setText(s);
                title.setText(t);
                body.setText(b);
                patient.setText(p);
                getSupportActionBar().setTitle(t);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
