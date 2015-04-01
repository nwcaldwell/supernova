package com.nathanwcaldwell.supernova;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by john4abin on 3/10/2015.
 */
public class GameView extends SurfaceView {

    GameLoopThread gameLoopThread;
    private SurfaceHolder holder;
    public static int globalxSpeed = 15;

    public static int score = 0;
    public static int highscore = 1000;
    public static int coinsCollected = 0;
    int xx = 0;

    public static int timerCoins = 0;
    private static SharedPreferences prefs;
    private String saveScore = "Highscore";

    Bitmap playerbmp;
    Bitmap coinbmp;
    Bitmap groundbmp;
    Bitmap meteorbmp;
    Bitmap backgroundbmp;

    private List<Player> player = new ArrayList<Player>();
    private List<Coin> coins = new ArrayList<Coin>();
    private List<Ground> ground = new ArrayList<Ground>();
    private List<Meteor> meteors = new ArrayList<>();

    public GameView (Context context){
        super(context);

        String spackage = "com.example.john4abin.endlessrunner1";
        prefs = context.getSharedPreferences(spackage,context.MODE_PRIVATE);

        highscore = prefs.getInt(saveScore, 0);

        gameLoopThread = new GameLoopThread(this);

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            gameLoopThread.running = false;
                score = 0;
                coinsCollected = 0;

            prefs.edit().putInt(saveScore,highscore).commit();
            }
        });
        playerbmp = BitmapFactory.decodeResource(getResources(),R.drawable.ship);
        coinbmp = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        meteorbmp = BitmapFactory.decodeResource(getResources(), R.drawable.meteor);
        groundbmp = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        backgroundbmp = BitmapFactory.decodeResource(getResources(),R.drawable.planet);

       player.add(new Player(GameView.this,playerbmp,435,50));

//        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background);
//        Canvas mCanvas = canvas;
//        if (mBitmap != null) {
//            mCanvas.drawBitmap(mBitmap, 0, 0, null);
//        }

/*
        Timer time = new Timer();

        time.schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                coins.add(new Coin(GameView.this, coinbmp,500,200));
                coins.add(new Coin(GameView.this, coinbmp,550,125));
                meteors.add(new Meteor(GameView.this, meteorbmp, 450, 300));
            }
        }, 5000);

    
        player.add(new Player(this,playerbmp,435,50));

        coins.add(new Coin(this, coinbmp,500,700));
        coins.add(new Coin(this, coinbmp,550,900));

        meteors.add(new Meteor(this, meteorbmp, 450, 450));
    */
    }




    public boolean onTouchEvent(MotionEvent e){
        //get the x position of the user touch
        int touch_x = (int)e.getX();

        //stuff to get the size of the screen in pixels
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int disp_width = size.x;

        //determine if the touch was on the left or right hand side of the screen
        boolean left = false;
        if (touch_x < (disp_width / 2)){
            left = true;
        }

        for (Player pplayer: player){
            pplayer.ontouch(left, disp_width);
        }
        return false;
    }

    public void updateScore(){
        score += 2;

        deleteGround();
        updateObstacles();
        if (score > highscore){
         highscore = score;
        }
    }

    public void updateObstacles(){
        timerCoins++;
        if (timerCoins == 20) {
            Random position = new Random();
            int x = position.nextInt(101);
            Random item = new Random();
            int y = item.nextInt(3);
            int z = 0;

            if (y == 0){
                z = -GameView.this.getWidth() / 5;
            }else if (y == 1){
                z = 0;
            }else if (y == 2){
                z = GameView.this.getWidth() / 5;
            }

            if (x % 2 == 0){
                meteors.add(new Meteor(GameView.this, meteorbmp, (GameView.this.getWidth()/2) + z, 20));

            }else if (x % 9 == 0){
                coins.add(new Coin(GameView.this, coinbmp, (GameView.this.getWidth() / 2) + z, 20));

            }//else if (x == 2){
//                coins.add(new Coin(GameView.this, coinbmp, (GameView.this.getWidth() / 2) , 20));
//            }


//            switch (x) {
//                case (0):
//                    coins.add(new Coin(GameView.this, coinbmp, GameView.this.getWidth() / 2, 200));
//
//                case (1):
//                    coins.add(new Coin(GameView.this, coinbmp, (GameView.this.getWidth() / 2) - (GameView.this.getWidth() / 5), 400));
//
//                case (2):
//                    coins.add(new Coin(GameView.this, coinbmp, (GameView.this.getWidth() / 2) + (GameView.this.getWidth() / 5), 600));
//            }
            timerCoins = 0;
        }
    }


    public void addGround(){

        while (xx < this.getWidth() + Ground.width){
            ground.add(new Ground(this, groundbmp,xx, 0));
            xx += groundbmp.getWidth();
        }
    }

    public void deleteGround(){
        for(int i = ground.size()-1; i >= 0; i--){

            int groundx = ground.get(i).returnX();

            if (groundx <=-Ground.width){
               ground.remove(i);
               ground.add(new Ground(this, groundbmp, groundx+this.getWidth()+Ground.width,0));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){

        canvas.drawBitmap(backgroundbmp, 0, 0, null);

        updateScore();
        //canvas.drawColor(Color.BLUE);

        addGround();
      //  deleteGround();
        Paint textpaint = new Paint();
        textpaint.setTextSize(32);
        textpaint.setColor(Color.WHITE);

        canvas.drawText("Score: " + String.valueOf(score), 0,32, textpaint);
        canvas.drawText("High Score: " + String.valueOf(highscore), 0, 64, textpaint);
        canvas.drawText("Coins: " + String.valueOf(coinsCollected), 0, 96, textpaint);

        for (Ground gground: ground){
            gground.onDraw(canvas);
        }
        for (Player pplayer: player){
            pplayer.onDraw(canvas);
        }
        for (int i = 0; i < meteors.size(); i++){

            meteors.get(i).onDraw(canvas);
            Rect playerr = player.get(0).GetBounds();
            Rect meteorr = meteors.get(i).GetBounds();

            if (meteors.get(i).checkCollision(playerr, meteorr)){
                meteors.remove(i);

                score = 1;
            }
        }

        for (int i = 0; i < coins.size(); i++){

            coins.get(i).onDraw(canvas);
            Rect playerr = player.get(0).GetBounds();
            Rect coinr = coins.get(i).GetBounds();

            if (coins.get(i).checkCollision(playerr, coinr)){
                coins.remove(i);
                coinsCollected++;
                score += 250;
            }
        }
    }


}