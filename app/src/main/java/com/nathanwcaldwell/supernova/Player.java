package com.nathanwcaldwell.supernova;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by john4abin on 3/10/2015.
 */

public class Player {
    static int x;
    static int y;
    static int gravity = 7;
    static int vspeed = 1;
    static int playerheight;
    static int playerwidth;
    static int jumppower = -50;

    private int width, height;
    private int mcolumnWidth = 1;
    private int mcolumnHeight = 1;
    private  int counter = 0;

    Bitmap bmp;
    GameView gameView;

    public Player(GameView gameView, Bitmap bmp, int x, int y){
        this.x = x;
        this.y = y;
        this.bmp = bmp;
        this.gameView = gameView;
        playerheight = bmp.getHeight();
        this.width = bmp.getWidth()/mcolumnWidth;
        this.height = bmp.getHeight()/mcolumnHeight;

    }

    public void checkground(){
        if (y < gameView.getHeight()-64-playerheight){
            vspeed += gravity;
            if (y > gameView.getHeight()-64-playerheight-vspeed){
                vspeed = gameView.getHeight()-64-y-playerheight;
            }
        }
        else if (vspeed > 0){
            vspeed = 0;
            y = gameView.getHeight()-64-playerheight;
        }
        y += vspeed;
    }

    public void ontouch(boolean left, int display_width){
        //if left touch
        if (left){
            //if player is not already on the left
            if (counter != -1) {
                if (y <= gameView.getHeight() - 64 - playerheight) {
                    //vspeed = jumppower;
                    x -= (display_width/5);
                    counter--;
                    Meteor.updatexSpeed(-5);
                    Coin.updatexSpeed(-5);
                    GameView.score_position--;
                }
            }
        }else {
            if (counter != 1) {
                if (y <= gameView.getHeight() - 64 - playerheight) {
                    //vspeed = jumppower;
                    x += (display_width/5);
                    counter++;
                    Meteor.updatexSpeed(5);
                    Coin.updatexSpeed(5);
                    GameView.score_position++;
                }
            }
        }
    }



    public Rect GetBounds(){
        return new Rect(this.x,this.y,this.x+width, this.y+height);
    }

    public void update(){
        checkground();
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp,x,y,null);
    }

}
