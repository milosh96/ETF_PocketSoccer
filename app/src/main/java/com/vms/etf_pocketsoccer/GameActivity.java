package com.vms.etf_pocketsoccer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vms.etf_pocketsoccer.game.GamePanel;

public class GameActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PARAMS FOR PLAYING
        preferences=getPreferences(MODE_PRIVATE);


        int speed=preferences.getInt("speed",0);
        if(speed==0){
            speed=preferences.getInt("speed_def",1);
        }

        //==1 time
        //==2 goals
        int condition=preferences.getInt("condition",0);
        if(condition==0){
            condition=preferences.getInt("condition_def",1);
        }

        String field=preferences.getString("field","empty");
        if(field.equals("empty")){
            field=preferences.getString("field_def","grass");
        }


        //PLAYERS INFO
        Bundle bundle=getIntent().getExtras();

        String name1="";
        String name2="";
        Boolean ai1=false;
        Boolean ai2=false;

        int flag1=0;
        int flag2=0;

        if(bundle!=null){
            name1=bundle.getString("PLAYER1_NAME");
            name2=bundle.getString("PLAYER2_NAME");

            ai1=bundle.getBoolean("PLAYER1_AI");
            ai2=bundle.getBoolean("PLAYER2_AI");

            flag1=bundle.getInt("PLAYER1_FLAG");
            flag2=bundle.getInt("PLAYER2_FLAG");
        }

        setContentView(new GamePanel(this,name1,name2,ai1,ai2,flag1,flag2,speed,condition,field));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO add preferences | stop game
        //release view
    }
}
