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

public class SelectPatientActivity extends AppCompatActivity {

    private String type, staff;
    private Toolbar selectPatientToolbar;
    private DatabaseReference patientRef;
    private RecyclerView selectPatientRecyclerView;
    private SelectPatientAdapter selectPatientAdapter;
    private List<SelectedPatient> selectedPatients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);

        selectPatientToolbar = (Toolbar) findViewById(R.id.selectPatientToolbar);
        setSupportActionBar(selectPatientToolbar);
        getSupportActionBar().setTitle("Patient List");
        selectPatientRecyclerView = (RecyclerView) findViewById(R.id.selectPatientRecyclerView);
        selectPatientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        type = getIntent().getStringExtra("type");
        staff = getIntent().getStringExtra("staff");
        selectedPatients = new ArrayList<>();
        selectPatientAdapter = new SelectPatientAdapter(selectedPatients, staff);
        selectPatientRecyclerView.setAdapter(selectPatientAdapter);


        patientRef = FirebaseDatabase.getInstance().getReference().child("patients").child(type);

        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String name = d.child("name").getValue().toString();
                    String image = d.child("image").getValue().toString();
                    String userid = d.child("id").getValue().toString();
                    selectedPatients.add(new SelectedPatient(name, image, d.getKey()));
                    selectPatientAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
