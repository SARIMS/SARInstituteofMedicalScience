package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private List<Appointment> appointmentList;
    private AppointmentRecyclerAdapter appointmentRecyclerAdapter;
    private RecyclerView reportsRecyclerView, noticeList;
    private DatabaseReference reportsRef, doctorRef, noticeRef, appointmentRef;
    private FirebaseAuth firebaseAuth;
    private String type;
    private TextView doctorHeaderEmail, doctorHeaderName;
    private CircleImageView doctorImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.doctorHomeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Doctor Home");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        doctorHeaderEmail = (TextView) headerView.findViewById(R.id.doctorEmailHeader);
        doctorHeaderName = (TextView) headerView.findViewById(R.id.doctorNameHeader);
        doctorImage = (CircleImageView) headerView.findViewById(R.id.doctorImage);

        noticeList = (RecyclerView) findViewById(R.id.doctorNoticeList);
        firebaseAuth = FirebaseAuth.getInstance();

        type = getIntent().getStringExtra("type");
        if (type != null) {
            if (!type.equals("Doctor")) {
                firebaseAuth.signOut();
                startActivity(new Intent(DoctorHomeActivity.this, AdminLoginActivity.class));
                finish();
            }
        }

        String userid = firebaseAuth.getCurrentUser().getUid();
        String email = firebaseAuth.getCurrentUser().getEmail();

        System.out.println(email);

        if (firebaseAuth != null) {

            doctorHeaderEmail.setText(email);
        }

        // reportsRef = FirebaseDatabase.getInstance().getReference().child("reports").child(userid);
        doctorRef = FirebaseDatabase.getInstance().getReference().child("doctors").child(userid);
        noticeRef = FirebaseDatabase.getInstance().getReference().child("notices");

        //keep data synced for offline
        doctorRef.keepSynced(true);
        //      reportsRef.keepSynced(true);
        noticeRef.keepSynced(true);


        System.out.println(userid);
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("Hiiii");
                    String name = dataSnapshot.child("name").getValue().toString();
                    doctorHeaderName.setText(name);
                    String image = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(image).placeholder(R.drawable.avatarplaceholder).into(doctorImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        noticeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

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
        getMenuInflater().inflate(R.menu.doctor_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.doctorSettings) {
            startActivity(new Intent(DoctorHomeActivity.this, SarimsStaffSettingsActivity.class).putExtra("type", "Doctor"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navReports) {
            // Handle the camera action
        } else if (id == R.id.navPatients) {
            startActivity(new Intent(DoctorHomeActivity.this, PatientManagementActivity.class).putExtra("type", type));


        } else if (id == R.id.navDoctorSignOut) {
            firebaseAuth.signOut();
            startActivity(new Intent(DoctorHomeActivity.this, AdminLoginActivity.class));
            finish();
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
                final Intent intent = new Intent(DoctorHomeActivity.this, ViewNoticeActivity.class);
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
