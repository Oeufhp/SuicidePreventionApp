package com.latte.oeuff.suicidepreventionapp;

//https://github.com/johnjohndoe/AndroidPlot

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


// The simplest possible example of using AndroidPlot to plot some data.
public class SurveyVisualization_Cage_Logics extends AppCompatActivity {
    //********** Volley *********************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //----getIntent--------
    Intent it;
    String username, password;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_visualization_cage);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //----------getIntent---------
        it = getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");

        //------------------------ My Logics -------------------------------
        seesurveyvisualization_cage();
    }

    //------------------  seesurveyvisualization_cage --------------------------------
    public void seesurveyvisualization_cage() {
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/seesurveyvisualization";
        String url = "http://auth.oeufhp.me/beleaf.php/seesurveyvisualization_cage";
        //---------Message----------------
        final ProgressDialog pd = new ProgressDialog(SurveyVisualization_Cage_Logics.this);
        pd.setMessage("loading...");
        pd.show();

        //----------POST Request---------------
        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("visual..cage..response", response);
                    //-----------My logics---------------
//1.APIs:
//1.1 get first n rows sorted by date & time -OK
//1.2 send back to the app only totalScore
//1.3 create a new graph using...?

                    //Map<String, String> visualizationData = new HashMap<>();
                    String[] str_cageAns = new String[3];

                    //1 get value(=String) from response(=json array)
                    JSONObject jsonResponse = new JSONObject(response);
                    String stringResponse = jsonResponse.getString("seesurveyvisualization_cage");
                    Log.d("visual cage..strRes", stringResponse);

                    //2 change type from String->json array
                    //http://stackoverflow.com/questions/9961018/getting-specific-value-from-jsonarray
                    JSONArray jsonarray_stringResponse = new JSONArray(stringResponse);

                    for (int i = jsonarray_stringResponse.length() - 1; i >= 0; i--) {
                        //3 get value(=json object = "one jsonObject" which is json array) from json array
                        JSONObject jsonobject_jsonarray_stringResponse = jsonarray_stringResponse.getJSONObject(i);

                        //4 get value(= String = each value in "one jsonObject") from "one jsonObject"
                        String username = jsonobject_jsonarray_stringResponse.getString("username");
                        //  String password = jsonobject_jsonarray_stringResponse.getString("password");
                        String sentdate = jsonobject_jsonarray_stringResponse.getString("sentdate");
                        String totalscore = jsonobject_jsonarray_stringResponse.getString("totalScore");

                        str_cageAns[0] = username;
                        str_cageAns[1] = sentdate;
                        str_cageAns[2] = totalscore;

                        Log.d("totalScoreSCORE:", str_cageAns[2]);
                    }
                    //-------------Check str_cageAns (null or not ?)--------------------------------
                    if (str_cageAns[2] == null) {
                        Toast.makeText(getApplicationContext(), "Please do at least 1 Cage questionaire", Toast.LENGTH_SHORT);
                        Intent it = new Intent(SurveyVisualization_Cage_Logics.this, SurveyVisualizationOverview.class);
                        it.putExtra("username", username);
                        it.putExtra("password", password);
                        startActivity(it);
                    } else {
                        //-------------- Show survey visualization in "SurveyVisualization_Cage" ---------------------------------
                        Intent it = new Intent(SurveyVisualization_Cage_Logics.this, SurveyVisualization_Cage.class);
                        it.putExtra("username", username);
                        it.putExtra("password", password);
                        //-------- the data -----------------
                        it.putExtra("sentdate", str_cageAns[1]);
                        it.putExtra("str_totalscore", str_cageAns[2]);
                        startActivity(it);

                    }
                    //-----if try is success -> dismiss the dialog ---------
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
                return params;
            }
        };

        requestQueue.add(postRequest);
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


