package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddDoctorActivity extends AppCompatActivity {

    private Spinner toSundaySpinner, fromSundaySpinner, toMondaySpinner, fromMondaySpinner, toTuesdaySpinner, fromTuesdaySpinner, toWednesdaySpinner, fromWednesdaySpinner, toThursdaySpinner, fromThursdaySpinner;
    private Spinner toFridaySpinner, fromFridaySpinner, toSaturdaySpinner, fromSaturdaySpinner;
    private TextInputLayout doctorRegName, doctorRegDepartment, doctorRegEmail, doctorRegPassword, doctorRegConfirmPassword;
    private Button addDoctorButton;
    private EditText fromSunday, fromMonday, fromTuesday, fromWednesday, fromThursday, fromFriday, fromSaturday;
    private EditText toSunday, toMonday, toTuesday, toWednesday, toThursday, toFriday, toSaturday;
    private Toolbar addDoctorToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);


        //To spinner
        toFridaySpinner = (Spinner) findViewById(R.id.fridayToSpinner);
        toMondaySpinner = (Spinner) findViewById(R.id.mondayToSpinner);
        toTuesdaySpinner = (Spinner) findViewById(R.id.tuesdayToSpinner);
        toWednesdaySpinner = (Spinner) findViewById(R.id.wednesdayToSpinner);
        toThursdaySpinner = (Spinner) findViewById(R.id.thursdayToSpinner);
        toSaturdaySpinner = (Spinner) findViewById(R.id.saturdayToSpinner);
        toSundaySpinner = (Spinner) findViewById(R.id.sundayToSpinner);

        //from spinner
        fromFridaySpinner = (Spinner) findViewById(R.id.fridayFromSpinner);
        fromMondaySpinner = (Spinner) findViewById(R.id.mondayFromSpinner);
        fromTuesdaySpinner = (Spinner) findViewById(R.id.tuesdayFromSpinner);
        fromWednesdaySpinner = (Spinner) findViewById(R.id.wednesdayFromSpinner);
        fromThursdaySpinner = (Spinner) findViewById(R.id.thursdayFromSpinner);
        fromSaturdaySpinner = (Spinner) findViewById(R.id.saturdayFromSpinner);
        fromSundaySpinner = (Spinner) findViewById(R.id.sundayFromSpinner);

        //basic details
        doctorRegName = (TextInputLayout) findViewById(R.id.doctorRegName);
        doctorRegEmail = (TextInputLayout) findViewById(R.id.doctorRegEmail);
        doctorRegDepartment = (TextInputLayout) findViewById(R.id.doctorRegDepartment);
        doctorRegPassword = (TextInputLayout) findViewById(R.id.doctorRegPassword);
        doctorRegConfirmPassword = (TextInputLayout) findViewById(R.id.doctorRegConfirmPassword);
        addDoctorButton = (Button) findViewById(R.id.addDoctorButton);

        //from timings
        fromSunday = (EditText) findViewById(R.id.sundayFromEditText);
        fromMonday = (EditText) findViewById(R.id.mondayFromEditText);
        fromTuesday = (EditText) findViewById(R.id.tuesdayFromEditText);
        fromWednesday = (EditText) findViewById(R.id.wednesdayFromEditText);
        fromThursday = (EditText) findViewById(R.id.thursdayFromEditText);
        fromFriday = (EditText) findViewById(R.id.fridayFromEditText);
        fromSaturday = (EditText) findViewById(R.id.saturdayFromEditText);

        //to timings
        toSunday = (EditText) findViewById(R.id.sundayToEditText);
        toMonday = (EditText) findViewById(R.id.mondayToEditText);
        toTuesday = (EditText) findViewById(R.id.tuesdayToEditText);
        toWednesday = (EditText) findViewById(R.id.wednesdayToEditText);
        toThursday = (EditText) findViewById(R.id.thursdayToEditText);
        toFriday = (EditText) findViewById(R.id.fridayToEditText);
        toSaturday = (EditText) findViewById(R.id.saturdayToEditText);

        //Toolbar
        addDoctorToolbar = (Toolbar) findViewById(R.id.addDoctorToolbar);
        setSupportActionBar(addDoctorToolbar);
        getSupportActionBar().setTitle("Add Doctor");

    }
}
