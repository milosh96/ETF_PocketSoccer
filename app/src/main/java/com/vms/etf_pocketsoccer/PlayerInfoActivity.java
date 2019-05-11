package com.vms.etf_pocketsoccer;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vms.etf_pocketsoccer.database.Entity.Player;
import com.vms.etf_pocketsoccer.database.MyViewModel;
import com.vms.etf_pocketsoccer.util.PlayerAdapter;

public class PlayerInfoActivity extends AppCompatActivity {

    private MyViewModel model;

    private String name1="";
    private String name2="";

    private PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        Bundle bundle=getIntent().getExtras();
        model=ViewModelProviders.of(this).get(MyViewModel.class);
        int db=0;
        int victor=0;

        if(bundle!=null){
            name1=bundle.getString("PLAYER_NAME1","Milos");
            name2=bundle.getString("PLAYER_NAME2","Milos");
            db=bundle.getInt("DB",0);
            if(db==1){
                //upisi u bazu
                victor=bundle.getInt("VICTOR",0);
            }
        }


        Player player1=model.findPlayer(name1);
        Player player2=model.findPlayer(name2);
        if(player1==null){
            model.insertPlayer(name1);
            player1=model.findPlayer(name1);
        }
        if(player2==null){
            model.insertPlayer(name2);
            player2=model.findPlayer(name2);
        }
        if(db==1){
            //rep se brine za dodavanje broja pobeda
            model.insertGame(name1,name2,victor);
            //da bi dohvatio azurne vrednosti za broj pobeda
            player1=model.findPlayer(name1);
            player2=model.findPlayer(name2);
        }

        TextView playerName1=findViewById(R.id.player_name1);

        playerName1.setText(player1.getName()+"("+player1.getWins()+")");

        TextView playerName2=findViewById(R.id.player_name2);

        playerName2.setText(player2.getName()+"("+player2.getWins()+")");

        adapter=new PlayerAdapter();


        RecyclerView player_games=findViewById(R.id.player_games);
        player_games.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        player_games.setAdapter(adapter);

        adapter.setGames(model.getGamesForPlayers(name1,name2));
    }

    public void resetClicked(View view) {
//        model.deleteGamesForPlayer(name1,adapter.getItemCount());
//        model.deleteGamesForPlayer(name2,adapter.getItemCount());

        model.deleteGamesForPlayers(name1,name2,adapter.getItemCount());

        onBackPressed();
    }

    public void backClicked(View view) {
        onBackPressed();
    }
}
