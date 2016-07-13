package com.latte.oeuff.suicidepreventionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Survey extends AppCompatActivity implements View.OnClickListener{
    //*******************************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //----Others-------------
    RadioGroup q1_group, q2_group, q3_group, q4_group, q5_group;
    RadioButton q1_1, q1_2, q1_3, q1_4,  q2_1, q2_2, q2_3, q2_4,  q3_1, q3_2, q3_3, q3_4,  q4_1, q4_2, q4_3, q4_4,  q5_1, q5_2, q5_3, q5_4;
    Button sendsurveybtn;
    TextView sendsurveystatus_textview;
    //----Answers------------
    String[] survey_answers = new String[5];
    int totalScore = 0;
    //----getIntent--------
    Intent it;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //----------getIntent---------
        it =getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
        //--------- binding ----------
        //Q1
        q1_group = (RadioGroup)findViewById(R.id.q1_group);
        q1_1 = (RadioButton)findViewById(R.id.q1_1); q1_1.setOnClickListener(this);
        q1_2 = (RadioButton)findViewById(R.id.q1_2); q1_2.setOnClickListener(this);
        q1_3 = (RadioButton)findViewById(R.id.q1_3); q1_3.setOnClickListener(this);
        q1_4 = (RadioButton)findViewById(R.id.q1_4); q1_4.setOnClickListener(this);
        //Q2
        q2_group = (RadioGroup)findViewById(R.id.q2_group);
        q2_1 = (RadioButton)findViewById(R.id.q2_1); q2_1.setOnClickListener(this);
        q2_2 = (RadioButton)findViewById(R.id.q2_2); q2_2.setOnClickListener(this);
        q2_3 = (RadioButton)findViewById(R.id.q2_3); q2_3.setOnClickListener(this);
        q2_4 = (RadioButton)findViewById(R.id.q2_4); q2_4.setOnClickListener(this);
        //Q3
        q3_group = (RadioGroup)findViewById(R.id.q3_group);
        q3_1 = (RadioButton)findViewById(R.id.q3_1); q3_1.setOnClickListener(this);
        q3_2 = (RadioButton)findViewById(R.id.q3_2); q3_2.setOnClickListener(this);
        q3_3 = (RadioButton)findViewById(R.id.q3_3); q3_3.setOnClickListener(this);
        q3_4 = (RadioButton)findViewById(R.id.q3_4); q3_4.setOnClickListener(this);
        //Q4
        q4_group = (RadioGroup)findViewById(R.id.q4_group);
        q4_1 = (RadioButton)findViewById(R.id.q4_1); q4_1.setOnClickListener(this);
        q4_2 = (RadioButton)findViewById(R.id.q4_2); q4_2.setOnClickListener(this);
        q4_3 = (RadioButton)findViewById(R.id.q4_3); q4_3.setOnClickListener(this);
        q4_4 = (RadioButton)findViewById(R.id.q4_4); q4_4.setOnClickListener(this);
        //Q5
        q5_group = (RadioGroup)findViewById(R.id.q5_group);
        q5_1 = (RadioButton)findViewById(R.id.q5_1); q5_1.setOnClickListener(this);
        q5_2 = (RadioButton)findViewById(R.id.q5_2); q5_2.setOnClickListener(this);
        q5_3 = (RadioButton)findViewById(R.id.q5_3); q5_3.setOnClickListener(this);
        q5_4 = (RadioButton)findViewById(R.id.q5_4); q5_4.setOnClickListener(this);

        sendsurveybtn = (Button)findViewById(R.id.sendsurveybtn);
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
                    case R.id.q1_1:
                        totalScore = totalScore + 1;
                        survey_answers[0]="1";
                        Log.d("Q1 score",totalScore+"");
                        break;
                    case R.id.q1_2:
                        totalScore = totalScore + 2;
                        survey_answers[0]="2";
                        Log.d("Q1 score",totalScore+"");
                        break;
                    case R.id.q1_3:
                        totalScore = totalScore + 3;
                        survey_answers[0]="3";
                        Log.d("Q1 score",totalScore+"");
                        break;
                    case R.id.q1_4:
                        totalScore = totalScore + 4;
                        survey_answers[0]="4";
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
                    case R.id.q2_1:
                        totalScore = totalScore + 1;
                        survey_answers[1]="1";
                        Log.d("Q2 score",totalScore+"");
                        break;
                    case R.id.q2_2:
                        totalScore = totalScore + 2;
                        survey_answers[1]="2";
                        Log.d("Q2 score",totalScore+"");
                        break;
                    case R.id.q2_3:
                        totalScore = totalScore + 3;
                        survey_answers[1]="3";
                        Log.d("Q2 score",totalScore+"");
                        break;
                    case R.id.q2_4:
                        totalScore = totalScore + 4;
                        survey_answers[1]="4";
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
                    case R.id.q3_1:
                        totalScore = totalScore + 1;
                        survey_answers[2]="1";
                        Log.d("Q3 score",totalScore+"");
                        break;
                    case R.id.q3_2:
                        totalScore = totalScore + 2;
                        survey_answers[2]="2";
                        Log.d("Q3 score",totalScore+"");
                        break;
                    case R.id.q3_3:
                        totalScore = totalScore + 3;
                        survey_answers[2]="3";
                        Log.d("Q3 score",totalScore+"");
                        break;
                    case R.id.q3_4:
                        totalScore = totalScore + 4;
                        survey_answers[2]="4";
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
                    case R.id.q4_1:
                        totalScore = totalScore + 1;
                        survey_answers[3]="1";
                        Log.d("Q4 score",totalScore+"");
                        break;
                    case R.id.q4_2:
                        totalScore = totalScore + 2;
                        survey_answers[3]="2";
                        Log.d("Q4 score",totalScore+"");
                        break;
                    case R.id.q4_3:
                        totalScore = totalScore + 3;
                        survey_answers[3]="3";
                        Log.d("Q4 score",totalScore+"");
                        break;
                    case R.id.q4_4:
                        totalScore = totalScore + 4;
                        survey_answers[3]="4";
                        Log.d("Q4 score",totalScore+"");
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"You have not answered all questions yet...",Toast.LENGTH_SHORT).show();
                        clear_survey();
                        unfinished = true;
                        break;
                }
                //Q5 answer
                int q5_ans = q5_group.getCheckedRadioButtonId(); //get id of the radioButton cheecked !
                switch (q5_ans) {
                    case R.id.q5_1:
                        totalScore = totalScore + 1;
                        survey_answers[4]="1";
                        Log.d("Q5 score",totalScore+"");
                        break;
                    case R.id.q5_2:
                        totalScore = totalScore + 2;
                        survey_answers[4]="2";
                        Log.d("Q5 score",totalScore+"");
                        break;
                    case R.id.q5_3:
                        totalScore = totalScore + 3;
                        survey_answers[4]="3";
                        Log.d("Q45 score",totalScore+"");
                        break;
                    case R.id.q5_4:
                        totalScore = totalScore + 4;
                        survey_answers[4]="4";
                        Log.d("Q5 score",totalScore+"");
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
                        sendsurvey();
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
        q5_group.clearCheck();
        totalScore = 0;
        survey_answers = new String[5];
    }

    //******************************* 2, Send them to API *****************************************
    //survey_answers
    //totalScore
    //-------------------------sendsurvey-----------------------------------------------------------
    public void sendsurvey() throws ParseException {
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/sendsurvey";
        String url = "http://auth.oeufhp.me/beleaf.php/sendsurvey";
        //---------Message----------------
        final ProgressDialog pd = new ProgressDialog(Survey.this);
        pd.setMessage("loading...");
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
                pd.dismiss(); //Dismiss & Removing it from the screen
                try {
                    Log.d("sendsurvey' response",response);
                    JSONObject jsonResponse = new JSONObject(response);
                    String sendsurvey_status = jsonResponse.getString("sendsurvey_status");

                    //-----------My logics---------------
                    if(sendsurvey_status.equals("1")) {
                        sendsurveystatus_textview.setText("sendsurvey");
                        Toast.makeText(getApplicationContext(), "sendsurvey", Toast.LENGTH_SHORT).show();
                        clear_survey();
                        //-----Return to SurveyOverview--------
                        Intent it = new Intent(Survey.this, SurveyOverview.class);
                        startActivity(it);
                    }
                    else {
                        sendsurveystatus_textview.setText("sendsurvey failed");
                        Toast.makeText(getApplicationContext(), "sendsurvey failed", Toast.LENGTH_SHORT).show();
                        clear_survey();
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
                        //-----------------------------------------------------------------
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
                params.put("survey_q1_ans", survey_answers[0]);
                params.put("survey_q2_ans", survey_answers[1]);
                params.put("survey_q3_ans", survey_answers[2]);
                params.put("survey_q4_ans", survey_answers[3]);
                params.put("survey_q5_ans", survey_answers[4]);
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

