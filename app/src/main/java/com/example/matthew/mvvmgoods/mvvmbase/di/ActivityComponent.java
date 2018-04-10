package com.example.matthew.mvvmgoods.mvvmbase.di;

import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModel;

import dagger.Component;

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(ViewModel viewModel);
}