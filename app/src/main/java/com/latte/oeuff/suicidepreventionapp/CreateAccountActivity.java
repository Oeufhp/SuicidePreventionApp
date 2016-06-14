package com.latte.oeuff.suicidepreventionapp;


import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;


public class CreateAccountActivity extends AppCompatActivity {
    RadioButton malebtn, femalebtn, otherbtn;
    EditText surname,name;
    //Spinner datespinner, monthspinner, yearspinner;
    TextView datetxtview, monthtxtview, yeartxtview;
    ImageButton pickdate;
    EditText email;
    RadioButton yesbtn, nobtn;
    Spinner countryflagspinner;
    TextView countrycodetxtview;
    EditText phoneno;
    EditText username, password;
    TextView isvalidtxtview, completedview;
    Button  checkbtn, createbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    //---------Binding--------------------------------------
        malebtn = (RadioButton)findViewById(R.id.malebtn);
        femalebtn = (RadioButton)findViewById(R.id.femalebtn);
        otherbtn = (RadioButton)findViewById(R.id.otherbtn);
        surname = (EditText)findViewById(R.id.surname);
        name = (EditText)findViewById(R.id.name);
        //datespinner = (Spinner)findViewById(R.id.datespinner);
        //monthspinner = (Spinner)findViewById(R.id.monthspinner);
        //yearspinner = (Spinner)findViewById(R.id.yearspinner);
        email = (EditText)findViewById(R.id.email);
        yesbtn = (RadioButton)findViewById(R.id.yesbtn);
        nobtn = (RadioButton)findViewById(R.id.nobtn);

//-----------------------for contryflagspinner-------------------------------------
//https://developer.android.com/guide/topics/ui/controls/spinner.html

        countryflagspinner = (Spinner)findViewById(R.id.countryflagspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countryflagspinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryflagspinner.setAdapter(adapter);
//-------------------------------------------------------------------------------

        countrycodetxtview = (TextView) findViewById(R.id.countrycodetxtview);
        phoneno = (EditText)findViewById(R.id.phoneno);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        checkbtn = (Button)findViewById(R.id.checkbtn);
        isvalidtxtview = (TextView)findViewById(R.id.isvalidtxtview);
        completedview = (TextView)findViewById(R.id.completedtxtview);
        createbtn = (Button)findViewById(R.id.createbtn);

    //------------------------------- My logics -----------------------------------------------------
    //DEMO ONLY (No APIs & Database)
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent it = new Intent(CreateAccountActivity.this, MainActivity.class);
                Intent it = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }

    //---------------- Show TimePicker & DatePicker --------------------------------------
//    public void showTimePickerDialog(View v) {
//        DialogFragment newTimeFragment = new TimePickerFragment();
//        newTimeFragment.show(getSupportFragmentManager(), "timePicker");
//    } and must be binded w/ XML file.

    public void showDatePickerDialog(View v) {
        DialogFragment newDateFragment = new DatePickerFragment();
        newDateFragment.show(getSupportFragmentManager(), "datePicker");

    }


}
