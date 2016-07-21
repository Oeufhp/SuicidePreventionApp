package com.latte.oeuff.suicidepreventionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
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
import android.widget.LinearLayout;
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

public class Feeling extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //************ Volley *******************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //----getIntent--------
    Intent it;
    String username,password;

    //---About choices-------------
    LinearLayout perfectlayout, verygoodlayout, neutrallayout, verybadlayout, terriblelayout;
    String str_send_feeling;

    //---About Dialog---------------
    DialogFragment newFeelingEmergencyFragment;
    DialogFragment newLogoutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //----------getIntent---------
        it =getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
        //---------About choices----------------
        perfectlayout = (LinearLayout)findViewById(R.id.perfectlayout);
        verygoodlayout = (LinearLayout)findViewById(R.id.verygoodlayout);
        neutrallayout = (LinearLayout)findViewById(R.id.neutrallayout);
        verybadlayout = (LinearLayout)findViewById(R.id.verybadlayout);
        terriblelayout = (LinearLayout)findViewById(R.id.terriblelayout);
        //---------About Dialog------------------
        newFeelingEmergencyFragment = new FeelingEmergency();
        newLogoutFragment = new LogOutDialog();

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

    //---------------------------- Choices' Logics -------------------------------------------------
        perfectlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(),"Feeling: Perfect", Toast.LENGTH_SHORT).show();
                try {
                    sendfeeling("perfect");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        verygoodlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(),"Feeling: Very Good", Toast.LENGTH_SHORT).show();
                try {
                    sendfeeling("verygood");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        neutrallayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(),"Feeling: Neutral", Toast.LENGTH_SHORT).show();
                try {
                    sendfeeling("neutral");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        verybadlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(),"Feeling: Very Bad", Toast.LENGTH_SHORT).show();
                try {
                    sendfeeling("verybad");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        terriblelayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(),"Feeling: Terrible", Toast.LENGTH_SHORT).show();
                try {
                    sendfeeling("terrible");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                newFeelingEmergencyFragment.show(getSupportFragmentManager(), "Feeling Emergency");
                return false;
            }
        });
    //----------------------------------------------------------------------------------------------
    }

    //-------------sendfeeling() --------------------------------------------------------------------
    public void sendfeeling(String str_feeling) throws ParseException {
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/sendsurvey";
        String url = "http://auth.oeufhp.me/beleaf.php/sendfeeling";
        //---------Message----------------
        final ProgressDialog pd = new ProgressDialog(Feeling.this);
        pd.setMessage("Sending...");
        pd.show();

        //-------- Match with a score ---------------
        if(str_feeling.equals("perfect")){
            str_send_feeling = "5";
        }
        else if(str_feeling.equals("verygood")){
            str_send_feeling = "4";
        }
        else if(str_feeling.equals("neutral")){
            str_send_feeling = "3";
        }
        else if(str_feeling.equals("verybad")){
            str_send_feeling = "2";
        }
        else if(str_feeling.equals("terrible")){
            str_send_feeling = "1";
        }
        else {
            Toast.makeText(getApplicationContext(),"Error !", Toast.LENGTH_SHORT);
            Intent it = new Intent(Feeling.this, MainActivity.class);
            it.putExtra("username",username);
            it.putExtra("password", password);
            startActivity(it);
        }

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
        Log.d("strSentDate", strSentDate);
        //4. convert from "String"->"Date": http://stackoverflow.com/questions/6510724/how-to-convert-java-string-to-date-object
        DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date sentdate = null;
        try {
            sentdate = mDateFormat.parse(strSentDate); //"String" strSentDate is parsed to be "Date"
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //###By the way, send String strSentDate(String)->APIs(String)->DB(Date) is working !! & easier because of the method "getParams()" below
        //=====================================================================

        //----------Post Request---------------
        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("sendfeeling res", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    String sendsurvey_status = jsonResponse.getString("sendfeeling_status");

                    //-----------My logics---------------
                    if (sendsurvey_status.equals("1")) {
                        Toast.makeText(getApplicationContext(), "sendfeeling", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "sendfeeling failed", Toast.LENGTH_SHORT).show();
                    }

                    //----- if try is success -> dismiss the dialog ---------
                    pd.dismiss(); //Dismiss & Removing it from the screen

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        //-----------Check error (useful !)-----------------------------------------------
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                        }

                        if (error instanceof TimeoutError) {
                            Log.e("Volley", "TimeoutError");
                        } else if (error instanceof NoConnectionError) {
                            Log.e("Volley", "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("Volley", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e("Volley", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e("Volley", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e("Volley", "ParseError");
                        }
                        //--------if error -> dismiss the dialog ---------
                        pd.dismiss(); //Dismiss & Removing it from the screen
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("username", username);
                params.put("password", password);
                params.put("sentdate", strSentDate);
                params.put("feeling",str_send_feeling);

                return params;
            }
        };

        requestQueue.add(postRequest);
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
            //--------- Logics after pressing items on Navigation ----------------
            Intent it;
            if (id == R.id.nav_home) {
                it = new Intent(Feeling.this, MainActivity.class);  //PROBLEM FeelingLogics
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            } else if (id == R.id.nav_yourspace) {
                it = new Intent(Feeling.this, YourSpace.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            } else if (id == R.id.nav_todo) {
                it = new Intent(Feeling.this, Todo.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            } else if (id == R.id.nav_safetyplanning) {
                it = new Intent(Feeling.this, SafetyPlanning.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            } else if (id == R.id.nav_helpnearyou) {
                it = new Intent(Feeling.this, HelpNearYouOverview.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            } else if (id == R.id.nav_feeling) {
                it = new Intent(Feeling.this, Feeling.class);
                it.putExtra("username",username);
                it.putExtra("password", password);
                startActivity(it);
            } else if (id == R.id.nav_survey) {
                it = new Intent(Feeling.this, SurveyOverview.class);
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

        //------------------------ Dialog for Importing a photo Logics -------------------------------------
        public class FeelingEmergency extends DialogFragment {

            LinearLayout callemergency, cancel_emergency;
            private static final String TAG = "MainActivity";

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                //-----Binding-----------
                View view = inflater.inflate(R.layout.dialog_feeling_emergency, container);
                callemergency = (LinearLayout) view.findViewById(R.id.callemergency);
                cancel_emergency = (LinearLayout) view.findViewById(R.id.cancel_emergency);

                //-----Logics-----------
                callemergency.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        newFeelingEmergencyFragment.dismiss(); //close the dialog
                        //This is for going to phone in mobile
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:911"));
                        //no need to request a permission
                        startActivity(callIntent);

                        return false;
                    }
                });
                cancel_emergency.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        newFeelingEmergencyFragment.dismiss(); //close the dialog
                        return false;
                    }
                });

                return view;
            }
        }
//---------------------------------------------------------------------------------------------

        //-----------------------------------Dialog for warning before logging out--------------------------
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
                        Intent it = new Intent(Feeling.this, LoginActivity.class);
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