package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmServicesActivity extends AppCompatActivity {
    private Context context;
    private int c = 0;
    private List<ServiceConfirm> confirmList;
    private ConfirmServicesAdapter servicesAdapter;
    private Toolbar confirmServicesToobar;
    private Button confirm, cancel;
    private RecyclerView selectedServicesList;
    private TextView subtotal, gtotal;
    private DatabaseReference billRef;
    private FirebaseAuth firebaseAuth;
    private String currentUserID;
    private Map<String, String> billmap, postMap;
    private String serviceType;


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
        billmap = new HashMap<>();
        postMap = new HashMap<>();

        //Firebase Components
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        billRef = FirebaseDatabase.getInstance().getReference().child("bills").child(currentUserID);


        //get Intents
        String total = getIntent().getStringExtra("total");
        HashMap<String, String> testMap = (HashMap<String, String>) getIntent().getSerializableExtra("itemMap");
        ArrayList<String> selectedList = getIntent().getStringArrayListExtra("itemSelectList");
        ArrayList<String> itemList = getIntent().getStringArrayListExtra("itemList");
        serviceType = getIntent().getStringExtra("serviceType");

        for (Object s : selectedList) {
            if (testMap.containsKey(itemList.get(Integer.valueOf(s.toString())))) {
                String price = String.valueOf(testMap.get(itemList.get(Integer.valueOf(s.toString()))));
                String item = itemList.get(Integer.valueOf(s.toString()));
                billmap.put(item, price);
                confirmList.add(new ServiceConfirm("Rs " + price, item));
            }
        }


        subtotal.setText("Rs " + total);
        Double d = (Double.parseDouble(total) * 0.025 + Double.parseDouble(total));

        gtotal.setText("Rs " + d.toString());


        //Cancel Button
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


        billRef.child(serviceType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    c = c + 1;
                    System.out.println(d.getKey());
                    System.out.println(d.getValue().toString());
                    postMap.put(d.getKey(), d.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //Confirm button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                billmap.putAll(postMap);
                billRef.child(serviceType).setValue(billmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Items added to bill");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(ConfirmServicesActivity.this, ServicesActivity.class));
                                    finish();
                                }
                            });
                            Dialog dialog = builder.create();
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }
}
