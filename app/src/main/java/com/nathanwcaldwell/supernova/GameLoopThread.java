package com.nathanwcaldwell.supernova;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by john4abin on 3/10/2015.
 */
public class GameLoopThread extends Thread {
    private GameView view;
    static final long FPS = 30;
    boolean running;

    public static final int STATE_RUNNING = 0;
    public static final int STATE_PAUSED = 1;

    private int mMode;

    public int getmMode() {
        return mMode;
    }

    public void setmMode(int mMode) {
        this.mMode = mMode;
    }

    public GameLoopThread(GameView view){
        this.view = view; this.mMode = STATE_RUNNING;
    }

    public void setRunning(boolean run){
        running = run;
    }

    @Override
    public void run(){
        long ticksPS = 1000/FPS;
        long startTime = 0;
        long sleepTime;
        while (running){
            Canvas c = null;

            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()){
                    view.onDraw(c);
                }
            } finally {
                if (c != null){
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0){
                    sleep(sleepTime);
                }
                else{
                    sleep(10);
                }
            } catch (Exception e){}
        }
    }
}
