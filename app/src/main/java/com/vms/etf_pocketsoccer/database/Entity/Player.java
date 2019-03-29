package com.vms.etf_pocketsoccer.database.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "player_table")
public class Player {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String name;

    private int wins;

    public Player(String name, int wins) {
        this.name = name;
        this.wins = wins;
    }

    public Player(){this.wins=0;}

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
