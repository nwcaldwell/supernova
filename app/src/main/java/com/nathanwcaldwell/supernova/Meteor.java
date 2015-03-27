package com.nathanwcaldwell.supernova;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.TimerTask;

/**
 * Created by john4abin on 3/12/2015.
 */
public class Meteor {

    private int x;
    private int y;
    private Bitmap bmp;
    private GameView gameView;
    private int mcurrentFrame = 0;
    private int mcolumnWidth = 1;
    private int mcolumnHeight = 1;
    private int width,height;
    private int xSpeed = -GameView.globalxSpeed;
    private Rect playerr;
    private Rect meteorr;

    public Meteor(GameView gameView, Bitmap bmp, int x, int y){
        this.x = x;
        this.y = y;
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight()/mcolumnHeight;
    }



    public void update(){
        y -= xSpeed;
        mcurrentFrame += 1;

        if (x < 0 - width){
            x = gameView.getWidth()+ width;
        }
    }

    public Rect GetBounds(){
        return new Rect(this.x,this.y,this.x+width, this.y+height);
    }

    public boolean checkCollision(Rect playerr, Rect meteorr){

        this.playerr = playerr;
        this.meteorr = meteorr;
        return Rect.intersects(playerr,meteorr);

    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }
}