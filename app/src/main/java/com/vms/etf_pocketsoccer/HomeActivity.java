package com.vms.etf_pocketsoccer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.vms.etf_pocketsoccer.R.color.colorGrey;

public class HomeActivity extends AppCompatActivity {

    public static final int PARAM_INTENT=12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button resume=findViewById(R.id.resume_btn);


        //TODO neki if
        resume.setEnabled(false);
        resume.setAlpha(0.85f);
    }

    public void resumeClicked(View view) {
    }

    public void newClicked(View view) {
        //TODO delete from preferences old games

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
