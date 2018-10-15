package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static android.view.View.GONE;

public class AddDoctorActivity extends AppCompatActivity {

    private Spinner toSundaySpinner, fromSundaySpinner, toMondaySpinner, fromMondaySpinner, toTuesdaySpinner, fromTuesdaySpinner, toWednesdaySpinner, fromWednesdaySpinner, toThursdaySpinner, fromThursdaySpinner;
    private Spinner toFridaySpinner, fromFridaySpinner, toSaturdaySpinner, fromSaturdaySpinner;
    private TextInputLayout doctorRegName, doctorRegDepartment, doctorRegEmail, doctorRegPassword, doctorRegConfirmPassword;
    private Button addDoctorButton;
    private EditText fromSunday, fromMonday, fromTuesday, fromWednesday, fromThursday, fromFriday, fromSaturday;
    private EditText toSunday, toMonday, toTuesday, toWednesday, toThursday, toFriday, toSaturday;
    private Toolbar addDoctorToolbar;
    private DatabaseReference doctorRef;
    private LinearLayout sundayLayout, mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout, saturdayLayout;
    private CheckBox sundayCheckbox, mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox, fridayCheckBox, saturdayCheckBox;

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

        //checkbox
        mondayCheckBox = (CheckBox) findViewById(R.id.mondayCheckbox);
        tuesdayCheckBox = (CheckBox) findViewById(R.id.tuesdayCheckbox);
        wednesdayCheckBox = (CheckBox) findViewById(R.id.wednesdayCheckBox);
        thursdayCheckBox = (CheckBox) findViewById(R.id.thursdayCheckBox);
        fridayCheckBox = (CheckBox) findViewById(R.id.fridayCheckBox);
        saturdayCheckBox = (CheckBox) findViewById(R.id.saturdayCheckbox);
        sundayCheckbox = (CheckBox) findViewById(R.id.sundayCheckBox);

        //layout
        sundayLayout = (LinearLayout) findViewById(R.id.sundayLayout);
        mondayLayout = (LinearLayout) findViewById(R.id.mondayLayout);
        tuesdayLayout = (LinearLayout) findViewById(R.id.tuesdayLayout);
        wednesdayLayout = (LinearLayout) findViewById(R.id.wednesdayLayout);
        thursdayLayout = (LinearLayout) findViewById(R.id.thursdayLayout);
        fridayLayout = (LinearLayout) findViewById(R.id.fridayLayout);
        saturdayLayout = (LinearLayout) findViewById(R.id.saturdayLayout);

        //Toolbar
        addDoctorToolbar = (Toolbar) findViewById(R.id.addDoctorToolbar);
        setSupportActionBar(addDoctorToolbar);
        getSupportActionBar().setTitle("Add Doctor");

        ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("AM");
        spinnerList.add("PM");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //to spinner
        toSundaySpinner.setAdapter(dataAdapter);
        toMondaySpinner.setAdapter(dataAdapter);
        toTuesdaySpinner.setAdapter(dataAdapter);
        toWednesdaySpinner.setAdapter(dataAdapter);
        toThursdaySpinner.setAdapter(dataAdapter);
        toFridaySpinner.setAdapter(dataAdapter);
        toSaturdaySpinner.setAdapter(dataAdapter);

        //from
        fromSundaySpinner.setAdapter(dataAdapter);
        fromMondaySpinner.setAdapter(dataAdapter);
        fromTuesdaySpinner.setAdapter(dataAdapter);
        fromWednesdaySpinner.setAdapter(dataAdapter);
        fromThursdaySpinner.setAdapter(dataAdapter);
        fromFridaySpinner.setAdapter(dataAdapter);
        fromSaturdaySpinner.setAdapter(dataAdapter);


        //initial visibility
        sundayLayout.setVisibility(GONE);
        mondayLayout.setVisibility(GONE);
        tuesdayLayout.setVisibility(GONE);
        wednesdayLayout.setVisibility(GONE);
        thursdayLayout.setVisibility(GONE);
        fridayLayout.setVisibility(GONE);
        saturdayLayout.setVisibility(GONE);

        sundayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sundayCheckbox.isChecked()) {
                    sundayLayout.setVisibility(View.VISIBLE);
                } else
                    sundayLayout.setVisibility(GONE);
            }
        });

        mondayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mondayCheckBox.isChecked())
                    mondayLayout.setVisibility(View.VISIBLE);
                else
                    mondayLayout.setVisibility(GONE);
            }
        });

        tuesdayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (tuesdayCheckBox.isChecked())
                    tuesdayLayout.setVisibility(View.VISIBLE);
                else
                    tuesdayLayout.setVisibility(GONE);
            }
        });

        wednesdayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (wednesdayCheckBox.isChecked())
                    wednesdayLayout.setVisibility(View.VISIBLE);
                else
                    wednesdayLayout.setVisibility(GONE);
            }
        });

        thursdayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (thursdayCheckBox.isChecked())
                    thursdayLayout.setVisibility(View.VISIBLE);
                else
                    thursdayLayout.setVisibility(GONE);
            }
        });

        fridayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (fridayCheckBox.isChecked())
                    fridayLayout.setVisibility(View.VISIBLE);
                else
                    fridayLayout.setVisibility(GONE);
            }
        });

        saturdayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (saturdayCheckBox.isChecked())
                    saturdayLayout.setVisibility(View.VISIBLE);
                else
                    saturdayLayout.setVisibility(GONE);
            }
        });





    }
}
