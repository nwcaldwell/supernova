package com.nathanwcaldwell.supernova;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by john4abin on 3/16/2015.
 */
public class Ground {

   private GameView gameView;
    private int x, y;
    private Bitmap bmp;
    public static int width;

    public Ground(GameView gameView, Bitmap bmp, int x, int y){
        this.gameView = gameView;
        this.bmp = bmp;
        this.x = x;
        this.y = y;
        this.width = bmp.getWidth();
    }

    public void update(){
        x-=gameView.globalxSpeed;
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp,x,y + gameView.getHeight()-64,null);
    }

    public int returnX() {
        return x;
    }
}
