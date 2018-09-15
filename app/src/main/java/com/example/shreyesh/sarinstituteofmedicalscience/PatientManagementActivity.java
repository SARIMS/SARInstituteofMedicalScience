package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class PatientManagementActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView patientRecyclerView;
    private TextView emptyText;
    private DatabaseReference patientRef;
    private FirebaseDatabase firebaseDatabase;
    private FloatingActionButton addPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_management);

        //Initialize
        toolbar = (Toolbar) findViewById(R.id.PatientManagementToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Management");
        patientRecyclerView = (RecyclerView) findViewById(R.id.patientList);
        emptyText = (TextView) findViewById(R.id.emptyListTextView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        patientRef = firebaseDatabase.getReference().child("patients");
        addPatient = (FloatingActionButton) findViewById(R.id.addPatient);

        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientManagementActivity.this, AddPatientActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Patient, PatientViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Patient, PatientViewHolder>(
                Patient.class,
                R.layout.patient_single_layout,
                PatientViewHolder.class,
                patientRef

        ) {
            @Override
            protected void populateViewHolder(PatientViewHolder viewHolder, Patient model, int position) {

            }
        };
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public PatientViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView pname = (TextView) mView.findViewById(R.id.singleName);
            pname.setText(name);
        }

        public void setAge(String age) {
            TextView pAge = (TextView) mView.findViewById(R.id.singleAge);
            pAge.setText(age);
        }

        public void setSex(String sex) {
            TextView pSex = (TextView) mView.findViewById(R.id.singleSex);
            pSex.setText(sex);
        }

        public void setImage(String image) {
            ImageView pImage = (ImageView) mView.findViewById(R.id.singleImage);
            Picasso.get().load(image).placeholder(R.drawable.avatarplaceholder).into(pImage);
        }
    }
}
