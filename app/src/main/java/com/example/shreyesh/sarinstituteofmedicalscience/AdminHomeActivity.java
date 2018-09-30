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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
    private DatabaseReference ipref, oref, noticeRef;
    private RecyclerView adminNoticeList;


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

        adminNoticeList = (RecyclerView) findViewById(R.id.noticeAdminList);
        adminNoticeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        inpatientCount = (TextView) findViewById(R.id.inpatientCount);
        outpatientCount = (TextView) findViewById(R.id.outpatientCount);
        doctorCount = (TextView) findViewById(R.id.doctorCount);
        ipref = FirebaseDatabase.getInstance().getReference().child("patients").child("inpatients");
        oref = FirebaseDatabase.getInstance().getReference().child("patients").child("outpatients");
        noticeRef = FirebaseDatabase.getInstance().getReference().child("notices");

        ipref.keepSynced(true);
        oref.keepSynced(true);
        noticeRef.keepSynced(true);

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


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Notice, NoticeViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Notice, NoticeViewHolder>(
                Notice.class,
                R.layout.notice_single,
                NoticeViewHolder.class,
                noticeRef
        ) {
            @Override
            protected void populateViewHolder(NoticeViewHolder viewHolder, Notice model, int position) {

                viewHolder.setNoticeDate(model.getDate());
                viewHolder.setNoticeTitle(model.getTitle());

                String id = getRef(position).getKey();
                final Intent intent = new Intent(AdminHomeActivity.this, ViewNoticeActivity.class);
                intent.putExtra("id", id);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(intent);
                    }
                });


            }
        };

        adminNoticeList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.addNoticeButton) {
            startActivity(new Intent(AdminHomeActivity.this, AddNoticeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_home_toolbar, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.patientManange:
                startActivity(new Intent(AdminHomeActivity.this, PatientManagementActivity.class));
            case R.id.adminLogOut:
                FirebaseAuth.getInstance().signOut();

        }
        return true;
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setNoticeTitle(String title) {
            TextView ntitle = (TextView) mView.findViewById(R.id.noticeSingleTitle);
            ntitle.setText(title);
        }

        public void setNoticeDate(String date) {
            TextView ndate = (TextView) mView.findViewById(R.id.noticeSingleDate);
            ndate.setText(date);
        }


    }
}
