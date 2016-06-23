package com.latte.oeuff.suicidepreventionapp;

import android.annotation.TargetApi;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.logging.LogRecord;

import static com.latte.oeuff.suicidepreventionapp.R.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener { //Listener for handling events on navigation items
    ImageButton shortcut1, shortcut2, shortcut3, shortcut4;
    TextView shortcut1txtview, shortcut2txtview, shortcut3txtview, shortcut4txtview;
    TextView locationtxtview, languagetxtview;

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";


    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {            //https://developer.android.com/training/implementing-navigation/nav-drawer.html
        //https://developer.android.com/guide/topics/ui/menus.html
        //https://developer.android.com/training/implementing-navigation/nav-drawer.html

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            shortcut1 = (ImageButton)findViewById(R.id.shortcut1);
            shortcut2 = (ImageButton)findViewById(R.id.shortcut2);
            shortcut3 = (ImageButton)findViewById(R.id.shortcut3);
            shortcut4 = (ImageButton)findViewById(R.id.shortcut4);
            shortcut1txtview = (TextView)findViewById(R.id.shortcut1txtview);
            shortcut2txtview = (TextView)findViewById(R.id.shortcut2txtview);
            shortcut3txtview = (TextView)findViewById(R.id.shortcut3txtview);
            shortcut4txtview = (TextView)findViewById(R.id.shortcut4txtview);
            locationtxtview = (TextView)findViewById(R.id.locationtxtview);
            languagetxtview = (TextView)findViewById(R.id.languagetxtview);

//----------------------------------SlideShow-----------------------------------------//
        final int [] imgID=new int[]{drawable.batman,
                drawable.bicycle,
                drawable.egg,
                drawable.dog,
                drawable.book_worm,
                drawable.car,
                drawable.coffee1,
                drawable.coffee2,
                drawable.smile,
                drawable.toilet_paper
                };

        imageView=(ImageView)findViewById(R.id.slideShowImg);
        imageView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                openImageChooser();
                return false;
            }
        });
//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            int p=0;
//            int i=0;
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if(p==0) i=0;
//                else i =p%imgID.length;
//                imageView.setImageResource(imgID[i]);
//                p++;
//                return false;
//            }
//        });
//        final Handler handler=new Handler();
//                Runnable runnable=new Runnable() {
//                    int i=0;
//                    @Override
//                    public void run() {
//                        imageView.setImageResource(imgID[i]);
//                        i++;
//                        if(i>imgID.length-1)i=0;
//                        handler.postDelayed(this,2000);
//                    }
//                };
//                handler.postDelayed(runnable,2000);
//---------------------------SlideShow-----------------------------------------//

//-------------------------Contents (Demo) ---------------------------------------------------------
            shortcut1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(MainActivity.this,YourSpace.class);
                    startActivity(it);
                }
            });
            shortcut2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(MainActivity.this,Reminders.class);
                    startActivity(it);
                }
            });
            shortcut3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(MainActivity.this,Resources.class);
                    startActivity(it);
                }
            });
        shortcut4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent it = new Intent(MainActivity.this,HelpNearYou.class);
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
                    this, drawer, toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);                         //Set a listener to be notified of drawer events
            toggle.syncState();                                       //Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout

            //view of "Navigation Drawer" (side)
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            //*****To uncover colors of icon**********
            navigationView.setItemIconTintList(null);

            //floating button (bottom)
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBtn);
            fab.setImageResource(drawable.ic_warning_white_40dp);
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
        //--------------Logics across an activity ----------------------
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("keyChannel", "12345");
        editor.commit();// commit is important here.
    }

//----------------------------------SlideShow-----------------------------------------//
    //----choose image from gallery----//
    void openImageChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                //get the Uri from the data
                Uri selectedUri=data.getData();
                if(selectedUri!=null){
                    //get the path from the Uri
                    String path=getPathFromURI(selectedUri);
                    Log.i(TAG,"Image path: "+path);
                    //set the image in ImageView
                    imageView.setImageURI(selectedUri);
                }
            }
        }
    }
    //get the real path from Uri
    public String getPathFromURI(Uri contentUri){
        String res=null;
        String []proj={MediaStore.Images.Media.DATA};
        Cursor cursor=getContentResolver().query(contentUri,proj,null,null,null);
        if(cursor.moveToFirst()){
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res=cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

//----------------------------------SlideShow-----------------------------------------//

//************************ This is for creating the Navigation Menu*********************************
    //Close "Navigation Drawer"
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
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
        getMenuInflater().inflate(R.menu.main, menu); //MenuInflater allows you to inflate the context menu from a menu resource
                                                      //Bind "MainActivity.java" <-> main.xml for using "setting popup-menu"

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
            it = new Intent(MainActivity.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(MainActivity.this, YourSpace.class);
            startActivity(it);
        } else if (id == R.id.nav_reminders) {
            it = new Intent(MainActivity.this, Reminders.class);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(MainActivity.this, SafetyPlanning.class);
            startActivity(it);
        } else if (id == R.id.nav_resources) {
            it = new Intent(MainActivity.this, Resources.class);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {
            it = new Intent(MainActivity.this, HelpNearYou.class);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(MainActivity.this, Feeling.class);
            startActivity(it);
        } else if (id==R.id.nav_setting){
            it = new Intent(MainActivity.this, Setting.class);
            startActivity(it);
        } else if (id == R.id.nav_logout) {
            it = new Intent(MainActivity.this, LoginMenuActivity.class);
            startActivity(it);

            //Dialouge ??
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//**************************************************************************************************
    @Override
    protected void onResume() {
        super.onResume();

    }
}
