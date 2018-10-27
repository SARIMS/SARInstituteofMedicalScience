package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class PatientManagementActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView patientRecyclerView;
    private TextView emptyText;
    private DatabaseReference patientRef, inRef, outRef;
    private RadioGroup radioGroup;
    private RadioButton inpatient, outpatient;
    private FloatingActionButton addPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_management);

        //Initialize
        patientRef = FirebaseDatabase.getInstance().getReference().child("patients");
        inRef = FirebaseDatabase.getInstance().getReference().child("patients").child("inpatients");
        outRef = FirebaseDatabase.getInstance().getReference().child("patients").child("outpatients");
        toolbar = (Toolbar) findViewById(R.id.PatientManagementToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Management");

        radioGroup = (RadioGroup) findViewById(R.id.patientTypeRBFilter);
        inpatient = (RadioButton) findViewById(R.id.inpatientRBFilter);
        outpatient = (RadioButton) findViewById(R.id.outpatientRBFilter);
        radioGroup.check(R.id.inpatientRBFilter);

        patientRecyclerView = (RecyclerView) findViewById(R.id.patientList);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        emptyText = (TextView) findViewById(R.id.emptyListTextView);

        addPatient = (FloatingActionButton) findViewById(R.id.addPatient);

        patientRef.keepSynced(true);

        String type = getIntent().getStringExtra("Doctor");
        if (type != null) {
            if (type.equals("Doctor"))
                addPatient.setVisibility(View.GONE);
            addPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(PatientManagementActivity.this, AddPatientActivity.class));
                }
            });
        }
        inRef.keepSynced(true);
        outRef.keepSynced(true);



    }

    @Override
    protected void onStart() {
        super.onStart();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                final RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb.getText().equals("Inpatient")) {
                    inRef = patientRef.child("inpatients");
                    FirebaseRecyclerAdapter<Patient, PatientViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Patient, PatientViewHolder>(
                            Patient.class,
                            R.layout.patient_single_layout,
                            PatientViewHolder.class,
                            inRef

                    ) {
                        @Override
                        protected void populateViewHolder(PatientViewHolder viewHolder, Patient model, int position) {
                            viewHolder.setName(model.getName());
                            viewHolder.setAge(model.getAge());
                            viewHolder.setSex(model.getGender());

                            final String uid = getRef(position).getKey();

                            String image = model.getImage();
                            if (!image.equalsIgnoreCase("default")) {
                                viewHolder.setImage(image);
                            }                                                   



                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(PatientManagementActivity.this, PatientDetailsActivtity.class);
                                    intent.putExtra("type", "inpatients");
                                    intent.putExtra("userID", uid);
                                    startActivity(intent);
                                }
                            });
                        }
                    };


                    firebaseRecyclerAdapter.notifyDataSetChanged();
                    patientRecyclerView.setAdapter(firebaseRecyclerAdapter);
                    firebaseRecyclerAdapter.startListening();
                } else if (rb.getText().equals("Outpatient")) {
                    outRef = patientRef.child("outpatients");
                    FirebaseRecyclerAdapter<Patient, PatientViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Patient, PatientViewHolder>(
                            Patient.class,
                            R.layout.patient_single_layout,
                            PatientViewHolder.class,
                            outRef

                    ) {
                        @Override
                        protected void populateViewHolder(PatientViewHolder viewHolder, Patient model, int position) {
                            viewHolder.setName(model.getName());
                            viewHolder.setAge(model.getAge());
                            viewHolder.setSex(model.getGender());

                            final String uid = getRef(position).getKey();


                            String image = model.getImage();
                            if (!image.equalsIgnoreCase("default")) {
                                viewHolder.setImage(image);
                            }


                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(PatientManagementActivity.this, PatientDetailsActivtity.class);
                                    intent.putExtra("type", "outpatients");
                                    intent.putExtra("userID", uid);
                                    startActivity(intent);
                                }
                            });
                        }
                    };


                    firebaseRecyclerAdapter.notifyDataSetChanged();
                    patientRecyclerView.setAdapter(firebaseRecyclerAdapter);
                    firebaseRecyclerAdapter.startListening();
                }
            }
        });

        final RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        if (rb.getText().equals("Inpatient")) {
            inRef = patientRef.child("inpatients");
            FirebaseRecyclerAdapter<Patient, PatientViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Patient, PatientViewHolder>(
                    Patient.class,
                    R.layout.patient_single_layout,
                    PatientViewHolder.class,
                    inRef

            ) {
                @Override
                protected void populateViewHolder(PatientViewHolder viewHolder, Patient model, int position) {
                    viewHolder.setName(model.getName());
                    viewHolder.setAge(model.getAge());
                    viewHolder.setSex(model.getGender());

                    final String uid = getRef(position).getKey();
                    String image = model.getImage();
                    if (!image.equalsIgnoreCase("default")) {
                        viewHolder.setImage(image);
                    }

                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(PatientManagementActivity.this, PatientDetailsActivtity.class);
                            intent.putExtra("type", "inpatients");
                            intent.putExtra("userID", uid);
                            startActivity(intent);
                        }
                    });
                }
            };


            firebaseRecyclerAdapter.notifyDataSetChanged();
            patientRecyclerView.setAdapter(firebaseRecyclerAdapter);
            firebaseRecyclerAdapter.startListening();
        } else if (rb.getText().equals("Outpatient")) {
            outRef = patientRef.child("outpatients");
            FirebaseRecyclerAdapter<Patient, PatientViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Patient, PatientViewHolder>(
                    Patient.class,
                    R.layout.patient_single_layout,
                    PatientViewHolder.class,
                    outRef

            ) {
                @Override
                protected void populateViewHolder(PatientViewHolder viewHolder, Patient model, int position) {
                    viewHolder.setName(model.getName());
                    viewHolder.setAge(model.getAge());
                    viewHolder.setSex(model.getGender());

                    String image = model.getImage();
                    if (!image.equalsIgnoreCase("default")) {
                        viewHolder.setImage(image);
                    }

                    final String uid = getRef(position).getKey();

                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(PatientManagementActivity.this, PatientDetailsActivtity.class);
                            intent.putExtra("type", "outpatients");
                            intent.putExtra("userID", uid);
                            startActivity(intent);
                        }
                    });
                }
            };
            firebaseRecyclerAdapter.notifyDataSetChanged();
            patientRecyclerView.setAdapter(firebaseRecyclerAdapter);
            firebaseRecyclerAdapter.startListening();
        }
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
