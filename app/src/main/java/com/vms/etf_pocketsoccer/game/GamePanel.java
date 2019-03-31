package com.vms.etf_pocketsoccer.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vms.etf_pocketsoccer.R;

import java.util.ArrayList;
import java.util.List;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    //properites
    private String name1;
    private String name2;
    private Boolean ai1;
    private Boolean ai2;
    private int flag1;
    private int flag2;

    //paints
    Paint paintPlayer;
    Paint paintSelected;
    Paint paintActive;
    Paint paintScore;

    //width and height
    public static int WIDTH=800;
    public static int HEIGHT=480;
    public static final int BALL_SIZE=150;
    private float scaleX;
    private float scaleY;

    //params
    private int speed;
    private int condition;
    private String field;

    //game
    private GameThread gameThread;
    private static int state=0;

    //background ELEMENTS
    private Background bg;
    private Background goals;

    //2 liste od 3 elem
    public static List<Player> player1=new ArrayList<>(3);
    public static List<Player> player2=new ArrayList<>(3);

    //lopta
    public static Ball ball;

    //SCORE
    private int goals_1=0;
    private int goals_2=0;
    private int turn=1;
    private static final int MOVE_TIME=5000;//milis
    private boolean scored=false;
    private static final int SCORE_WIDTH=350;
    private static final int SCORE_HEIGHT=300;

    //Logika
    private Logic logic;

    //END GAME
    private boolean end=false;

    public GamePanel(Context context, String name1, String name2, Boolean ai1,Boolean ai2, int flag1,int flag2, int speed, int condition, String field) {
        super(context);

        getHolder().addCallback(this);

        this.name1=name1;
        this.name2=name2;
        this.ai1=ai1;
        this.ai2=ai2;
        this.flag1=flag1;
        this.flag2=flag2;

        this.speed=speed;
        this.condition=condition;
        this.field=field;

        setFocusable(true);

        gameThread=new GameThread(this,getHolder());
    }

    public Ball getBall() {
        return ball;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //main code

        player1.clear();
        player2.clear();

        //background

        int resourceBG=0;

        switch (field){
            case "grass":{
                resourceBG= R.drawable.field1_hd;
                break;
            }
            case "concrete":{
                resourceBG=R.drawable.field2_hd;
                break;
            }
            case "clay":{
                resourceBG=R.drawable.field3_hd;
                break;
            }
            default:{
                resourceBG=R.drawable.field4_hd;
                break;
            }
        }

        Bitmap imgBG=BitmapFactory.decodeResource(getResources(),resourceBG);

        WIDTH=imgBG.getWidth();
        HEIGHT=imgBG.getHeight();

        bg=new Background(imgBG);
        goals=new Background(BitmapFactory.decodeResource(getResources(),R.drawable.fieldkeeper_hd));

        int resourceFlag1=0;
        switch (this.flag1){
            case 0:{
                resourceFlag1=R.drawable.captain;
                break;
            }
            case 1:{
                resourceFlag1=R.drawable.ironman;
                break;
            }
            case 2:{
                resourceFlag1=R.drawable.logan;
                break;
            }
            case 3:{
                resourceFlag1=R.drawable.spiderman;
                break;
            }
            case 4:{
                resourceFlag1=R.drawable.cyclops;
                break;
            }
            case 5:{
                resourceFlag1=R.drawable.daredevil;
                break;
            }
            default:{
                resourceFlag1=R.drawable.deadpool;
                break;
            }
        }

        int resourceFlag2=0;
        switch (this.flag2){
            case 0:{
                resourceFlag2=R.drawable.captain;
                break;
            }
            case 1:{
                resourceFlag2=R.drawable.ironman;
                break;
            }
            case 2:{
                resourceFlag2=R.drawable.logan;
                break;
            }
            case 3:{
                resourceFlag2=R.drawable.spiderman;
                break;
            }
            case 4:{
                resourceFlag2=R.drawable.cyclops;
                break;
            }
            case 5:{
                resourceFlag2=R.drawable.daredevil;
                break;
            }
            default:{
                resourceFlag2=R.drawable.deadpool;
                break;
            }
        }


        //player elements

        int x_one=WIDTH/15;
        int y_one=HEIGHT/10;

        paintPlayer=new Paint();
        paintPlayer.setColor(getResources().getColor(R.color.colorWhite));
        //score font
        paintPlayer.setTextSize(120);
        paintScore=new Paint();
        paintScore.setColor(getResources().getColor(R.color.colorDarkLight));
        paintSelected=new Paint();
        paintSelected.setColor(getResources().getColor(R.color.colorGreyDark));
        paintActive=new Paint();
        paintActive.setColor(getResources().getColor(R.color.colorAccent));



        Bitmap imgFlag1=BitmapFactory.decodeResource(getResources(),resourceFlag1);
        Bitmap img1=Bitmap.createScaledBitmap(imgFlag1,280,280,false);
        Bitmap imgFlag2=BitmapFactory.decodeResource(getResources(),resourceFlag2);
        Bitmap img2=Bitmap.createScaledBitmap(imgFlag2,280,280,false);

        player1.add(new Player(x_one*2,y_one*5,120,img1,paintPlayer,paintSelected,paintActive));
        player1.add(new Player(x_one*4,y_one*3,120,img1,paintPlayer,paintSelected,paintActive));
        player1.add(new Player(x_one*4,y_one*7,120,img1,paintPlayer,paintSelected,paintActive));

        //moglo je lepse
        for(Player player:player1){
            player.setActive(true);
        }

        player2.add(new Player(x_one*13,y_one*5,120,img2,paintPlayer,paintSelected,paintActive));
        player2.add(new Player(x_one*11,y_one*3,120,img2,paintPlayer,paintSelected,paintActive));
        player2.add(new Player(x_one*11,y_one*7,120,img2,paintPlayer,paintSelected,paintActive));

        Bitmap ballOriginal=BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        Bitmap ballImg=Bitmap.createScaledBitmap(ballOriginal,BALL_SIZE,BALL_SIZE,false);
        ball=new Ball(WIDTH/2-BALL_SIZE/2,HEIGHT/2-BALL_SIZE/2,0,ballImg);


        logic=new Logic(this,this.speed);

        //thread
        gameThread.setRun(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //empty
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRun(false);
        boolean retry=true;
        while (retry) {
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry=false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        final float scaleX=getWidth()/(WIDTH*1.f);
        final float scaleY=getHeight()/(HEIGHT*1.f);
        switch (action){
            case MotionEvent.ACTION_DOWN:{
                float x=event.getX()/scaleX;
                float y=event.getY()/scaleY;
                Log.d("POINT","X: "+x+". Y: "+y);
                if(turn==1){
                    for(Player player:player1){
                        if(player.pointInside(x,y)){
                            //sacuvaj igraca
                            logic.addSelected(1,player);
                            break;
                        }
                    }
                }
                else{
                    for(Player player:player2){
                        if(player.pointInside(x,y)){
                            logic.addSelected(2,player);
                            break;
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                //kako proveriti za vreme
                float x=event.getX()/scaleX;
                float y=event.getY()/scaleY;
                Log.d("POINT_UP","X: "+x+". Y: "+y);

                logic.moveSelected(turn,x,y);
                break;
            }
        }
        return true;
        //return super.onTouchEvent(event);
    }


    //update from thraed
    public void update(){
        //bg.update();
        //goals.update();

        logic.update();

        for(Player player:player1) {
            player.update();
        }
        for(Player player:player2){
            player.update();
        }

        ball.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //posto je slika prevelika,za telefon barem
        //zelimo da povecamo canvas


     final float scaleX=getWidth()/(WIDTH*1.f);
     final float scaleY=getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int state=canvas.save();

            canvas.scale(scaleX,scaleY);

            bg.draw(canvas);

            for(Player player:player1) {
                player.draw(canvas);
            }
            for(Player player:player2){
                player.draw(canvas);
            }

            ball.draw(canvas);
            goals.draw(canvas);

            if(scored){
                //rezultat
                drawScore(canvas);
            }

            //we return otherwise we scale everytime we call
            canvas.restoreToCount(state);
        }
    }

    private void drawScore(Canvas canvas){
        canvas.drawRect(0,HEIGHT/2-SCORE_HEIGHT,WIDTH,HEIGHT/2+SCORE_HEIGHT,paintScore);
        canvas.drawRect(SCORE_WIDTH,HEIGHT/2-SCORE_HEIGHT,WIDTH-SCORE_WIDTH,HEIGHT/2+SCORE_HEIGHT,paintActive);
        canvas.drawText(goals_1+" - "+goals_2,WIDTH/2-120,HEIGHT/2,paintPlayer);

        //check if end
        //TODO
        if(end){
            //cekaj 5 sekundi i pokreni aktivnost sa prikazom rezultata
        }
        else{
            //cekaj 3 sekunde i set scored=false;
        }

    }

    public void changePlayer(){
        if(turn==1){
            turn=2;
            for(Player player:player1){
                player.setActive(false);
                player.setSelected(false);
            }
            for(Player player:player2){
                player.setActive(true);
            }
        }
        else{
            turn=1;
            for(Player player:player1){
                player.setActive(true);
            }
            for(Player player:player2){
                player.setActive(false);
                player.setSelected(false);
            }
        }
    }
}
