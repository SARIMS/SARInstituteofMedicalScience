package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;




public class PatientLoginActivity extends AppCompatActivity {


    private Toolbar patientLoginToolbar;
    private TextView regPatient;
    private ProgressDialog progressDialog;
    private Button patientLoginButton;
    private FirebaseAuth firebaseAuth;
    private RadioGroup patientLoginRadioGroup;
    private RadioButton patientLoginInpatient, patientLoginOutpatient;

    private TextInputLayout patientEmail, patientPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        patientLoginToolbar = (Toolbar) findViewById(R.id.patientLoginToolbar);
        setSupportActionBar(patientLoginToolbar);
        getSupportActionBar().setTitle("Patient Login");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        regPatient = (TextView) findViewById(R.id.regPatientLoginPage);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait while we check your credentials...");
        progressDialog.setCanceledOnTouchOutside(false);

        patientLoginRadioGroup = (RadioGroup) findViewById(R.id.patientLoginType);
        patientLoginInpatient = (RadioButton) findViewById(R.id.inpatientLogin);
        patientLoginOutpatient = (RadioButton) findViewById(R.id.outpatientLogin);
        patientEmail = (TextInputLayout) findViewById(R.id.patientLoginEmail);
        patientPassword = (TextInputLayout) findViewById(R.id.patientLoginPassword);
        patientLoginButton = (Button) findViewById(R.id.patientLoginButton);


        patientLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = patientEmail.getEditText().getText().toString();
                String password = patientPassword.getEditText().getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(PatientLoginActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(PatientLoginActivity.this, "Invalid email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!patientLoginInpatient.isChecked() && !patientLoginOutpatient.isChecked()) {
                    Toast.makeText(PatientLoginActivity.this, "Please select patient type", Toast.LENGTH_LONG).show();
                    return;
                }

                RadioButton rb = (RadioButton) patientLoginRadioGroup.findViewById(patientLoginRadioGroup.getCheckedRadioButtonId());
                final String type = rb.getText().toString() + "s";
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(PatientLoginActivity.this, PatientHomeActivity.class);
                            intent.putExtra("type", type.toLowerCase());
                            startActivity(intent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(PatientLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

            }
        });

        regPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientLoginActivity.this, AddPatientActivity.class));
            }
        });



    }
}
