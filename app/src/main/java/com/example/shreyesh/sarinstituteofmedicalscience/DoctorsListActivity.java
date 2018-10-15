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


public class DoctorsListActivity extends AppCompatActivity {

    private RecyclerView doctorRecyclerView;
    private Toolbar doctorListToolbar;
    private List<Doctor> doctorList;
    private DoctorListAdapter doctorListAdapter;
    private DatabaseReference doctorRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        doctorRecyclerView = (RecyclerView) findViewById(R.id.doctorRecyclerView);
        doctorListToolbar = (Toolbar) findViewById(R.id.doctorListToolbar);
        setSupportActionBar(doctorListToolbar);
        getSupportActionBar().setTitle("Doctors List");

        doctorList = new ArrayList<>();
        doctorListAdapter = new DoctorListAdapter(doctorList);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorRecyclerView.setAdapter(doctorListAdapter);

        doctorRef = FirebaseDatabase.getInstance().getReference().child("doctors");
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    /*String name=d.child("name").getValue().toString();
                    String department=d.child("department").getValue().toString();
                    String image=d.child("image").getValue().toString();
                    String monday=d.child("monday").getValue().toString();
                    String tuesday=d.child("tuesday").getValue().toString();
                    String wednesday=d.child("wednesday").getValue().toString();
                    String thursday=d.child("thursday").getValue().toString();
                    String friday=d.child("friday").getValue().toString();
                    String saturday=d.child("saturday").getValue().toString();
                    String sunday=d.child("sunday").getValue().toString();*/
                    Doctor doctor = d.getValue(Doctor.class);

                    //doctorList.add(new Doctor(name,department,image,sunday,monday,tuesday,wednesday,thursday,friday,saturday));
                    doctorList.add(doctor);
                    doctorListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
