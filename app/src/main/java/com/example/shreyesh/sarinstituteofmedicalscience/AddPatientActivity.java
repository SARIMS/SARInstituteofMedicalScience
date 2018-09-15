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
    private Button registerPatient, reset;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference patientRef;
    private RadioButton maleButton, femaleButton;
    private RadioGroup genderGroup;
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
        patientPassword = (TextInputLayout) findViewById(R.id.patientPassword);
        maleButton = (RadioButton) findViewById(R.id.maleRadioButton);
        femaleButton = (RadioButton) findViewById(R.id.femaleRadioButton);
        genderGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        genderGroup.clearCheck();
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


        registerPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String pAge = patientAge.getEditText().getText().toString();
                final String pName = patientName.getEditText().getText().toString();
                final String pPhone = patientPhone.getEditText().getText().toString();
                final String pPassword = patientPassword.getEditText().getText().toString();
                String pConfirmPassword = patientConfirmPassword.getEditText().getText().toString();
                final String pEmail = patientEmail.getEditText().getText().toString();

                if (TextUtils.isEmpty(pAge) || TextUtils.isEmpty(pName) || TextUtils.isEmpty(pPhone) || TextUtils.isEmpty(pPassword) || TextUtils.isEmpty(pConfirmPassword) || TextUtils.isEmpty(pEmail)) {
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

                RadioButton rb = (RadioButton) genderGroup.findViewById(genderGroup.getCheckedRadioButtonId());
                final String g = rb.getText().toString();
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(pEmail, pPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = firebaseAuth.getCurrentUser().getUid();
                            HashMap<String, String> patientMap = new HashMap();
                            patientMap.put("name", pName);
                            patientMap.put("age", pAge);
                            patientMap.put("gender", g);
                            patientMap.put("phone", pPhone);

                            patientRef.child(uid).setValue(patientMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                            Toast.makeText(AddPatientActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    }
                });

            }
        });

    }
}
