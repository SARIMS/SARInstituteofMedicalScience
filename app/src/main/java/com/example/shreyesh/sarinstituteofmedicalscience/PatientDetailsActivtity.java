package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PatientDetailsActivtity extends AppCompatActivity {

    private TextView patientName, patientAge, patientGender, patientPhone, patientAddress, patientNationality;
    private ImageView patientImage;
    private DatabaseReference pRef;
    private String pType, pUID;
    private Toolbar patientDetailsToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_activtity);

        pType = getIntent().getStringExtra("type");
        pUID = getIntent().getStringExtra("userID");

        //Initialize
        patientImage = (ImageView) findViewById(R.id.patientDetailsImage);
        patientAddress = (TextView) findViewById(R.id.patientDetailsAddress);
        patientAge = (TextView) findViewById(R.id.patientDetailsAge);
        patientGender = (TextView) findViewById(R.id.patientDetailsGender);
        patientNationality = (TextView) findViewById(R.id.patientDetailsNationality);
        patientPhone = (TextView) findViewById(R.id.patientDetailsPhone);
        patientName = (TextView) findViewById(R.id.patientDetailsName);
        patientDetailsToolbar = (Toolbar) findViewById(R.id.patientDetailsToolbar);
        setSupportActionBar(patientDetailsToolbar);
        getSupportActionBar().setTitle("Patient Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pRef = FirebaseDatabase.getInstance().getReference().child("patients").child(pType).child(pUID);

        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String age = dataSnapshot.child("age").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String gender = dataSnapshot.child("gender").getValue().toString();
                String nationality = dataSnapshot.child("nationality").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                patientAddress.setText(address);
                patientAge.setText(age);
                patientName.setText(name);
                patientPhone.setText(phone);
                patientGender.setText(gender);
                patientNationality.setText(nationality);

                if (!image.equals("default")) {
                    Picasso.get().load(image).placeholder(R.drawable.avatarplaceholder).into(patientImage);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
