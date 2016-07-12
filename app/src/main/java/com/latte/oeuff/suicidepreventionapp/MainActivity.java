package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.latte.oeuff.suicidepreventionapp.R.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener { //Listener for handling events on navigation items
    //----About ImageShow-----
    ImageView home_imageView;
    boolean home_photoexists = false;
    private int REQUEST_CAPTURE_IMAGE = 1;
    private int REQUEST_CHOOSE_IMAGE = 2;
    Intent home_imageIntent;
    ImageButton addPhoto_in_home, addPhoto_in_home_small;

    //---About Dialog---
    DialogFragment newImportPhotoFragment;
    DialogFragment newLogoutFragment;

    //---About Others---------
    ImageButton shortcut1, shortcut2, shortcut3, shortcut4;
    TextView shortcut1txtview, shortcut2txtview, shortcut3txtview, shortcut4txtview;
    TextView locationtxtview, languagetxtview;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {            //https://developer.android.com/training/implementing-navigation/nav-drawer.html
        //https://developer.android.com/guide/topics/ui/menus.html
        //https://developer.android.com/training/implementing-navigation/nav-drawer.html
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //----About ImageShow-----
        addPhoto_in_home = (ImageButton)findViewById(R.id.addPhoto_in_home);
        addPhoto_in_home_small = (ImageButton)findViewById(R.id.addPhoto_in_home_small);
        home_imageView=(ImageView)findViewById(id.home_imageview);            //android:src="@drawable/demo_slide"  in home_imageView
        newImportPhotoFragment = new ImportPhoto();
    //---About Others---------
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
    //-----About Dialog--------
        newLogoutFragment = new LogOutDialog();

//----------------------------------SlideShow + ImageShow-----------------------------------------//
    //======= Slide Show =======
    /*    final int [] imgID=new int[]{drawable.batman,
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
    //---Touch to Change---
    /*    home_imageView.setOnTouchListener(new View.OnTouchListener() {
=======

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
//        imageView=(ImageView)findViewById(R.id.slideShowImg);
//        final int [] imgID=new int[]{drawable.batman,
//                drawable.bicycle,
//                drawable.egg,
//                drawable.dog,
//                drawable.book_worm,
//                drawable.car,
//                drawable.coffee1,
//                drawable.coffee2,
//                drawable.smile,
//                drawable.toilet_paper
//                };

        imageView=(ImageView)findViewById(R.id.slideShowImg); //android:src="@drawable/demo_slide"  in imageView
        imageView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                openImageChooser();
                return false;
            }
        });
    //====Touch to Change====
    /*    imageView.setOnTouchListener(new View.OnTouchListener() {
>>>>>>> b01c70cfc8f422e81a672bba90de1de43bd00efe
            int p=0;
            int i=0;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(p==0) i=0;
                else i =p%imgID.length;
                home_imageView.setImageResource(imgID[i]);
                p++;
                return false;
            }
        }); */

    //--- Auto Change ---
    /*
        final Handler handler=new Handler();
                Runnable runnable=new Runnable() {
                    int i=0;
                    @Override
                    public void run() {
                        home_imageView.setImageResource(imgID[i]);
                        i++;
                        if(i>imgID.length-1)i=0;
                        handler.postDelayed(this,2000);
                    }
                };
                handler.postDelayed(runnable,2000);
    */


    //=======Image Show========
    //--big camera button---
    addPhoto_in_home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(home_photoexists==false) {
                newImportPhotoFragment.show(getSupportFragmentManager(), "ImportPhoto");
            }
        }
    });
    //--small camera button--
    addPhoto_in_home_small.setVisibility(View.GONE);    //At first it's invisible.
    addPhoto_in_home_small.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(home_photoexists==true) {
                newImportPhotoFragment.show(getSupportFragmentManager(), "ImportPhoto");
            }
        }
    });
//------------------------------------------------------------------------------------------------//

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
//--------------------------------------------------------------------------------------------------

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
            fab.setImageResource(drawable.emergencycall);
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
//--------------Logics across an activity ----------------------
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("keyChannel", "12345");
//        editor.commit();// commit is important here.
//---------------------------------------------------------------
    }

//------------------------ Dialog for Importing a photo Logics -------------------------------------//
public class ImportPhoto extends DialogFragment {
    //private static final String TAG = "MainActivity";
    LinearLayout takeaphoto,gallery,cancel_import;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //-----Binding-----------
        View view = inflater.inflate(R.layout.dialog_add_photo_in_home, container);
        takeaphoto = (LinearLayout) view.findViewById(R.id.takeaphoto);
        gallery = (LinearLayout) view.findViewById(R.id.gallery);
        cancel_import = (LinearLayout) view.findViewById(R.id.cancel_import);

