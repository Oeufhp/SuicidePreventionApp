//google map activity: scrolling activity: Insert codes of "Navigation Drawer Activity"
//https://developers.google.com/maps/documentation/android-api/map
//The logic is same as in "CreateAccountActivity.java"
//***getnearbyplaces_gps() / getnearbyplaces_search() / GPS are work in a smartphone but not in a genymotion emulator .***

package com.latte.oeuff.suicidepreventionapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//Fragment is a piece of an application's user interface or behavior that can be placed in an Activity
//https://developer.android.com/reference/android/app/Fragment.html

public class HelpNearYou extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, /*there are methods I can override*/
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    //*********** Volley ********************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //---About Google Map's set up----------
    LocationRequest mLocationRequest;        //https://developers.google.com/android/reference/com/google/android/gms/location/LocationRequest
    GoogleApiClient mGoogleApiClient; //&&&
    // provides a common entry point to all the Google Play services: https://developers.google.com/android/guides/api-client#start_an_automatically_managed_connection
    SupportMapFragment mFragment;
    //---About Google Map's contents---------
    GoogleMap mGoogleMap;
    LatLng latLng;
    Location mLastLocation;
    Marker mCurrLocation;
    //------Check gps status-----------------
    LocationManager manager;
    boolean gps_status;
    //--------------Others--------------------------
    Intent it;
    Double displacement;
    String place_keyword_city, place_keyword_country;
    private double latitude, longitude;
    String url, ip;
    JSONObject jsonResult;
    String sLati, sLongi, sCity, sCountry; //for the current location
    String[][] nearby_places_array = new String[200000][8]; //The data shouldn't be exceed.
    ProgressDialog pd;
    Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_near_you);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //************* getIntent ****************************
        it = getIntent();
        //-------Check Intent from previous activity "city & country" : null (nearby places) , not null (search nearby places) ---------
        if(it.getStringExtra("input_city").equals("empty") && it.getStringExtra("input_country").equals("empty") && !( it.getStringExtra("displacement").equals("empty") ) ) {
            place_keyword_city="empty";
            place_keyword_country="empty";
            displacement = Double.parseDouble(it.getStringExtra("displacement"));
            getnearbyplaces_gps();
        }
        else {
            place_keyword_city = it.getStringExtra("input_city").toLowerCase().trim();
            place_keyword_country = it.getStringExtra("input_country").toLowerCase().trim();
            getnearbyplaces_search();
        }
        //---------- set up fot Google Map---------------------------------
        // Obtain the SupportMapFragment and get notified when the map is ready to be used. It's a wrapper around a view of a map to automatically handle the necessary life cycle needs.
        //https://developers.google.com/android/reference/com/google/android/gms/maps/SupportMapFragment
        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);
        //------Check gps status-----------------
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        gps_status = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        //------ ProcessDialog "Loading..." + Show toast of url -----------------
        pd = new ProgressDialog(HelpNearYou.this);
        pd.setMessage("loading...");
        pd.show();

    //For getting constant current location
