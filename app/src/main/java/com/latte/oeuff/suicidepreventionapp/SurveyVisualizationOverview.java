package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyVisualizationOverview extends AppCompatActivity {
    Button cage_seeupdatebtn, c_ssrs_seeupdatebtn;
    //----getIntent--------
    Intent it;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_visualization_overview);
        //----------getIntent---------
        it = getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");

        //--------- binding ----------
        cage_seeupdatebtn = (Button) findViewById(R.id.cage_seeupdatebtn);
        c_ssrs_seeupdatebtn = (Button) findViewById(R.id.c_ssrs_seeupdatebtn);

        //---------My logics ---------
        cage_seeupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyVisualizationOverview.this, SurveyVisualization_Cage_Logics.class);
                it.putExtra("username", username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
        c_ssrs_seeupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(SurveyVisualizationOverview.this, SurveyVisualization_C_ssrs.class);
//                it.putExtra("username",username);
//                it.putExtra("password",password);
//                startActivity(it);
            }
        });
    }
}
