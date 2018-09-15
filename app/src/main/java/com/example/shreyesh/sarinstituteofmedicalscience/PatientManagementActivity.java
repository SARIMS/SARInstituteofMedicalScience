package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


public class PatientManagementActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView patientRecyclerView;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_management);

        //Initialize
        toolbar = (Toolbar) findViewById(R.id.PatientManagementToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Management");
        patientRecyclerView = (RecyclerView) findViewById(R.id.patientList);
        emptyText = (TextView) findViewById(R.id.emptyListTextView);


    }
}
