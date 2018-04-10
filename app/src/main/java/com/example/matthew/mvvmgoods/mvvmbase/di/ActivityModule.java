package com.example.matthew.mvvmgoods.mvvmbase.di;

import com.example.matthew.mvvmgoods.mvvmbase.view.ViewModelActivity;

import dagger.Module;
import dagger.Provides;

@Module
public final class ActivityModule {

    private final ViewModelActivity activity;

    public ActivityModule(ViewModelActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AttachedActivity provideAttachedActivity() {
        return new AttachedViewModelActivity(activity);
    }
}
