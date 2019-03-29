package com.vms.etf_pocketsoccer.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x,y;

    public Background(Bitmap img){
        this.image=img;
        x=0;
        y=0;
    }

    public void update(){
        //empty no update for background
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }
}
