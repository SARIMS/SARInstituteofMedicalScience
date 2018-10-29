package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminConsultantListActivity extends AppCompatActivity {

    private RecyclerView consultantRecyclerView;
    private Toolbar adminConsultantListToolbar;
    private DatabaseReference consultantRef;
    private List<Patient> consultantList;
    private ConsultantListAdapter consultantListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_consultant_list);

        adminConsultantListToolbar = (Toolbar) findViewById(R.id.adminConsultantListToolbar);
        setSupportActionBar(adminConsultantListToolbar);
        getSupportActionBar().setTitle("Consultant List");


        consultantRef = FirebaseDatabase.getInstance().getReference().child("consultants");
        consultantRecyclerView = (RecyclerView) findViewById(R.id.adminConsultantRecyclerView);

        consultantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        consultantList = new ArrayList<>();
        consultantListAdapter = new ConsultantListAdapter(consultantList);
        consultantRecyclerView.setAdapter(consultantListAdapter);

        consultantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String name = d.child("name").getValue().toString();
                    String age = d.child("age").getValue().toString();
                    String gender = d.child("gender").getValue().toString();
                    String image = d.child("image").getValue().toString();
                    consultantList.add(new Patient(name, age, gender, image));
                    consultantListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.consultant_list_toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminAddConsultant:
                startActivity(new Intent(AdminConsultantListActivity.this, AddConsultantActivity.class));
                break;
            default:
                Toast.makeText(AdminConsultantListActivity.this, "Invalid Option", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
