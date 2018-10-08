package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfirmServicesActivity extends AppCompatActivity {
    private Context context;

    private List<ServiceConfirm> confirmList;
    private ConfirmServicesAdapter servicesAdapter;
    private Toolbar confirmServicesToobar;
    private Button confirm, cancel;
    private RecyclerView selectedServicesList;
    private TextView subtotal, gtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_services);

        //Initialize components
        subtotal = (TextView) findViewById(R.id.confirmServiceTotal);
        gtotal = (TextView) findViewById(R.id.confirmServicesGrandTotal);
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
        context = this;

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


        subtotal.setText("Rs " + total);
        Double d = (Double.parseDouble(total) * 0.025 + Double.parseDouble(total));

        gtotal.setText("Rs " + d.toString());


        //Cancel

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cancel");
                builder.setMessage("Are you sure your want to cancel?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(ConfirmServicesActivity.this, ServicesActivity.class));
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });

                Dialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });





    }
}
