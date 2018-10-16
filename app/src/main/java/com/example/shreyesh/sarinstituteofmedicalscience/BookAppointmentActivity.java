package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class BookAppointmentActivity extends AppCompatActivity {

    private Toolbar bookAppointmentToolbar;
    private EditText date, time;
    private Button confirm, cancel;
    private String userid, username;
    private DatabaseReference doctorRef, appointmentRef;
    private TextView doctorsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        userid = getIntent().getStringExtra("userid");
        username = getIntent().getStringExtra("username");

        //Initialize views
        bookAppointmentToolbar = (Toolbar) findViewById(R.id.bookAppointmentToolbar);
        setSupportActionBar(bookAppointmentToolbar);
        getSupportActionBar().setTitle("Book Appointment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        date = (EditText) findViewById(R.id.appointmentDate);
        time = (EditText) findViewById(R.id.appointmentTime);
        confirm = (Button) findViewById(R.id.confirmAppointment);
        cancel = (Button) findViewById(R.id.cancelAppointment);
        doctorsName = (TextView) findViewById(R.id.doctorNameAppointment);

        doctorsName.setText("Dr. " + username);


    }
}
