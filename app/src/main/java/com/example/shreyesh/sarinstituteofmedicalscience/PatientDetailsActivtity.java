package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PatientDetailsActivtity extends AppCompatActivity {

    private TextView patientName, patientAge, patientGender, patientPhone, patientAddress, patientNationality;
    private ImageView patientImage;
    private DatabaseReference pRef, recRef;
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
        recRef = FirebaseDatabase.getInstance().getReference().child("recommendations").child(pUID);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addRecommendation) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Recommendation");
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.rec_layout, null);
            builder.setView(view);
            final EditText editText = (EditText) view.findViewById(R.id.recEditText);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String r = editText.getText().toString();
                    final Calendar current = Calendar.getInstance();
                    current.set(Calendar.HOUR, 0);
                    current.set(Calendar.MINUTE, 0);
                    current.set(Calendar.SECOND, 0);
                    current.set(Calendar.MILLISECOND, 0);
                    final Date currentDate = current.getTime();
                    SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = new Date();
                    String da = sfd.format(date1);

                    HashMap<String, String> recMap = new HashMap<>();
                    recMap.put("rec", r);
                    recMap.put("date", da);
                    recRef.push().setValue(recMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(PatientDetailsActivtity.this, "Recommendation added", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
