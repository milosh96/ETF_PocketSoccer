package com.vms.etf_pocketsoccer.database.Dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.vms.etf_pocketsoccer.database.Entity.Player;

import java.util.List;

@Dao
public interface PlayerDAO {

    @Insert
    void insertPlayer(Player player);

    @Query("SELECT * FROM player_table")
    List<Player> getAll();

    @Query("SELECT * FROM player_table WHERE name LIKE :name")
    Player findName(String name);

    @Query("UPDATE player_table SET wins=wins-:number WHERE name LIKE :name")
    void resetWins(String name, int number);

    @Query("UPDATE player_table SET wins=0")
    void resetAll();
}
