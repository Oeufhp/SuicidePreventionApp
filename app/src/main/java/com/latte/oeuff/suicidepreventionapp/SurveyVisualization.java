//The logic is same as in "CreateAccountActivity.java"

package com.latte.oeuff.suicidepreventionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

public class SurveyVisualization extends AppCompatActivity {
    //********** Volley *********************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //----getIntent--------
    Intent it;
    String username, password;
    //----Others-----------
    FrameLayout seesurveyvisualization_layout;
    //  FrameLayout line1_layout, line2_layout, line3_layout, line4_layout;
    TextView visualizationstatus_textview;
    Button seesurveyvisualizationbtn;

    //----Graph-----------
    ImageView graph_imageview;
    //1-7 red / 8-11 orange / 12-15 yellow / 16-20 green
    ImageView reddot_col1, reddot_col2, reddot_col3, reddot_col4, reddot_col5;                 // 0 <= totalScoew <= 7  red
    ImageView orangedot_col1, orangedot_col2, orangedot_col3, orangedot_col4, orangedot_col5;  // 8 <= totalScore <= 11 orange
    ImageView yellowdot_col1, yellowdot_col2, yellowdot_col3, yellowdot_col4, yellowdot_col5;  // 12 <=totlScore <=15   yellow
    ImageView greendot_col1, greendot_col2, greendot_col3, greendot_col4, greendot_col5;       // 16 <=totalScore <= 20 green

    //------The application may be doing too much work on its main thread.-------
//    int[][] drawLine_array = new int[2][2];
//    int sumDrawLine = 0;
//    DrawCanvas[] drawCanvasDrawLine_array = new DrawCanvas[4];
//    int cdl_index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_visualization);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //----------getIntent---------
        it = getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
        //--------- Binding -------------
        seesurveyvisualization_layout = (FrameLayout) findViewById(R.id.seesurveyvisualization_layout);
        //-------The application may be doing too much work on its main thread.-------
//       line1_layout = (FrameLayout)findViewById(R.id.line1_layout);
//       line2_layout = (FrameLayout)findViewById(R.id.line2_layout);
//       line3_layout = (FrameLayout)findViewById(R.id.line3_layout);
//       line4_layout = (FrameLayout)findViewById(R.id.line4_layout);

        visualizationstatus_textview = (TextView) findViewById(R.id.visualizationstatus_textview);
        visualizationstatus_textview = (TextView) findViewById(R.id.visualizationstatus_textview);
        seesurveyvisualizationbtn = (Button) findViewById(R.id.seesurveyvisualizationbtn);

        //--------- Graph --------------
        graph_imageview = (ImageView) findViewById(R.id.graph_imageview);
        //reddot
        reddot_col1 = (ImageView) findViewById(R.id.reddot_col1);
        reddot_col2 = (ImageView) findViewById(R.id.reddot_col2);
        reddot_col3 = (ImageView) findViewById(R.id.reddot_col3);
        reddot_col4 = (ImageView) findViewById(R.id.reddot_col4);
        reddot_col5 = (ImageView) findViewById(R.id.reddot_col5);
        //orangedot
        orangedot_col1 = (ImageView) findViewById(R.id.orangedot_col1);
        orangedot_col2 = (ImageView) findViewById(R.id.orangedot_col2);
        orangedot_col3 = (ImageView) findViewById(R.id.orangedot_col3);
        orangedot_col4 = (ImageView) findViewById(R.id.orangedot_col4);
        orangedot_col5 = (ImageView) findViewById(R.id.orangedot_col5);
        //yellowdot
        yellowdot_col1 = (ImageView) findViewById(R.id.yellowdot_col1);
        yellowdot_col2 = (ImageView) findViewById(R.id.yellowdot_col2);
        yellowdot_col3 = (ImageView) findViewById(R.id.yellowdot_col3);
        yellowdot_col4 = (ImageView) findViewById(R.id.yellowdot_col4);
        yellowdot_col5 = (ImageView) findViewById(R.id.yellowdot_col5);
        //greendot
        greendot_col1 = (ImageView) findViewById(R.id.greendot_col1);
        greendot_col2 = (ImageView) findViewById(R.id.greendot_col2);
        greendot_col3 = (ImageView) findViewById(R.id.greendot_col3);
        greendot_col4 = (ImageView) findViewById(R.id.greendot_col4);
        greendot_col5 = (ImageView) findViewById(R.id.greendot_col5);

