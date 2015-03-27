package com.nathanwcaldwell.supernova;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.WindowManager;

/**
 * Created by john4abin on 3/11/2015.
 */
public class Coin {

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
    private Rect coinr;


    public Coin(GameView gameView, Bitmap bmp, int x, int y){
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

    public boolean checkCollision(Rect playerr, Rect coinr){

        this.playerr = playerr;
        this.coinr = coinr;
        return Rect.intersects(playerr,coinr);

    }

    public void onDraw(Canvas canvas){
        update();
        int srcX = mcurrentFrame*width;
        Rect src = new Rect(mcurrentFrame*width,0,srcX + width, 16);
        Rect dst = new Rect(x,y,x + width, y + 16);
        canvas.drawBitmap(bmp, x, y, null);
    }
}