package com.example.matthew.mvvmgoods.mvvmbase.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.matthew.mvvmgoods.model.Basket;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.view.BasketAdapter;
import com.example.matthew.mvvmgoods.view.GoodsAdapter;
import com.example.matthew.mvvmgoods.viewmodel.BasketItemViewModel;
import com.example.matthew.mvvmgoods.viewmodel.BasketViewModel;
import com.example.matthew.mvvmgoods.viewmodel.GoodsViewModel;
import com.example.matthew.mvvmgoods.viewmodel.GoodsItemViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;

@Singleton
public class AppViewModelFactory implements ViewModelFactory {

    @Inject
    AppViewModelFactory() {

    }

    @NonNull
    @Override
    public GoodsViewModel createGoodsViewModel(
            @NonNull GoodsAdapter adapter,
            @NonNull ActivityComponent activityComponent,
            @Nullable ViewModel.State savedViewModelState) {
        return new GoodsViewModel(adapter, activityComponent, savedViewModelState);
    }

    @NonNull
    @Override
    public GoodsItemViewModel createGoodsItemViewModel(
            @NonNull ActivityComponent activityComponent) {
        return new GoodsItemViewModel(activityComponent);
    }

    @NonNull
    @Override
    public BasketViewModel createBasketViewModel(
            @NonNull BasketAdapter adapter,
            @NonNull ActivityComponent activityComponent,
            @Nullable ViewModel.State savedViewModelState,
            Basket basket) {
        return new BasketViewModel(adapter, activityComponent, savedViewModelState, basket.getBasket());
    }

    @NonNull
    @Override
    public BasketItemViewModel createBasketItemViewModel(
            @NonNull ActivityComponent activityComponent,
            @NonNull CompositeDisposable compositeDisposable) {
        return new BasketItemViewModel(activityComponent,compositeDisposable);
    }

}
