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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddConsultantActivity extends AppCompatActivity {

    private Button addConsultant;
    private TextInputLayout consultantName, consultantEmail, consultantPassword, consultantConfirmPassword, consultantAge;
    private RadioGroup consultantGenderGroup;
    private RadioButton male, female;
    private DatabaseReference consultantRef;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Toolbar addConsultantToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consultant);

        addConsultantToolbar = (Toolbar) findViewById(R.id.addConsultantToolbar);
        setSupportActionBar(addConsultantToolbar);
        getSupportActionBar().setTitle("Add Consultant");
        addConsultant = (Button) findViewById(R.id.addConsulant);
        consultantAge = (TextInputLayout) findViewById(R.id.consultantRegAge);
        consultantEmail = (TextInputLayout) findViewById(R.id.consultantRegEmail);
        consultantName = (TextInputLayout) findViewById(R.id.consultantRegName);
        consultantPassword = (TextInputLayout) findViewById(R.id.consultantRegPassword);
        consultantConfirmPassword = (TextInputLayout) findViewById(R.id.consultantRegConfirmPassword);
        consultantGenderGroup = (RadioGroup) findViewById(R.id.consultantGender);
        male = (RadioButton) findViewById(R.id.consultantRegMale);
        female = (RadioButton) findViewById(R.id.consultantRegFemale);

        consultantRef = FirebaseDatabase.getInstance().getReference().child("consultants");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while we register...");
        progressDialog.setTitle("Registering");
        progressDialog.setCanceledOnTouchOutside(false);

        addConsultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rb = (RadioButton) consultantGenderGroup.findViewById(consultantGenderGroup.getCheckedRadioButtonId());
                final String gender = rb.getText().toString();
                final String age = consultantAge.getEditText().getText().toString();
                String email = consultantEmail.getEditText().getText().toString();
                String password = consultantPassword.getEditText().getText().toString();
                String confirmPassword = consultantConfirmPassword.getEditText().getText().toString();
                final String name = consultantName.getEditText().getText().toString();

                if (TextUtils.isEmpty(age) || TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(AddConsultantActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(AddConsultantActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!male.isChecked() && !female.isChecked()) {
                    Toast.makeText(AddConsultantActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(AddConsultantActivity.this, "Passwords donot match", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userid = task.getResult().getUser().getUid();
                            HashMap<String, String> consultantMap = new HashMap<>();
                            consultantMap.put("name", name);
                            consultantMap.put("gender", gender);
                            consultantMap.put("age", age);
                            consultantMap.put("image", "default");
                            consultantRef.child(userid).setValue(consultantMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(AddConsultantActivity.this, "Consultant Added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddConsultantActivity.this, AdminConsultantListActivity.class));
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(AddConsultantActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AddConsultantActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
