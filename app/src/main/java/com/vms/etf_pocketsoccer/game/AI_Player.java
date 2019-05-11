package com.vms.etf_pocketsoccer.game;

import android.util.Log;

import com.vms.etf_pocketsoccer.database.Entity.Game;

import java.util.Random;

public class AI_Player {

    private Random rand;
    private int turn;
    private GamePanel panel;

    public static final int MAX_X=100;
    public static final int MAX_Y=100;

    public AI_Player(int turn, GamePanel panel){
        rand=new Random();
        this.turn=turn;
        this.panel=panel;
    }

    public void play(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                calculateMove();
            }
        }).start();
    }

    private void calculateMove(){
        int index=rand.nextInt(3);
        Player player=null;
        if(turn==1){
            player=GamePanel.player1.get(index);
        }
        else{
            player=GamePanel.player2.get(index);
        }
        //distance
        int dx=GamePanel.ball.getX()-player.getX();
        int dy=GamePanel.ball.getY()-player.getY();
        //todo conitnue

        Log.d("AI_PLAYER","X is " +player.getDx()+" Y is "+player.getDy());
        Log.d("AI_PLAYER","Dx is "+dx+" Dy is "+dy);

        if(dx>MAX_X){
            dx=MAX_X;
        }
        if(dy>MAX_Y){
            dy=MAX_Y;
        }
        if(dx<-MAX_X){
            dx=-MAX_X;
        }
        if(dy<-MAX_Y){
            dy=-MAX_Y;
        }

        Log.d("AI_PLAYER","LIMITS---Dx is "+dx+" Dy is "+dy);
        player.setDy(dy);
        player.setDx(dx);

        panel.changePlayer();
    }
}
