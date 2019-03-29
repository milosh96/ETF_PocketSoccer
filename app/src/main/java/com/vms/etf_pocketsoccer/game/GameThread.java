package com.vms.etf_pocketsoccer.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.concurrent.TimeUnit;

public class GameThread extends Thread {

    private int FPS=30;
    private boolean running=false;

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private static Canvas canvas;

    public GameThread(GamePanel gamePanel,SurfaceHolder surfaceHolder){
        super();
        this.surfaceHolder=surfaceHolder;
        this.gamePanel=gamePanel;
    }

    @Override
    public void run() {
        long startTime=0;
        long totalTimeMili=0;
        long endTime=0;
        long waitTimeMili=0;

        long targetTimeMili=1000/FPS;


        while (running){
            startTime=System.nanoTime();
            canvas=null;

            try{
                canvas=this.surfaceHolder.lockCanvas();
                if(canvas!=null) {
                    synchronized (canvas) {
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);
                    }
                }
            }
            finally {
                if(canvas!=null){
                    try {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e){e.printStackTrace();}
                }
            }
            endTime=System.nanoTime();

            //time in miliseconds it took to update and draw
            //totalTimeMili=(endTime-startTime)/1 000 000;

            totalTimeMili=TimeUnit.NANOSECONDS.toMillis(endTime-startTime);

            waitTimeMili=targetTimeMili-totalTimeMili;

            try {
                Log.d("THREAD","Wait time is: " + waitTimeMili);
                if(waitTimeMili>0) {
                    Thread.sleep(waitTimeMili);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void setRun(boolean run){
        this.running=run;
    }
}
