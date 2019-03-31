package com.vms.etf_pocketsoccer.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

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


    public Player(int x, int y, int r, Bitmap image,Paint regular,Paint paintSelected,Paint paintActive) {
        super(x, y, r);
        active=false;
        selected=false;
        this.flag=image;
        myPaint=regular;
        this.paintActive=paintActive;
        this.paintSelected=paintSelected;
        imgX=x-r-20;
        imgY=y-r-20;
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
            x += dx;
            y += dy;
            imgY += dy;
            imgX += dx;

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
                dx *= -1;
            }
            if (x + r >= GamePanel.WIDTH) {
                dx *= -1;
            }

            if (y - r <= 20) {
                dy *= -1;
            }
            if (y + r >= GamePanel.HEIGHT) {
                dy *= -1;
            }

            //check collisions
            for(Player player:GamePanel.player1){
                if(player!=this){
                   if(player.collision(this)){
                       //bolja varijanta
//                       player.setDx(dx);
//                       player.setDy(dy);
//
//                       //sebe promeni?
//                       dx*=-1;
//                       dy*=-1;
                       int new_dx=player.getDx();
                       int new_dy=player.getDy();
                       player.setDx(dx);
                       player.setDy(dy);
                       setDx(new_dx);
                       setDy(new_dy);
                   }
                }
            }

            for(Player player:GamePanel.player2){
                if(player!=this){
                    if(player.collision(this)){
//                        player.setDx(dx/2);
//                        player.setDy(dy/2);
//
//                        //sebe promeni?
//                        dx*=-1;
//                        dy*=-1;
                        int new_dx=player.getDx();
                        int new_dy=player.getDy();
                        player.setDx(dx);
                        player.setDy(dy);
                        setDx(new_dx);
                        setDy(new_dy);
                    }
                }
            }
            //lopta
            if(GamePanel.ball.collision(this)){
                GamePanel.ball.setDx(dx);
                GamePanel.ball.setDy(dy);

                dx*=-1;
                dy*=-1;
            }
        }
    }

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
}
