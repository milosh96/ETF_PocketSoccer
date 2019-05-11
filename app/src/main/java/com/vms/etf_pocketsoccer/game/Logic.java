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
    int frame=0;

    private int turnSelected=0;
    private Player playerSelected;

    //timer
    long startTimer=0;
    long endTimer=0;
    long scoredShowing=0;

    //5 min game
    long gameStart;
    long gameTime;

    //moving pixels
    public static final int LIMIT_1=400;        //400
    public static final int VAL_1=30;
    public static final int LIMIT_2=700;
    public static final int VAL_2=45;
    public static final int LIMIT_3=900;
    public static final int VAL_3=70;
    public static final int VAL_4=100;

    public Logic(GamePanel panel, int speed, int condition, long timeLeft){
        //todo add fields
        this.panel=panel;
        this.speed=speed;

        if(condition==1){
            gameStart=System.currentTimeMillis();
            this.gameTime=timeLeft;
        }
    }

    private void changePlayer(){
        panel.changePlayer();
    }

    public void update(){
        if(start==0){
            start=System.currentTimeMillis();
//            frame=0;
        }
        end=System.currentTimeMillis();

        if(end-start>5000){
            //menjaj igraca nakon 5 sekundi
            start=System.currentTimeMillis();
            changePlayer();
        }
//        if(frame%3==0) {
          checkCollision();
//        }
//        frame++;
        if(startTimer!=0){
            if(end-startTimer>3000){
                Log.d("END_GAME","3 Seconds passed");
                startTimer=0;
                panel.showEnd();
            }
        }
        if(scoredShowing!=0){
            if(end-scoredShowing>2000){
                scoredShowing=0;
                panel.setScored(false);
            }
        }
        if(gameStart!=0){
            if(end-gameStart>=gameTime){
                //zavrsi igru
                this.panel.showEnd();
            }
        }
    }

    public long getPassed(){
        long passed=System.currentTimeMillis()-gameStart;
        return (passed>0) ? passed : 0;
    }

    private void checkCollision(){
        //logika sudaranja
        for(int i=0;i<6;i++){
            Player p1=getPlayer(i);
            for(int j=i+1;j<6;j++){
                Player p2=getPlayer(j);
                //proveri koliziju
                if(p1.collision(p2)){
                    //doslo do sudara
                    Log.d("COLLISION","Sudar pocinje-----");
                    calculateTransfer(p1,p2);
                }
            }
            //lopta
            if(p1.collision(GamePanel.ball)){
                calculateTransfer(p1,GamePanel.ball);
                hit();
            }
        }
    }

    public void scored(int player){
        if(!panel.getScored()) {
            panel.addGoal(player);
            panel.crowd();
            panel.resetField();
        }
    }

    public void hit(){
        if(panel!=null){
            panel.hitSound();
        }
    }

    private void calculateTransfer(GameObject p1, GameObject p2){
        int dx=p1.getDx();
        int dy=p1.getDy();
        int x=p1.getX();
        int y=p1.getY();
        int dx2=p2.getDx();
        int dy2=p2.getDy();
        int x2=p2.getX();
        int y2=p2.getY();

        int diff_x=x-x2;
        int diff_y=y-y2;
        int vel_x=dx2-dx;
        int vel_y=dy2-dy;
        int dotProduct=diff_x*vel_x+diff_y*vel_y;
        if(dotProduct>0) {
            Log.d("COLLISION","Dx: "+dx+" DY:"+dy);
            double collisionAngle =  Math.atan2(diff_y, diff_x);
            double magnitude_1 = Math.sqrt(dx * dx + dy * dy);
            double direction_1 =  Math.atan2(dy, dx);

            double magnitude_2 =  Math.sqrt(dx2 * dx2 + dy2 * dy2);
            double direction_2 = Math.atan2(dy2, dx2);

            double xspeed_1 = magnitude_1 * Math.cos(direction_1 - collisionAngle);
            double yspeed_1 =  (magnitude_1 * Math.sin(direction_1 - collisionAngle));
            double xspeed_2 =  (magnitude_2 * Math.cos(direction_2 - collisionAngle));
            double yspeed_2 =  (magnitude_2 * Math.sin(direction_2 - collisionAngle));

            double speed_x1=((p1.getMass()-p2.getMass())*xspeed_1+(p2.getMass()+p2.getMass())*xspeed_2)/(p1.getMass()+p2.getMass());
            double speed_x2=((p1.getMass()+p1.getMass())*xspeed_1+(p2.getMass()-p1.getMass())*xspeed_2)/(p1.getMass()+p2.getMass());

            double final_x1 = Math.cos(collisionAngle) * speed_x1 + Math.cos(collisionAngle + Math.PI / 2) * yspeed_1;
            double final_y1 =  Math.sin(collisionAngle) * speed_x1 + Math.sin(collisionAngle + Math.PI / 2) * yspeed_1;
            double final_x2 = Math.cos(collisionAngle) * speed_x2 + Math.cos(collisionAngle + Math.PI / 2) * yspeed_2;
            double final_y2 = Math.sin(collisionAngle) * speed_x2 + Math.sin(collisionAngle + Math.PI / 2) * yspeed_2;

            //top speed
            if(final_x1>VAL_4){
                final_x1=VAL_4;
            }
            if(final_x1<-VAL_4){
                final_x1=-VAL_4;
            }
            if(final_x2>VAL_4){
                final_x2=VAL_4;
            }
            if(final_x2<-VAL_4){
                final_x2=-VAL_4;
            }
            if(final_y1>VAL_4){
                final_y1=VAL_4;
            }
            if(final_y1<-VAL_4){
                final_y1=-VAL_4;
            }
            if(final_y2>VAL_4){
                final_y2 =VAL_4;
            }
            if(final_y2<-VAL_4){
                final_y2=-VAL_4;
            }
            p1.setDx((int)final_x1);
            p1.setDy((int)final_y1);
            p2.setDx((int)final_x2);
            p2.setDy((int)final_y2);
            Log.d("COLLISION","Collision finished: Dot product is"+dotProduct+" Dx: "+dx+" Dy:"+dy);
        }
    }

    private Player getPlayer(int index){
        if(index<3){
            return GamePanel.player1.get(index);
        }
        else{
            return GamePanel.player2.get(index-3);
        }
    }

    public void endBegun(){
        startTimer=System.currentTimeMillis();
        endTimer=0;
    }

    public void scoredStarted(){
        if(scoredShowing==0) {
            scoredShowing = System.currentTimeMillis();
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

                if(x_diff<LIMIT_1){
                    Log.d(LOGIC,"Limit is <"+LIMIT_1);
                    x_move*=VAL_1;
                }
                else if(x_diff<LIMIT_2){
                    Log.d(LOGIC,"Limit is <"+LIMIT_2);
                    x_move*=VAL_2;
                }
                else if(x_diff<LIMIT_3){
                    Log.d(LOGIC,"Limit is <"+LIMIT_3);
                    x_move*=VAL_3;
                }
                else{
                    Log.d(LOGIC,"Limit biggest");
                    x_move*=VAL_4;
                }

                if(y_diff<LIMIT_1){
                    Log.d(LOGIC,"Limit Y is <"+LIMIT_1);
                    y_move*=VAL_1;
                }
                else if(y_diff<LIMIT_2){
                    Log.d(LOGIC,"Limit Y is <"+LIMIT_2);
                    y_move*=VAL_2;
                }
                else if(y_diff<LIMIT_3){
                    Log.d(LOGIC,"Limit Y is <"+LIMIT_3);
                    y_move*=VAL_3;
                }
                else{
                    Log.d(LOGIC,"Limit Y biggest");
                    y_move*=VAL_4;
                }

                playerSelected.setDx(x_move);
                playerSelected.setDy(y_move);
                playerSelected.setSelected(false);
                playerSelected=null;
            }
        }
    }
}
