
//https://developer.android.com/reference/android/os/Handler.html#postDelayed(java.lang.Runnable, long)

package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.os.Handler;

public class SplashSreen extends AppCompatActivity {
    android.os.Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bitmap n = BitmapFactory.decodeResource(getResources(), R.drawable.demo_splashscreen);
        //n = Bitmap.createScaledBitmap(n, 100, 100, true);
        //-------for SplashScreen (important)--------------------
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        //-------------------------------------------------------
        setContentView(R.layout.activity_splash_sreen);
        handler = new Handler();

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
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

}
