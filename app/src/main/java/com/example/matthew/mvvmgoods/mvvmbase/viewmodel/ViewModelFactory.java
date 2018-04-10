package com.example.matthew.mvvmgoods.mvvmbase.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.matthew.mvvmgoods.model.Basket;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.view.BasketAdapter;
import com.example.matthew.mvvmgoods.view.GoodsAdapter;
import com.example.matthew.mvvmgoods.viewmodel.BasketItemViewModel;
import com.example.matthew.mvvmgoods.viewmodel.BasketViewModel;
import com.example.matthew.mvvmgoods.viewmodel.GoodsItemViewModel;
import com.example.matthew.mvvmgoods.viewmodel.GoodsViewModel;

import io.reactivex.disposables.CompositeDisposable;

public interface ViewModelFactory {

    @NonNull
    GoodsViewModel createGoodsViewModel(
            @NonNull GoodsAdapter adapter,
            @NonNull ActivityComponent activityComponent,
            @Nullable ViewModel.State savedViewModelState);

    @NonNull
    BasketViewModel createBasketViewModel(
            @NonNull BasketAdapter adapter,
            @NonNull ActivityComponent activityComponent,
            @Nullable ViewModel.State savedViewModelState,
            Basket basket);

    @NonNull
    GoodsItemViewModel createGoodsItemViewModel(
            @NonNull ActivityComponent activityComponent);

    @NonNull
    BasketItemViewModel createBasketItemViewModel(
            @NonNull ActivityComponent activityComponent,
            @NonNull CompositeDisposable compositeDisposable);
}