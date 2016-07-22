//The logic is same as in "CreateAccountActivity.java"

package com.latte.oeuff.suicidepreventionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Survey_C_ssrs extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    //************ Volley *******************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //----getIntent--------
    Intent it;
    String username,password;
    //---------------------tabLayout---------------------------------
    // MyPagerAdapter -> ViewPager -> contents (fragment_ ...)
    //MyPageAdapter = Base class providing the adapter to populate pages inside of a ViewPager

    private TabLayout tabLayout;    //it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter} that will provide fragments for each of the sections.
    private ViewPager mViewPager;   //This class will host the section contents

    //---------------------- About dialog ----------------------------------------
    static DialogFragment newAddSafetyplanningFragment;
    DialogFragment newLogoutFragment;

    //---- Others -------------
//    RadioGroup q1_group, q2_group, q3_group, q4_group, q5_group;
//    RadioButton q1_1, q1_2, q1_3, q1_4,  q2_1, q2_2, q2_3, q2_4,  q3_1, q3_2, q3_3, q3_4,  q4_1, q4_2, q4_3, q4_4,  q5_1, q5_2, q5_3, q5_4;
//    Button sendsurveybtn;
//    TextView sendsurveystatus_textview;
    //----Answers------------
    String[] survey_answers = new String[5];
    int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_c_ssrs);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //----------getIntent---------
        it =getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");

        //----About dialog------
        //newAddSafetyplanningFragment = new AddSafetyplanningFragment();
        newLogoutFragment = new LogOutDialog();

        //------------------------This is for creating the tabLayout and its contents--------------------------
        //#### Get  ViewPager && set it's PagerAdapter -> so that it can display items ####
        MyPagerAdapter mMypageAdapter = new MyPagerAdapter(getSupportFragmentManager()); //=Base class providing the adapter to populate pages inside of a ViewPager
        mViewPager = (ViewPager) findViewById(R.id.container_survey_c_ssrs);
        mViewPager.setAdapter(mMypageAdapter);

        //#### Give the TabLayout the ViewPager ####
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
//      tabLayout.addTab(tabLayout.newTab().setText("..."));
        tabLayout.setupWithViewPager(mViewPager);

        //************************ This is for creating the Navigation Menu*********************************
//        //Toolbar (Top)
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar); //Set a Toolbar to act as  ActionBar for this Activity
//
//
//        // top-level container of "Navigation Drawer" (side)
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(  //=tie together "functionality of DrawerLayout" <-> "framework ActionBar"
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);                         //Set a listener to be notified of drawer events
//        toggle.syncState();                                       //Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout
//
//        //view of "Navigation Drawer" (side)
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        //*****To uncover colors of icon**********
//        navigationView.setItemIconTintList(null);
//
//        //floating button (bottom)
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBtn);
//        fab.setImageResource(R.drawable.emergencycall);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Emergency call ?", Snackbar.LENGTH_LONG) //=bottom black bar
////                        .setAction("Action", null).show();
//
//                //This is for going to phone in mobile
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:112"));
//                //no need to request a permission
//                startActivity(callIntent);
//
//            }
//        });
        //**************************************************************************************************


