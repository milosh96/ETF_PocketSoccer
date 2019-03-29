package com.vms.etf_pocketsoccer.game;

public class Logic {

    private GamePanel panel;
    //polja za sudare
    /**
     * okvir
     * golovi
     * lopta
     * igraci
     */

    long start=0;
    long end=0;

    public Logic(GamePanel panel){
        //todo add fields
        this.panel=panel;
    }

    private void changePlayer(){
        panel.changePlayer();
    }

    public void update(){
        if(start==0){
            start=System.currentTimeMillis();
        }
        end=System.currentTimeMillis();

        if(end-start>5000){
            //menjaj igraca nakon 5 sekundi
            start=System.currentTimeMillis();
            changePlayer();
        }
    }

    public void timeReset(){
        start=System.currentTimeMillis();
    }
}
