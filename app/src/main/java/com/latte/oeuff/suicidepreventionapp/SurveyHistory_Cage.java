//The logic is same as in "CreateAccountActivity.java"

package com.latte.oeuff.suicidepreventionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class SurveyHistory_Cage extends AppCompatActivity {
    //************** Volley *****************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //----getIntent--------
    Intent it;
    String username,password;
    //----Others-----------
    TextView seesurveyhistory_textview;
    LinearLayout seesurveyhistory_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_history__cage);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //----------getIntent---------
        it =getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
        //--------- Binding -------------
        seesurveyhistory_textview = (TextView)findViewById(R.id.seesurveyhistory_textview);
        seesurveyhistory_layout = (LinearLayout)findViewById(R.id.seesurveyhistory_layout);

        //-------My Logics --------------------
        seesurveyhistory_textview.setVisibility(View.GONE);
        seesurveyhistory_layout.removeAllViews();
        seesurveyhistory_cage();
    }

    //------------------  seesurveyhistory_cage --------------------------------
    public void seesurveyhistory_cage(){
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/seesurveyhistory";
        String url = "http://auth.oeufhp.me/beleaf.php/seesurveyhistory_cage";
        //---------Message----------------
        final ProgressDialog pd = new ProgressDialog(SurveyHistory_Cage.this);
        pd.setMessage("loading...");
        pd.show();

        //----------POST Request---------------
        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("seesurvey cage  res",response);
                    //-----------My logics---------------
                    String seesurveyhistory="";

                    //1 get value(=String) from response(=json array)
                    JSONObject jsonResponse = new JSONObject(response);
                    String stringResponse = jsonResponse.getString("seesurveyhistory_cage");

                    //2 change type from String->json array
                    //http://stackoverflow.com/questions/9961018/getting-specific-value-from-jsonarray
                    JSONArray jsonarray_stringResponse = new JSONArray(stringResponse);

                    for(int i=0; i < jsonarray_stringResponse.length();i++){
                        //3 get value(=json object = "one jsonObject" which is json array) from json array
                        JSONObject jsonobject_jsonarray_stringResponse = jsonarray_stringResponse.getJSONObject(i);

                        //4 get value(= String = each value in "one jsonObject") from "one jsonObject"
                        String username = jsonobject_jsonarray_stringResponse.getString("username");
                        //  String password = jsonobject_jsonarray_stringResponse.getString("password");
                        String sentdate = jsonobject_jsonarray_stringResponse.getString("sentdate");
                        String survey_q1_ans = jsonobject_jsonarray_stringResponse.getString("survey_cage_q1_ans");
                        String survey_q2_ans = jsonobject_jsonarray_stringResponse.getString("survey_cage_q2_ans");
                        String survey_q3_ans = jsonobject_jsonarray_stringResponse.getString("survey_cage_q3_ans");
                        String survey_q4_ans = jsonobject_jsonarray_stringResponse.getString("survey_cage_q4_ans");
                        String totalscore = jsonobject_jsonarray_stringResponse.getString("totalScore");

                        seesurveyhistory =  " Record:"+(i+1)+" "+"username:"+username+" "+"sentdate:"+sentdate+" "
                                +"Q1ans:"+survey_q1_ans+" "
                                +"Q2ans:"+survey_q2_ans+" "
                                +"Q3ans:"+survey_q3_ans+" "
                                +"Q4ans:"+survey_q4_ans+" "
                                +"total score:"+totalscore+" ";
                        //-------------- Show survey history ---------------------------------------
                        TextView aline =new TextView(SurveyHistory_Cage.this); //create a textview without binding to XML file
                        aline.setText(seesurveyhistory);
                        seesurveyhistory_layout.setBackgroundColor(Color.TRANSPARENT);
                        seesurveyhistory_layout.addView(aline); //add that textview in the LinearLayout
                        System.getProperty("line.separator"); //go to a new line

                        TextView space = new TextView(SurveyHistory_Cage.this);
                        space.setText("-------------------------------------------");
                        seesurveyhistory_layout.addView(space);
                        System.getProperty("line.separator");

                        //----- if try is success -> dismiss the dialog ---------
                        pd.dismiss(); //Dismiss & Removing it from the screen

                    }

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
                        pd.dismiss(); //Dismiss & Removing it from the screen
                    }
                }
        )
        {
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