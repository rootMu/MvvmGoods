package com.example.matthew.mvvmgoods;

import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.AppViewModelFactory;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GoodsAppModule {

    @Provides
    @Singleton
    ViewModelFactory provideViewModelFactory(AppViewModelFactory appViewModelFactory) {
        return appViewModelFactory;
    }
}
