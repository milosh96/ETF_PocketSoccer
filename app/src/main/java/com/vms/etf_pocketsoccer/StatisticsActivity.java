package com.vms.etf_pocketsoccer;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vms.etf_pocketsoccer.database.Entity.Player;
import com.vms.etf_pocketsoccer.database.MyViewModel;
import com.vms.etf_pocketsoccer.util.MyRecyclerAdapter;

public class StatisticsActivity extends AppCompatActivity implements MyRecyclerAdapter.FindPlayer {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;
    private MyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        model=ViewModelProviders.of(this).get(MyViewModel.class);

        //recyclerview
        recyclerView=findViewById(R.id.recycler_view);
        adapter=new MyRecyclerAdapter(getApplicationContext(),this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        adapter.setPlayers(model.getAllPlayers());
        adapter.setGames(model.getAllGames());

    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.setGames(model.getAllGames());
    }

    public void backClicked(View view) {
        onBackPressed();
    }

    @Override
    public Player findPlayer(String name) {
        return model.findPlayer(name);
    }

    public void resetClicked(View view) {
        //delete all games
        model.deleteAllGames();
    }
}
