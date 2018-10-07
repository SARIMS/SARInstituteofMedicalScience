package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfirmServicesActivity extends AppCompatActivity {

    private List<ServiceConfirm> confirmList;
    private ConfirmServicesAdapter servicesAdapter;
    private Toolbar confirmServicesToobar;
    private Button confirm, cancel;
    private RecyclerView selectedServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_services);

        //Initialize components
        confirm = (Button) findViewById(R.id.confirmServiceConfirm);
        cancel = (Button) findViewById(R.id.confirmServiceCancel);
        confirmServicesToobar = (Toolbar) findViewById(R.id.confirmServciesToolbar);
        setSupportActionBar(confirmServicesToobar);
        getSupportActionBar().setTitle("Confirm");
        selectedServicesList = (RecyclerView) findViewById(R.id.selectedServicesList);
        confirmList = new ArrayList<>();
        servicesAdapter = new ConfirmServicesAdapter(confirmList);
        selectedServicesList.setLayoutManager(new LinearLayoutManager(this));
        selectedServicesList.setAdapter(servicesAdapter);


        //get Intents
        String total = getIntent().getStringExtra("total");
        HashMap<String, String> testMap = (HashMap<String, String>) getIntent().getSerializableExtra("itemMap");
        ArrayList<String> selectedList = getIntent().getStringArrayListExtra("itemSelectList");
        ArrayList<String> itemList = getIntent().getStringArrayListExtra("itemList");


        for (Object s : selectedList) {
            if (testMap.containsKey(itemList.get(Integer.valueOf(s.toString())))) {
                String price = String.valueOf(testMap.get(itemList.get(Integer.valueOf(s.toString()))));
                String item = itemList.get(Integer.valueOf(s.toString()));
                confirmList.add(new ServiceConfirm("Rs " + price, item));
            }
        }


    }
}
