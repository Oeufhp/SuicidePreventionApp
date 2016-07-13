package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HelpNearYouOverview extends AppCompatActivity {

    Button nearbyplacesbtn,searchnearbyplacesbtn ;
    EditText searchbyplaces_gps_editttext, searchnearbyplaces_city_edittext, searchnearbyplaces_country_edittext;
    //----getIntent--------
    Intent it;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_near_you_overview);
        //----------getIntent---------
        it =getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
            /*Bundle b = getIntent().getExtras();
                String username = b.getString("username");
                String password = b.getString("password");*/
        //--------- binding ----------
        nearbyplacesbtn = (Button)findViewById(R.id.nearbyplacesbtn);
        searchbyplaces_gps_editttext = (EditText)findViewById(R.id.searchnearbyplaces_gps_edittext);
        searchnearbyplaces_city_edittext = (EditText)findViewById(R.id.searchnearbyplaces_city_edittext);
        searchnearbyplaces_country_edittext = (EditText)findViewById(R.id.searchnearbyplaces_country_edittext);
        searchnearbyplacesbtn = (Button)findViewById(R.id.searchnearbyplacesbtn);

        //---------My logics ---------
        nearbyplacesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ! (searchbyplaces_gps_editttext.getText().toString().equals("")) ) {
                    Intent it = new Intent(HelpNearYouOverview.this, HelpNearYou.class);
                    it.putExtra("input_city", "empty");
                    it.putExtra("input_country", "empty");
                    it.putExtra("displacement", searchbyplaces_gps_editttext.getText().toString());
                    startActivity(it);
                }
            }
        });
        searchnearbyplacesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //*************** important: getText().toString().equals("") ************************
                if( ! (searchnearbyplaces_city_edittext.getText().toString().equals("")) && ! (searchnearbyplaces_country_edittext.getText().toString().equals(""))){
                    Intent it = new Intent(HelpNearYouOverview.this, HelpNearYou.class);
                    it.putExtra("input_city", searchnearbyplaces_city_edittext.getText().toString());
                    it.putExtra("input_country", searchnearbyplaces_country_edittext.getText().toString());
                    it.putExtra("displacement", "empty" );
                    startActivity(it);
                }
                else {
                    Toast.makeText(getApplicationContext(),"You have to complete your inputs !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