        //-------My Logics --------------------
        seesurveyvisualizationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seesurveyvisualization();
            }
        });
    }

    //------------------  seesurveyvisualization --------------------------------
    public void seesurveyvisualization() {
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/seesurveyvisualization";
        String url = "http://auth.oeufhp.me/beleafTest.php/seesurveyvisualization";
        //---------Message----------------
        final ProgressDialog pd = new ProgressDialog(SurveyVisualization.this);
        pd.setMessage("loading...");
        pd.show();

        //----------POST Request---------------
        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("see..response", response);
                    //-----------My logics---------------
//1.APIs:
//1.1 get first n rows sorted by date & time -OK
//1.2 send back to the app only totalScore
//1.3 create a new graph using...?

                    //Map<String, String> visualizationData = new HashMap<>();
                    String[][] visualizationData = new String[5][3];

                    //1 get value(=String) from response(=json array)
                    JSONObject jsonResponse = new JSONObject(response);
                    String stringResponse = jsonResponse.getString("seesurveyvisualization");
                    Log.d("see..strRes", stringResponse);

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

                        visualizationData[jsonarray_stringResponse.length() - 1 - i][0] = username;
                        visualizationData[jsonarray_stringResponse.length() - 1 - i][1] = sentdate;
                        visualizationData[jsonarray_stringResponse.length() - 1 - i][2] = totalscore;

                        Log.d("totalScoreSCORE:", visualizationData[jsonarray_stringResponse.length() - 1 - i][2]);
                    }
                    //-------------Check String[][]------------------------------------
                    for (int i = 0; i < visualizationData.length; i++) {
                        for (int j = 0; j < visualizationData[i].length; j++) {
                            if (visualizationData[i][j] == null) {
                                visualizationData[i][j] = "-1";      //AND THIS CANNOT BE USED ANYMORE...
                            }

                            Log.d("String i:" + i + " j:" + j + "->", visualizationData[i][j]);
                        }
                    }
                    //-------------- Show survey visualization ---------------------------------------
                    visualizationstatus_textview.setText("username:" + username);
                    seesurveyvisualization_layout.setVisibility(View.VISIBLE);

                    //=========set dot here========================================
                    //----col 1-------
                    if (Integer.parseInt(visualizationData[0][2]) >= 0 && Integer.parseInt(visualizationData[0][2]) <= 7) {
                        reddot_col1.setVisibility(View.VISIBLE);
                        //totalScore, scoreRange(red,orange,yellow,green), dot, col
                        setDot_col(Integer.parseInt(visualizationData[0][2]), "red", reddot_col1, 1);
                    } else if (Integer.parseInt(visualizationData[0][2]) >= 8 && Integer.parseInt(visualizationData[0][2]) <= 11) {
                        orangedot_col1.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[0][2]), "orange", orangedot_col1, 1);
                    } else if (Integer.parseInt(visualizationData[0][2]) >= 12 && Integer.parseInt(visualizationData[0][2]) <= 15) {
                        yellowdot_col1.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[0][2]), "yellow", yellowdot_col1, 1);
                    } else if (Integer.parseInt(visualizationData[0][2]) >= 16 && Integer.parseInt(visualizationData[0][2]) <= 20) {
                        greendot_col1.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[0][2]), "green", greendot_col1, 1);
                    } else {
                        visualizationData[0][2] = "-1";
                    }

                    //----col 2 ------
                    if (Integer.parseInt(visualizationData[1][2]) >= 0 && Integer.parseInt(visualizationData[1][2]) <= 7) {
                        reddot_col2.setVisibility(View.VISIBLE);
                        //totalScore, scoreRange(red,orange,yellow,green), dot
                        setDot_col(Integer.parseInt(visualizationData[1][2]), "red", reddot_col2, 2);
                    } else if (Integer.parseInt(visualizationData[1][2]) >= 8 && Integer.parseInt(visualizationData[1][2]) <= 11) {
                        orangedot_col2.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[1][2]), "orange", orangedot_col2, 2);
                    } else if (Integer.parseInt(visualizationData[1][2]) >= 12 && Integer.parseInt(visualizationData[1][2]) <= 15) {
                        yellowdot_col2.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[1][2]), "yellow", yellowdot_col2, 2);
                    } else if (Integer.parseInt(visualizationData[1][2]) >= 16 && Integer.parseInt(visualizationData[1][2]) <= 20) {
                        greendot_col2.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[1][2]), "green", greendot_col2, 2);
                    } else {
                        visualizationData[1][2] = "-1";
                    }

                    //----col 3 ------
                    if (Integer.parseInt(visualizationData[2][2]) >= 0 && Integer.parseInt(visualizationData[2][2]) <= 7) {
                        reddot_col3.setVisibility(View.VISIBLE);
                        //totalScore, scoreRange(red,orange,yellow,green), dot
                        setDot_col(Integer.parseInt(visualizationData[2][2]), "red", reddot_col3, 3);
                    } else if (Integer.parseInt(visualizationData[2][2]) >= 8 && Integer.parseInt(visualizationData[2][2]) <= 11) {
                        orangedot_col3.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[2][2]), "orange", orangedot_col3, 3);
                    } else if (Integer.parseInt(visualizationData[2][2]) >= 12 && Integer.parseInt(visualizationData[2][2]) <= 15) {
                        yellowdot_col3.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[2][2]), "yellow", yellowdot_col3, 3);
                    } else if (Integer.parseInt(visualizationData[2][2]) >= 16 && Integer.parseInt(visualizationData[2][2]) <= 20) {
                        greendot_col3.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[2][2]), "green", greendot_col3, 3);
                    } else {
                        visualizationData[2][2] = "-1";
                    }

                    //----col 4 ------
                    if (Integer.parseInt(visualizationData[3][2]) >= 0 && Integer.parseInt(visualizationData[3][2]) <= 7) {
                        reddot_col4.setVisibility(View.VISIBLE);
                        //totalScore, scoreRange(red,orange,yellow,green), dot
                        setDot_col(Integer.parseInt(visualizationData[3][2]), "red", reddot_col4, 4);
                    } else if (Integer.parseInt(visualizationData[3][2]) >= 8 && Integer.parseInt(visualizationData[3][2]) <= 11) {
                        orangedot_col4.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[3][2]), "orange", orangedot_col4, 4);
                    } else if (Integer.parseInt(visualizationData[3][2]) >= 12 && Integer.parseInt(visualizationData[3][2]) <= 15) {
                        yellowdot_col4.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[3][2]), "yellow", yellowdot_col4, 4);
                    } else if (Integer.parseInt(visualizationData[3][2]) >= 16 && Integer.parseInt(visualizationData[3][2]) <= 20) {
                        greendot_col4.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[3][2]), "green", greendot_col4, 4);
                    } else {
                        visualizationData[3][2] = "-1";
                    }

                    //----col 5 ------
                    if (Integer.parseInt(visualizationData[4][2]) >= 0 && Integer.parseInt(visualizationData[4][2]) <= 7) {
                        reddot_col5.setVisibility(View.VISIBLE);
                        //totalScore, scoreRange(red,orange,yellow,green), dot
                        setDot_col(Integer.parseInt(visualizationData[4][2]), "red", reddot_col5, 5);
                    } else if (Integer.parseInt(visualizationData[4][2]) >= 8 && Integer.parseInt(visualizationData[4][2]) <= 11) {
                        orangedot_col5.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[4][2]), "orange", orangedot_col5, 5);
                    } else if (Integer.parseInt(visualizationData[4][2]) >= 12 && Integer.parseInt(visualizationData[4][2]) <= 15) {
                        yellowdot_col5.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[4][2]), "yellow", yellowdot_col5, 5);
                    } else if (Integer.parseInt(visualizationData[4][2]) >= 16 && Integer.parseInt(visualizationData[4][2]) <= 20) {
                        greendot_col5.setVisibility(View.VISIBLE);
                        setDot_col(Integer.parseInt(visualizationData[4][2]), "green", greendot_col5, 5);
                    } else {
                        visualizationData[4][2] = "-1";
                    }
                    //=============================================================
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

    //-----------------------setDot_col-------------------------------------------
    public void setDot_col(int totalScore, String scoreRange, ImageView dot, int col) {

        Log.d("totalScore:scoreRange", totalScore + " " + scoreRange);

        //----This will help to set imageView position--------
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        //------col no.-----------------------
        if (col == 1) {
            params.leftMargin = 153;
        }
        if (col == 2) {
            params.leftMargin = 263;
        }
        if (col == 3) {
            params.leftMargin = 370;
        }
        if (col == 4) {
            params.leftMargin = 476;
        }
        if (col == 5) {
            params.leftMargin = 585;
        }

        //-------color range-------
        if (scoreRange.equals("red")) {

            //---------totalScore------------
            if (totalScore == 0) {
                //params.setMargins(0,496, 0,0); //setMargins(int left, int top, int right, int bottom)
                params.topMargin = 496;
            } else if (totalScore == 1) {
                //params.setMargins(0,477, 0,0);
                params.topMargin = 477;
            } else if (totalScore == 2) {
                //params.setMargins(0,458, 0,0);
                params.topMargin = 458;
            } else if (totalScore == 3) {
                // params.setMargins(0,439, 0,0);
                params.topMargin = 439;
            } else if (totalScore == 4) {
                //params.setMargins(0,419, 0,0);
                params.topMargin = 419;
            } else if (totalScore == 5) {
                //params.setMargins(0,400, 0,0); //correct
                params.topMargin = 400;
            } else if (totalScore == 6) {
                //params.setMargins(0,383, 0,0);
                params.topMargin = 383;
            } else if (totalScore == 7) {
                //params.setMargins(0,364, 0,0);
                params.topMargin = 364;
            }
        } else if (scoreRange.equals("orange")) {
            if (totalScore == 8) {
                //params.setMargins(0,345, 0,0);
                params.topMargin = 345;
            } else if (totalScore == 9) {
                //params.setMargins(0,326, 0,0);
                params.topMargin = 326;
            } else if (totalScore == 10) {
                //params.setMargins(0,306, 0,0);
                params.topMargin = 306;
            } else if (totalScore == 11) {
                //params.setMargins(0,287, 0,0); //correct
                params.topMargin = 287;
            }
        } else if (scoreRange.equals("yellow")) {
            if (totalScore == 12) {
                //params.setMargins(0,269, 0,0); //correct
                params.topMargin = 269;
            } else if (totalScore == 13) {
                //params.setMargins(0,249, 0,0);
                params.topMargin = 249;
            } else if (totalScore == 14) {
                //params.setMargins(0,230, 0,0);
                params.topMargin = 230;
            } else if (totalScore == 15) {
                //params.setMargins(0,210, 0,0);
                params.topMargin = 210;
            }
        } else if (scoreRange.equals("green")) {
            if (totalScore == 16) {
                //params.setMargins(0,191, 0,0); //correct
                params.topMargin = 191;
            } else if (totalScore == 17) {
                //params.setMargins(0,171, 0,0);
                params.topMargin = 171;
            } else if (totalScore == 18) {
                //params.setMargins(0,152, 0,0);
                params.topMargin = 152;
            } else if (totalScore == 19) {
                //params.setMargins(0,132, 0,0); //+ about 19.5 (ALL) from point 20
                params.topMargin = 132;
            } else if (totalScore == 20) {
                //params.setMargins(0,113, 0,0); //correct
                params.topMargin = 113;
            }
        }

        //-----setLayoutParams-------------
        dot.setLayoutParams(params);

//#$%^&&#*$&(#$*    add views from "drawCanvasDrawLine_array" into "seesurveyvisualization_layout" here    #$%^&&#*$&(#$*
//------The application may be doing too much work on its main thread.-------

        //*** ========= keep data for drawing a line ======== ***
//        Log.d("sum..........",sumDrawLine+"");
//        Log.d("cdl_.........",cdl_index+"");
//
//        if(sumDrawLine <2) {
//            drawLine_array[sumDrawLine][0] = params.leftMargin;
//            drawLine_array[sumDrawLine][1] = params.topMargin;
//
//            Log.d("sumDraw dot:"+sumDrawLine,"left.."+params.leftMargin);
//            Log.d("sumDraw dot:"+sumDrawLine,"top.."+params.topMargin);
//
//            sumDrawLine++;
//        }
//        else {
//        //*** ===========Draw a line ========================== ***
//            DrawCanvas pcc = new DrawCanvas (this);
//            Canvas canvas = new Canvas();
//            pcc.draw(canvas);
//            pcc.setLayoutParams(new FrameLayout.LayoutParams(seesurveyvisualization_layout.getWidth(), seesurveyvisualization_layout.getHeight()));
//
//            if(cdl_index==0){
//                line1_layout.addView(pcc);
//                //seesurveyvisualization_layout.addView(line1_layout);
//                Log.d("cdl=0","success");
//            }
//
//            else if(cdl_index==1){
//                line2_layout.addView(pcc);
//                //seesurveyvisualization_layout.addView(line2_layout);
//                Log.d("cdl=1","success");
//            }
//            else if(cdl_index==2){
//                line3_layout.addView(pcc);
//                //seesurveyvisualization_layout.addView(line3_layout);
//                Log.d("cdl=2","success");
//            }
//            else if(cdl_index==3){
//                line4_layout.addView(pcc);
//                //seesurveyvisualization_layout.addView(line4_layout);
//                Log.d("cdl=3","success");
//            }
//            else {
//                Log.d("Logic","Wrong!");
//            }

//            seesurveyvisualization_layout.addView(pcc);
//            drawCanvasDrawLine_array[cdl_index] = pcc;
//            seesurveyvisualization_layout.addView(drawCanvasDrawLine_array[cdl_index]);
        //===============================================
//            sumDrawLine = 0;
//            cdl_index ++;
//            drawLine_array = new int[2][2];
//        }
//
    }

    //***==========DrawCanvas============================ ***
//    public class DrawCanvas extends View{
//        public DrawCanvas(Context mContext) {
//            super(mContext);
//        }
//        public void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//
//            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            //canvas.drawColor(Color.BLACK);
//            paint.setColor(Color.BLACK);
//            canvas.drawColor(Color.TRANSPARENT);
//            canvas.drawLine(drawLine_array[0][0],drawLine_array[0][1],drawLine_array[1][0],drawLine_array[1][1],paint);
//
//            Log.d("draw..","already");
//        }
//
//    }
    //===================================================

//#$%^&&#*$&(#$*&#($*&#($#*$&(#$&*#$(#&$*&(#$*#$(&#*$&(#$*&(#*#(&*$(#$&*#($&#(*$&(#*&#($*&#*($#*$&#($*&$($&(*$

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