//        //--------- binding ----------
//        //Q1
//        q1_group = (RadioGroup)findViewById(R.id.q1_group);
//        q1_1 = (RadioButton)findViewById(R.id.q1_1); q1_1.setOnClickListener(this);
//        q1_2 = (RadioButton)findViewById(R.id.q1_2); q1_2.setOnClickListener(this);
//        q1_3 = (RadioButton)findViewById(R.id.q1_3); q1_3.setOnClickListener(this);
//        q1_4 = (RadioButton)findViewById(R.id.q1_4); q1_4.setOnClickListener(this);
//        //Q2
//        q2_group = (RadioGroup)findViewById(R.id.q2_group);
//        q2_1 = (RadioButton)findViewById(R.id.q2_1); q2_1.setOnClickListener(this);
//        q2_2 = (RadioButton)findViewById(R.id.q2_2); q2_2.setOnClickListener(this);
//        q2_3 = (RadioButton)findViewById(R.id.q2_3); q2_3.setOnClickListener(this);
//        q2_4 = (RadioButton)findViewById(R.id.q2_4); q2_4.setOnClickListener(this);
//        //Q3
//        q3_group = (RadioGroup)findViewById(R.id.q3_group);
//        q3_1 = (RadioButton)findViewById(R.id.q3_1); q3_1.setOnClickListener(this);
//        q3_2 = (RadioButton)findViewById(R.id.q3_2); q3_2.setOnClickListener(this);
//        q3_3 = (RadioButton)findViewById(R.id.q3_3); q3_3.setOnClickListener(this);
//        q3_4 = (RadioButton)findViewById(R.id.q3_4); q3_4.setOnClickListener(this);
//        //Q4
//        q4_group = (RadioGroup)findViewById(R.id.q4_group);
//        q4_1 = (RadioButton)findViewById(R.id.q4_1); q4_1.setOnClickListener(this);
//        q4_2 = (RadioButton)findViewById(R.id.q4_2); q4_2.setOnClickListener(this);
//        q4_3 = (RadioButton)findViewById(R.id.q4_3); q4_3.setOnClickListener(this);
//        q4_4 = (RadioButton)findViewById(R.id.q4_4); q4_4.setOnClickListener(this);
//        //Q5
//        q5_group = (RadioGroup)findViewById(R.id.q5_group);
//        q5_1 = (RadioButton)findViewById(R.id.q5_1); q5_1.setOnClickListener(this);
//        q5_2 = (RadioButton)findViewById(R.id.q5_2); q5_2.setOnClickListener(this);
//        q5_3 = (RadioButton)findViewById(R.id.q5_3); q5_3.setOnClickListener(this);
//        q5_4 = (RadioButton)findViewById(R.id.q5_4); q5_4.setOnClickListener(this);
//
//        sendsurveybtn = (Button)findViewById(R.id.sendsurveybtn);
//        sendsurveystatus_textview = (TextView)findViewById(R.id.sendsurveystatus_textview);

        //-----------------------------------My logics ----------------------------------------------------
        //************************* 1. Keep ans and points *****************************************
        //http://stackoverflow.com/questions/10356733/getcheckedradiobuttonid-returning-useless-int

