package com.nathanwcaldwell.supernova;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MercuryActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(new GameView(this));
    }


    @Override
    public void onPause(){
        super.onPause();

        if (gameView != null) {
            if (gameView.gameLoopThread != null) {
//                gameView.gameLoopThread.running = false;
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();


        if (gameView != null) {
            if (gameView.gameLoopThread != null) {
//                gameView.gameLoopThread.running = true;
//                gameView.gameLoopThread.start();
            }
        }
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
