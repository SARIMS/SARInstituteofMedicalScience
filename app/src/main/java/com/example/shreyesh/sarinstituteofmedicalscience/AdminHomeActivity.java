package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout adminDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar adminToolbar;
    private ActionBar actionBar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

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