//        sendsurveybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //debugging var
//                boolean unfinished = false;
//
//                //Q1 answer
//                int q1_ans = q1_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
//                switch (q1_ans) {
//                    case R.id.q1_1:
//                        totalScore = totalScore + 1;
//                        survey_answers[0]="1";
//                        Log.d("Q1 score",totalScore+"");
//                        break;
//                    case R.id.q1_2:
//                        totalScore = totalScore + 2;
//                        survey_answers[0]="2";
//                        Log.d("Q1 score",totalScore+"");
//                        break;
//                    case R.id.q1_3:
//                        totalScore = totalScore + 3;
//                        survey_answers[0]="3";
//                        Log.d("Q1 score",totalScore+"");
//                        break;
//                    case R.id.q1_4:
//                        totalScore = totalScore + 4;
//                        survey_answers[0]="4";
//                        Log.d("Q1 score",totalScore+"");
//                        break;
//                    default:
//                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
//                        clear_survey();
//                        unfinished = true;
//                        break;
//                }
//                //Q2 answer
//                int q2_ans = q2_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
//                switch (q2_ans) {
//                    case R.id.q2_1:
//                        totalScore = totalScore + 1;
//                        survey_answers[1]="1";
//                        Log.d("Q2 score",totalScore+"");
//                        break;
//                    case R.id.q2_2:
//                        totalScore = totalScore + 2;
//                        survey_answers[1]="2";
//                        Log.d("Q2 score",totalScore+"");
//                        break;
//                    case R.id.q2_3:
//                        totalScore = totalScore + 3;
//                        survey_answers[1]="3";
//                        Log.d("Q2 score",totalScore+"");
//                        break;
//                    case R.id.q2_4:
//                        totalScore = totalScore + 4;
//                        survey_answers[1]="4";
//                        Log.d("Q2 score",totalScore+"");
//                        break;
//                    default:
//                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
//                        clear_survey();
//                        unfinished = true;
//                        break;
//                }
//                //Q3 answer
//                int q3_ans = q3_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
//                switch (q3_ans) {
//                    case R.id.q3_1:
//                        totalScore = totalScore + 1;
//                        survey_answers[2]="1";
//                        Log.d("Q3 score",totalScore+"");
//                        break;
//                    case R.id.q3_2:
//                        totalScore = totalScore + 2;
//                        survey_answers[2]="2";
//                        Log.d("Q3 score",totalScore+"");
//                        break;
//                    case R.id.q3_3:
//                        totalScore = totalScore + 3;
//                        survey_answers[2]="3";
//                        Log.d("Q3 score",totalScore+"");
//                        break;
//                    case R.id.q3_4:
//                        totalScore = totalScore + 4;
//                        survey_answers[2]="4";
//                        Log.d("Q3 score",totalScore+"");
//                        break;
//                    default:
//                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
//                        clear_survey();
//                        unfinished = true;
//                        break;
//                }
//                //Q4 answer
//                int q4_ans = q4_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
//                switch (q4_ans) {
//                    case R.id.q4_1:
//                        totalScore = totalScore + 1;
//                        survey_answers[3]="1";
//                        Log.d("Q4 score",totalScore+"");
//                        break;
//                    case R.id.q4_2:
//                        totalScore = totalScore + 2;
//                        survey_answers[3]="2";
//                        Log.d("Q4 score",totalScore+"");
//                        break;
//                    case R.id.q4_3:
//                        totalScore = totalScore + 3;
//                        survey_answers[3]="3";
//                        Log.d("Q4 score",totalScore+"");
//                        break;
//                    case R.id.q4_4:
//                        totalScore = totalScore + 4;
//                        survey_answers[3]="4";
//                        Log.d("Q4 score",totalScore+"");
//                        break;
//                    default:
//                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
//                        clear_survey();
//                        unfinished = true;
//                        break;
//                }
//                //Q5 answer
//                int q5_ans = q5_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
//                switch (q5_ans) {
//                    case R.id.q5_1:
//                        totalScore = totalScore + 1;
//                        survey_answers[4]="1";
//                        Log.d("Q5 score",totalScore+"");
//                        break;
//                    case R.id.q5_2:
//                        totalScore = totalScore + 2;
//                        survey_answers[4]="2";
//                        Log.d("Q5 score",totalScore+"");
//                        break;
//                    case R.id.q5_3:
//                        totalScore = totalScore + 3;
//                        survey_answers[4]="3";
//                        Log.d("Q45 score",totalScore+"");
//                        break;
//                    case R.id.q5_4:
//                        totalScore = totalScore + 4;
//                        survey_answers[4]="4";
//                        Log.d("Q5 score",totalScore+"");
//                        break;
//                    default:
//                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
//                        clear_survey();
//                        unfinished = true;
//                        break;
//                }
//
//                Log.d("totalScore:",totalScore+"");
//                if(unfinished==false) {
//                    for (int i = 0; i < survey_answers.length; i++) {
//                        Log.d("ans " + (i + 1) + ": ", survey_answers[i]);
//                    }
//                    try {
//                        sendsurvey();
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    //CANNOT PUT clear_survey(); HERE: "Unhandled exception java.lang.NullPointerException"
//                }
//            }
//        });
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
            it = new Intent(Survey_C_ssrs.this, MainActivity.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(Survey_C_ssrs.this, YourSpace.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_todo) {
            it = new Intent(Survey_C_ssrs.this, Todo.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(Survey_C_ssrs.this, SafetyPlanning.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {
            it = new Intent(Survey_C_ssrs.this, HelpNearYouOverview.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(Survey_C_ssrs.this, Feeling.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        } else if (id == R.id.nav_survey) {
            it = new Intent(Survey_C_ssrs.this, SurveyOverview.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        }
        else if (id == R.id.nav_logout) {
            newLogoutFragment.show(getSupportFragmentManager(), "LogOut");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //**************************************************************************************************

    @Override
    public void onClick(View v) {

    }
    //------- To clear a survey -----------
//    public void clear_survey(){
//        q1_group.clearCheck(); //Clear checked raioButton in radioGroup"q1_group"
//        q2_group.clearCheck();
//        q3_group.clearCheck();
//        q4_group.clearCheck();
//        q5_group.clearCheck();
//        totalScore = 0;
//        survey_answers = new String[5];
//    }

    //******************************* 2, Send them to API *****************************************
    //survey_answers
    //totalScore
    //-------------------------sendsurvey-----------------------------------------------------------
    public void sendsurvey() throws ParseException {
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/sendsurvey";
        String url = "http://auth.oeufhp.me/beleafTest.php/sendsurvey";
        //---------Message----------------
        final ProgressDialog pd = new ProgressDialog(Survey_C_ssrs.this);
        pd.setMessage("Sending...");
        pd.show();

        //getCurrent date & time
        //================== Date Pattern (complicated) ===================
        //Develop it to be changable according to a user (Step 2.)

        //1.set the format: https://developer.android.com/reference/java/text/SimpleDateFormat.html
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        //2.set the timezone(GMT/UTC/...): http://stackoverflow.com/questions/22814263/how-set-timezone-in-android
        mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+02:00"));
        //3. get the correct date(=String): http://stackoverflow.com/questions/23068676/how-to-get-current-timestamp-in-string-format-in-java-yyyy-mm-dd-hh-mm-ss
        //###
        final String strSentDate = mSimpleDateFormat.format(new java.util.Date());
        Log.d("strSentDate",strSentDate);
        //4. convert from "String"->"Date": http://stackoverflow.com/questions/6510724/how-to-convert-java-string-to-date-object
        DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date sentdate = null;
        try{
            sentdate = mDateFormat.parse(strSentDate); //"String" strSentDate is parsed to be "Date"
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //###By the way, send String strSentDate(String)->APIs(String)->DB(Date) is working !! & easier because of the method "getParams()" below
        //=====================================================================

        //----------Post Request---------------
//        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    Log.d("sendsurvey' response",response);
//                    JSONObject jsonResponse = new JSONObject(response);
//                    String sendsurvey_status = jsonResponse.getString("sendsurvey_status");
//
//                    //-----------My logics---------------
//                    if(sendsurvey_status.equals("1")) {
//                        sendsurveystatus_textview.setText("sendsurvey");
//                        Toast.makeText(getApplicationContext(), "sendsurvey", Toast.LENGTH_SHORT).show();
//                        clear_survey();
//                        //-----Return to SurveyOverview--------
//                        Intent it = new Intent(Survey_C_ssrs.this, SurveyOverview.class);
//                        startActivity(it);
//                    }
//                    else {
//                        sendsurveystatus_textview.setText("sendsurvey failed");
//                        Toast.makeText(getApplicationContext(), "sendsurvey failed", Toast.LENGTH_SHORT).show();
//                        clear_survey();
//                    }
//
//                    //----- if try is success -> dismiss the dialog ---------
//                    pd.dismiss(); //Dismiss & Removing it from the screen
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//
//                        //-----------Check error (useful !)-----------------------------------------------
//                        NetworkResponse networkResponse = error.networkResponse;
//                        if (networkResponse != null) {
//                            Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
//                        }
//
//                        if (error instanceof TimeoutError) {
//                            Log.e("Volley", "TimeoutError");
//                        }else if(error instanceof NoConnectionError){
//                            Log.e("Volley", "NoConnectionError");
//                        } else if (error instanceof AuthFailureError) {
//                            Log.e("Volley", "AuthFailureError");
//                        } else if (error instanceof ServerError) {
//                            Log.e("Volley", "ServerError");
//                        } else if (error instanceof NetworkError) {
//                            Log.e("Volley", "NetworkError");
//                        } else if (error instanceof ParseError) {
//                            Log.e("Volley", "ParseError");
//                        }
//                        //--------if error -> dismiss the dialog ---------
//                        pd.dismiss(); //Dismiss & Removing it from the screen
//                    }
//                }
//        )
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                // the POST parameters:
//                params.put("username", username);
//                params.put("password", password);
//                params.put("sentdate", strSentDate);
//                params.put("survey_q1_ans", survey_answers[0]);
//                params.put("survey_q2_ans", survey_answers[1]);
//                params.put("survey_q3_ans", survey_answers[2]);
//                params.put("survey_q4_ans", survey_answers[3]);
//                params.put("survey_q5_ans", survey_answers[4]);
//                params.put("totalScore",totalScore+"");
//
//                return params;
//            }
//        };
//
//        requestQueue.add(postRequest);
    }

    //SEE THE FLOW OF MyPagerAdapter && FirstFragment/SecondFragment/ThirdFragment IN LOGCAT & ORANGE NOTEBOOK
//------------ =Base class providing the adapter to populate pages inside of a ViewPager ------------------------
//--------------"1." This class returns a fragment corresponding to one of the sections/tabs/pages. ----------------------
    private class MyPagerAdapter extends FragmentPagerAdapter {
        //constructor
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm); //use the constructor of FragmentManager class (msectionsPagerAdapter)
            Log.d("Class:MyPagerAdapter","Method:Constructor");
        }

        //called to instantiate the fragment(PlaceholderFragment: defined as a static inner class below) for the given page.
        @Override
        public Fragment getItem(int pos) {
            switch(pos) {   // &&& do method "newInstance" below
                case 0:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=0)Ins1");
                    return FirstFragment.newInstance("FirstFragment, Instance 1");
                case 1:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=1)Ins2");
                    return SecondFragment.newInstance("SecondFragment, Instance 2");
                case 2:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=2)Ins3");
                    return ThirdFragment.newInstance("ThirdFragment, Instance 3");
                case 3:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=3)Ins4");
                    return FourthFragment.newInstance("FourthFragment, Instance 4");
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            Log.d("Class:MyPagerAdapter","Method:getCount()");
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=0)");
                    return "Suicidal Idea 1";
                case 1:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=1)");
                    return "Suicidal Idea 2";
                case 2:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=2)");
                    return "Intensity of Idea 3";
                case 3:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=2)");
                    return "Suicidal Behavior";
            }
            return null;
        }
    }

    //----------------------FirstFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class FirstFragment extends Fragment {
