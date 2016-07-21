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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SurveyOverview extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button cagebtn, c_ssrsbtn, surveyhistorybtn, seeupdatebtn ;
    //----getIntent--------
    Intent it;
    String username,password;

    //---About Dialog---
    DialogFragment newLogoutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_overview);
        //----------getIntent---------
        it = getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
        //--------- binding ----------
        surveyhistorybtn = (Button) findViewById(R.id.surveyhistorybtn);
        seeupdatebtn = (Button) findViewById(R.id.seeupdatebtn);
        cagebtn = (Button) findViewById(R.id.cagebtn);
        c_ssrsbtn = (Button) findViewById(R.id.c_ssrsbtn);
        //---------My logics ---------
        cagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyOverview.this, Survey_Cage.class);
                it.putExtra("username", username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
        c_ssrsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyOverview.this, Survey_C_ssrs.class);
                it.putExtra("username", username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
        surveyhistorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyOverview.this, SurveyHistoryOverview.class);
                it.putExtra("username", username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
        seeupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SurveyOverview.this, SurveyVisualizationOverview.class);
                it.putExtra("username", username);
                it.putExtra("password", password);
                startActivity(it);
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
            it = new Intent(SurveyOverview.this, MainActivity.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(SurveyOverview.this, YourSpace.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_todo) {
            it = new Intent(SurveyOverview.this, Todo.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(SurveyOverview.this, SafetyPlanning.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {    //go to HelpNearYouOverview.class
            it = new Intent(SurveyOverview.this, HelpNearYouOverview.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(SurveyOverview.this, Feeling.class);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_survey) {
            it = new Intent(SurveyOverview.this, SurveyOverview.class);
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
                    Intent it = new Intent(SurveyOverview.this, LoginActivity.class);
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