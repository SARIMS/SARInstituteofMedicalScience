package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRecommendationActivity extends AppCompatActivity {

    String pid;
    private Toolbar recToolbar;
    private RecyclerView recRecyclerView;
    private DatabaseReference recRef;
    private List<Appointment> recList;
    private RecommendationAdapter recommendationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recommendation);

        recRecyclerView = (RecyclerView) findViewById(R.id.viewRecommendationRecyclerView);
        recToolbar = (Toolbar) findViewById(R.id.ViewRecommendationToolbar);
        setSupportActionBar(recToolbar);
        getSupportActionBar().setTitle("Recommendation List");

        recList = new ArrayList<>();

        recRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recommendationAdapter = new RecommendationAdapter(recList);

        recRecyclerView.setAdapter(recommendationAdapter);

        pid = getIntent().getStringExtra("pid");

        recRef = FirebaseDatabase.getInstance().getReference().child("recommendations").child(pid);

        recRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String rec = d.child("rec").getValue().toString();
                    String date = d.child("date").getValue().toString();
                    recList.add(new Appointment(rec, "", date));
                    recommendationAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
