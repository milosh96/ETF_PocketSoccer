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
