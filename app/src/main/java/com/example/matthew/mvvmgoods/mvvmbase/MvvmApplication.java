package com.example.matthew.mvvmgoods.mvvmbase;

import android.app.Application;

import com.example.matthew.mvvmgoods.mvvmbase.di.AppComponent;

public abstract class MvvmApplication extends Application {

    private AppComponent appComponent;

    protected abstract AppComponent createAppComponent();

    @Override
    public void onCreate() {
        super.onCreate();

        if (appComponent == null) {
            appComponent = createAppComponent();
        }
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}