package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;

public class PatientHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView patientHomeReportsList;
    private DatabaseReference reportsRef, userRef;
    private FirebaseAuth firebaseAuth;
    private TextView patientHeaderEmail, patientHeaderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.patientHomeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Home");


        String type = getIntent().getStringExtra("type");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        patientHeaderEmail = (TextView) headerView.findViewById(R.id.patientHeaderEmail);
        patientHeaderName = (TextView) headerView.findViewById(R.id.patientHeaderName);


        firebaseAuth = FirebaseAuth.getInstance();
        String userid = firebaseAuth.getCurrentUser().getUid();
        String email = firebaseAuth.getCurrentUser().getEmail();


        if (firebaseAuth != null) {

            patientHeaderEmail.setText(email);
        }

        reportsRef = FirebaseDatabase.getInstance().getReference().child("reports").child(userid);
        userRef = FirebaseDatabase.getInstance().getReference().child("patients").child(type).child(userid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    patientHeaderName.setText(name);
                } else {
                    Toast.makeText(PatientHomeActivity.this, "Wrong Patient Type", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PatientHomeActivity.this, PatientLoginActivity.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        patientHomeReportsList = (RecyclerView) findViewById(R.id.patientHomeReportList);
        patientHomeReportsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));


        //Database Sync
        userRef.keepSynced(true);
        reportsRef.keepSynced(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.patientLogOut) {
            firebaseAuth.signOut();
            startActivity(new Intent(PatientHomeActivity.this, PatientLoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navDocSchedule) {
            // Handle the camera action
        } else if (id == R.id.navRequestService) {

        } else if (id == R.id.navBills) {

        } else if (id == R.id.navSettings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
