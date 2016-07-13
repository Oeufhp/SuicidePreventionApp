package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyOverview extends AppCompatActivity {
    Button dosurveybtn, surveyhistorybtn, seeupdatebtn;
    //----getIntent--------
    Intent it;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_overview);
        //----------getIntent---------
        it =getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
        //--------- binding ----------
        dosurveybtn = (Button)findViewById(R.id.dosurveybtn);
        surveyhistorybtn = (Button)findViewById(R.id.surveyhistorybtn);
        seeupdatebtn = (Button)findViewById(R.id.seeupdatebtn);
        //---------My logics ---------
        dosurveybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyOverview.this, Survey.class);
                it.putExtra("username",username);
                it.putExtra("password",password);
                startActivity(it);
            }
        });
        surveyhistorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyOverview.this, SurveyHistory.class);
                it.putExtra("username",username);
                it.putExtra("password",password);
                startActivity(it);
            }
        });
        seeupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyOverview.this, SurveyVisualization.class);
                it.putExtra("username",username);
                it.putExtra("password",password);
                startActivity(it);
            }
        });
    }
}