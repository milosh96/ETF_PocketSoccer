package com.vms.etf_pocketsoccer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vms.etf_pocketsoccer.util.SpinnerHeadAdapter;

public class SetUpActivity extends AppCompatActivity {

    private Spinner sp1;
    private Spinner sp2;
    private EditText name1;
    private EditText name2;
    private CheckBox ai1;
    private CheckBox ai2;

    //flag chosen
    private int flag1=0;
    private int flag2=0;

    int[] images={R.drawable.captain, R.drawable.ironman, R.drawable.logan,R.drawable.spiderman,R.drawable.cyclops,R.drawable.daredevil,R.drawable.deadpool};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        sp1=findViewById(R.id.player1_spinner);
        sp2=findViewById(R.id.player2_spinner);
        name1=findViewById(R.id.player1_name);
        name2=findViewById(R.id.player2_name);
        ai1=findViewById(R.id.player1_ai);
        ai2=findViewById(R.id.player2_ai);


        //spinnerzz
        SpinnerHeadAdapter adapter=new SpinnerHeadAdapter(getApplicationContext(),R.layout.spinner_item,getResources().getStringArray(R.array.names),images);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flag1=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2.setAdapter(adapter);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flag2=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void readyClicked(View view) {
        String player1=name1.getText().toString();
        String player2=name2.getText().toString();

        Boolean ai1=this.ai1.isChecked();
        Boolean ai2=this.ai2.isChecked();

        if(player1.trim().equals("")||player2.trim().equals("")){
            Toast.makeText(view.getContext(),"Names are required!",Toast.LENGTH_LONG).show();
            return;
        }

        //TODO create activity and intent
        if(player1.trim().equals(player2.trim())){
            Toast.makeText(view.getContext(),"Names cannot be identical!",Toast.LENGTH_LONG).show();
            return;
        }

        //create intent
        Intent intent=new Intent(this,GameActivity.class);
        intent.putExtra("PLAYER1_NAME",player1);
        intent.putExtra("PLAYER2_NAME",player2);

        intent.putExtra("PLAYER1_AI",ai1);
        intent.putExtra("PLAYER2_AI",ai2);

        intent.putExtra("PLAYER1_FLAG",flag1);
        intent.putExtra("PLAYER2_FLAG",flag2);

        startActivity(intent);
    }
}
