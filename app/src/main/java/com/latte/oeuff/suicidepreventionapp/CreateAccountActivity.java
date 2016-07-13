package com.latte.oeuff.suicidepreventionapp;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


public class CreateAccountActivity extends AppCompatActivity {

    //==========Volley====================
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //===================================

    RadioButton malebtn, femalebtn, otherbtn;
        String gender="m"; //m (default)/ f / o
    EditText surname,name;

    //******** About Calendar ***********************
    TextView daytxtview, monthtxtview, yeartxtview;
    ImageButton pickdate;
    private int mYear;
    private int mMonth; //must +1
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
        String str_birthdate;
    //**********************************************

    EditText email;
    RadioButton create_yesbtn, create_nobtn;
        String create_btn="y"; //y(default) / n
    Spinner countryflagspinner;
    TextView countrycodetxtview;
    EditText phoneno;
    EditText username, password;
    TextView isvalidtxtview, completedview;
    Button  createbtn;
    ImageButton checkbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    //============= Volley ============================
        requestQueue = Volley.newRequestQueue(this);
    //---------Binding--------------------------------------
        malebtn = (RadioButton)findViewById(R.id.malebtn);
        femalebtn = (RadioButton)findViewById(R.id.femalebtn);
        otherbtn = (RadioButton)findViewById(R.id.otherbtn);
        surname = (EditText)findViewById(R.id.surname);
        name = (EditText)findViewById(R.id.name);

        //********** About Calendar *****************************
        daytxtview = (TextView)findViewById(R.id.daytxtview);
        monthtxtview = (TextView)findViewById(R.id.monthtxtview);
        yeartxtview = (TextView)findViewById(R.id.yeartxtview);
        pickdate = (ImageButton)findViewById(R.id.pickdate);
        //*****************************************************

        email = (EditText)findViewById(R.id.email);
        create_yesbtn = (RadioButton)findViewById(R.id.create_yesbtn);
        create_nobtn = (RadioButton)findViewById(R.id.create_nobtn);

    //-----------------------for contryflagspinner-------------------------------------
    //https://developer.android.com/guide/topics/ui/controls/spinner.html

        countryflagspinner = (Spinner)findViewById(R.id.countryflagspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countryflagspinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryflagspinner.setAdapter(adapter);
    //-------------------------------------------------------------------------------

        countrycodetxtview = (TextView) findViewById(R.id.countrycodetxtview);
        phoneno = (EditText)findViewById(R.id.phoneno);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        checkbtn = (ImageButton)findViewById(R.id.checkbtn);
        isvalidtxtview = (TextView)findViewById(R.id.isvalidtxtview);
        completedview = (TextView)findViewById(R.id.completedtxtview);
        createbtn = (Button)findViewById(R.id.createbtn);

    //---------------- My Logics ----------------------------------------------------
        //----gender----------------------------------------------------
        malebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "m";
                Log.d("gender1",gender);
            }
        });
        femalebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "f";
                Log.d("gender1",gender);
            }
        });
        otherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "o";
                Log.d("gender1",gender);
            }
        });

        //-------About calendar -----------------------------------------------
        //******* Show a DatePickerDialog ************
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        //***** get the current date ****
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1; //must +1
        mDay = c.get(Calendar.DAY_OF_MONTH);
        Log.d("get the current date","reach");

        //**** update the display ****
        updateDisplay();
        Log.d("updateDisplay","reach");

        //-------------- Get news via email ? ----------------------------
        create_yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_btn = "y" ;
            }
        });
        create_nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_btn = "n" ;
            }
        });

        //-------------- countryflagspinner ------------------------------
        //http://stackoverflow.com/questions/20151414/how-can-i-use-onitemselected-in-android (Noni A 's comment)
        // getSelectedItem().toString() :  http://stackoverflow.com/questions/1947933/how-to-get-spinner-value
       countryflagspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               switch (position) {
                   case 0:  //FRANCE
                       countrycodetxtview.setText("+33");
                       Log.d("countryflagspinner", countryflagspinner.getSelectedItem().toString()+" "+countrycodetxtview.getText().toString());
                       break;
                   case 1: //THAILAND
                       countrycodetxtview.setText("+66");
                       Log.d("countryflagspinner", countryflagspinner.getSelectedItem().toString()+" "+countrycodetxtview.getText().toString());
                       break;
                   case 2: //U.K.
                       countrycodetxtview.setText("+44");
                       Log.d("countryflagspinner", countryflagspinner.getSelectedItem().toString()+" "+countrycodetxtview.getText().toString());
                       break;
                   case 3: //U.S.A.
                       countrycodetxtview.setText("+1");
                       Log.d("countryflagspinner", countryflagspinner.getSelectedItem().toString()+" "+countrycodetxtview.getText().toString());
                       break;
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        //--------------- check-------------------------------------------
        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid();
            }
        });

        //-------------- create an account --------------------------------
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });

    }

    //**************** Show TimePicker & DatePicker *******************************
    //This class is for creating a Calendar up-to-date
    //https://developer.android.com/guide/topics/ui/controls/pickers.html

    // the call back received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear + 1; //must +1
                    mDay = dayOfMonth;
                    updateDisplay();
                    Log.d("DatePickerDialog","reach");
                }
            };

    //return DatePickerDialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        Log.d("onCreateDialog","reach");
        return null;
    }

    //update month day year
    private void updateDisplay() {
        daytxtview.setText(mDay+"");
        monthtxtview.setText(mMonth+"");
        yeartxtview.setText(mYear+"");
        str_birthdate = mYear + "-" + mMonth + "-" + mDay ;
        Log.d("str_sentdate", str_birthdate);
    }

