package com.nathanwcaldwell.supernova;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by john4abin on 3/10/2015.
 */
public class GameView extends SurfaceView {

    Resources resources = getResources();

    final float dp16 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, resources.getDisplayMetrics());
    final float dp18 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, resources.getDisplayMetrics());
    final float dp24 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, resources.getDisplayMetrics());
    final float dp32 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, resources.getDisplayMetrics());

    GameLoopThread gameLoopThread;
    private SurfaceHolder holder;
    public static int globalxSpeed = 20;

    /* -1 = left; 0 = middle; 1 = right */
    public static int score_position = 0;
    public static int score = 0;
    public static int highscore = 1000;
    public static int coinsCollected = 0;
    int xx = 0;

    public static int timerCoins = 0;
    private static SharedPreferences prefs;
    private String saveScore = "High Score";

    Bitmap playerBMP;
    Bitmap coinBMP;
    Bitmap groundBMP;
    Bitmap meteorBMP;
    Bitmap backgroundBMP;
    Bitmap resizedBackgroundBMP;
    Bitmap pauseBMP;

    private List<Player> player = new ArrayList<Player>();
    private List<Coin> coins = new ArrayList<Coin>();
    private List<Ground> ground = new ArrayList<Ground>();
    private List<Meteor> meteors = new ArrayList<Meteor>();

    boolean[] leftMeteors = {false, false};
    boolean[] middleMeteors = {false, false};
    boolean[] rightMeteors = {false, false};

    int left;
    int middle;
    int right;

    boolean leftBoolean;
    boolean rightBoolean;

    int width = screenWidth();
    int height = screenHeight();

    Region pauseRegion;

    public GameView (Context context) {
        super(context);

        String spackage = "com.nathanwcaldwell.supernova";
        prefs = context.getSharedPreferences(spackage, context.MODE_PRIVATE);

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

        playerBMP = BitmapFactory.decodeResource(getResources(),R.drawable.ship);
        coinBMP = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        meteorBMP = BitmapFactory.decodeResource(getResources(), R.drawable.meteor);
        groundBMP = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        pauseBMP = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button);

        backgroundBMP = BitmapFactory.decodeResource(getResources(), R.drawable.planet);
        resizedBackgroundBMP = Bitmap.createScaledBitmap(backgroundBMP, width, height, false);

        player.add(new Player(GameView.this, playerBMP,width/2,(int)(height*1.0/4)));

        pauseRegion = new Region((int)(width- pauseBMP.getWidth()-dp24), 0, (int)(width-pauseBMP.getWidth()+dp24), (int)(dp32+16));

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
                coins.add(new Coin(GameView.this, coinBMP,500,200));
                coins.add(new Coin(GameView.this, coinBMP,550,125));
                meteors.add(new Meteor(GameView.this, meteorBMP, 450, 300));
            }
        }, 5000);
    */
    }

    public int screenWidth() {
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public int screenHeight() {
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }


    public boolean onTouchEvent(MotionEvent e){
        //get the x position of the user touch
        int touch_x = (int)e.getX();
        int touch_y = (int)e.getY();

        if(pauseRegion.contains((int)touch_x, (int)touch_y))
        {
            gameLoopThread.setRunning(false);
            Intent intent = new Intent(this.getContext(), PauseActivity.class);
            this.getContext().startActivity(intent);

        }

        //determine if the touch was on the left or right hand side of the screen
        boolean left = false;
        if (touch_x < (width / 2)){
            left = true;
        }

        for (Player pplayer: player){
            pplayer.ontouch(left, width);
        }
        return false;
    }

    public void updateScore(){
        if (score_position < 0){
            score += 4;
        }

        else if (score_position == 0) {
            score += 2;
        }

        else if (score_position > 0) {
            score++;
        }

//        deleteGround();
        updateObstacles();

        if (score > highscore) {
         highscore = score;
        }
    }

    public void updateObstacles() {
        timerCoins++;



        if (timerCoins == 30) {

            Random position = new Random();
            left = position.nextInt(1001);
            middle = position.nextInt(1001);
            right = position.nextInt(1001);
            leftBoolean = false;
            rightBoolean = false;

            Random item = new Random();


//            z = -GameView.this.getWidth() / 5;
//            z = GameView.this.getWidth() / 5;

            if (left % 2 == 0 && !((middleMeteors[0] || middleMeteors[1]) && rightBoolean)) {
                meteors.add(new Meteor(GameView.this, meteorBMP, (GameView.this.getWidth()/2) + -GameView.this.getWidth() / 5, 20));

                leftMeteors[0] = leftMeteors[1];
                leftMeteors[1] = true;
                leftBoolean = true;
            }

            else {
                leftMeteors[0] = leftMeteors[1];
                leftMeteors[1] = false;
            }


            if (middle % 2 == 0 && !((leftMeteors[0] || leftMeteors[1]) && (rightMeteors[0] || rightMeteors[1]))) {
                meteors.add(new Meteor(GameView.this, meteorBMP, (GameView.this.getWidth()/2), 20));

                middleMeteors[0] = middleMeteors[1];
                middleMeteors[1] = true;
            }

            else {
                middleMeteors[0] = middleMeteors[1];
                middleMeteors[1] = false;
            }

            if (right % 2 == 0 && !((middleMeteors[0] || middleMeteors[1]) && leftBoolean)) {
                meteors.add(new Meteor(GameView.this, meteorBMP, (GameView.this.getWidth()/2) + GameView.this.getWidth() / 5, 20));

                rightMeteors[0] = rightMeteors[1];
                rightMeteors[1] = true;
                rightBoolean = true;
            }

            else {
                rightMeteors[0] = rightMeteors[1];
                rightMeteors[1] = false;
            }

            if (!leftMeteors[1] && !middleMeteors[1] && !rightMeteors[1]) {
                leftBoolean = false;
                rightBoolean = false;
            }
//            else if (x % 9 == 0){
//                coins.add(new Coin(GameView.this, coinBMP, (GameView.this.getWidth() / 2) + z, 20));
//            }


//            else if (x == 2){
//                coins.add(new Coin(GameView.this, coinBMP, (GameView.this.getWidth() / 2) , 20));
//            }


//            switch (x) {
//                case (0):
//                    coins.add(new Coin(GameView.this, coinBMP, GameView.this.getWidth() / 2, 200));
//
//                case (1):
//                    coins.add(new Coin(GameView.this, coinBMP, (GameView.this.getWidth() / 2) - (GameView.this.getWidth() / 5), 400));
//
//                case (2):
//                    coins.add(new Coin(GameView.this, coinBMP, (GameView.this.getWidth() / 2) + (GameView.this.getWidth() / 5), 600));
//            }

            timerCoins = 0;
        }
    }


//    public void addGround(){
//
//        while (xx < this.getWidth() + Ground.width){
//            ground.add(new Ground(this, groundBMP,xx, 0));
//            xx += groundBMP.getWidth();
//        }
//    }
//
//    public void deleteGround(){
//        for(int i = ground.size()-1; i >= 0; i--){
//
//            int groundx = ground.get(i).returnX();
//
//            if (groundx <=-Ground.width){
//               ground.remove(i);
//               ground.add(new Ground(this, groundBMP, groundx+this.getWidth()+Ground.width,0));
//            }
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (score > 1000){
            Intent intent = new Intent(this.getContext(), LevelCompleteActivity.class);
            this.getContext().startActivity(intent);
        }

        canvas.drawBitmap(resizedBackgroundBMP, 0, 0, null);
        canvas.drawBitmap(pauseBMP, width - pauseBMP.getWidth() - dp16, dp16, null);

        updateScore();
//        canvas.drawColor(Color.BLUE);

//        deleteGround();
//        addGround();
        Paint textpaint = new Paint();
        textpaint.setTextSize(dp18);
        textpaint.setColor(Color.WHITE);

        canvas.drawText("Score: " + String.valueOf(score), 0, dp18, textpaint);
        canvas.drawText("High Score: " + String.valueOf(highscore), 0, 2 * dp18, textpaint);
        canvas.drawText("Coins: " + String.valueOf(coinsCollected), 0, 3 * dp18, textpaint);

//        for (Ground gground: ground){
//            gground.onDraw(canvas);
//        }

        for (Player pplayer : player) {
            pplayer.onDraw(canvas);
        }

        for (int i = 0; i < meteors.size(); i++) {

            meteors.get(i).onDraw(canvas);
            Rect playerr = player.get(0).GetBounds();
            Rect meteorr = meteors.get(i).GetBounds();

            if (meteors.get(i).checkCollision(playerr, meteorr)) {
                meteors.remove(i);

                score = 1;
                gameLoopThread.setRunning(false);
                Intent intent = new Intent(this.getContext(), GameOverActivity.class);
                this.getContext().startActivity(intent);
            }
        }

        for (int i = 0; i < coins.size(); i++) {

            coins.get(i).onDraw(canvas);
            Rect playerr = player.get(0).GetBounds();
            Rect coinr = coins.get(i).GetBounds();

            if (coins.get(i).checkCollision(playerr, coinr)) {
                coins.remove(i);
                coinsCollected++;
                score += 250;
            }
        }
    }
}
