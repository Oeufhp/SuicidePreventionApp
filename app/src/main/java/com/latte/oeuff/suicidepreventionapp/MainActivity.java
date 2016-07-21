package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.io.File;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.latte.oeuff.suicidepreventionapp.R.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener { //Listener for handling events on navigation items
    //----getIntent--------
    Intent it;
    String username,password;

    //----About ImageShow-----
    ImageView home_imageView;

    //**** Save states: image resource for imageview
    SharedPreferences sp;
    private Bitmap bm;
    private String BITMAP_FILE = "bitmap_file";
    private String IMG_PATH = "img_path";
    private String PHOTO_EXIST="photo_exist";
    private static Uri fileUri = null;
    int[] feeling_states;
    //----------------------------------
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

    //----Android Plot-------
    ImageButton refreshbtn_home;
    private XYPlot plot;
    Number[] series1Numbers;    //This array is for ploting & It must be "Number" only (defined in android plot 's libs) //Can keep int
    String str_dot1, str_dot2, str_dot3, str_dot4, str_dot5;
    int dot1, dot2, dot3, dot4, dot5;
    List<Number> list_seriesNumbers; //This list is for ' Rearrange the data (in int & no -1 from "SurveyAndroidPlotLogics.java" ) '
    //***IMPORTANT: http://stackoverflow.com/questions/2965747/why-i-get-unsupportedoperationexception-when-trying-to-remove-from-the-list

    @Override
    protected void onCreate(Bundle savedInstanceState) {            //https://developer.android.com/training/implementing-navigation/nav-drawer.html
        //https://developer.android.com/guide/topics/ui/menus.html
        //https://developer.android.com/training/implementing-navigation/nav-drawer.html
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----getIntent-----------
        it = getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");

        //----About ImageShow-----
        addPhoto_in_home = (ImageButton) findViewById(R.id.addPhoto_in_home);
        addPhoto_in_home_small = (ImageButton) findViewById(R.id.addPhoto_in_home_small);
        home_imageView = (ImageView) findViewById(id.home_imageview);            //android:src="@drawable/demo_slide"  in home_imageView
        newImportPhotoFragment = new ImportPhoto();

    //**** REACH"1"  MUST REACH(feeling_states) 1. FLOW-startActivity: NO.1 Check saveInstanceState ( !null / null ) --------------------
        sp = getSharedPreferences("MainSp", 1);

        if (savedInstanceState != null) {
            Log.d("onCre", "saveInstan not null");
            bm = savedInstanceState.getParcelable(BITMAP_FILE);
            int nh=(int)(bm.getHeight()*(2048.0/bm.getWidth()));
            bm=Bitmap.createScaledBitmap(bm,2048,nh,true);
            home_imageView.setImageBitmap(bm);
            feeling_states = savedInstanceState.getIntArray("feeling_states");

            //**** 1. FLOW-startActivity: NO.2 Check feeling_states ( !null / null ) -------
            if(feeling_states != null){
                Log.d("saveIns not null","feeling not null");
            }
            else {
                Log.d("saveIns not null","feeling  null");
            }
        }
        else {
            //generate an init bitmap for solving null obj reference
            Log.d("oncCre", "saveInstan null");
            bm = Bitmap.createBitmap(100, 100,Bitmap.Config.ARGB_8888);
            home_photoexists = false;       //important
            Log.d("init bitmap","success1");

            // Get the values from SharedPref
            feeling_states = new int[5];
            feeling_states[0] = sp.getInt("feeling_states_1", 1);
            feeling_states[1] = sp.getInt("feeling_states_2", 1);
            feeling_states[2] = sp.getInt("feeling_states_3", 1);
            feeling_states[3] = sp.getInt("feeling_states_4", 1);
            feeling_states[4] = sp.getInt("feeling_states_5", 1);
        }

    //**** 2. Flow-refresh: NO.1: Go to "FeelingLogics.java" ----------------------------
        refreshbtn_home= (ImageButton)findViewById(R.id.refreshbtn_home);
        refreshbtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, FeelingLogics.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });

    //**** MUST REACH(dot1-5) 2. Flow-refresh: NO.2: Receive & check the data from "FeelingLogics.java" -----------
        str_dot1 = it.getStringExtra("str_dot1");
        str_dot2 = it.getStringExtra("str_dot2");
        str_dot3 = it.getStringExtra("str_dot3");
        str_dot4 = it.getStringExtra("str_dot4");
        str_dot5 = it.getStringExtra("str_dot5");

        //2.2.1 Check that they are null (reach here from activities)  OR  not null (... from refresh_btn)
        if(str_dot1 != null) {
            Log.d(" str_dot not null","reach here from refresh_btn");
            //----------- Rearrange the data (in int) -----------------------------------------------
            dot1 = Integer.parseInt(str_dot1);
            dot2 = Integer.parseInt(str_dot2);
            dot3 = Integer.parseInt(str_dot3);
            dot4 = Integer.parseInt(str_dot4);
            dot5 = Integer.parseInt(str_dot5);

            //**** Keep the data in feeling_states -------
            feeling_states[0] = dot1;
            feeling_states[1] = dot2;
            feeling_states[2] = dot3;
            feeling_states[3] = dot4;
            feeling_states[4] = dot5;

        }
        else {
            Log.d("str_dot null","reach here from activities");
            if(feeling_states != null){
                Log.d("str_dot null / feeling ","not null");
                dot1 = feeling_states[0];
                dot2 = feeling_states[1];
                dot3 = feeling_states[2];
                dot4 = feeling_states[3];
                dot5 = feeling_states[4];
            }
            else {
                Log.d("str_dot null / feeling ","null");
                dot1 = -1;
                dot2 = -1;
                dot3 = -1;
                dot4 = -1;
                dot5 = -1;
            }
        }

    //**** MUST REACH(series1Numbers) 3.Flow-prepare: NO.1 : Get the data to preapare & androidplot ------------------
        series1Numbers = new Number[]{dot1, dot2, dot3, dot4, dot5};
        list_seriesNumbers = new LinkedList<Number>();

        //**** Keep feeling_states in SharedPreferences -------
        if(feeling_states !=null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("feeling_states_1", feeling_states[0]);
            editor.putInt("feeling_states_2", feeling_states[1]);
            editor.putInt("feeling_states_3", feeling_states[2]);
            editor.putInt("feeling_states_4", feeling_states[3]);
            editor.putInt("feeling_states_5", feeling_states[4]);
            editor.commit();
        }

        //3.1.1 Rearrange the data (in int & no -1 from "SurveyAndroidPlotLogics.java" )
        //Add elements != -1 in list_seriesNumbers
        for (int i = series1Numbers.length - 1; i >= 0; i--) { //Loop in descent
            if (!series1Numbers[i].equals(-1)) {    //***IMPORTANT
                list_seriesNumbers.add(series1Numbers[i]);
                Log.d("series1Number " + i + " ", "added in the list");
            }
        }
        //3.1.2 Check list_seriesNumbers (empty / ! empty)
            if (list_seriesNumbers.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please do at least 3 surveys before", Toast.LENGTH_SHORT).show();
//                Intent it = new Intent(MainActivity.this, SurveyOverview.class);
//                it.putExtra("username",username);
//                it.putExtra("password", password);
//                startActivity(it);                    //problem
            } else {
                //series1Numbers = list_seriesNumbers.toArray(series1Numbers); //REAL PROBLEM !
                series1Numbers = new Number[list_seriesNumbers.size()];

                //----According to android plot's condition -----------------
                if (series1Numbers.length < 3) {
                    Toast.makeText(getApplicationContext(), "Please do at least 3 surveys before", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(MainActivity.this, SurveyOverview.class);
//                    it.putExtra("username",username);
//                    it.putExtra("password", password);
//                    startActivity(it);                //problem
                } else {
                    //for loop with multiple conditions
                    for (int i = list_seriesNumbers.size() - 1, j = 0; i >= 0 && j < series1Numbers.length; i--, j++) {
                        series1Numbers[j] = list_seriesNumbers.get(i);
                    }
                    //----------Android Plot (Arrays-> XYSeries -> Formatters -> Create) -------------------------
                    plot = (XYPlot) findViewById(id.plot_home);
                    androidplot();
                }
            }

        //---About Others---------
        shortcut1 = (ImageButton) findViewById(R.id.shortcut1);
        shortcut2 = (ImageButton) findViewById(R.id.shortcut2);
        shortcut3 = (ImageButton) findViewById(R.id.shortcut3);
        shortcut4 = (ImageButton) findViewById(R.id.shortcut4);
        shortcut1txtview = (TextView) findViewById(R.id.shortcut1txtview);
        shortcut2txtview = (TextView) findViewById(R.id.shortcut2txtview);
        shortcut3txtview = (TextView) findViewById(R.id.shortcut3txtview);
        shortcut4txtview = (TextView) findViewById(R.id.shortcut4txtview);
        locationtxtview = (TextView) findViewById(R.id.locationtxtview);
        languagetxtview = (TextView) findViewById(R.id.languagetxtview);
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

    //====Touch to Change====
    /*    imageView.setOnTouchListener(new View.OnTouchListener() {

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

                Log.d("addPhoto_in_home","clicked");
                if (home_photoexists == false) {
                    newImportPhotoFragment.show(getSupportFragmentManager(), "ImportPhoto");
                }
            }
        });
        //--small camera button--
        addPhoto_in_home_small.setVisibility(View.GONE);    //At first it's invisible.
        addPhoto_in_home_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (home_photoexists == true) {
                    newImportPhotoFragment.show(getSupportFragmentManager(), "ImportPhoto");
                }
            }
        });
//------------------------------------------------------------------------------------------------//

//-------------------------Contents (Demo) ---------------------------------------------------------
        shortcut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, YourSpace.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
        shortcut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Todo.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
        shortcut3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, SafetyPlanning.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
        shortcut4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    //go to HelpNearYouOverview.class
                Intent it = new Intent(MainActivity.this, HelpNearYouOverview.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
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

//----------- androidplot() ------------------------------------------------------------
        public void androidplot() {
            //1."Arrays"  (of y-values to plot) (ABOVE)

            //-------------Check String[][]------------------------------------
            //**** Keep feeling_states
            for (int x = 0; x < series1Numbers.length; x++) {
                Log.d("seriesNumbers x:" + x + "->", series1Numbers[x]+"");
            }

            //2.Arrays -> "XYSeries" (Y_VALS_ONLY = use the element index as the x value)
            XYSeries series = new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series");

            //3.XYSeries - > "Formatters" (using LineAndPointRenderer and configure them from xml)
            LineAndPointFormatter seriesFormat = new LineAndPointFormatter();
            seriesFormat.setPointLabelFormatter(new PointLabelFormatter());
            seriesFormat.configure(getApplicationContext(), R.xml.line_point_formatter_with_labels_2);

            //----------Just for fun -----------------------------------------
            seriesFormat.setInterpolationParams(
                    new CatmullRomInterpolator.Params(20, CatmullRomInterpolator.Type.Centripetal));
            // add an "dash" effect to the series2 line:
        //        seriesFormat.getLinePaint().setPathEffect(
        //                new DashPathEffect(new float[]{ PixelUtils.dpToPix(20), PixelUtils.dpToPix(15)}, 5));
        //                               //( new float[] {intervals}(no.1, no.2) , float phase }

            //https://developer.android.com/reference/android/graphics/DashPathEffect.html
            // just for fun, add some smoothing to the lines: see: http://androidplot.com/smooth-curves-and-androidplot/
            //------------------------------------------------------------------

            //---4.Plot Setting--- //SORT THE CODES LIKE THIS !
            //http://stackoverflow.com/questions/35164669/androidplot-background-start-end-values-and-ranges

            //plot.setBackgroundResource(R.drawable.appbg);
            plot.setGridPadding(0, 0, 0, 0);                    //= left, top, right, bottom
            plot.getGraphWidget().setMarginRight(20);           //Set (domain/range) margins
            plot.getDomainLabelWidget().setMargins(50,0,0,0);   //Set (domain / range) label 's margins
            //plot.getRangeLabelWidget().setHeight(0);
            //plot.getRangeLabelWidget().setPadding(0, 0, 0, 0);
            plot.getRangeLabelWidget().setMargins(50, 0, 0, 100);

            //4.1 Domain
            plot.getGraphWidget().setDomainLabelOrientation(0); //Rotate (domain / range) value labels (under the graph) < - is turn left / + is turn right>
            plot.getGraphWidget().getDomainTickLabelPaint().setTextSize(PixelUtils.dpToPix(10)); //Set (domain / range) letter size
            plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1); //Set (domain / range) incremnt by val
            plot.centerOnDomainOrigin(3);                       //Set the center value of an (domain / range) axis
            plot.setDomainValueFormat(new DecimalFormat("#.#") { //Set (domain/range) formats
                @Override
                public StringBuffer format(double d, StringBuffer sb, FieldPosition fp) {
                    return sb.append(((int)d + 1) + ""); //Set the start point (shortcut to convert d+1 into a String)
                }

                // unused
                @Override
                public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) { return null;}

                // unused
                @Override
                public Number parse(String s, ParsePosition parsePosition) { return null;}
            });
            plot.setDomainBoundaries(0,4, BoundaryMode.FIXED);   //Set (domain / range) boundaries with BoundaryMode

            //4.2 Range
            plot.setTicksPerRangeLabel(1);                      //Set the amount of light lines between each pair of (horizontal / vertical) lines
            plot.getGraphWidget().getRangeTickLabelPaint().setTextSize(PixelUtils.dpToPix(8));
            plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
            plot.setRangeBoundaries(1,6, BoundaryMode.FIXED);   //Set (domain / range) boundaries with BoundaryMode
            plot.setRangeValueFormat(new DecimalFormat("#.#"));

            //PROBLEM How to use
        //      plot.setRangeStepValue(30);
        //      plot.setDomainStepValue(5);
        //      plot.setDomainStep(XYStepMode.SUBDIVIDE, 5);
        //      plot.setRangeStep(XYStepMode.SUBDIVIDE, 20);

            //---5. Create (add a new series' to the xyplot)---
            plot.addSeries(series, seriesFormat);

            Log.d("androidplot","finished");
    }


    //------------------- Dialog for Importing a photo Logics -------------------------------------//
    public class ImportPhoto extends DialogFragment {
        //private static final String TAG = "MainActivity";
        LinearLayout takeaphoto, gallery, cancel_import;

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
    void openCamera() {
        //hasSystemFeature(PackageManager.FEATURE_CAMERA).
        home_photoexists = true;
        addPhoto_in_home.setVisibility(View.GONE);
        addPhoto_in_home_small.setVisibility(View.VISIBLE);

        home_imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (home_imageIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(home_imageIntent, REQUEST_CAPTURE_IMAGE);
//        }
    }

    //---Choose an image from the gallery---
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //https://developer.android.com/training/basics/intents/result.html
        //***1. Start another activity ( can receive a result back later)
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CHOOSE_IMAGE);
    }

    //***** 2. Receive a result back *****
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            Bundle extras =data.getExtras();
            //take a photo
            if (requestCode == REQUEST_CAPTURE_IMAGE) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                bm = (Bitmap) extras.get("data");
                //****Save an image into the gallery****
//###############################################################################################
                //FileOutputStream out = null;
                //imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); error
                //out.flush(); //Flushes this output stream and forces any buffered output bytes to be written out.
                //out.close();
                //FileOutputStream -> File file -> file.getAbsolutePath() -> FILL IN () below
                //imageBitmap = BitmapFactory.decodeFile(out);

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "BeLeaF", timeStamp);
                //---Show an image---
//                Bitmap resultimageBitmap = getResizedBitmap(imageBitmap,home_imageView.getWidth(), home_imageView.getHeight());

                int nh=(int)(imageBitmap.getHeight()*(2048.0/imageBitmap.getWidth()));
                imageBitmap=Bitmap.createScaledBitmap(imageBitmap,2048,nh,true);
                home_imageView.setImageBitmap(imageBitmap);

              //**** save image resource's state
                Log.d("onAct", "extra if");
                extras.putParcelable(BITMAP_FILE, imageBitmap);
//##############################################################################################
            }
            //gallery
            else if (requestCode == REQUEST_CHOOSE_IMAGE) {
                Uri selectedUri = data.getData(); //get the Uri from the data

                Log.d("selectedUri:", selectedUri+"");

                if (selectedUri != null) {

                    Log.d("selectedUri","not null");

                    String path = getPathFromURI(selectedUri); //@@@ get the real Path from the Uri (Call getPathFromURI below)
                    Log.i(TAG, "Image path: " + path);
                    //---Show an image---
                    File image = new File(path);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                    Bitmap bitmap=BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
                    bm = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions); //PROBLEM
                    int nh=(int)(bm.getHeight()*(2048.0/bm.getWidth()));
                    bm=Bitmap.createScaledBitmap(bm,2048,nh,true);
//                    bm = Bitmap.createScaledBitmap(bm, home_imageView.getWidth(), home_imageView.getHeight(),true);
                    home_imageView.setImageBitmap(bm);
                    home_imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    home_imageView.setImageURI(selectedUri); //set the image in home_ImageView
                    home_photoexists=true;

                    //**** IMPORTANT:  Save image resource's state  //PROBLEM
                        //https://developer.android.com/training/basics/activity-lifecycle/pausing.html#Resume
                    Log.d("onAct", "extra else");
                    extras.putParcelable(BITMAP_FILE, bm);
//                    SharedPreferences sp2=getSharedPreferences("AppSharedPref",1);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString(IMG_PATH,path);
                    editor.commit();
                    extras.putString(IMG_PATH,path);
                    extras.putBoolean(PHOTO_EXIST,home_photoexists);
                }
            }
        }
    }
//*******************************

    //@@@ get the real path from Uri
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
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
        //--------- Logics after pressing items on Navigation -------------
        Intent it;
        if (id == R.id.nav_home) {
            it = new Intent(MainActivity.this, MainActivity.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(MainActivity.this, YourSpace.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_todo) {
            it = new Intent(MainActivity.this, Todo.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(MainActivity.this, SafetyPlanning.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {    //go to HelpNearYouOverview.class
            it = new Intent(MainActivity.this, HelpNearYouOverview.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(MainActivity.this, Feeling.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_survey) {
            it = new Intent(MainActivity.this, SurveyOverview.class);
            it.putExtra("username",username);
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
//----------------------------------- About to save states ---------------------------------------------------------------
//https://developer.android.com/training/basics/activity-lifecycle/pausing.html#Resume

    //**** REACH"2","5" : Called when returning to focus on this activity (Called everytime including when it starts)
    @Override
    protected void onResume() {
            Log.d("onResume", "resume");

//            SharedPreferences sp2 = getSharedPreferences("AppSharedPref", 1);
            String path = sp.getString(IMG_PATH, "");
            home_photoexists = sp.getBoolean(PHOTO_EXIST, true);
            bm = BitmapFactory.decodeFile(path);
            int nh;

        //------ Check feeling -----------------------------
        if(feeling_states != null) {
            int feeling_states_1 = sp.getInt("feeling_states_1", 1);
            int feeling_states_2 = sp.getInt("feeling_states_2", 1);
            int feeling_states_3 = sp.getInt("feeling_states_3", 1);
            int feeling_states_4 = sp.getInt("feeling_states_4", 1);
            int feeling_states_5 = sp.getInt("feeling_states_5", 1);

            feeling_states[0] = feeling_states_1;
            feeling_states[1] = feeling_states_2;
            feeling_states[2] = feeling_states_3;
            feeling_states[3] = feeling_states_4;
            feeling_states[4] = feeling_states_5;
        }
        else if (feeling_states == null) {
            Log.d("resume","feeling_null");
        }

        //-----Check: Is bm null ?--------------------
        if(bm !=null){ //not null
            nh = (int) (bm.getHeight() * (2048.0 / bm.getWidth()));
            bm = Bitmap.createScaledBitmap(bm, 2048, nh, true);
            home_imageView.setImageBitmap(bm);
            if (home_photoexists) {
                addPhoto_in_home.setVisibility(View.GONE);
                addPhoto_in_home_small.setVisibility(View.VISIBLE);
            }

        }
        else { //null
            //generate an init bitmap for solving null obj reference
            bm = Bitmap.createBitmap(100, 100,Bitmap.Config.ARGB_8888);
            //important
            home_photoexists = false;
            Log.d("init bitmap","success2");
        }

        super.onResume();
    }

    //**** REACH"3" :  Called when this activity is interrupted by others (So It's not focused but visible)
    //Save the data here such as "Auto-Saved"
    @Override
    protected void onPause(){
        Log.d("onPause", "Pause");
        super.onPause();
    }

    //**** REACH"4" :  Called when this activity is interrupted by others (So It's not focused but visible)
    @Override
    protected void onSaveInstanceState(Bundle saveInstancestate) {
        Log.d("onSaveIns", "save");
        saveInstancestate.putParcelable(BITMAP_FILE, bm);
        saveInstancestate.putBoolean(PHOTO_EXIST,home_photoexists);
        saveInstancestate.putIntArray("feeling_states", feeling_states);
        super.onSaveInstanceState(saveInstancestate);
        Log.d("onSaveIns", "end");
    }

    //**** Call the saved states when recreating this activity again
        //https://developer.android.com/training/basics/activity-lifecycle/recreating.html
    @Override
    protected void onRestoreInstanceState(Bundle restoreInstanceState) {
        Log.d("onRestore", "restore");
        super.onRestoreInstanceState(restoreInstanceState);
        if (restoreInstanceState != null) {
            bm = restoreInstanceState.getParcelable(BITMAP_FILE);
            home_photoexists=restoreInstanceState.getBoolean(PHOTO_EXIST);
            int nh=(int)(bm.getHeight()*(2048.0/bm.getWidth()));
            bm=Bitmap.createScaledBitmap(bm,2048,nh,true);
            home_imageView.setImageBitmap(bm);
            if(home_photoexists){
                addPhoto_in_home.setVisibility(View.GONE);
                addPhoto_in_home_small.setVisibility(View.VISIBLE);
            }
            feeling_states = restoreInstanceState.getIntArray("feeling_states");
        }
    }


}
