package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDoctorListActivity extends AppCompatActivity {

    private RecyclerView doctorRecyclerView;
    private Toolbar adminDoctorListToolbar;
    private DatabaseReference doctorRef;
    private List<Doctor> doctorList;
    private AdminDoctorListAdapter doctorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_doctor_list);

        adminDoctorListToolbar = (Toolbar) findViewById(R.id.adminDoctorListToolbar);
        setSupportActionBar(adminDoctorListToolbar);
        getSupportActionBar().setTitle("Doctor List");


        doctorRef = FirebaseDatabase.getInstance().getReference().child("doctors");
        doctorRecyclerView = (RecyclerView) findViewById(R.id.adminDoctorRecyclerView);

        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorList = new ArrayList<>();
        doctorListAdapter = new AdminDoctorListAdapter(doctorList);
        doctorRecyclerView.setAdapter(doctorListAdapter);

        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Doctor doctor = d.getValue(Doctor.class);
                    doctorList.add(doctor);
                    doctorListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        doctorRef.keepSynced(true);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doctor_list_toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addDoctorAdmin:
                startActivity(new Intent(AdminDoctorListActivity.this, AddDoctorActivity.class));
                break;
            default:
                Toast.makeText(AdminDoctorListActivity.this, "Invalid Option", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
