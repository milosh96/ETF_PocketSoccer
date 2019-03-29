package com.vms.etf_pocketsoccer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                openHome();
            }
        }).start();
    }


    public void openHome(){
        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
