package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Appointment> appointmentList, recList;
    private AppointmentRecyclerAdapter appointmentRecyclerAdapter;
    private RecyclerView appointmentsReclcyerView, noticeList, recRecyclerView;
    private DatabaseReference reportsRef, userRef, noticeRef, appointmentRef, recRef;
    private FirebaseAuth firebaseAuth;
    private String type, pid;
    private TextView patientHeaderEmail, patientHeaderName;
    private CircleImageView patientImage;
    private RecommendationAdapter recommendationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.patientHomeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Home");


        type = getIntent().getStringExtra("type");

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
        patientImage = (CircleImageView) headerView.findViewById(R.id.patientImage);


        firebaseAuth = FirebaseAuth.getInstance();
        String userid = firebaseAuth.getCurrentUser().getUid();
        String email = firebaseAuth.getCurrentUser().getEmail();


        if (firebaseAuth != null) {

            patientHeaderEmail.setText(email);
        }

        pid = firebaseAuth.getCurrentUser().getUid();
        reportsRef = FirebaseDatabase.getInstance().getReference().child("reports").child(userid);
        userRef = FirebaseDatabase.getInstance().getReference().child("patients").child(type).child(userid);
        noticeRef = FirebaseDatabase.getInstance().getReference().child("notices");
        appointmentRef = FirebaseDatabase.getInstance().getReference().child("appointments").child(userid);
        recRef = FirebaseDatabase.getInstance().getReference().child("recommendations").child(userid);

        //keep data synced for offline
        userRef.keepSynced(true);
        reportsRef.keepSynced(true);
        noticeRef.keepSynced(true);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    patientHeaderName.setText(name);
                    String image = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(image).placeholder(R.drawable.avatarplaceholder).into(patientImage);
                } else {
                    Toast.makeText(PatientHomeActivity.this, "Wrong Patient Type", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PatientHomeActivity.this, PatientLoginActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        noticeList = (RecyclerView) findViewById(R.id.patientHomeReportList);
        noticeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));



        appointmentsReclcyerView=(RecyclerView)findViewById(R.id.appointmentsRecyclerView);
        appointmentsReclcyerView.setLayoutManager(new LinearLayoutManager(this));


        appointmentList=new ArrayList<>();
        appointmentRecyclerAdapter=new AppointmentRecyclerAdapter(appointmentList);
        appointmentsReclcyerView.setAdapter(appointmentRecyclerAdapter);

        recRecyclerView = (RecyclerView) findViewById(R.id.patientHomeRecList);
        recRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        recList = new ArrayList<>();
        recommendationAdapter = new RecommendationAdapter(recList);
        recRecyclerView.setAdapter(recommendationAdapter);


        appointmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()) {
                    String date = d.child("date").getValue().toString();
                    String time = d.child("time").getValue().toString();
                    String doctor = d.child("doctor").getValue().toString();
                    appointmentList.add(new Appointment("Dr " + doctor, time, date, d.getKey()));
                    appointmentRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String rec = d.child("rec").getValue().toString();
                    String date = d.child("date").getValue().toString();
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
                    String da = sfd.format(new Date(date));
                    recList.add(new Appointment(rec, "", da));
                    recommendationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Database Sync
        userRef.keepSynced(true);
        reportsRef.keepSynced(true);
        appointmentRef.keepSynced(true);
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
            finish();
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
            startActivity(new Intent(PatientHomeActivity.this, DoctorsListActivity.class));
            // Handle the camera action
        } else if (id == R.id.navRequestService) {
            startActivity(new Intent(PatientHomeActivity.this, ServicesActivity.class).putExtra("type", type));

        } else if (id == R.id.navBills) {
            startActivity(new Intent(PatientHomeActivity.this, ViewBillsActivity.class));

        } else if (id == R.id.navSettings) {
            startActivity(new Intent(PatientHomeActivity.this, AccountSettingsActivity.class).putExtra("type", type));

        } else if (id == R.id.navPatientReports) {
            startActivity(new Intent(PatientHomeActivity.this, AllReportsActivity.class).putExtra("pid", pid));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Notice, AdminHomeActivity.NoticeViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Notice, AdminHomeActivity.NoticeViewHolder>(
                Notice.class,
                R.layout.notice_single,
                AdminHomeActivity.NoticeViewHolder.class,
                noticeRef
        ) {
            @Override
            protected void populateViewHolder(AdminHomeActivity.NoticeViewHolder viewHolder, Notice model, int position) {

                viewHolder.setNoticeDate(model.getDate());
                viewHolder.setNoticeTitle(model.getTitle());
                noticeList.setLongClickable(true);

                final String id = getRef(position).getKey();
                final Intent intent = new Intent(PatientHomeActivity.this, ViewNoticeActivity.class);
                intent.putExtra("id", id);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(intent);
                    }
                });

            }
        };

        noticeList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        firebaseRecyclerAdapter.startListening();
    }

}
