package com.vms.etf_pocketsoccer.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.vms.etf_pocketsoccer.R;

public class Player extends GameObject {

    //dodatna polja
    private boolean active;
    private boolean selected;

    private Bitmap flag;
    private Paint myPaint;
    private Paint paintSelected;
    private Paint paintActive;

    private int imgX;
    private int imgY;
    private boolean changed=false;
    private int speed;
    private int diff_sp;

    public Player(int x, int y, int r, Bitmap image,Paint regular,Paint paintSelected,Paint paintActive, int speed) {
        super(x, y, r);
        active=false;
        selected=false;
        this.flag=image;
        myPaint=regular;
        this.paintActive=paintActive;
        this.paintSelected=paintSelected;
        imgX=x-r-20;
        imgY=y-r-20;
        this.speed=speed;
        this.diff_sp=(speed+2)/3;
        if(diff_sp<1){
            diff_sp=1;
        }
    }

    public boolean isActive() {
        return active;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

            //golovi
            /**
             * sirina 120
             * visina 20
             *
             * y/one*5 -200 i +200
             */
//            if(x-r<=180){
//                //dx=0; DOBRO
//            }
//            if(x+r>=GamePanel.WIDTH-180){
//                //dx=0; DOBRO
//            }
//            if((y+r>=GamePanel.y_one*5-200)&&(y-r<=GamePanel.y_one*5-200+10)&&((x-r<=180)||(x+r>=GamePanel.WIDTH-180))){
//                dy=0;
//            }
//            if((y+r>=GamePanel.y_one*5+200)&&(y-r<=GamePanel.y_one*5+200+10)&&((x-r<=180)||(x+r>=GamePanel.WIDTH-180))){
//                dy=0;
//            }
            if((y+r>=GamePanel.y_one*5-200-10)&&(y+r<=GamePanel.y_one*5-200)&&((x-r<=180)||(x+r>=GamePanel.WIDTH-180))){
                dy*=-1;
                y-=10;
                imgY=y-r-20;
            }
            if((y-r<=GamePanel.y_one*5+200+10)&&(y-r>=GamePanel.y_one*5+200)&&((x-r<=180)||(x+r>=GamePanel.WIDTH-180))){
                dy*=-1;
                y+=10;
                imgY=y-r-20;
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

            if((y+r>=GamePanel.y_one*5-200-10)&&(y+r<=GamePanel.y_one*5-200)&&((x-r<=180)||(x+r>=GamePanel.WIDTH-180))){
                y-=10;
                imgY=y-r-20;
            }
            if((y-r<=GamePanel.y_one*5+200+10)&&(y-r>=GamePanel.y_one*5+200)&&((x-r<=180)||(x+r>=GamePanel.WIDTH-180))){
                y+=10;
                imgY=y-r-20;
            }

        }


    }

    @Override
    public int getMass() {
        return 3;
    }

//    private void calculateTransfer(GameObject otherObject){
//        int dx2=otherObject.getDx();
//        int dy2=otherObject.getDy();
//        int x2=otherObject.getX();
//        int y2=otherObject.getY();
//
//        int diff_x=x-x2;
//        int diff_y=y-y2;
//        int vel_x=dx2-dx;
//        int vel_y=dy2-dy;
//        int dotProduct=diff_x*vel_x+diff_y*vel_y;
//        if(dotProduct>0) {
//            Log.d("COLLISION","Dx: "+dx+" DY:"+dy);
//            double collisionAngle =  Math.atan2(diff_y, diff_x);
//            double magnitude_1 = Math.sqrt(dx * dx + dy * dy);
//            double direction_1 =  Math.atan2(dy, dx);
//
//            double magnitude_2 =  Math.sqrt(dx2 * dx2 + dy2 * dy2);
//            double direction_2 = Math.atan2(dy2, dx2);
//
//            double xspeed_1 = magnitude_1 * Math.cos(direction_1 - collisionAngle);
//            double yspeed_1 =  (magnitude_1 * Math.sin(direction_1 - collisionAngle));
//            double xspeed_2 =  (magnitude_2 * Math.cos(direction_2 - collisionAngle));
//            double yspeed_2 =  (magnitude_2 * Math.sin(direction_2 - collisionAngle));
//            double final_x1 = (Math.cos(collisionAngle) * xspeed_1 + Math.cos(collisionAngle + Math.PI / 2) * yspeed_1);
//            double final_y1 =  (Math.sin(collisionAngle) * xspeed_1 + Math.cos(collisionAngle + Math.PI / 2) * yspeed_1);
//            double final_x2 = (Math.cos(collisionAngle) * xspeed_2 + Math.cos(collisionAngle + Math.PI / 2) * yspeed_2);
//            double final_y2 = (Math.sin(collisionAngle) * xspeed_2 + Math.cos(collisionAngle + Math.PI / 2) * yspeed_2);
//            setDx((int)final_x2);
//            setDy((int)final_y1);
//            otherObject.setDx((int)final_x1);
//            otherObject.setDy((int)final_y2);
//            Log.d("COLLISION","Collision detected: Dot product is"+dotProduct+" Dx: "+dx+" Dy:"+dy);
//        }
//    }

    public void draw(Canvas canvas){
        //crtanje kruga;

        //provere da li je active ili selected
        if(active) {
            canvas.drawCircle(super.getX(), super.getY(), super.getR() + 15, paintActive);
        }
        if(selected){
            canvas.drawCircle(super.getX(), super.getY(), super.getR() + 60, paintSelected);
        }

        canvas.drawCircle(super.getX(),super.getY(),super.getR(),myPaint);

        canvas.drawBitmap(this.flag,imgX,imgY,null);
    }

    public boolean pointInside(float x, float y){

        int x_dist=(int)Math.pow(x-super.getX(),2);
        int y_dist=(int)Math.pow(y-super.getY(),2);

        if(Math.sqrt(x_dist+y_dist)>super.getR()){
            return false;
        }

        setSelected(true);
        return true;
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        imgX=x-r-20;
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        imgY=y-r-20;
    }
}
