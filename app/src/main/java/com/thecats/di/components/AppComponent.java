package com.thecats.di.components;

import com.thecats.MainActivity;
import com.thecats.di.modules.ContextModules;
import com.thecats.di.modules.NetworkModules;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModules.class, ContextModules.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
