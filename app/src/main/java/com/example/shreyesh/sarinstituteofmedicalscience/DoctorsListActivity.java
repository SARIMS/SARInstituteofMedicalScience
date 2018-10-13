package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;


public class DoctorsListActivity extends AppCompatActivity {

    private RecyclerView doctorRecyclerView;
    private Toolbar doctorListToolbar;
    private List<Doctor> doctorList;
    private DoctorListAdapter doctorListAdapter;

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

        doctorList.add(new Doctor("Satwik Sharma", "Cardiology", "default", "10am-1pm", "2pm-5pm", "2pm-5pm", "2pm-5pm", "2pm-5pm", "2pm-5pm", "-"));



    }
}
