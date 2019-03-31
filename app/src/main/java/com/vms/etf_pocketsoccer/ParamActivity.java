package com.vms.etf_pocketsoccer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.vms.etf_pocketsoccer.util.SpinnerHeadAdapter;

public class ParamActivity extends AppCompatActivity {

    int[] images={R.drawable.grass,R.drawable.concrete,R.drawable.clay,R.drawable.wood};

    private Spinner mySpinner;
    private SeekBar seekBar;
    private RadioGroup radioGroup;

    private int selectedItem=0;

    private SharedPreferences preferences;
    private static final String PREF="Params";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);


        preferences=getSharedPreferences(PREF,MODE_PRIVATE);


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

//        numberPicker=findViewById(R.id.number_picker);
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(5);
//        numberPicker.setValue(speed);
        seekBar=findViewById(R.id.seek_bar);
        //progres 0..5
        seekBar.setMax(5);
        seekBar.setProgress(speed);

        mySpinner=findViewById(R.id.spinner_field);

        SpinnerHeadAdapter adapter=new SpinnerHeadAdapter(getApplicationContext(),R.layout.spinner_item,getResources().getStringArray(R.array.fields),images);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int pos=0;
        switch (field){
            case "grass": {
                pos=0;
                break;
            }
            case "concrete": {
                pos=1;
                break;
            }
            case "clay": {
                pos=2;
                break;
            }
            case "wood": {
                pos=3;
                break;
            }
        }
        mySpinner.setSelection(pos);

        radioGroup=findViewById(R.id.radio_group);

        RadioButton rb=findViewById(R.id.radio_time);;

        switch (condition){
            case 1:{
                //time
                rb=findViewById(R.id.radio_time);
                rb.setChecked(true);
                break;
            }
            case 2:{
                rb=findViewById(R.id.radio_goals);
                rb.setChecked(true);
                break;
            }
            default:{
                rb.setChecked(true);
                break;
            }
        }
    }

    public void saveClicked(View view) {
        //proveravamo promene
        int radioClicked=radioGroup.getCheckedRadioButtonId();
        int condition=1;

        if(radioClicked==R.id.radio_goals){
            condition=2;
        }

        String field="";
        switch (selectedItem){
            case 0:{
                //grass
                field="grass";
                break;
            }
            case 1:{
                //grass
                field="concrete";
                break;
            }
            case 2:{
                //grass
                field="clay";
                break;
            }
            case 3:{
                //grass
                field="wood";
                break;
            }
            default:{
                //grass
                field="grass";
                break;
            }
        }
        int speed=seekBar.getProgress();

        SharedPreferences.Editor editor=preferences.edit();

        editor.putInt("speed",speed);
        editor.putInt("condition",condition);
        editor.putString("field",field);


        editor.commit();

        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    public void cancelClicked(View view) {
        //nista se ne cuva
        Intent intent=new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
}
