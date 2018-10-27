package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


interface MyCallback {
    void onCallBack(double value);
}

public class ViewBillsActivity extends AppCompatActivity {

    private double testTotal = 0, imgTotal = 0, medTotal = 0, grandTotal;
    private Toolbar viewBillToolbar;
    private int testFlag, imgFlag, medFlag;
    private Context context;
    private List<ServiceConfirm> billItemList;
    private ConfirmServicesAdapter billAdapter;
    private FirebaseAuth firebaseAuth;
    private String currentUserID;
    private TextView gtotal;
    private RecyclerView billList;
    private Button cancel, makePayment;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String test;
    private DatabaseReference testRef, imgRef, medRef, checkRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);


        sharedPreferences = this.getPreferences(MODE_PRIVATE);
        editor = this.getPreferences(MODE_PRIVATE).edit();
        billList = (RecyclerView) findViewById(R.id.billList);
        cancel = (Button) findViewById(R.id.cancelPayment);
        makePayment = (Button) findViewById(R.id.makePayment);
        gtotal = (TextView) findViewById(R.id.viewBillTotal);
        viewBillToolbar = (Toolbar) findViewById(R.id.viewBillsToolbar);
        setSupportActionBar(viewBillToolbar);
        getSupportActionBar().setTitle("Your Bill");
        imgFlag = medFlag = testFlag = 0;


        context = this;
        billItemList = new ArrayList<>();
        billAdapter = new ConfirmServicesAdapter(billItemList);
        billList.setLayoutManager(new LinearLayoutManager(this));
        billList.setAdapter(billAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();

        checkRef = FirebaseDatabase.getInstance().getReference().child("bills").child(currentUserID);

        testRef = checkRef.child("bloodTest");
        imgRef = checkRef.child("imaging");
        medRef = checkRef.child("medicines");


        checkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("bloodTest") || dataSnapshot.hasChild("imaging") || dataSnapshot.hasChild("medicines")) {

                    testRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                testTotal = Double.parseDouble(dataSnapshot.child("Total").getValue().toString());
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    String item = d.getKey();
                                    String price = d.getValue().toString();
                                    if (!d.getKey().equalsIgnoreCase("Total")) {
                                        billItemList.add(new ServiceConfirm("Rs " + price, item));
                                    }
                                    billAdapter.notifyDataSetChanged();
                                }
                            }
                            imgRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {
                                        imgTotal = Double.parseDouble(dataSnapshot.child("Total").getValue().toString());
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                            String item = d.getKey();
                                            String price = d.getValue().toString();
                                            if (!d.getKey().equalsIgnoreCase("Total")) {
                                                billItemList.add(new ServiceConfirm("Rs " + price, item));
                                            }
                                            billAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    medRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {

                                                medTotal = Double.parseDouble(dataSnapshot.child("Total").getValue().toString());
                                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                    String item = d.getKey();
                                                    String price = d.getValue().toString();
                                                    if (!d.getKey().equalsIgnoreCase("Total")) {
                                                        billItemList.add(new ServiceConfirm("Rs " + price, item));
                                                    }
                                                    billAdapter.notifyDataSetChanged();
                                                }


                                            }
                                            grandTotal = testTotal + imgTotal + medTotal;
                                            gtotal.setText(String.valueOf(grandTotal));

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] options = {"Credit/Debit Card", "Cash"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Payment Mode");
                builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                break;
                            case 1:
                                AlertDialog.Builder b1 = new AlertDialog.Builder(context);
                                b1.setMessage("Please go to Counter 2 and pay.\nThank You !");
                                b1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        return;
                                    }
                                });
                                Dialog dialog = b1.create();
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                        }
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
