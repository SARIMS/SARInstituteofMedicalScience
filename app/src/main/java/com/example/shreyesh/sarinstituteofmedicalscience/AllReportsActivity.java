package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllReportsActivity extends AppCompatActivity {

    private Toolbar allReportsToolbar;
    private RecyclerView allReportsRecyclerView;
    private List<Report> reportList;
    private AllReportsAdapter allReportsAdapter;
    private DatabaseReference reportRef;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports);

        allReportsToolbar = (Toolbar) findViewById(R.id.allReportsToolbar);
        setSupportActionBar(allReportsToolbar);
        getSupportActionBar().setTitle("Report List");
        allReportsRecyclerView = (RecyclerView) findViewById(R.id.allReportsReclcyclerView);
        allReportsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reportList = new ArrayList<>();

        allReportsAdapter = new AllReportsAdapter(reportList);

        allReportsRecyclerView.setAdapter(allReportsAdapter);

        pid = getIntent().getStringExtra("pid");

        reportRef = FirebaseDatabase.getInstance().getReference().child("reports").child(pid);

        reportRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String title = d.child("title").getValue().toString();
                    String doctor = d.child("doctor").getValue().toString();
                    String id = d.getKey();
                    reportList.add(new Report(title, doctor, pid, id));
                    allReportsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
