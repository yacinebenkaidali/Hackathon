package com.example.yacinebenkaidali.habit_tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anis.widget_habbit.R;

public class GetStarted extends Activity {
Button btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        btn= findViewById(R.id.get_start_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(GetStarted.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });





    }
}
