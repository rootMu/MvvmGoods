package com.example.matthew.mvvmgoods.mvvmbase.viewmodel;

import android.support.annotation.NonNull;

import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;

public abstract class ItemViewModel<ITEM_T> extends ViewModel {

    public ItemViewModel(@NonNull ActivityComponent activityComponent) {
        super(activityComponent, null);
    }

    public abstract void setItem(ITEM_T item);
}