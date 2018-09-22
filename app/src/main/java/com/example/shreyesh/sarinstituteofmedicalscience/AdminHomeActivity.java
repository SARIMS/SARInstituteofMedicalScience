package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout adminDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar adminToolbar;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private TextView inpatientCount, outpatientCount, doctorCount;
    private DatabaseReference ipref, oref;
    private RecyclerView adminNoticeList;
    private FloatingActionButton addNoticeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addNoticeButton = (FloatingActionButton) findViewById(R.id.addNoticeButton);
        adminToolbar = (Toolbar) findViewById(R.id.adminHomeToolbar);
        setSupportActionBar(adminToolbar);
        actionBar = getSupportActionBar();
        adminDrawerLayout = (DrawerLayout) findViewById(R.id.adminDrawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, adminDrawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.nav);

        navigationView.setNavigationItemSelectedListener(this);

        adminDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Admin Home");

        inpatientCount = (TextView) findViewById(R.id.inpatientCount);
        outpatientCount = (TextView) findViewById(R.id.outpatientCount);
        doctorCount = (TextView) findViewById(R.id.doctorCount);
        ipref = FirebaseDatabase.getInstance().getReference().child("patients").child("inpatients");
        oref = FirebaseDatabase.getInstance().getReference().child("patients").child("outpatients");

        ipref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                inpatientCount.setText(Long.toString(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        oref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                outpatientCount.setText(Long.toString(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, AddNoticeActivity.class));
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.patientManange:
                startActivity(new Intent(AdminHomeActivity.this, PatientManagementActivity.class));
            case R.id.adminLogOut:

        }
        return true;
    }
}
