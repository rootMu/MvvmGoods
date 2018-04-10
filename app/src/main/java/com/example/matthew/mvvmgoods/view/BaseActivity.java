package com.example.matthew.mvvmgoods.view;

import com.example.matthew.mvvmgoods.GoodsAppComponent;
import com.example.matthew.mvvmgoods.mvvmbase.di.AppComponent;
import com.example.matthew.mvvmgoods.mvvmbase.view.ViewModelActivity;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class BaseActivity extends ViewModelActivity {


    @Inject
    protected ViewModelFactory viewModelFactory;

    @Override
    protected void inject(AppComponent appComponent) {
        ((GoodsAppComponent) appComponent).inject(this);
    }
}