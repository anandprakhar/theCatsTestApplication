package com.thecats;

import android.app.Application;

import com.thecats.di.components.AppComponent;
import com.thecats.di.components.DaggerAppComponent;

public class BaseApplication extends Application {

    private AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        appComponent= DaggerAppComponent.create();
    }

    public AppComponent appComponent()
    {
        return appComponent;
    }
}
