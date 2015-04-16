package com.nathanwcaldwell.supernova;

import android.app.Application;

/**
 * Created by nathan on 4/10/15.
 */
public class MyApplication extends Application {

    public static boolean isPauseOrGameOverOrLevelCompleteVisible() {
        return activityVisible;
    }

    public static void PauseOrGameOverOrLevelCompleteResumed() {
        activityVisible = true;
    }

    public static void PauseOrGameOverOrLevelCompletePaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}