        //-----Logics-----------
        takeaphoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openCamera();
                newImportPhotoFragment.dismiss(); //close the dialog
                return false;
            }
        });
        gallery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openImageChooser();
                newImportPhotoFragment.dismiss(); //close the dialog
                return false;
            }
        });
        cancel_import.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                newImportPhotoFragment.dismiss(); //close the dialog
                return false;
            }
        });

        return view;
    }
}
//---------------------------------------------------------------------------------------------//

//----------------------------------ImageShow Logics-------------------------------------------//
//***** 1. startActivityForResult() *****
    //---Take a photo---
    void openCamera(){
        //hasSystemFeature(PackageManager.FEATURE_CAMERA).
        home_photoexists = true;
        addPhoto_in_home.setVisibility(View.GONE);
        addPhoto_in_home_small.setVisibility(View.VISIBLE);

        home_imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (home_imageIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(home_imageIntent, REQUEST_CAPTURE_IMAGE);
        }
    }
    //---Choose an image from the gallery---
    void openImageChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //https://developer.android.com/training/basics/intents/result.html
        //***1. Start another activity ( can receive a result back later)
        startActivityForResult(Intent.createChooser(intent,"Select Image"),REQUEST_CHOOSE_IMAGE);
    }

//***** 2. Receive a result back *****
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if(resultCode==RESULT_OK){
            //take a photo
            if(requestCode ==REQUEST_CAPTURE_IMAGE){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                //****Save an image into the gallery****
//###############################################################################################
                //FileOutputStream out = null;
                //imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); error
                    //out.flush(); //Flushes this output stream and forces any buffered output bytes to be written out.
                    //out.close();
                //FileOutputStream -> File file -> file.getAbsolutePath() -> FILL IN () below
                //imageBitmap = BitmapFactory.decodeFile(out);

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "BeLeaF" , timeStamp);
                //---Show an image---
                Bitmap resultimageBitmap = getResizedBitmap(imageBitmap,home_imageView.getWidth(), home_imageView.getHeight());
                home_imageView.setImageBitmap(resultimageBitmap);
                //setPic
//##############################################################################################
            }
            //gallery
            else if(requestCode==REQUEST_CHOOSE_IMAGE) {
                Uri selectedUri=data.getData(); //get the Uri from the data
                if(selectedUri!=null){
                    String path=getPathFromURI(selectedUri); //@@@ get the real Path from the Uri (Call getPathFromURI below)
                    Log.i(TAG,"Image path: "+path);
                    //---Show an image---
                    home_imageView.setImageURI(selectedUri); //set the image in home_ImageView
                    //setPic();
                }
            }
        }
    }
//*******************************

    //@@@ get the real path from Uri
    public String getPathFromURI(Uri contentUri){
        String res=null;
        String []proj={MediaStore.Images.Media.DATA};
        Cursor cursor=getContentResolver().query(contentUri,proj,null,null,null);
        if(cursor.moveToFirst()){
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res=cursor.getString(column_index);
        }
        cursor.close();
        //After adding a first photo
        home_photoexists = true;
        addPhoto_in_home.setVisibility(View.GONE);
        addPhoto_in_home_small.setVisibility(View.VISIBLE);

        return res;
    }

//###############################################################################
    //Just an idea from "https://developer.android.com/training/camera/photobasics.html"
    public Bitmap getResizedBitmap(Bitmap imageBitmap, int bitmapWidth, int bitmapHeight) {
        //http://stackoverflow.com/questions/15759195/reduce-size-of-bitmap-to-some-specified-pixel-in-android
        return Bitmap.createScaledBitmap(imageBitmap, bitmapWidth, bitmapHeight, true);
    }
//###############################################################################

//-----------------------------------------------------------------------------------------------//

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
            newLogoutFragment.show(getSupportFragmentManager(), "LogOut");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//**************************************************************************************************
//---------------------------- Dialog for warning before logging out -------------------------------//
public class LogOutDialog extends DialogFragment {

    TextView yesbtn_logout,nobtn_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //-----Binding-----------
        View view = inflater.inflate(layout.dialog_logout, container);
        yesbtn_logout = (TextView) view.findViewById(id.yesbtn_logout);
        nobtn_logout = (TextView) view.findViewById(id.nobtn_logout);

        //-----Logics-----------
        yesbtn_logout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                newLogoutFragment.dismiss(); //close the dialog
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
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
//--------------------------------------------------------------------------------------------------//
    @Override
    protected void onResume() {
        super.onResume();
    }

}
