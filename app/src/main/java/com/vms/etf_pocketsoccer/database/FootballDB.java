package com.vms.etf_pocketsoccer.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.vms.etf_pocketsoccer.database.Dao.GameDAO;
import com.vms.etf_pocketsoccer.database.Dao.PlayerDAO;
import com.vms.etf_pocketsoccer.database.Entity.Game;
import com.vms.etf_pocketsoccer.database.Entity.Player;

import java.util.Date;

@Database(version = 2,entities = {Player.class,Game.class})
@TypeConverters({Converters.class})
public abstract class FootballDB extends RoomDatabase {


    private static FootballDB instance=null;

    public abstract PlayerDAO playerDAO();
    public abstract GameDAO gameDAO();

    public static synchronized FootballDB getInstance(Context context){

        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),FootballDB.class,"football_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(dbCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback dbCallback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateTask().execute();
            //init
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //new PopulateTask().execute();
        }
    };

    private static class PopulateTask extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            //create players

            Player player1=new Player("Milos",2);
            Player player2=new Player("Lena",1);
            Player player3=new Player("Taylor",2);

            PlayerDAO playerDAO=instance.playerDAO();

            playerDAO.insertPlayer(player1);
            playerDAO.insertPlayer(player2);
            playerDAO.insertPlayer(player3);

            //3 games
            Date timestamp=new Date();

            Game game1=new Game("Milos", "Lena",1,timestamp);
            Game game2=new Game("Milos", "Taylor",1,timestamp);
            Game game3=new Game("Taylor", "Lena",2,timestamp);
            Game game4=new Game("Taylor", "Lena",1,timestamp);
            Game game5=new Game("Milos", "Taylor",2,timestamp);

            GameDAO gameDAO=instance.gameDAO();

            gameDAO.insertGame(game1);
            gameDAO.insertGame(game2);
            gameDAO.insertGame(game3);
            gameDAO.insertGame(game4);
            gameDAO.insertGame(game5);


            return null;
        }
    }
}
