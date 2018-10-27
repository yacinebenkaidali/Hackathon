package com.example.yacinebenkaidali.habit_tracker;

import android.content.Intent;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.anis.widget_habbit.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent in=new Intent(Splash.this,GetStarted.class);
        Thread timer=new Thread(){
                public void run()
                {
                    try {
                        sleep(3000);
                    }
                catch (InterruptedException e)
                {
                    e.getStackTrace();
                }
                finally {
                    startActivity(in);
                    overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_in);
                    finish();
                }
            }
        };
        timer.start();
    }
}
