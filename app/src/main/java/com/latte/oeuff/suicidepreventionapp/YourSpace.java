package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class YourSpace extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //================Camera===================================
    //https://developer.android.com/guide/topics/media/camera.html
    Button camerabtn;
    ImageView imgView;
    Button showImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_space);

        camerabtn = (Button)findViewById(R.id.camerabtn);
        imgView = (ImageView)findViewById(R.id.imgView);
        showImg = (Button)findViewById(R.id.showImg);

//************************ This is for creating the Navigation Menu*********************************
        //Toolbar (Top)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //Set a Toolbar to act as  ActionBar for this Activity

        // top-level container of "Navigation Drawer" (side)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( //=tie together "functionality of DrawerLayout" <-> "framework ActionBar"
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle); //Set a listener to be notified of drawer events
        toggle.syncState();               //Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout

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
                //This is for going to phone in mobile
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                //no need to request a permission
                startActivity(callIntent);
            }
        });


//**************************************************************************************************
//=============================== Camera ======================================== Enable in Geny but Error in Fong's mobile / Sometimes error
        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File imagesFolder = new File(android.os.Environment.DIRECTORY_DCIM);
                    //? params(path): Environment.getExternalStorageDirectory(), "MyImages" / android.os.Environment.DIRECTORY_DCIM / Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/"
                    //Constructs a new file using the specified directory and name
                    imagesFolder.mkdirs();  //Creates the directory named by this file, assuming its parents exist.

                    File image = new File(imagesFolder, "image_001.jpg");
                    Uri uriSavedImage = Uri.fromFile(image); //Creates a Uri from a file  "image" above
                    imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage); //Keep a photo taken at "uriSavedImage" above
                    startActivityForResult(imageIntent,0);  //start Camera with "imageIntent"

                    Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
                    imgView.setImageBitmap(myBitmap);

            }
        });

        showImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"Pressed",Toast.LENGTH_LONG);

                File imgFile = new  File( Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/");
                if(imgFile.exists()){
                    //Toast.makeText(getApplicationContext(),"Photos",Toast.LENGTH_LONG);
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imgView.setImageBitmap(myBitmap);
                }
                else {
                    //Toast.makeText(getApplicationContext(),"No photo",Toast.LENGTH_LONG);
                }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);  //MenuInflater allows you to inflate the context menu from a menu resource
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
        Intent it ;
        if (id == R.id.nav_home) {
            it = new Intent(YourSpace.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(YourSpace.this, YourSpace.class);
            startActivity(it);
        } else if (id == R.id.nav_reminders) {
            it = new Intent(YourSpace.this, Reminders.class);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(YourSpace.this, SafetyPlanning.class);
            startActivity(it);
        } else if (id == R.id.nav_resources) {
            it = new Intent(YourSpace.this, Resources.class);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {
            it = new Intent(YourSpace.this, HelpNearYou.class);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(YourSpace.this, Feeling.class);
            startActivity(it);
        }
        else if (id == R.id.nav_logout) {
            it = new Intent(YourSpace.this, LoginMenuActivity.class);
            startActivity(it);

            //Dialouge ??
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//*************************************************************************************

}