//        url = "https://freegeoip.net/json/";
//        Intent it = getIntent();
//        url = it.getStringExtra("web"); //url = https://freegeoip.net/json/ + it.getStringExtra("web");
//    //=========== Same as GeoIP.java========================
//    ConnectivityManager connMgr = (ConnectivityManager)
//            getSystemService(Context.CONNECTIVITY_SERVICE);
//    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//    if (networkInfo != null && networkInfo.isConnected()) {
//        new DownloadData().execute(url);
//    } else {
//        Toast.makeText(this,"No network connection available !!!",Toast.LENGTH_SHORT).show();
//    }
//    //=====================================================

    }

    //----------------- Show gps dialog if it's not open ----------------------------
    private void showGPSDisabledAlertToUser(){
        //declaire a dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //set "cancel" button
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel(); //cancel & close a dialog
                    }
                });
        //set message
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                //set "enable gps" button
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){ //go to settings to open gps
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        //create & show a dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

//    //For getting constant current location

//    //======================== Same as GeoIP.java==============================================
//
//    private class DownloadData extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            Toast.makeText(getApplicationContext(),"Connecting...",Toast.LENGTH_SHORT).show();
//            Log.d("onPreExecute","reach");
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                Log.d("doInBackground","reach");
//                return downloadUrl(urls[0]);
//
//            } catch (IOException e) {
////                Toast.makeText(getApplicationContext(),"Unable to retrieve web page. URL may be invalid/ Unstable Connection.",Toast.LENGTH_SHORT).show();
//                //-----------------------------
//                return "Unable to retrieve web page. URL may be invalid/ Unstable Connection.";
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            ip = result;
//
//            Log.d("onPostExecute","reach");
//            Log.d("ip",ip);
//
//            try {
//                // &&&&&&&&&&&&&&&&&& My Logics &&&&&&&&&&&&&&&&&&&&&&&
//                //-------- IP (String) -> IP (JsonObject) -> getString <latitude,longitude> -> <latitude,longitude>(Double) -----------
//                jsonResult = new JSONObject(ip);
//                sLati = jsonResult.getString("latitude");
//                sLongi = jsonResult.getString("longitude");
//
//                latitude = Double.parseDouble(sLati);
//                longitude = Double.parseDouble(sLongi);
//
//                //---------------- city, country -----------------------
//                sCity = jsonResult.getString("city");
//                sCountry = jsonResult.getString("country_name");
//
//                //----------Toast of success + final lati-longi  + Dismiss processdialog "loading"-------------
//                Toast.makeText(getApplicationContext(), "This is the final lati-longi: " + sLati + " " + sLongi, Toast.LENGTH_SHORT).show();
//                pd.dismiss();
//                Log.d("dismiss in onPost", "success");
//
//                //************************* ########### Important ######### ****************************************
//
//                if (place_keyword_city.equals("empty") && place_keyword_country.equals("empty")) {
//                    Toast.makeText(getApplicationContext(),"gps", Toast.LENGTH_SHORT);
//                }
//
//                else {
//                    LatLng t = new LatLng(latitude, longitude);
//
//                    Log.d("your latitude: " + latitude + "", "your longitude: " + longitude + "");
//
//                    marker = mGoogleMap.addMarker(new MarkerOptions().position(t).title("Your location").snippet("latitude: " + latitude + " " + "longitude: " + longitude)); //Add a marker in the map
//                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(t, 10), 500, null);
//
//                    //---My Logics---------
//                    getnearbyplaces_search();
//
//                    Log.d("getnearbyplaces", "reach");
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        private String downloadUrl(String myurl) throws IOException {
//            InputStream is = null;
//            // Only display the first 500 characters of the retrieved
//            // web page content.
//            int len = 500;
//
//            try {
//                URL url = new URL(myurl);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(30000 /* milliseconds */);
//                conn.setConnectTimeout(35000 /* milliseconds */);
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                conn.connect();
//
//                Log.d("reach","connect");
//
//                is = conn.getInputStream();
//
//                // Convert the InputStream into a string
//                String contentAsString = readIt(is, len);
//                return contentAsString;
//
//                // Makes sure that the InputStream is closed after the app is finished using it.
//            } finally {
//                if (is != null) {
//                    is.close();
//                }
//            }
//        }
//
//        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
//            Reader reader = null;
//            reader = new InputStreamReader(stream, "UTF-8");
//            char[] buffer = new char[len];
//            reader.read(buffer);
//            return new String(buffer);
//        }
//    }

    //------------------------- getnearbysearch_gps ------------------------------------------------------
    public void getnearbyplaces_gps(){
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/seesurveyhistory";
        String url = "http://auth.oeufhp.me/beleafTest.php/getnearbyplaces_gps";

        if(gps_status) {
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
            //----------GET Request---------------
            //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
            StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.d("getnear_gps:", response);
                        //-----------My logics---------------
                        //1 get value(=String) from response(=json array)
                        JSONObject jsonResponse = new JSONObject(response);
                        String stringResponse = jsonResponse.getString("getnearbyplaces_gps");

                        //2 change type from String->json array
                        //http://stackoverflow.com/questions/9961018/getting-specific-value-from-jsonarray
                        JSONArray jsonarray_stringResponse = new JSONArray(stringResponse);

                        int j = 0;
                        for (int i = 0; i < jsonarray_stringResponse.length(); i++) {
                            //3 get value(=json object = "one jsonObject" which is json array) from json array
                            JSONObject jsonobject_jsonarray_stringResponse = jsonarray_stringResponse.getJSONObject(i);

                            //4 get value(= String = each value in "one jsonObject") from "one jsonObject"
                            String place_keyword_city = jsonobject_jsonarray_stringResponse.getString("place_keyword_city");
                            String place_keyword_country = jsonobject_jsonarray_stringResponse.getString("place_keyword_country");
                            String place_name = jsonobject_jsonarray_stringResponse.getString("place_name");
                            String place_address = jsonobject_jsonarray_stringResponse.getString("place_address");
                            Double place_latitude = jsonobject_jsonarray_stringResponse.getDouble("place_latitude");
                            Double place_longitude = jsonobject_jsonarray_stringResponse.getDouble("place_longitude");
                            String place_phoneno = jsonobject_jsonarray_stringResponse.getString("place_phoneno");
                            String place_info = jsonobject_jsonarray_stringResponse.getString("place_info");

                            //===============Calculate the displacement====================================================
                            Location current_location = new Location("current_location");
                            current_location.setLatitude(mLastLocation.getLatitude());
                            current_location.setLongitude(mLastLocation.getLongitude());

                            Location nearby_location = new Location("nearby_location");
                            nearby_location.setLatitude(place_latitude);
                            nearby_location.setLongitude(place_longitude);

                            float displacement_result = mLastLocation.distanceTo(nearby_location) / 1000;
                            //float[] displacement_result = new float[1];
                            //Location.distanceBetween(mLastLocation.getLatitude(), mLastLocation.getLongitude(), place_latitude, place_longitude, displacement_result);

                            Log.d("displacement_result " + i + " ", displacement_result + "");
                            Log.d("displacement " + i + " ", displacement + "");
                            //===============================================================================================

                            if (displacement_result <= displacement) {
                                //-------------- keep data in nearbyplaces_location_map ---------------------------------------
                                nearby_places_array[j][0] = place_keyword_city;
                                nearby_places_array[j][1] = place_keyword_country;
                                nearby_places_array[j][2] = place_name;
                                nearby_places_array[j][3] = place_address;
                                nearby_places_array[j][4] = place_latitude + "";
                                nearby_places_array[j][5] = place_longitude + "";
                                nearby_places_array[j][6] = place_phoneno;
                                nearby_places_array[j][7] = place_info;

                                Log.d("getnear_gps " + j, nearby_places_array[j][4]);
                                Log.d("getnear_gps" + j, nearby_places_array[j][5]);

                                j++;

                            }
                        }

                        //################Show  nearbyplaces_location_map ##############################
                        for (int i = 0; i < nearby_places_array.length; i++) {

                            //if nearby_places_array is NULL -> break this loop
                            //********* important: default value in string array ==null *****************
                            if (nearby_places_array[0][4] == null && nearby_places_array[0][5] == null) {
                                Toast.makeText(getApplicationContext(), "No nearby places in your displacement range found", Toast.LENGTH_SHORT).show();
                                break;
                            }

                            Double nLatitude = Double.parseDouble(nearby_places_array[i][4]);
                            Double nLongitude = Double.parseDouble(nearby_places_array[i][5]);

                            Log.d("getnear_search_show i=" + i, "nLatitude:" + nLatitude + " nLongitude:" + nLongitude);

                            LatLng tnearby = new LatLng(nLatitude, nLongitude);
                            String newline = System.getProperty("line.separator");
                            marker = mGoogleMap.addMarker(new MarkerOptions().position(tnearby).title(nearby_places_array[i][2]).snippet("Address: " + nearby_places_array[i][3] + newline + "Phone NO.: " + nearby_places_array[i][6] + newline + "Info: " + nearby_places_array[i][7]).icon(BitmapDescriptorFactory.fromResource(R.drawable.helpnearyouicon_gps))); //Add a marker in the map
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tnearby, 5), 500, null);

                            //---check for breaking out of this loop-----
                            if (nearby_places_array[i + 1][2] == null) break;

                        }

                        //----- if try is success -> dismiss the dialog ---------
                        pd.dismiss();

                        //##############################################################################

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
                            pd.dismiss();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    // the POST parameters:

                    return params;
                }
            };

            requestQueue.add(postRequest);
        }
        else {
            pd.dismiss();
            Toast.makeText(this, "GPS is Disable in your devide", Toast.LENGTH_SHORT).show();
            showGPSDisabledAlertToUser();

        }
    }
    //-----------------------------------------------------------------------------------------------------

    //-------------------------  getnearbyplaces_search ---------------------------------------------------
    public void getnearbyplaces_search(){
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/seesurveyhistory";
        String url = "http://auth.oeufhp.me/beleafTest.php/getnearbyplaces_search";
        //---------Message----------------
//            final ProgressDialog pd_search = new ProgressDialog(NearbyPlaces.this);
//            pd_search.setMessage("pd_search...");
//            pd_search.show(); //PROBLEM

        //----------POST Request---------------
        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("getnear_search:" ,response);
                    //-----------My logics---------------

                    //1 get value(=String) from response(=json array)
                    JSONObject jsonResponse = new JSONObject(response);
                    String stringResponse = jsonResponse.getString("getnearbyplaces_search");

                    //2 change type from String->json array
                    //http://stackoverflow.com/questions/9961018/getting-specific-value-from-jsonarray
                    JSONArray jsonarray_stringResponse = new JSONArray(stringResponse);

                    for(int i=0; i < jsonarray_stringResponse.length();i++){
                        //3 get value(=json object = "one jsonObject" which is json array) from json array
                        JSONObject jsonobject_jsonarray_stringResponse = jsonarray_stringResponse.getJSONObject(i);

                        //4 get value(= String = each value in "one jsonObject") from "one jsonObject"
                        String place_keyword_city = jsonobject_jsonarray_stringResponse.getString("place_keyword_city");
                        String place_keyword_country = jsonobject_jsonarray_stringResponse.getString("place_keyword_country");
                        String place_name = jsonobject_jsonarray_stringResponse.getString("place_name");
                        String place_address = jsonobject_jsonarray_stringResponse.getString("place_address");
                        Double place_latitude = jsonobject_jsonarray_stringResponse.getDouble("place_latitude");
                        Double place_longitude = jsonobject_jsonarray_stringResponse.getDouble("place_longitude");
                        String place_phoneno = jsonobject_jsonarray_stringResponse.getString("place_phoneno");
                        String place_info = jsonobject_jsonarray_stringResponse.getString("place_info");

                        //-------------- keep data in nearbyplaces_location_map ---------------------------------------
                        nearby_places_array[i][0] = place_keyword_city;
                        nearby_places_array[i][1] = place_keyword_country;
                        nearby_places_array[i][2] = place_name;
                        nearby_places_array[i][3] = place_address;
                        nearby_places_array[i][4] = place_latitude + "";
                        nearby_places_array[i][5] = place_longitude + "";
                        nearby_places_array[i][6] = place_phoneno;
                        nearby_places_array[i][7] = place_info;

                        Log.d("getnear_search " + i, nearby_places_array[i][4]);
                        Log.d("getnear_search" + i, nearby_places_array[i][5]);

                    }

                    //################ Show  nearbyplaces_location_map ##############################
                    for(int i=0; i < nearby_places_array.length; i++) {

                        Double nLatitude = Double.parseDouble(nearby_places_array[i][4]);
                        Double nLongitude = Double.parseDouble(nearby_places_array[i][5]);

                        Log.d("getnear_search_show i="+i, "nLatitude:" + nLatitude + " nLongitude:" + nLongitude);

                        LatLng tnearby = new LatLng(nLatitude, nLongitude );
                        String newline = System.getProperty( "line.separator" );
                        marker = mGoogleMap.addMarker(new MarkerOptions().position(tnearby).title(nearby_places_array[i][2]).snippet("Address: " + nearby_places_array[i][3]+ newline + "Phone NO.: " + nearby_places_array[i][6] + newline + "Info: " + nearby_places_array[i][7] ).icon(BitmapDescriptorFactory.fromResource(R.drawable.helpnearyouicon))); //Add a marker in the map
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tnearby, 10), 500, null);

                        //---check for breaking out of this loop-----
                        if( nearby_places_array[i+1][2] == null ) break;

                    }
                    //--------if try is success -> dismiss the dialog ---------
                    pd.dismiss();
                    //##############################################################################

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
                            Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                        }

                        if (error instanceof TimeoutError) {
                            Log.e("Volley", "TimeoutError");
                        }else if(error instanceof NoConnectionError){
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
                        pd.dismiss();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("place_keyword_city", place_keyword_city );
                params.put("place_keyword_country", place_keyword_country);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    //-----------------------------------------------------------------------------------------------

    //**************************About Google Map *****************************************************************************
    //FLOW 1.: https://developers.google.com/android/reference/com/google/android/gms/maps/OnMapReadyCallback#public-method-summary
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("onMapReady","reach");

        mGoogleMap = googleMap;
        //-------------------- UI Settings --------------------------------------
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        //-----Just following a condition------
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //*********IMPORTANT: Enable blue circle + "zoom to my location" Button**************
        if (place_keyword_city.equals("empty") && place_keyword_country.equals("empty")) {
            Toast.makeText(getApplicationContext(),"gps", Toast.LENGTH_SHORT);

            mGoogleMap.setMyLocationEnabled(true);
            buildGoogleApiClient(); //FLOW NO.2
            mGoogleApiClient.connect(); //FLOW NO.3

        }
        //-------------------------------------



    }

    //FLOW NO.2 : Builder to configure a GoogleApiClient &&&
    // https://developers.google.com/android/reference/com/google/android/gms/location/LocationRequest#constants
    protected synchronized void buildGoogleApiClient() {

        Log.d("buildGoogleApiClient","reach");

        //&&&
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //FLOW NO.3 : Do when it connects
    @Override
    public void onConnected(Bundle bundle) {

        Log.d("onConnected","reach");

        //---Just following a condition-------
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //****getLastLocation****** ###
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        //------------

        //------- set up LocationRequest -------------
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(8000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        //-------place marker at current position-------
        if (mLastLocation != null) {
            mGoogleMap.clear(); //###
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            //****important: how to decorate icon (img must be png)*****
            mCurrLocation = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("My Current Location").snippet("My Latitude:" + mLastLocation.getLatitude() +", My Longitude:" + mLastLocation.getLongitude()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocationicon)));

        }
    }

    //FLOW NO.4 : Update the location when it changes
    @Override
    public void onLocationChanged(Location location) {

        Log.d("onLocationChanged","reach");

        //------remove previous current location marker and add new one at current position--------
        if (mCurrLocation != null) {
            mCurrLocation.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //****important: how to decorate icon (img must be png)*****
        mCurrLocation = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("My Current Location").snippet("My Latitude:" + mLastLocation.getLatitude() +", My Longitude:" + mLastLocation.getLongitude()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocationicon)));

        //Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();
        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    //FLOW NO.5 : Whe Exit the app
    @Override
    public void onPause() {
        super.onPause();
        //Unregister for location callbacks:
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

            Log.d("onPause","success");

        }
    }

    //-------------------------------- Unused ---------------------------------------------------
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    //https://developer.android.com/reference/android/app/Activity.html
//    @Override
//    protected void onStop() { //to stop the activity
//        super.onStop();
//        if (googleApiClient.isConnected()) {
//            googleApiClient.disconnect();
//        }
//    }


    //---------------------- Volley ----------------------------------------------
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
    //------------------------------------------------------------------------------------------------
}