package com.vms.etf_pocketsoccer.game;

import android.util.Log;

public class Logic {

    private GamePanel panel;
    //polja za sudare
    /**
     * okvir
     * golovi
     * lopta
     * igraci
     */

    private int speed;
    public static final String LOGIC="LOGIC";

    long start=0;
    long end=0;

    private int turnSelected=0;
    private Player playerSelected;

    //timer
    long startTimer=0;
    long endTimer=0;

    public Logic(GamePanel panel, int speed){
        //todo add fields
        this.panel=panel;
        this.speed=speed;
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

    public void addSelected(int turn, Player player){
        this.turnSelected=turn;
        this.playerSelected=player;
    }

    public void moveSelected(int turn, float x, float y){
        if(turn==turnSelected){
            //pomeri igraca

            if(playerSelected!=null){
                //trebalo bi uvek

                int x_selected=playerSelected.getX();
                int y_selected=playerSelected.getY();

                int x_diff=(int)Math.abs(x-x_selected);
                int y_diff=(int)Math.abs(y-y_selected);

                //1 ako je x novo vece od trenutnog
                //-1 ako je obrnuto
                int x_sign=(int)Math.signum(x-x_selected);
                int y_sign=(int)Math.signum(y-y_selected);

                int x_move=x_sign;
                int y_move=y_sign;

                if(x_diff<200){
                    Log.d(LOGIC,"X is 10");
                    x_move*=20;
                }
                else if(x_diff<400){
                    Log.d(LOGIC,"X is 20");
                    x_move*=40;
                }
                else if(x_diff<500){
                    Log.d(LOGIC,"X is 25");
                    x_move*=50;
                }
                else{
                    Log.d(LOGIC,"X is 50");
                    x_move*=80;
                }

                if(y_diff<200){
                    Log.d(LOGIC,"Y is 10");
                    y_move*=20;
                }
                else if(y_diff<400){
                    Log.d(LOGIC,"Y is 20");
                    y_move*=40;
                }
                else if(y_diff<500){
                    Log.d(LOGIC,"Y is 25");
                    y_move*=50;
                }
                else{
                    Log.d(LOGIC,"Y is 50");
                    y_move*=80;
                }

                playerSelected.setDx(x_move);
                playerSelected.setDy(y_move);
                playerSelected.setSelected(false);
            }
        }
    }
}
