package com.example.georgesice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private final int SplashDIsplaytime = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Context context = MainActivity.this;
                Class DestinationActivity = HomeActivity.class;
                Intent mainIintent = new Intent(context,DestinationActivity );
                MainActivity.this.startActivity(mainIintent);
                MainActivity.this.finish();

            }
        }, SplashDIsplaytime);
    }
}
