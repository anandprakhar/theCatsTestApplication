package com.thecats.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ContextModules {
    private  final Context context;
    public ContextModules(Context context) {
        this.context = context;
    }

    @Binds
    abstract Context bindcontext(Application application);

}
