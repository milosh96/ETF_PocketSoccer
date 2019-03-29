package com.vms.etf_pocketsoccer.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.vms.etf_pocketsoccer.database.Entity.Game;
import com.vms.etf_pocketsoccer.database.Entity.Player;


import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private Repository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application.getApplicationContext());
    }

    public void insertPlayer(String name){
        repository.insertPlayer(name);
    }

    public List<Player> getAllPlayers(){
        return repository.getAllPlayers();
    }

    public void resetWins(String name, int number){
        repository.resetWins(name,number);
    }
    public void resetAllPlayers(){
        repository.resetAllPlayers();
    }

    public boolean findPlayerName(String name){
         return repository.findPlayerName(name);
    }
    public Player findPlayer(String name){
        return repository.findPlayer(name);
    }


    //for games
    public void insertGame(String user_1, String user_2, int victor){
        repository.insertGame(user_1,user_2,victor);
    }

    public List<Game> getAllGames(){
        return repository.getAllGames();
    }

    public List<Game> getGamesForPlayer(String name){
        return repository.getGamesForPlayer(name);
    }

    public List<Game> getGamesForPlayers(String name1, String name2){
        return repository.getGamesForPlayers(name1,name2);
    }

    public void deleteAllGames(){
        repository.deleteAllGames();
    }

    public void deleteGamesForPlayer(String name,int number){
        repository.deleteGamesForPlayer(name,number);
    }

    public void deleteGamesForPlayers(String name1,String name2, int number){
        repository.deleteGamesForPlayers(name1,name2,number);
    }
}
