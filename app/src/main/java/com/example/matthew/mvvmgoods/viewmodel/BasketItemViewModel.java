package com.example.matthew.mvvmgoods.viewmodel;

import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.matthew.mvvmgoods.MvvmGoodsApplication;
import com.example.matthew.mvvmgoods.data.FixerResponse;
import com.example.matthew.mvvmgoods.data.FixerService;
import com.example.matthew.mvvmgoods.model.Item;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ItemViewModel;
import com.example.matthew.mvvmgoods.view.BasketActivity;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BasketItemViewModel extends ItemViewModel<Item> {

    private Item item;
    MvvmGoodsApplication mvvmGoodsApplication;

    public BasketItemViewModel(@NonNull ActivityComponent activityComponent, @NonNull CompositeDisposable compositeDisposable) {
        super(activityComponent);
        mvvmGoodsApplication = MvvmGoodsApplication.getInstance();
        mvvmGoodsApplication.callForExchangeRate(compositeDisposable, new Runnable() {
            @Override
            public void run() {
                //DO NOTHING
            }
        });
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
        notifyChange();
    }

    @Bindable
    public String getName() {
        return item.getName();
    }

    @Bindable
    public String getPrice() {
        return mvvmGoodsApplication.getCurrencySymbol() + String.format(Locale.getDefault(),"%10.2f", (item.getPrice() * mvvmGoodsApplication.getExchangeRate()) * item.getQuanity());
    }

    @Bindable
    public int getQuanity() {
        return item.getQuanity();
    }
    
    public void test(HashMap<String,Double> rates){
        Iterator it = rates.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d("RATE = ",pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }


}