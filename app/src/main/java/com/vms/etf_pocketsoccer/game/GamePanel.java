package com.vms.etf_pocketsoccer.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import com.vms.etf_pocketsoccer.PlayerInfoActivity;
import com.vms.etf_pocketsoccer.R;
import com.vms.etf_pocketsoccer.database.Entity.Game;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
    public static int x_one;
    public static int y_one;

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
    private AI_Player ai_player1;
    private AI_Player ai_player2;

    //END GAME
    private boolean end=false;
    private Context context;
    private int victor=0;
    private boolean showed=false;

    //Preferences
    private SharedPreferences preferences;
    private static final String PREF="Continue";
    private boolean pref=false;

    //sound
    private MediaPlayer crowdSound;
    private MediaPlayer hitSound;

    public GamePanel(Context context, String name1, String name2, Boolean ai1,Boolean ai2, int flag1,int flag2, int speed, int condition, String field,Boolean pref) {
        super(context);


        getHolder().addCallback(this);

        this.context=context;
        preferences=context.getSharedPreferences(PREF,MODE_PRIVATE);
        this.pref=pref;

        if(pref){
            readFromPreferences();
        }
        else {
            this.name1 = name1;
            this.name2 = name2;
            this.ai1 = ai1;
            this.ai2 = ai2;
            this.flag1 = flag1;
            this.flag2 = flag2;
        }


        this.speed = speed;
        this.condition = condition;
        this.field = field;

        crowdSound = MediaPlayer.create(context, R.raw.crowd);
        hitSound = MediaPlayer.create(context, R.raw.ball_collision);

        setFocusable(true);

        gameThread=new GameThread(this,getHolder());
    }

    private void readFromPreferences(){
        //citanje vrednosti iz preferenca
        this.name1=preferences.getString("name_1","Taylor");
        this.name2=preferences.getString("name_2","Taylor");
        this.flag1=preferences.getInt("flag_1",0);
        this.flag2=preferences.getInt("flag_2",0);
        this.ai1=preferences.getBoolean("ai_1",false);
        this.ai2=preferences.getBoolean("ai_2",false);
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

        x_one=WIDTH/15;
        y_one=HEIGHT/10;

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

        //TODO dodati if(!pref)
        if(!pref) {
            player1.add(new Player(x_one * 2, y_one * 5, 120, img1, paintPlayer, paintSelected, paintActive,speed));
            player1.add(new Player(x_one * 4, y_one * 3, 120, img1, paintPlayer, paintSelected, paintActive,speed));
            player1.add(new Player(x_one * 4, y_one * 7, 120, img1, paintPlayer, paintSelected, paintActive,speed));
        }
        else{
            int x1=preferences.getInt("player10_x",x_one * 2);
            int y1=preferences.getInt("player10_y",y_one * 5);
            int x2=preferences.getInt("player11_x",x_one * 4);
            int y2=preferences.getInt("player11_y",y_one * 3);
            int x3=preferences.getInt("player12_x",x_one * 4);
            int y3=preferences.getInt("player12_y",y_one * 7);

            player1.add(new Player(x1, y1, 120, img1, paintPlayer, paintSelected, paintActive,speed));
            player1.add(new Player(x2, y2, 120, img1, paintPlayer, paintSelected, paintActive,speed));
            player1.add(new Player(x3, y3, 120, img1, paintPlayer, paintSelected, paintActive,speed));
        }

        //moglo je lepse
        for(Player player:player1){
            player.setActive(true);
        }

        //isto za pref
        if(!pref) {
            player2.add(new Player(x_one * 13, y_one * 5, 120, img2, paintPlayer, paintSelected, paintActive,speed));
            player2.add(new Player(x_one * 11, y_one * 3, 120, img2, paintPlayer, paintSelected, paintActive,speed));
            player2.add(new Player(x_one * 11, y_one * 7, 120, img2, paintPlayer, paintSelected, paintActive,speed));
        }
        else {
            int x1=preferences.getInt("player20_x",x_one * 13);
            int y1=preferences.getInt("player20_y",y_one * 5);
            int x2=preferences.getInt("player21_x",x_one * 11);
            int y2=preferences.getInt("player21_y",y_one * 3);
            int x3=preferences.getInt("player22_x",x_one * 11);
            int y3=preferences.getInt("player22_y",y_one * 7);

            player2.add(new Player(x1, y1, 120, img2, paintPlayer, paintSelected, paintActive,speed));
            player2.add(new Player(x2, y2, 120, img2, paintPlayer, paintSelected, paintActive,speed));
            player2.add(new Player(x3, y3, 120, img2, paintPlayer, paintSelected, paintActive,speed));
        }

        if(!pref) {
            logic = new Logic(this, this.speed, this.condition,60000 );
        }
        else{
            //sacuvamo vreme koje je ostalo
            long passed=preferences.getLong("time_passed",0);
            logic = new Logic(this, this.speed, this.condition,60000-passed );
        }

        Bitmap ballOriginal=BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        Bitmap ballImg=Bitmap.createScaledBitmap(ballOriginal,BALL_SIZE,BALL_SIZE,false);
        //isto za pref
        if(!pref) {
            ball = new Ball(WIDTH / 2 - BALL_SIZE / 2, HEIGHT / 2 - BALL_SIZE / 2, BALL_SIZE / 2, ballImg,logic,speed);
        }
        else{
            int x=preferences.getInt("ball_x",WIDTH / 2 - BALL_SIZE / 2);
            int y=preferences.getInt("ball_y",HEIGHT / 2 - BALL_SIZE / 2);
            ball = new Ball(x, y, BALL_SIZE / 2, ballImg,logic,speed);
        }

        if(ai1){
            ai_player1=new AI_Player(1,this);
            ai_player1.play();
        }
        if(ai2){
            ai_player2=new AI_Player(2,this);
        }
        if(pref){
            goals_1=preferences.getInt("goals_1",0);
            goals_2=preferences.getInt("goals_2",0);
        }

        //thread
        gameThread.setRun(true);
        gameThread.start();
    }

    public void resetField(){

        Player player=player1.get(0);

        player.setX(x_one*2);
        player.setY(y_one*5);
        player.setDx(0);
        player.setDy(0);
        player=player1.get(1);
        player.setX(x_one*4);
        player.setY(y_one*3);
        player.setDx(0);
        player.setDy(0);
        player=player1.get(2);
        player.setX(x_one*4);
        player.setY(y_one*7);
        player.setDx(0);
        player.setDy(0);

        player=player2.get(0);
        player.setX(x_one*13);
        player.setY(y_one*5);
        player.setDx(0);
        player.setDy(0);
        player=player2.get(1);
        player.setX(x_one*11);
        player.setY(y_one*3);
        player.setDx(0);
        player.setDy(0);
        player=player2.get(2);
        player.setX(x_one*11);
        player.setY(y_one*7);
        player.setDx(0);
        player.setDy(0);

        ball.setDy(0);
        ball.setDx(0);
        ball.setX(WIDTH/2-BALL_SIZE/2);
        ball.setY(HEIGHT/2-BALL_SIZE/2);
        logic.timeReset();

        if(ai_player1!=null){
            ai_player1.play();
        }
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
        Log.d("END_GAME","Surface destroyed");
        //cuvanje u preferencama, stanje partije
        /***
         * name1,name2 x
         * flag1,flag2 x
         * ai1,ai2 x
         * position: players,ball x
         * svi dx=0 i dy=0
         */
        if(!showed) {
            SharedPreferences.Editor editor = preferences.edit();

            editor.putBoolean("continue", true);
            editor.putString("name_1", name1);
            editor.putString("name_2", name2);
            editor.putInt("flag_1", flag1);
            editor.putInt("flag_2", flag2);
            editor.putBoolean("ai_1", ai1);
            editor.putBoolean("ai_2", ai2);
            editor.putInt("ball_x", ball.getX());
            editor.putInt("ball_y", ball.getY());

            //players1
            editor.putInt("player10_x", player1.get(0).getX());
            editor.putInt("player10_y", player1.get(0).getY());
            editor.putInt("player11_x", player1.get(1).getX());
            editor.putInt("player11_y", player1.get(1).getY());
            editor.putInt("player12_x", player1.get(2).getX());
            editor.putInt("player12_y", player1.get(2).getY());

            //players2
            editor.putInt("player20_x", player2.get(0).getX());
            editor.putInt("player20_y", player2.get(0).getY());
            editor.putInt("player21_x", player2.get(1).getX());
            editor.putInt("player21_y", player2.get(1).getY());
            editor.putInt("player22_x", player2.get(2).getX());
            editor.putInt("player22_y", player2.get(2).getY());
            editor.putLong("time_passed", logic.getPassed());
            editor.putInt("goals_1", goals_1);
            editor.putInt("goals_2", goals_2);

            editor.commit();
        }
        ai_player1=null;
        ai_player2=null;
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
                if((turn==1)&&(ai_player1==null)){
                    for(Player player:player1){
                        if(player.pointInside(x,y)){
                            //sacuvaj igraca
                            logic.addSelected(1,player);
                            break;
                        }
                    }
                }
                else if((turn==2)&&(ai_player2==null)){
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
                if(((turn==1)&&(ai_player1==null))) {
                    logic.moveSelected(turn, x, y);
                    changePlayer();
                }
                else if((turn==2)&&(ai_player2==null)){
                    logic.moveSelected(turn, x, y);
                    changePlayer();
                }
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

        for(Player player:player1) {
            player.update();
        }
        for(Player player:player2){
            player.update();
        }
        ball.update();
        logic.update();
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
            if(end){
                Log.d("END_GAME", "End begun");
                logic.endBegun();
                end=false;
            }
            canvas.restoreToCount(state);
        }
    }

    public void showEnd(){
        Log.d("END_GAME","End started showing");
        if(!showed) {
            if(goals_1>goals_2){
                victor=1;
            }
            else if(goals_2>goals_1){
                victor=2;
            }
            preferences=context.getSharedPreferences(PREF,MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("continue",false);
            edit.commit();
            Intent intent = new Intent(context, PlayerInfoActivity.class);
            intent.putExtra("PLAYER_NAME1", name1);
            intent.putExtra("PLAYER_NAME2", name2);
            intent.putExtra("DB", 1);
            intent.putExtra("VICTOR", victor);
            showed = true;
            this.context.startActivity(intent);
        }
    }

    private void drawScore(Canvas canvas){
        canvas.drawRect(0,HEIGHT/2-SCORE_HEIGHT,WIDTH,HEIGHT/2+SCORE_HEIGHT,paintScore);
        canvas.drawRect(SCORE_WIDTH,HEIGHT/2-SCORE_HEIGHT,WIDTH-SCORE_WIDTH,HEIGHT/2+SCORE_HEIGHT,paintActive);
        canvas.drawText(goals_1+" - "+goals_2,WIDTH/2-120,HEIGHT/2,paintPlayer);

        //check if end
        //TODO

        logic.scoredStarted();
    }

    //SCORE
    public void setScored(boolean value){
        this.scored=value;
    }
    public void addGoal(int player){
        if(player==1){
            goals_1++;
            setScored(true);
            if(this.condition==2){
                if(goals_1>=5){
                    victor=1;
                    logic.endBegun();
                }
            }
        }
        else if(player==2){
            goals_2++;
            setScored(true);
            if(this.condition==2){
                if(goals_2>=5){
                    victor=2;
                    logic.endBegun();
                }
            }
        }
    }

    public void changePlayer(){
        logic.timeReset();
        if(turn==1){
            turn=2;
            for(Player player:player1){
                player.setActive(false);
                player.setSelected(false);
            }
            for(Player player:player2){
                player.setActive(true);
            }
            if(ai_player2!=null){
                ai_player2.play();
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
            if(ai_player1!=null){
                ai_player1.play();
            }
        }

    }
    public boolean getScored(){
        return scored;
    }

    public void crowd(){
        crowdSound.start();
    }

    public void hitSound(){
        hitSound.start();
    }
}
