package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HelpNearYouOverview extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton nearbyplacesbtn,searchnearbyplacesbtn ;
    EditText searchbyplaces_gps_editttext, searchnearbyplaces_city_edittext, searchnearbyplaces_country_edittext;

    //----getIntent--------
    Intent it;
    String username,password;

    //---About Dialog---
    DialogFragment newLogoutFragment;

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
        nearbyplacesbtn = (ImageButton)findViewById(R.id.nearbyplacesbtn);
        searchbyplaces_gps_editttext = (EditText)findViewById(R.id.searchnearbyplaces_gps_edittext);
        searchnearbyplaces_city_edittext = (EditText)findViewById(R.id.searchnearbyplaces_city_edittext);
        searchnearbyplaces_country_edittext = (EditText)findViewById(R.id.searchnearbyplaces_country_edittext);
        searchnearbyplacesbtn = (ImageButton)findViewById(R.id.searchnearbyplacesbtn);

        //---------My logics ---------
        nearbyplacesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ! (searchbyplaces_gps_editttext.getText().toString().equals("")) ) {
                    Intent it = new Intent(HelpNearYouOverview.this, HelpNearYou.class);
                    it.putExtra("input_city", "empty");
                    it.putExtra("input_country", "empty");
                    it.putExtra("displacement", searchbyplaces_gps_editttext.getText().toString());

                    Log.d("nearbyplacesbtn","here");

                    startActivity(it);
                }
                else {
                    Toast.makeText(getApplicationContext(),"You have to complete your inputs !", Toast.LENGTH_SHORT).show();
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

        //************************ This is for creating the Navigation Menu*********************************
        //Toolbar (Top)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //Set a Toolbar to act as  ActionBar for this Activity

        // top-level container of "Navigation Drawer" (side)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(  //=tie together "functionality of DrawerLayout" <-> "framework ActionBar"
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);                         //Set a listener to be notified of drawer events
        toggle.syncState();                                       //Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout

        //view of "Navigation Drawer" (side)
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //*****To uncover colors of icon**********
        navigationView.setItemIconTintList(null);

        //floating button (bottom)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBtn);
        fab.setImageResource(R.drawable.emergencycall);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                Snackbar.make(view, "Emergency call ?", Snackbar.LENGTH_LONG) //=bottom black bar
                //                        .setAction("Action", null).show();

                //This is for going to phone in mobile
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:112"));
                //no need to request a permission
                startActivity(callIntent);

            }
        });
        //**************************************************************************************************
    }

    //************************ This is for creating the Navigation Menu*********************************
    //Close "Navigation Drawer"
    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) { //GravityCompat = Compatibility shim for accessing newer functionality from Gravity
            //Gravity =  Standard constants, tools for placing an object within a potentially larger container
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Initialize the contents of the Activity's standard options menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate(add) the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu); //MenuInflater allows you to inflate the context menu from a menu resource
        //Bind "MainActivity.java" <-> main.xml for using "setting popup-menu"
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    { // Called when the user selects an item from the options menu. Handle action bar item clicks here.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) { //it is in main.xml (ctrl+click to see)
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item)
    { //This method is called whenever a navigation item in your action bar is selected
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //CHECK IN "activity_main_drawer.xml"
        //--------- Logics after pressing items on Navigation -------------
        Intent it;
        if (id == R.id.nav_home) {
            it = new Intent(HelpNearYouOverview.this, MainActivity.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(HelpNearYouOverview.this, YourSpace.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_todo) {
            it = new Intent(HelpNearYouOverview.this, Todo.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(HelpNearYouOverview.this, SafetyPlanning.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {    //go to HelpNearYouOverview.class
            it = new Intent(HelpNearYouOverview.this, HelpNearYouOverview.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(HelpNearYouOverview.this, Feeling.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_survey) {
            it = new Intent(HelpNearYouOverview.this, SurveyOverview.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_logout) {
            newLogoutFragment.show(getSupportFragmentManager(), "LogOut");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //**************************************************************************************************
//---------------------------- Dialog for warning before logging out -------------------------------//
    public class LogOutDialog extends DialogFragment {

        TextView yesbtn_logout, nobtn_logout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //-----Binding-----------
            View view = inflater.inflate(R.layout.dialog_logout, container);
            yesbtn_logout = (TextView) view.findViewById(R.id.yesbtn_logout);
            nobtn_logout = (TextView) view.findViewById(R.id.nobtn_logout);

            //-----Logics-----------
            yesbtn_logout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newLogoutFragment.dismiss(); //close the dialog
                    Intent it = new Intent(HelpNearYouOverview.this, LoginActivity.class);
                    startActivity(it);
                    return false;
                }
            });
            nobtn_logout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newLogoutFragment.dismiss(); //close the dialog
                    return false;
                }
            });
            return view;
        }
    }
}
