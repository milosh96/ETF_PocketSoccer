package com.vms.etf_pocketsoccer.database.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "game_table")
public class Game {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name_1;

    private String name_2;

    //==1 name_1
    //==2 name_2
    private int victor;

    private Date timestamp;

    public Game(){}

    public Game(String name_1, String name_2, int victor, Date timestamp) {
        this.name_1 = name_1;
        this.name_2 = name_2;
        this.victor = victor;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getName_1() {
        return name_1;
    }

    public String getName_2() {
        return name_2;
    }

    public int getVictor() {
        return victor;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName_1(String name_1) {
        this.name_1 = name_1;
    }

    public void setName_2(String name_2) {
        this.name_2 = name_2;
    }

    public void setVictor(int victor) {
        this.victor = victor;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
