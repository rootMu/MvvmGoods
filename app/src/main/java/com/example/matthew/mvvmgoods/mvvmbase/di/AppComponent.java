package com.example.matthew.mvvmgoods.mvvmbase.di;

import android.content.Context;

import com.example.matthew.mvvmgoods.MvvmGoodsApplication;
import com.example.matthew.mvvmgoods.model.Basket;
import com.example.matthew.mvvmgoods.mvvmbase.view.ViewModelActivity;
import com.example.matthew.mvvmgoods.mvvmbase.view.ViewModelFragment;

public interface AppComponent {

    @AppContext
    Context appContext();

    void inject(ViewModelActivity viewModelActivity);

    void inject(ViewModelFragment viewModelFragment);
}
