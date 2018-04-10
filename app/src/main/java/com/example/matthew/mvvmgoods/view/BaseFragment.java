package com.example.matthew.mvvmgoods.view;

import com.example.matthew.mvvmgoods.GoodsAppComponent;
import com.example.matthew.mvvmgoods.mvvmbase.di.AppComponent;
import com.example.matthew.mvvmgoods.mvvmbase.view.ViewModelFragment;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public abstract class BaseFragment extends ViewModelFragment {

    @Inject
    protected ViewModelFactory viewModelFactory;

    @Override
    protected void inject(AppComponent appComponent) {
        ((GoodsAppComponent) appComponent).inject(this);
    }
}
