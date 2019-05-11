package com.vms.etf_pocketsoccer.database;

import android.content.Context;

import com.vms.etf_pocketsoccer.database.Entity.Game;
import com.vms.etf_pocketsoccer.database.Entity.Player;

import java.util.Date;
import java.util.List;

public class Repository {

    private FootballDB inFootballDB;

    public Repository(Context context){
        inFootballDB=FootballDB.getInstance(context);
    }

    //for players

    public void insertPlayer(String name){
        Player player=new Player(name,0);
        inFootballDB.playerDAO().insertPlayer(player);
    }

    public List<Player> getAllPlayers(){
        return inFootballDB.playerDAO().getAll();
    }

    public void resetWins(String name,int number){
        inFootballDB.playerDAO().resetWins(name,number);
    }
    public void resetAllPlayers(){
        inFootballDB.playerDAO().resetAll();
    }

    public boolean findPlayerName(String name){
        Player player=inFootballDB.playerDAO().findName(name);
        if(player==null)
            return false;
        else return true;
    }

    public Player findPlayer(String name){
        return  inFootballDB.playerDAO().findName(name);
    }


    //for games
    public void insertGame(String user_1, String user_2, int victor){
        Date timestamp=new Date();

        Game game=new Game(user_1,user_2,victor,timestamp);

        inFootballDB.gameDAO().insertGame(game);
        if(victor==1){
            inFootballDB.playerDAO().addWin(user_1);
        }
        else if(victor==2){
            inFootballDB.playerDAO().addWin(user_2);
        }
    }

    public List<Game> getAllGames(){
        return inFootballDB.gameDAO().getAllGames();
    }

    public List<Game> getGamesForPlayer(String name){
        return inFootballDB.gameDAO().getGamesForPlayer(name);
    }

    public List<Game> getGamesForPlayers(String name1,String name2){
        return inFootballDB.gameDAO().getGamesForPlayers(name1,name2);
    }

    public void deleteAllGames(){
        inFootballDB.gameDAO().deleteAll();
        inFootballDB.playerDAO().resetAll();
    }

    public void deleteGamesForPlayer(String name,int number){
        inFootballDB.gameDAO().deleteForPlayer(name);
        inFootballDB.playerDAO().resetWins(name, number);
    }

    public void deleteGamesForPlayers(String name1,String name2,int number){
        inFootballDB.gameDAO().deleteGamesForPlayers(name1,name2);
        inFootballDB.playerDAO().resetWins(name1, number);
        inFootballDB.playerDAO().resetWins(name2, number);
    }
}
