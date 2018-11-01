package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DoctorsListActivity extends AppCompatActivity {

    private RecyclerView doctorRecyclerView;
    private Toolbar doctorListToolbar;
    private List<Doctor> doctorList;
    private DoctorListAdapter doctorListAdapter;
    private DatabaseReference doctorRef;
    private ImageView filter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        doctorRecyclerView = (RecyclerView) findViewById(R.id.doctorRecyclerView);
        doctorListToolbar = (Toolbar) findViewById(R.id.doctorListToolbar);
        setSupportActionBar(doctorListToolbar);
        getSupportActionBar().setTitle("Doctors List");
        filter = (ImageView) findViewById(R.id.doctorFilter);
        context = this;

        doctorList = new ArrayList<>();
        doctorListAdapter = new DoctorListAdapter(doctorList);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorRecyclerView.setAdapter(doctorListAdapter);

        doctorRef = FirebaseDatabase.getInstance().getReference().child("doctors");
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    /*String name=d.child("name").getValue().toString();
                    String department=d.child("department").getValue().toString();
                    String image=d.child("image").getValue().toString();
                    String monday=d.child("monday").getValue().toString();
                    String tuesday=d.child("tuesday").getValue().toString();
                    String wednesday=d.child("wednesday").getValue().toString();
                    String thursday=d.child("thursday").getValue().toString();
                    String friday=d.child("friday").getValue().toString();
                    String saturday=d.child("saturday").getValue().toString();
                    String sunday=d.child("sunday").getValue().toString();*/
                    Doctor doctor = d.getValue(Doctor.class);

                    //doctorList.add(new Doctor(name,department,image,sunday,monday,tuesday,wednesday,thursday,friday,saturday));
                    doctorList.add(doctor);
                    doctorListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String option[] = {"Neurology", "Cardiology", "Dermatology", "ENT", "Gastroenterology", "Nephrology", "Gynaecology"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Department");
                builder.setSingleChoiceItems(option, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dept;
                        dialogInterface.dismiss();
                        switch (i) {
                            case 0:
                                dept = option[0];
                                break;
                            case 1:
                                dept = option[1];
                                break;
                            case 2:
                                dept = option[2];
                                break;
                            case 3:
                                dept = option[3];
                                break;
                            case 4:
                                dept = option[4];
                                break;
                            case 5:
                                dept = option[5];
                                break;
                            case 6:
                                dept = option[6];
                                break;
                            default:
                                return;
                        }

                        Query query = doctorRef.orderByChild("department").equalTo(dept);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int c = 0;
                                doctorList.clear();
                                if (dataSnapshot.getChildrenCount() == 0) {
                                    Toast.makeText(context, "No Doctor yet in the chosen Department", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    Doctor doctor = d.getValue(Doctor.class);
                                    doctorList.add(doctor);
                                    doctorListAdapter.notifyDataSetChanged();
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });
                Dialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
    }


}
