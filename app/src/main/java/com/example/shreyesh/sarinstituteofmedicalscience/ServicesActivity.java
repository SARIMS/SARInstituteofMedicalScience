package com.example.shreyesh.sarinstituteofmedicalscience;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    private RecyclerView servicesRecyclerView;
    private Toolbar servicesToolbar;
    private ServicesAdapter servicesAdapter;
    private List<Services> servicesList;
    private String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        type = getIntent().getStringExtra("type");

        servicesRecyclerView = (RecyclerView) findViewById(R.id.servicesList);
        servicesToolbar = (Toolbar) findViewById(R.id.servicesToolbar);
        setSupportActionBar(servicesToolbar);
        getSupportActionBar().setTitle("Select Service");

        servicesList = new ArrayList<>();
        servicesAdapter = new ServicesAdapter(servicesList);
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        servicesRecyclerView.setAdapter(servicesAdapter);

        servicesList.add(new Services(getURLForResource(R.drawable.blood), "Blood Test"));
        servicesList.add(new Services(getURLForResource(R.drawable.xrayred), "Imaging"));
        servicesList.add(new Services(getURLForResource(R.drawable.medicine), "Medicines"));
        servicesList.add(new Services(getURLForResource(R.drawable.room), "Room Services"));


    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }
}
