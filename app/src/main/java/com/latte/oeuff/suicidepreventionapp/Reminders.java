//scrolling activity: Insert codes of "Navigation Drawer Activity"

package com.latte.oeuff.suicidepreventionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;


public class Reminders extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    private EditText fromDateEtxt;
//    private EditText toDateEtxt;
//
//    private DatePickerDialog fromDatePickerDialog;
//    private DatePickerDialog toDatePickerDialog;
//
//    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

//************************ This is for creating the Navigation Menu*********************************
    //Toolbar (Top)
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRem);
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
        fab.setImageResource(R.drawable.ic_warning_white_40dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Emergency call ?", Snackbar.LENGTH_LONG) //=bottom black bar
//                        .setAction("Action", null).show();

                //This is for going to phone in mobile
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                //no need to request a permission
                startActivity(callIntent);
            }
        });
//**************************************************************************************************

    //Floating Button in Reminders
    FloatingActionButton fabRem = (FloatingActionButton) findViewById(R.id.fabBtnRem);
        fabRem.setImageResource(R.drawable.ic_new_reminder);
        fabRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("AA","BB");
                final AlertDialog.Builder builder = new AlertDialog.Builder(Reminders.this);
                LayoutInflater inflator = Reminders.this.getLayoutInflater();
                builder.setView(inflator.inflate(R.layout.dialog_create_reminder, null));
//                builder.setTitle("New Reminder");
                builder.setPositiveButton("create reminder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();

            }
        });
    }

//************************ This is for creating the Navigation Menu*********************************
    //Close "Navigation Drawer"
    @Override
    public void onBackPressed() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate(add) the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu); //MenuInflater allows you to inflate the context menu from a menu resource
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Called when the user selects an item from the options menu. Handle action bar item clicks here.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) { //it is in main.xml (ctrl+click to see)
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) { //This method is called whenever a navigation item in your action bar is selected
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //CHECK IN "activity_main_drawer.xml"
        //--------- Logics after pressing items on Navigation ----------------
        Intent it;
        if (id == R.id.nav_home) {
            it = new Intent(Reminders.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(Reminders.this, YourSpace.class);
            startActivity(it);
        } else if (id == R.id.nav_reminders) {
            it = new Intent(Reminders.this, Reminders.class);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(Reminders.this, SafetyPlanning.class);
            startActivity(it);
        } else if (id == R.id.nav_resources) {
            it = new Intent(Reminders.this, Resources.class);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {
            it = new Intent(Reminders.this, HelpNearYou.class);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(Reminders.this, Feeling.class);
            startActivity(it);
        }
        else if (id == R.id.nav_logout) {
            it = new Intent(Reminders.this, LoginMenuActivity.class);
            startActivity(it);

            //Dialouge ??
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//**************************************************************************************************


}
