package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddReportActivity extends AppCompatActivity {

    private DatabaseReference reportRef, staffRef;
    private TextInputLayout reportTitle;
    private TextView doctorsName, patientName;
    private EditText reportBody;
    private Button saveReport;
    private Toolbar addReportToolbar;
    private String type, currentID, patient, pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        doctorsName = (TextView) findViewById(R.id.reportDoctorName);
        patientName = (TextView) findViewById(R.id.reportPatientName);
        saveReport = (Button) findViewById(R.id.saveReport);
        reportBody = (EditText) findViewById(R.id.reportBody);
        reportTitle = (TextInputLayout) findViewById(R.id.reportTitle);
        addReportToolbar = (Toolbar) findViewById(R.id.addReportToolbar);
        setSupportActionBar(addReportToolbar);
        getSupportActionBar().setTitle("Add New Report");

        reportRef = FirebaseDatabase.getInstance().getReference().child("reports");

        type = getIntent().getStringExtra("type");
        patient = getIntent().getStringExtra("name");
        pid = getIntent().getStringExtra("pid");

        System.out.println(type);

        patientName.setText(patient);
        currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        staffRef = FirebaseDatabase.getInstance().getReference();
        staffRef.child(type).child(currentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String n = dataSnapshot.child("name").getValue().toString();
                if (type.equals("doctors"))
                    doctorsName.setText("Dr. " + n);
                else
                    doctorsName.setText("Consultant " + n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        saveReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String body = reportBody.getText().toString();
                String doctor = doctorsName.getText().toString();
                String title = reportTitle.getEditText().getText().toString();
                if (TextUtils.isEmpty(body)) {
                    Toast.makeText(AddReportActivity.this, "Please write report body", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> reportMap = new HashMap<>();
                reportMap.put("doctor", doctor);
                reportMap.put("title", title);
                reportMap.put("patient", patient);
                reportMap.put("body", body);

                reportRef.child(pid).push().setValue(reportMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddReportActivity.this, "Report Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddReportActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}
