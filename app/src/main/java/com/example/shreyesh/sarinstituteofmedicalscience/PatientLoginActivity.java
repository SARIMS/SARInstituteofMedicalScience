package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class PatientLoginActivity extends AppCompatActivity {


    private Toolbar patientLoginToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        patientLoginToolbar = (Toolbar) findViewById(R.id.patientLoginToolbar);
        setSupportActionBar(patientLoginToolbar);
        getSupportActionBar().setTitle("Patient Login");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
