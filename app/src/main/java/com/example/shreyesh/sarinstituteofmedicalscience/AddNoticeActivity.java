package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddNoticeActivity extends AppCompatActivity {

    private DatabaseReference noticeRef;
    private Toolbar addNoticeToolbar;
    private ProgressDialog progressDialog;
    private Button postNotice;
    private EditText noticeDate, noticeTitle, noticeBody;
    private Calendar myCalender = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding Notice");
        progressDialog.setMessage("Please wait while we add your notice...");
        progressDialog.setCanceledOnTouchOutside(false);
        noticeDate = (EditText) findViewById(R.id.noticeDate);
        noticeBody = (EditText) findViewById(R.id.noticeBody);
        noticeTitle = (EditText) findViewById(R.id.noticeTitle);
        addNoticeToolbar = (Toolbar) findViewById(R.id.addNoticeToolbar);
        setSupportActionBar(addNoticeToolbar);
        getSupportActionBar().setTitle("Add New Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        postNotice = (Button) findViewById(R.id.addNewNoticeButton);

        noticeRef = FirebaseDatabase.getInstance().getReference().child("notices");


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalender.set(Calendar.YEAR, i);
                myCalender.set(Calendar.MONTH, i1);
                myCalender.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();
            }
        };

        noticeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNoticeActivity.this, date, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(
                        Calendar.DAY_OF_MONTH)).show();
            }
        });


        postNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = noticeDate.getText().toString();
                String title = noticeTitle.getText().toString();
                String body = noticeBody.getText().toString();

                if (TextUtils.isEmpty(date) || TextUtils.isEmpty(title) || TextUtils.isEmpty(body)) {
                    Toast.makeText(AddNoticeActivity.this, "Please fill all details", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.show();
                HashMap<String, String> noticeMap = new HashMap<>();
                noticeMap.put("date", date);
                noticeMap.put("title", title);
                noticeMap.put("body", body);

                noticeRef.push().setValue(noticeMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(AddNoticeActivity.this, "Notice added succesfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AddNoticeActivity.this, AdminHomeActivity.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AddNoticeActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        noticeDate.setText(simpleDateFormat.format(myCalender.getTime()));
    }
}
