
//-------Still error postDelayed --------------------
//See: http://www.akexorcist.com/2013/05/android-code-splash-screen.html
//https://developer.android.com/reference/android/os/Handler.html#postDelayed(java.lang.Runnable, long)

package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import java.util.logging.Handler;
import android.app.Activity;


public class SplashSreen extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent it = new Intent(SplashSreen.this, LoginMenuActivity.class);
                startActivity(it);
                finish();
                //if (a user is in "log-in" state) go to MainActivity.class
            }
        };
    }
    public void onResume() {
        super.onResume();
        delay_time = time;
        //handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        //handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
}