//===================== createanaccount ======================================================

    public void create() {
        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/createanaccount";
        String url = "http://auth.oeufhp.me/beleaf.php/createanaccount";
        //---------Process Dialog----------------
        final ProgressDialog pd = new ProgressDialog(CreateAccountActivity.this);
        pd.setMessage("Creating...");
        pd.show();

        //----------Post Request---------------
        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //2.FLOW No.2
            @Override
            public void onResponse(String response) {
                pd.dismiss(); //Dismiss & Removing it from the screen

                try {
                    Log.d(" create's response",response);
                    JSONObject jsonResponse = new JSONObject(response);
                    String create_status = jsonResponse.getString("create_status");
                    //-----------My Logics---------------
                    if(create_status.equals("1")){
                        Toast.makeText(getApplicationContext(),"create", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(CreateAccountActivity.this, LoginActivity.class);
                        it.putExtra("username",username.getText().toString());
                        it.putExtra("password",password.getText().toString());
                            /*Bundle extras = new Bundle();
                                extras.putString("username", username_login_edittext.getText().toString());
                                extras.putString("password", password_login_edittext.getText().toString());
                            it.putExtras(extras);*/
                        startActivity(it);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"create failed", Toast.LENGTH_LONG).show();
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
                    }
                }
        )
        {
            //Flow NO 1.
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("gender", gender);
                params.put("surname", surname.getText().toString());
                params.put("name", name.getText().toString());
                //----About Calendar----------
                //params.put("day", daytxtview.getText().toString());
                //params.put("month", monthtxtview.getText().toString());
                //params.put("year", yeartxtview.getText().toString());
                params.put("birthdate", str_birthdate);
                //----------------------------
                params.put("email", email.getText().toString());
                params.put("create_btn", create_btn);
                params.put("countryflagspinner", countryflagspinner.getSelectedItem().toString());
                params.put("codetextview", countrycodetxtview.getText().toString());
                params.put("phoneno", phoneno.getText().toString());

                //username
                params.put("username", username.getText().toString());
                //password
                params.put("password", password.getText().toString());

                //---Problem------------
                Log.d("gender",gender);
                Log.d("surname",surname.getText().toString());
                Log.d("name",name.getText().toString());
                Log.d("birthdate",str_birthdate);
                Log.d("email", email.getText().toString());
                Log.d("create_btn",create_btn);
//                Log.d("countryflagspinner",countryflagspinner.getSelectedItem().toString());
//                Log.d("codetextview",countrycodetxtview.getText().toString());
//                Log.d("phoneno",phoneno.getText().toString());
//                Log.d("username",username.getText().toString());
//                Log.d("password",password.getText().toString());

                return params;
            }
        };

        requestQueue.add(postRequest);
    }

//====================== isValid() =================================================
    public void isValid(){

    }

//==================== Volley ======================================================
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
//==============================================================================

}


