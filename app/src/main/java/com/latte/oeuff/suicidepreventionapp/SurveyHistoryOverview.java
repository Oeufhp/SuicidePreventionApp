package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyHistoryOverview extends AppCompatActivity {
    Button cagehistorybtn, c_ssrshistorybtn;
    //----getIntent--------
    Intent it;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_history_overview);
        //----------getIntent---------
        it = getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");

        //------ Binding ---------------------
        cagehistorybtn = (Button) findViewById(R.id.cagehistorybtn);
        c_ssrshistorybtn = (Button) findViewById(R.id.c_ssrshistorybtn);

        //-------My Logics --------------------
        cagehistorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyHistoryOverview.this, SurveyHistory_Cage.class);
                it.putExtra("username", username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
        c_ssrshistorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(SurveyHistoryOverview.this, SurveyHistory_C_ssrs.class);
//                it.putExtra("username",username);
//                it.putExtra("password",password);
//                startActivity(it);
            }
        });
    }
}