//        LinearLayout addplan_no1_layout, delplan_no1_layout;
//        TextView addasafetyplanningtextview1;

        //This is the main method of this class->create a View (fragment_safetyplanning_plan1)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewfirst = inflater.inflate(R.layout.fragment_survey_c_ssrs_first, container, false); //attachToRoot = false important !
            //-----binding-----------
            newAddSafetyplanningFragment = new AddSafetyplanningFragment();
//            addplan_no1_layout = (LinearLayout)viewfirst.findViewById(R.id.addplan_no1_layout);
//            delplan_no1_layout = (LinearLayout)viewfirst.findViewById(R.id.delplan_no1_layout);
//            addasafetyplanningtextview1 = (TextView)viewfirst.findViewById(R.id.addasafetyplanningtextview1);
            //-----Logics------------
//            addplan_no1_layout.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    newAddSafetyplanningFragment.show(getFragmentManager(),"Add a safetyplanning");
//                    return false;
//                }
//            });
//
//            delplan_no1_layout.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    //delete
//                    return false;
//                }
//            });

            Log.d("Class:FirstFragment","Method:onCreateView(...)");

            return viewfirst;
        }

        //&&& 2. This method create & return "a new instance" of this fragment according to  "String text".
        public static FirstFragment newInstance(String text) {

            FirstFragment f = new FirstFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //---------------------------
            f.setArguments(b);

            Log.d("Class:FirstFragment","Method:newInstance(FirstFragment, Instance 1 )");

            return f;
        }
    }

    //----------------------SecondFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class SecondFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_safetyplanning_plan2)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewsecond = inflater.inflate(R.layout.fragment_survey_c_ssrs_second, container, false);//attachToRoot = false important !

            //-----binding-----------

            //-----Logics------------


            Log.d("Class:SecondFragment","Method:onCreateView(...)");

            return viewsecond;
        }

        //&&& This method create & return "a new instance" of this fragment according to  "String text".
        public static SecondFragment newInstance(String text) {

            SecondFragment f = new SecondFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //--------------------------
            f.setArguments(b);

            Log.d("Class:SecondFragment","Method:newInstance(SecondFragment, Instance 2 )");

            return f;
        }
    }
    //----------------------ThirdFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class ThirdFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_safetyplanning_plan3)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewthird = inflater.inflate(R.layout.fragment_survey_c_ssrs_third, container, false); //attachToRoot = false important !

            //-----binding-----------

            //-----Logics------------

            Log.d("Class:ThirdFragment","Method:onCreateView(...)");

            return viewthird;
        }

        //&&& This method create & return "a new instance" of this fragment according to  "String text".
        public static ThirdFragment newInstance(String text) {

            ThirdFragment f = new ThirdFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //---------------------------
            f.setArguments(b);

            Log.d("Class:ThirdFragment","Method:newInstance(ThirdFragment, Instance 3 )");

            return f;
        }
    }

    //----------------------FourthFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class FourthFragment extends Fragment {

        //This is the main method of this class->create a View (ffragment_safetyplanning_plan4)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewfourth = inflater.inflate(R.layout.fragment_survey_c_ssrs_fourth, container, false); //attachToRoot = false important !

            //-----binding-----------

            //-----Logics------------


            Log.d("Class:FourthFragment","Method:onCreateView(...)");

            return viewfourth;
        }

        //&&& 2. This method create & return "a new instance" of this fragment according to  "String text".
        public static FourthFragment newInstance(String text) {

            FourthFragment f = new FourthFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //---------------------------
            f.setArguments(b);

            Log.d("Class:FourthFragment","Method:newInstance(FourthFragment, Instance 4 )");

            return f;
        }
    }

    //-----------------------------------------------------------------------------------------------------

    //------------------------ Dialog for Importing a photo Logics -------------------------------------
    public static class AddSafetyplanningFragment extends DialogFragment {
        TextView addplan_btn;
        TextView cancelplan_btn;
        private static final String TAG = "MainActivity";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //-----Binding-----------
            View view = inflater.inflate(R.layout.dialog_add_safetyplanning, container);
            addplan_btn = (TextView)view.findViewById(R.id.addplan_btn);
            cancelplan_btn = (TextView)view.findViewById(R.id.cancelplan_btn);
            //-----Logics-----------
            addplan_btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newAddSafetyplanningFragment.dismiss();
                    //addasafetyplanningtextview.setText("A safety planning is added !"); //IMPROVE IT BEACUSE IT IS COMPLEXED !
                    return false;
                }
            });
            cancelplan_btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newAddSafetyplanningFragment.dismiss();
                    return false;
                }
            });

            return view;
        }
    }
