package com.hitch.hitch.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hitch.hitch.R;;

public class FinishDetailActivity extends AppCompatActivity {

    ProgressBar progressbar;
    TextView text;
    Button finish;
    private int progressBarValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_detail);

        progressbar = (ProgressBar)findViewById(R.id.finish_progress);
        text = (TextView)findViewById(R.id.wait);
        finish = (Button)findViewById(R.id.button_finish);

      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressBarValue < 100)
                {
                    progressBarValue++;

                    try{
                        Thread.sleep(20);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/



        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FinishDetailActivity.this, UserProfileActivity.class);
                startActivity(i);
            }
        });


    }
}
