package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddPatientActivity extends AppCompatActivity {


    private TextInputLayout patientName, patientEmail, patientPassword, patientConfirmPassword, patientPhone, patientAge;
    private TextInputLayout patientAddress, patientNationality, patientTreatmentNeeded, patientDepartmentToVisit;
    private Button registerPatient, reset;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference patientRef;
    private RadioButton maleButton, femaleButton, inpatientButton, outPatient;
    private RadioGroup genderGroup, patientTypeGroup;
    private Toolbar addPatientToolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        //Initialize
        patientAge = (TextInputLayout) findViewById(R.id.patientAge);
        patientConfirmPassword = (TextInputLayout) findViewById(R.id.patientConfirmPassword);
        patientEmail = (TextInputLayout) findViewById(R.id.patientEmail);
        patientName = (TextInputLayout) findViewById(R.id.patientName);
        patientPhone = (TextInputLayout) findViewById(R.id.patientPhone);
        patientNationality = (TextInputLayout) findViewById(R.id.patientNationality);
        patientAddress = (TextInputLayout) findViewById(R.id.patientAddress);
        patientTreatmentNeeded = (TextInputLayout) findViewById(R.id.patientTreatmentNeeded);
        patientDepartmentToVisit = (TextInputLayout) findViewById(R.id.patientDepartment);
        patientPassword = (TextInputLayout) findViewById(R.id.patientPassword);
        maleButton = (RadioButton) findViewById(R.id.maleRadioButton);
        inpatientButton = (RadioButton) findViewById(R.id.inPaientRadioButton);
        outPatient = (RadioButton) findViewById(R.id.Outpatient);
        patientTypeGroup = (RadioGroup) findViewById(R.id.patientTypeRadioGroup);
        femaleButton = (RadioButton) findViewById(R.id.femaleRadioButton);
        genderGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        genderGroup.clearCheck();
        patientTypeGroup.clearCheck();
        addPatientToolbar = (Toolbar) findViewById(R.id.addPatientToolbar);
        setSupportActionBar(addPatientToolbar);
        getSupportActionBar().setTitle("Add Patient");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering Patient");
        progressDialog.setMessage("Please wait while we register you...");
        progressDialog.setCanceledOnTouchOutside(false);

        registerPatient = (Button) findViewById(R.id.registerPatient);
        reset = (Button) findViewById(R.id.resetPatient);

        firebaseAuth = FirebaseAuth.getInstance();
        patientRef = FirebaseDatabase.getInstance().getReference().child("patients");


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientAge.getEditText().setText("");
                patientName.getEditText().setText("");
                patientPassword.getEditText().setText("");
                patientConfirmPassword.getEditText().setText("");
                patientPhone.getEditText().setText("");
                patientEmail.getEditText().setText("");
                genderGroup.clearCheck();
            }
        });

        patientTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb.getText().equals("Inpatient")) {
                    patientTreatmentNeeded.setVisibility(View.VISIBLE);
                } else {
                    patientTreatmentNeeded.setVisibility(View.GONE);
                }
            }
        });

        registerPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String pAge = patientAge.getEditText().getText().toString();
                final String pName = patientName.getEditText().getText().toString();
                final String pPhone = patientPhone.getEditText().getText().toString();
                final String pPassword = patientPassword.getEditText().getText().toString();
                String pConfirmPassword = patientConfirmPassword.getEditText().getText().toString();
                final String pEmail = patientEmail.getEditText().getText().toString();
                final String pAddress = patientAddress.getEditText().getText().toString();
                final String pNationality = patientNationality.getEditText().getText().toString();
                final String pDepartment = patientDepartmentToVisit.getEditText().getText().toString();
                final String pTreatment = patientTreatmentNeeded.getEditText().getText().toString();

                if (TextUtils.isEmpty(pAge) || TextUtils.isEmpty(pName) || TextUtils.isEmpty(pPhone) || TextUtils.isEmpty(pPassword) || TextUtils.isEmpty(pConfirmPassword) || TextUtils.isEmpty(pEmail) || TextUtils.isEmpty(pAddress) || TextUtils.isEmpty(pNationality) || TextUtils.isEmpty(pDepartment)) {
                    Toast.makeText(AddPatientActivity.this, "Please fill details", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(pEmail).matches()) {
                    Toast.makeText(AddPatientActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!pPassword.equals(pConfirmPassword)) {
                    Toast.makeText(AddPatientActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pPhone.length() != 10) {
                    Toast.makeText(AddPatientActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!maleButton.isChecked() && !femaleButton.isChecked()) {
                    Toast.makeText(AddPatientActivity.this, "Please select gender", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!inpatientButton.isChecked() && !outPatient.isChecked()) {
                    Toast.makeText(AddPatientActivity.this, "Please select patient Type", Toast.LENGTH_LONG).show();
                    return;
                }

                RadioButton rb = (RadioButton) genderGroup.findViewById(genderGroup.getCheckedRadioButtonId());
                final String g = rb.getText().toString();
                RadioButton radioButton = (RadioButton) patientTypeGroup.findViewById(patientTypeGroup.getCheckedRadioButtonId());
                final String type = radioButton.getText().toString();
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(pEmail, pPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (BedCount.blist.size() >= 600 && type.equals("Inpatient")) {
                                Toast.makeText(AddPatientActivity.this, "Bed Not Available", Toast.LENGTH_LONG).show();
                                return;
                            }
                            String bno = Integer.toString(++BedCount.bCount);
                            BedCount.blist.add(Integer.parseInt(bno));
                            String uid = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference patientTypeRef;
                            if (type.equals("Outpatient")) {
                                patientTypeRef = patientRef.child("outpatients");
                                HashMap<String, String> patientMap = new HashMap();
                                patientMap.put("name", pName);
                                patientMap.put("age", pAge);
                                patientMap.put("gender", g);
                                patientMap.put("phone", pPhone);
                                patientMap.put("image", "default");
                                patientMap.put("address", pAddress);
                                patientMap.put("nationality", pNationality);
                                patientMap.put("department", pDepartment);
                                patientTypeRef.child(uid).setValue(patientMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(AddPatientActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(AddPatientActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            progressDialog.hide();
                                        }
                                    }
                                });
                            } else {
                                patientTypeRef = patientRef.child("inpatients");
                                HashMap<String, String> patientMap = new HashMap();
                                patientMap.put("name", pName);
                                patientMap.put("age", pAge);
                                patientMap.put("gender", g);
                                patientMap.put("phone", pPhone);
                                patientMap.put("image", "default");
                                patientMap.put("address", pAddress);
                                patientMap.put("nationality", pNationality);
                                patientMap.put("department", pDepartment);
                                patientMap.put("treatment", pTreatment);
                                patientTypeRef.child(uid).setValue(patientMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(AddPatientActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(AddPatientActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            progressDialog.hide();
                                        }
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(AddPatientActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    }
                });
            }
        });

    }
}
