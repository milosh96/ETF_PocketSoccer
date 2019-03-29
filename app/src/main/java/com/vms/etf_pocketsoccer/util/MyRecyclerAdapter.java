package com.vms.etf_pocketsoccer.util;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vms.etf_pocketsoccer.PlayerInfoActivity;
import com.vms.etf_pocketsoccer.R;
import com.vms.etf_pocketsoccer.database.Entity.Game;
import com.vms.etf_pocketsoccer.database.Entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.GameHolder> {

    private Context context;
    private List<Game> games=new ArrayList<>();
    private List<Player> players=new ArrayList<>();

    private FindPlayer listener;

    public interface FindPlayer{
        Player findPlayer(String name);
    }

    public MyRecyclerAdapter(Context context, FindPlayer listener){
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(this.context).inflate(R.layout.game_row,viewGroup,false);
        return new GameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder gameHolder, int i) {
        Game game=games.get(i);
        String name_1=game.getName_1();
        String name_2=game.getName_2();

        Player player1=listener.findPlayer(name_1);
        Player player2=listener.findPlayer(name_2);
        int wins_1=0;

        if(player1!=null) {
            wins_1 = player1.getWins();
        }
        int wins_2=0 ;
        if(player2!=null){
            wins_2=player2.getWins();
        }

        gameHolder.name_1.setText(name_1);
        gameHolder.name_2.setText(name_2);

        gameHolder.wins_1.setText(String.valueOf(wins_1));
        gameHolder.wins_2.setText(String.valueOf(wins_2));
    }

    public void setGames(List<Game> games){
        this.games=games;
        notifyDataSetChanged();
    }

    public void setPlayers(List<Player> players){
        this.players=players;
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class GameHolder extends RecyclerView.ViewHolder{

        public TextView name_1;
        public TextView name_2;
        public TextView wins_1;
        public TextView wins_2;

        public GameHolder(@NonNull View itemView) {

            super(itemView);

            name_1=itemView.findViewById(R.id.name_1);
            name_2=itemView.findViewById(R.id.name_2);

            wins_1=itemView.findViewById(R.id.wins_1);
            wins_2=itemView.findViewById(R.id.wins_2);


            //neww activity
            name_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,"Name_1 clicked",Toast.LENGTH_LONG).show();
                }
            });

            name_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(context,"Name_2 clicked",Toast.LENGTH_LONG).show();

                }
            });

            ImageView info_1=itemView.findViewById(R.id.info_1);
            ImageView info_2=itemView.findViewById(R.id.info_2);

            info_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,PlayerInfoActivity.class);
                    intent.putExtra("PLAYER_NAME1",name_1.getText().toString());
                    intent.putExtra("PLAYER_NAME2",name_2.getText().toString());
                    context.startActivity(intent);
                }
            });

            info_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,PlayerInfoActivity.class);
                    intent.putExtra("PLAYER_NAME1",name_1.getText().toString());
                    intent.putExtra("PLAYER_NAME2",name_2.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
