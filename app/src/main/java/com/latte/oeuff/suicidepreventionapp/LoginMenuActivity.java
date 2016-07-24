
package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class LoginMenuActivity extends AppCompatActivity {
    //TextView url;
    ImageButton login, facebook, gmail, www, loginmenu_create;
    ImageButton frenchbtn, thaibtn, englishbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        //-----Tie variables to id in xml----------------------
        //url = (TextView)findViewById(R.id.url);
        login = (ImageButton) findViewById(R.id.login);
        //facebook = (ImageButton)findViewById(R.id.facebook);
        //gmail = (ImageButton)findViewById(R.id.gmail);
        www = (ImageButton) findViewById(R.id.www);
        loginmenu_create = (ImageButton) findViewById(R.id.loginmenu_create);
        //frenchbtn = (ImageButton)findViewById(R.id.frenchbtn);
        //thaibtn = (ImageButton)findViewById(R.id.thaibtn);
        //frenchbtn = (ImageButton)findViewById(R.id.englishbtn);

        //-------------------My Logics--------------------------------------------------------------
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //Must add 2 lines in styles.xml to be able to startActivity(it)
                Intent it = new Intent(LoginMenuActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });
        //url.setText("http://www.suicidepreventionlifeline.org/");
        //Linkify.addLinks(url, Linkify.ALL);
        www.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //Must add 2 lines in styles.xml to be able to startActivity(it)
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                //intent.setData(Uri.parse("http://www.suicidepreventionlifeline.org/"));
                intent.setData(Uri.parse("http://oeufhp.me/")); // Use the url that link to API
                startActivity(intent);

            }
        });
        loginmenu_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginMenuActivity.this, CreateAccountActivity.class);
                startActivity(it);
            }
        });
        //------------------------------------------------------------------------------------------
    }
}
