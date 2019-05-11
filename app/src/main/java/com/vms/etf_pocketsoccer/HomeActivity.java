package com.vms.etf_pocketsoccer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.vms.etf_pocketsoccer.R.color.colorGrey;

public class HomeActivity extends AppCompatActivity {

    public static final int PARAM_INTENT=12345;
    private SharedPreferences preferences;
    private static final String PREF="Continue";

    private Button resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        resume=findViewById(R.id.resume_btn);

        preferences=getSharedPreferences(PREF,MODE_PRIVATE);

        boolean exist=preferences.getBoolean("continue",false);

        //TODO neki if
        if(!exist) {
            resume.setEnabled(false);
            resume.setAlpha(0.85f);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean exist=preferences.getBoolean("continue",false);
        Log.d("HomeActivityCycle","OnStart called.");
        //TODO neki if
        if(!exist) {
            resume.setEnabled(false);
            resume.setAlpha(0.85f);
        }
        else {
            resume.setEnabled(true);
            resume.setAlpha(1.0f);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences=getSharedPreferences(PREF,MODE_PRIVATE);
        boolean exist=preferences.getBoolean("continue",false);
        Log.d("HomeActivityCycle","OnResume called.");
        //TODO neki if
        if(!exist) {
            resume.setEnabled(false);
            resume.setAlpha(0.85f);
        }
        else {
            resume.setEnabled(true);
            resume.setAlpha(1.0f);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        preferences=getSharedPreferences(PREF,MODE_PRIVATE);
        boolean exist=preferences.getBoolean("continue",false);
        Log.d("HomeActivityCycle","OnRestart called.");
        //TODO neki if
        if(!exist) {
            resume.setEnabled(false);
            resume.setAlpha(0.85f);
        }
        else {
            resume.setEnabled(true);
            resume.setAlpha(1.0f);
        }
    }

    public void resumeClicked(View view) {
        //dohvati sve podatke i posalji u intent
        Log.d("CONTINUE","Continue pressed");
        preferences=getSharedPreferences(PREF,MODE_PRIVATE);
        boolean exist=preferences.getBoolean("continue",false);
        if(exist) {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("CONTINUE", true);
                startActivity(intent);
        }
        else{
            resume.setEnabled(false);
            resume.setAlpha(0.85f);
        }
    }

    public void newClicked(View view) {
        //TODO delete from preferences old games


        SharedPreferences.Editor edit = preferences.edit();
        edit.remove("continue");
        edit.putBoolean("finish", false);
        edit.clear();
        edit.commit();

        Intent intent=new Intent(this, SetUpActivity.class);
        startActivity(intent);
    }

    public void tableClicked(View view) {
        Intent intent=new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void paramClicked(View view) {
        Intent intent=new Intent(this, ParamActivity.class);
        startActivityForResult(intent,PARAM_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PARAM_INTENT){
            if(resultCode==RESULT_OK){
                Toast.makeText(getApplicationContext(),"New settings saved!",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No new settings.",Toast.LENGTH_LONG).show();
            }
        }
    }
}
