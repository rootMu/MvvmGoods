package com.example.matthew.mvvmgoods.mvvmbase.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context appContext;

    public AppModule(Application application) {
        appContext = application;
    }

    @Provides
    @Singleton
    @AppContext
    Context provideAppContext() {
        return appContext;
    }
}