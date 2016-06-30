package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText user,pass;
    Button loginbtn;
    TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //-----tie vars to id--------------------------------------------------
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        loginbtn = (Button)findViewById(R.id.loginbtn);
        forgotpass = (TextView)findViewById(R.id.forgotpass);

    //------------------------- Demo Login (No APIs & Database) ----------------------------------------------
        //strUser = user.getText().toString().trim();
        //strPass = user.getText().toString().trim();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),user.getText(),Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),pass.getText(),Toast.LENGTH_LONG).show();

                //if(user.getText().equals(pass.getText())) { //and == but don't work
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                //}
//                else {
//                      //-----Toast in setOnClickListener-------
//                        Toast.makeText(getApplicationContext(),"Invalid Account",Toast.LENGTH_LONG).show();
//                    }

            }
        });
        //----------------------------------------------------------------------------------------------------
        //------Link and change textView to be same as the link-------      //HOW TO LINK PERFECTLY ???
//        forgotpass.setText("Forgot your pasword");
//        Linkify.addLinks(forgotpass, Linkify.ALL);

        //---------------------------------------------------------------------------------------------

    }
}
