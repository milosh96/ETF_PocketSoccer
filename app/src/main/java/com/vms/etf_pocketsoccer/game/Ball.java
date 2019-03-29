package com.vms.etf_pocketsoccer.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball extends GameObject {


    private Bitmap ball;
    private Paint myPaint;

    private int imgX;
    private int imgY;

    public Ball(int x, int y, int r, Bitmap image) {
        super(x, y, r);

        this.ball=image;
        imgX=x;
        imgY=y;
    }


    public void update(){
        //podesavanje kretanja
    }

    public void draw(Canvas canvas){
        //crtanje kruga;

        //canvas.drawCircle(super.getX(),super.getY(),super.getR(),myPaint);

        canvas.drawBitmap(this.ball,imgX,imgY,null);
    }
}
