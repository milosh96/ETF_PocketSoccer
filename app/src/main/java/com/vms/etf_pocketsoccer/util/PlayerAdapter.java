package com.vms.etf_pocketsoccer.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vms.etf_pocketsoccer.R;
import com.vms.etf_pocketsoccer.database.Entity.Game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerGHolder> {

    private List<Game> games=new ArrayList<>();

    @NonNull
    @Override
    public PlayerGHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.details_row,viewGroup,false);
        return new PlayerAdapter.PlayerGHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerGHolder playerGHolder, int i) {
        Game game=games.get(i);
        String order=String.valueOf(i+1);
        playerGHolder.order.setText(order);


        String pattern = "MM/dd/yyyy HH:mm:ss";


        DateFormat df = new SimpleDateFormat(pattern);

        String date=df.format(game.getTimestamp());
        playerGHolder.date.setText(date);

        if(game.getVictor()==1){
            playerGHolder.victor.setText(game.getName_1());
        }else{
            playerGHolder.victor.setText(game.getName_2());
        }
    }

    public void setGames(List<Game> games){
        this.games=games;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class PlayerGHolder extends RecyclerView.ViewHolder{

        private TextView order;
        private TextView date;
        private TextView victor;

        public PlayerGHolder(@NonNull View itemView) {

            super(itemView);

            order=itemView.findViewById(R.id.players_order);
            date=itemView.findViewById(R.id.players_date);
            victor=itemView.findViewById(R.id.players_victor);

        }
    }
}
