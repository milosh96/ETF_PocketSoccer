package com.vms.etf_pocketsoccer.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball extends GameObject {


    private Bitmap ball;
    private Paint myPaint;
    private Logic logic;

    private int imgX;
    private int imgY;
    private int speed;
    private int diff_sp;

    public Ball(int x, int y, int r, Bitmap image, Logic logic, int speed) {
        super(x, y, r);

        this.ball=image;
        imgX=x;
        imgY=y;
        this.x=x+GamePanel.BALL_SIZE/2;
        this.y=y+GamePanel.BALL_SIZE/2;
        this.logic=logic;
        this.speed=speed;
        this.diff_sp=(speed+2)/3;
        if(diff_sp<1){
            diff_sp=1;
        }
    }

    @Override
    public int getMass() {
        return 1;
    }

    public void update(){
        //podesavanje kretanja
        if(dx!=0||dy!=0) {
            x += dx*diff_sp;
            y += dy*diff_sp;
            imgY += dy*diff_sp;
            imgX += dx*diff_sp;

            //provera za granice?

            if (dx > 0) {
                dx -= 1;
                if (dx <= 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += 1;
                if (dx >= 0) {
                    dx = 0;
                }
            }
            if (dy > 0) {
                dy -= 1;
                if (dy < 0) {
                    dy = 0;
                }
            } else if (dy < 0) {
                dy += 1;
                if (dy > 0) {
                    dy = 0;
                }
            }

            //sirina i visina

            if (x - r <= 20) {
                x=20+r;
                imgX=x-r-20;
                dx *= -1;
            }
            if (x + r >= GamePanel.WIDTH) {
                x=GamePanel.WIDTH-r;
                imgX=x-r-20;
                dx *= -1;
            }

            if (y - r <= 20) {
                y=r;
                imgY=y-r-20;
                dy *= -1;
            }
            if (y + r >= GamePanel.HEIGHT) {
                y=GamePanel.HEIGHT-r;
                imgY=y-r-20;
                dy *= -1;
            }

            //GOOOl
            //odbijanje
            if((y+r>=GamePanel.y_one*5-200-10)&&(y+r<=GamePanel.y_one*5-200)&&((x-r<=180)||(x+r>=GamePanel.WIDTH-180))){
                dy*=-1;
                y-=10;
                imgY=y;
            }
            if((y-r<=GamePanel.y_one*5+200+10)&&(y-r>=GamePanel.y_one*5+200)&&((x-r<=180)||(x+r>=GamePanel.WIDTH-180))){
                dy*=-1;
                y+=10;
                imgY=y;
            }
            if((y-r>=GamePanel.y_one*5-200)&&(y+r<=GamePanel.y_one*5+200)&&(x-r<=180)){
                logic.scored(2);
            }
            if((y-r>=GamePanel.y_one*5-200)&&(y+r<=GamePanel.y_one*5+200)&&(x+r>=GamePanel.WIDTH-180)){
                logic.scored(1);
            }

        }
        else{
            //dx==0 i dy==0
            if(x+r>GamePanel.WIDTH){
                x=GamePanel.WIDTH-r;
                imgX=x-r-20;
            }
            if(x-r<=20){
                x=20+r;
                imgX=x-r-20;
            }
            if(y+r>GamePanel.HEIGHT){
                y=GamePanel.HEIGHT-r;
                imgY=y-r-20;
            }
            if(y-r<20){
                y=r;
                imgY=y-r-20;
            }
        }
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        imgX=x;
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        imgY=y;
    }

    public void draw(Canvas canvas){
        //crtanje kruga;

        //canvas.drawCircle(super.getX(),super.getY(),super.getR(),myPaint);

        canvas.drawBitmap(this.ball,imgX,imgY,null);
    }
}
