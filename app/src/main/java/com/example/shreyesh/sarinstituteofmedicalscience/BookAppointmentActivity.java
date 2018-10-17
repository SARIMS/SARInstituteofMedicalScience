package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookAppointmentActivity extends AppCompatActivity {

    private Toolbar bookAppointmentToolbar;
    private EditText appointmentDate, appointmentTime;
    private Button confirm, cancel;
    private String userid, username;
    private DatabaseReference doctorRef, appointmentRef;
    private TextView doctorsName;
    private Calendar myCalender = Calendar.getInstance();
    private Calendar mcurrentTime = Calendar.getInstance();
    private int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    private int minute = mcurrentTime.get(Calendar.MINUTE);
    private TimePickerDialog mTimePicker;

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

        appointmentDate = (EditText) findViewById(R.id.appointmentDate);
        appointmentTime = (EditText) findViewById(R.id.appointmentTime);
        confirm = (Button) findViewById(R.id.confirmAppointment);
        doctorsName = (TextView) findViewById(R.id.doctorNameAppointment);

        doctorsName.setText("Dr. " + username);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalender.set(Calendar.YEAR, i);
                myCalender.set(Calendar.MONTH, i1);
                myCalender.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();
            }
        };

        appointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BookAppointmentActivity.this, date, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(
                        Calendar.DAY_OF_MONTH)).show();
            }
        });


        appointmentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTimePicker = new TimePickerDialog(BookAppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String am_pm = "AM";
                        if (selectedHour > 12) {
                            selectedHour = selectedHour - 12;
                            am_pm = "PM";
                        }
                        appointmentTime.setText(selectedHour + ":" + selectedMinute + " " + am_pm);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        appointmentDate.setText(simpleDateFormat.format(myCalender.getTime()));
    }
}
