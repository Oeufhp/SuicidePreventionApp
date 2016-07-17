
//The logic is same as in "CreateAccountActivity.java"

package com.latte.oeuff.suicidepreventionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoginActivity extends AppCompatActivity {
    //**********Volley*********************
    RequestQueue requestQueue;
    static TrustManager[] trustManagers;
    static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //---------- Others ---------------------------
    EditText user,pass;
    Button loginbtn;
    TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //************** Volley **********************
        requestQueue = Volley.newRequestQueue(this);
        //-----tie vars to id--------------------------------------------------
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        loginbtn = (Button)findViewById(R.id.loginbtn);
        forgotpass = (TextView)findViewById(R.id.forgotpass);
        //----- For a faster login: get Intent (MUST BE BELOW "tie vars to id") ----------------------------------------------------
        Intent it = getIntent();
        if(it != null){
            user.setText(it.getStringExtra("username"));
            pass.setText(it.getStringExtra("password"));
        }

        //----- My Logics ----------------------------------------
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //------Link and change textView to be same as the link-------   //PROBLEM forgetpass
        //forgotpass.setText("Forgot your pasword");
        //Linkify.addLinks(forgotpass, Linkify.ALL);

    }

    //-------------------------Login-----------------------------------------------------------
    public void login() {

        HttpsTrustManager.allowAllSSL(); //Trusting all certificates
        //String url = "http://ahealth.burnwork.space/vip/myapp/suicidePreventionAPIs.php/login";
        String url = "http://auth.oeufhp.me/beleaf.php/login";
        //---------Process Dialog----------------
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("loading...");
        pd.show();

        //----------Post Request---------------
        //https://github.com/codepath/android_guides/wiki/Networking-with-the-Volley-Library
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("login's response",response);
                    JSONObject jsonResponse = new JSONObject(response);
                    String login_status = jsonResponse.getString("login_status");
                    //-----------My Logics---------------
                    if(login_status.equals("1")){
                        Toast.makeText(getApplicationContext(),"login", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        it.putExtra("username",user.getText().toString());
                        it.putExtra("password",pass.getText().toString());
                            /*Bundle extras = new Bundle();
                                extras.putString("username", username_login_edittext.getText().toString());
                                extras.putString("password", password_login_edittext.getText().toString());
                            it.putExtras(extras);*/
                        startActivity(it);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"login failed", Toast.LENGTH_SHORT).show();
                    }

                    //----- if try is success -> dismiss the dialog ---------
                    pd.dismiss();

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
                params.put("username", user.getText().toString());
                params.put("password", pass.getText().toString());

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
