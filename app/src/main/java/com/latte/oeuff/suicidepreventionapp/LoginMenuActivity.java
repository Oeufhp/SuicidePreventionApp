package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginMenuActivity extends AppCompatActivity {
    TextView ourwebsite;
    ImageButton login, create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        //-----Tie variables to id in xml----------------------
        ourwebsite = (TextView)findViewById(R.id.ourwebsite);
        login = (ImageButton)findViewById(R.id.login);
        //facebook
        //gmail
        create = (ImageButton)findViewById(R.id.create);

        //----------Logics-----------------------------------------------
        ourwebsite.setText("http://www.suicidepreventionlifeline.org/");
        Linkify.addLinks(ourwebsite, Linkify.ALL);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //Must add 2 lines in styles.xml to be able to startActivity(it)
                Intent it = new Intent(LoginMenuActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginMenuActivity.this, CreateAccountActivity.class);
                startActivity(it);
            }
        });



    }
}