//---------------------------------------------------------------------------------------------

    //-----------------------------------Dialog for warning before logging out--------------------------
    public class LogOutDialog extends DialogFragment {

        TextView yesbtn_logout,nobtn_logout;

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
                    Intent it = new Intent(Survey_C_ssrs.this, LoginActivity.class);
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
//--------------------------------------------------------------------------------------------------

    //----------------------Volley----------------------------------------------
    //Volley is a library for send & receive msg better
    //http://stackoverflow.com/questions/17045795/making-a-https-request-using-android-volley,
    //HttpsTrustManager is use for Trust self-certificated and I choose to use TrustAllCertificated.
    public static class HttpsTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] x509Certificates, String s)
                throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] x509Certificates, String s)
                throws java.security.cert.CertificateException {
        }

        public boolean isClientTrusted(X509Certificate[] chain) {
            return true;
        }

        public boolean isServerTrusted(X509Certificate[] chain) {
            return true;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return _AcceptedIssuers;
        }

        public static void allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

            });

            SSLContext context = null;
            if (trustManagers == null) {
                trustManagers = new TrustManager[]{new HttpsTrustManager()};
            }

            try {
                context = SSLContext.getInstance("TLS");
                context.init(null, trustManagers, new SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        }

    }
    //-------------------------------------------------------------------------
}
