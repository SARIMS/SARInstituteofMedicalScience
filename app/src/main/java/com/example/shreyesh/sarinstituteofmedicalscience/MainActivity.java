package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar homeToolbar;
    private TextView admin, patient, consultant, aboutus, doctor;
    private ImageView adminImage, patientImage, doctorImage, consultantImage, aboutUsImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise
        homeToolbar = (Toolbar) findViewById(R.id.homeToolbar);

        admin = (TextView) findViewById(R.id.homeAdminText);
        patient = (TextView) findViewById(R.id.homePatientText);
        doctor = (TextView) findViewById(R.id.homeDoctorText);
        consultant = (TextView) findViewById(R.id.homeConsultantText);
        aboutus = (TextView) findViewById(R.id.homeAboutUsTextView);

        adminImage = (ImageView) findViewById(R.id.homeAdminImage);
        patientImage = (ImageView) findViewById(R.id.homePatientImage);
        doctorImage = (ImageView) findViewById(R.id.homeDoctorImage);
        consultantImage = (ImageView) findViewById(R.id.homeConsultantImage);
        aboutUsImage = (ImageView) findViewById(R.id.homeAboutUsImage);


        //TextView Listener
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });
        adminImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });
        doctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });

        consultantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });
        consultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });

        patientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PatientLoginActivity.class));
            }
        });
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PatientLoginActivity.class));
            }
        });

        aboutUsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
            }
        });

        //Setup Toolbar
        setSupportActionBar(homeToolbar);
        getSupportActionBar().setTitle("S.A.R.I.M.S Home");

    }



}
