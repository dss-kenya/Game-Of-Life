package com.dhara.gameoflife;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class GameOfLifeApp extends Application{
    private static GameOfLifeApp mApp;
    private static Context mContext;
    private Scheduler defaultSubscribeScheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mContext = this;
    }

    public static GameOfLifeApp getAppContext() {
        if(mApp == null) {
            mApp = (GameOfLifeApp)(mContext == null ? new GameOfLifeApp() : mContext);
        }
        return mApp;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
