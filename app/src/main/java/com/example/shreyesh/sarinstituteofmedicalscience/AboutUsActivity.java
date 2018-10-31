package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    private Toolbar aboutUsToolbar;
    private TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        aboutUsToolbar = (Toolbar) findViewById(R.id.aboutUsToolbar);
        setSupportActionBar(aboutUsToolbar);
        getSupportActionBar().setTitle("About Us");
        about = (TextView) findViewById(R.id.aboutUsDesc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String desc = "Today, S.A.R.I.M.S has touched the lives of over 45 million patients from 121 countries. The group, which started out as a 150-bed hospital, is now recognized as a pioneer of private healthcare in India, with 64 hospitals in operation. S.A.R.I.M.S is known not only as a medical institution but has emerged as an integrated healthcare provider in Asia, specializing also in consultancy, clinics, pharmacy, insurance and holistic therapy.";

        about.setText(desc);
    }
}
