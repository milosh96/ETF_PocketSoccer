package com.vms.etf_pocketsoccer.game;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int r;

    protected int dx;
    protected int dy;

    public GameObject(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setR(int r) {
        this.r = r;
    }

    public boolean collision(GameObject otherObj){
        double distance=centerDistance(this.x,this.y,otherObj.getX(),otherObj.getY());

        if(distance<= this.r+otherObj.getR()){
            return true;
        }
        else {
            return false;
        }
    }

    public double centerDistance(int x1, int y1,int x2, int y2){
        int xSqr=(int)Math.pow(x1-x2,2);
        int ySqr=(int)Math.pow(y1-y2,2);
        return Math.sqrt(xSqr+ySqr);
    }
}
