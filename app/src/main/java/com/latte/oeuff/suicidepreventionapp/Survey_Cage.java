//The logic is same as in "CreateAccountActivity.java"

package com.latte.oeuff.suicidepreventionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class Survey_Cage extends AppCompatActivity implements View.OnClickListener{
    //************ Volley *******************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //----getIntent--------
    Intent it;
    String username,password;
    //---- Others -------------
    RadioGroup q1_group, q2_group, q3_group, q4_group;
    RadioButton q1_yes, q1_no, q2_yes, q2_no, q3_yes, q3_no,q4_yes, q4_no ;
    ImageButton sendsurveybtn;
    TextView sendsurveystatus_textview;
    //----Answers------------
    String[] survey_answers = new String[4];
    int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_cage);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //----------getIntent---------
        it =getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
        //--------- binding ----------
        //Q1
        q1_group = (RadioGroup)findViewById(R.id.q1_group);
        q1_yes = (RadioButton)findViewById(R.id.q1_yes); q1_yes.setOnClickListener(this);
        q1_no = (RadioButton)findViewById(R.id.q1_no); q1_no.setOnClickListener(this);
        //Q2
        q2_group = (RadioGroup)findViewById(R.id.q2_group);
        q2_yes = (RadioButton)findViewById(R.id.q2_yes); q2_yes.setOnClickListener(this);
        q2_no = (RadioButton)findViewById(R.id.q2_no); q2_no.setOnClickListener(this);
        //Q3
        q3_group = (RadioGroup)findViewById(R.id.q3_group);
        q3_yes = (RadioButton)findViewById(R.id.q3_yes); q3_yes.setOnClickListener(this);
        q3_no = (RadioButton)findViewById(R.id.q3_no); q3_no.setOnClickListener(this);
        //Q4
        q4_group = (RadioGroup)findViewById(R.id.q4_group);
        q4_yes = (RadioButton)findViewById(R.id.q4_yes); q4_yes.setOnClickListener(this);
        q4_no = (RadioButton)findViewById(R.id.q4_no); q4_no.setOnClickListener(this);

        sendsurveybtn = (ImageButton)findViewById(R.id.sendsurveybtn);
        sendsurveystatus_textview = (TextView)findViewById(R.id.sendsurveystatus_textview);

        //-----------------------------------My logics ----------------------------------------------------
        //************************* 1. Keep ans and points *****************************************
        //http://stackoverflow.com/questions/10356733/getcheckedradiobuttonid-returning-useless-int

        sendsurveybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debugging var
                boolean unfinished = false;

                //Q1 answer
                int q1_ans = q1_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
                switch (q1_ans) {
                    case R.id.q1_yes:
                        totalScore = totalScore + 1;
                        survey_answers[0]="1";
                        Log.d("Q1 score",totalScore+"");
                        break;
                    case R.id.q1_no:
                        totalScore = totalScore + 0;
                        survey_answers[0]="0";
                        Log.d("Q1 score",totalScore+"");
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
                        clear_survey();
                        unfinished = true;
                        break;
                }
                //Q2 answer
                int q2_ans = q2_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
                switch (q2_ans) {
                    case R.id.q2_yes:
                        totalScore = totalScore + 1;
                        survey_answers[1]="1";
                        Log.d("Q2 score",totalScore+"");
                        break;
                    case R.id.q2_no:
                        totalScore = totalScore + 0;
                        survey_answers[1]="0";
                        Log.d("Q2 score",totalScore+"");
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
                        clear_survey();
                        unfinished = true;
                        break;
                }
                //Q3 answer
                int q3_ans = q3_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
                switch (q3_ans) {
                    case R.id.q3_yes:
                        totalScore = totalScore + 1;
                        survey_answers[2]="1";
                        Log.d("Q3 score",totalScore+"");
                        break;
                    case R.id.q3_no:
                        totalScore = totalScore + 0;
                        survey_answers[2]="0";
                        Log.d("Q3 score",totalScore+"");
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
                        clear_survey();
                        unfinished = true;
                        break;
                }
                //Q4 answer
                int q4_ans = q4_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
                switch (q4_ans) {
                    case R.id.q4_yes:
                        totalScore = totalScore + 1;
                        survey_answers[3]="1";
                        Log.d("Q4 score",totalScore+"");
                        break;
                    case R.id.q4_no:
                        totalScore = totalScore + 0;
                        survey_answers[3]="0";
                        Log.d("Q4 score",totalScore+"");
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
                        clear_survey();
                        unfinished = true;
                        break;
                }

                Log.d("totalScore:",totalScore+"");
                if(unfinished==false) {
                    for (int i = 0; i < survey_answers.length; i++) {
                        Log.d("ans " + (i + 1) + ": ", survey_answers[i]);
                    }
                    try {
                        sendsurvey_cage();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //CANNOT PUT clear_survey(); HERE: "Unhandled exception java.lang.NullPointerException"
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
    //------- To clear a survey -----------
    public void clear_survey(){
        q1_group.clearCheck(); //Clear checked raioButton in radioGroup"q1_group"
        q2_group.clearCheck();
        q3_group.clearCheck();
        q4_group.clearCheck();
        totalScore = 0;
        survey_answers = new String[4];
    }

    //******************************* 2, Send them to API *****************************************
    //survey_answers
    //totalScore
    //-------------------------sendsurvey_cage-----------------------------------------------------------
    public void sendsurvey_cage() throws ParseException {
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/sendsurvey";
        String url = "http://auth.oeufhp.me/beleaf.php/sendsurvey_cage";
        //---------Message----------------
        final ProgressDialog pd = new ProgressDialog(Survey_Cage.this);
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
        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("sendsurvey_cage res",response);
                    JSONObject jsonResponse = new JSONObject(response);
                    String sendsurvey_status = jsonResponse.getString("sendsurvey_cage_status");

                    //-----------My logics---------------
                    if(sendsurvey_status.equals("1")) {
                        sendsurveystatus_textview.setText("sendsurvey_cage");
                        Toast.makeText(getApplicationContext(), "sendsurvey_cage", Toast.LENGTH_SHORT).show();
                        clear_survey();
                        //-----Return to SurveyOverview--------
//                        Intent it = new Intent(Survey_Cage.this, SurveyOverview.class);
//                        startActivity(it);
                    }
                    else {
                        sendsurveystatus_textview.setText("sendsurvey_cage failed");
                        Toast.makeText(getApplicationContext(), "sendsurvey_cage failed", Toast.LENGTH_SHORT).show();
                        clear_survey();
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
                params.put("sentdate", strSentDate);
                params.put("survey_cage_q1_ans", survey_answers[0]);
                params.put("survey_cage_q2_ans", survey_answers[1]);
                params.put("survey_cage_q3_ans", survey_answers[2]);
                params.put("survey_cage_q4_ans", survey_answers[3]);
                params.put("totalScore",totalScore+"");

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
