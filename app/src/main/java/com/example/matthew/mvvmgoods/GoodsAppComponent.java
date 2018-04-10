package com.example.matthew.mvvmgoods;

import com.example.matthew.mvvmgoods.mvvmbase.di.AppComponent;
import com.example.matthew.mvvmgoods.mvvmbase.di.AppContextModule;
import com.example.matthew.mvvmgoods.view.BaseActivity;
import com.example.matthew.mvvmgoods.view.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppContextModule.class,
        GoodsAppModule.class})
public interface GoodsAppComponent extends AppComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);
}