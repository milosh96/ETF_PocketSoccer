package com.vms.etf_pocketsoccer.database.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.vms.etf_pocketsoccer.database.Entity.Game;

import java.util.List;

@Dao
public interface GameDAO {

    @Insert
    void insertGame(Game game);

    @Query("SELECT * FROM game_table")
    List<Game> getAllGames();

    @Query("SELECT * FROM game_table WHERE name_1 LIKE :name OR name_2 LIKE :name")
    List<Game> getGamesForPlayer(String name);

    @Query("SELECT * FROM game_table WHERE (name_1 LIKE :name1 AND name_2 LIKE :name2) OR (name_1 LIKE :name2 AND name_2 LIKE :name1)")
    List<Game> getGamesForPlayers(String name1, String name2);

    @Query("DELETE FROM game_table")
    void deleteAll();

    @Query("DELETE FROM game_table WHERE name_1 LIKE :name OR name_2 LIKE :name")
    void deleteForPlayer(String name);

    @Query("DELETE FROM game_table WHERE (name_1 LIKE :name1 AND name_2 LIKE :name2) OR (name_1 LIKE :name2 AND name_2 LIKE :name1)")
    void deleteGamesForPlayers(String name1, String name2);
}
