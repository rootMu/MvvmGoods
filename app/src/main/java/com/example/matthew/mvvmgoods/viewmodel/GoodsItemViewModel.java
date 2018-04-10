package com.example.matthew.mvvmgoods.viewmodel;

import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.matthew.mvvmgoods.MvvmGoodsApplication;
import com.example.matthew.mvvmgoods.data.FixerResponse;
import com.example.matthew.mvvmgoods.data.FixerService;
import com.example.matthew.mvvmgoods.model.Item;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ItemViewModel;
import com.example.matthew.mvvmgoods.view.BasketActivity;

import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GoodsItemViewModel extends ItemViewModel<Item> {

    private Item item;
    MvvmGoodsApplication mvvmGoodsApplication;

    public GoodsItemViewModel(@NonNull ActivityComponent activityComponent) {
        super(activityComponent);
        mvvmGoodsApplication = MvvmGoodsApplication.getInstance();
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
        notifyChange();
    }

    public void onClick() {
        mvvmGoodsApplication.addToBasket(item);
    }

    @Bindable
    public String getName() {
        return item.getName();
    }

    @Bindable
    public String getPrice() {
        return mvvmGoodsApplication.getCurrencySymbol() + String.format(Locale.getDefault(),"%10.2f", item.getPrice());
    }

    @Bindable
    public String getUnit() {
        return item.getUnit();
    }

}