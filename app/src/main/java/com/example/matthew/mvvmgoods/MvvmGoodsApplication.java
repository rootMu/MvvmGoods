package com.example.matthew.mvvmgoods;

import android.util.Log;
import android.widget.Toast;

import com.example.matthew.mvvmgoods.data.FixerFactory;
import com.example.matthew.mvvmgoods.data.FixerResponse;
import com.example.matthew.mvvmgoods.data.FixerService;
import com.example.matthew.mvvmgoods.model.Basket;
import com.example.matthew.mvvmgoods.model.Item;
import com.example.matthew.mvvmgoods.mvvmbase.MvvmApplication;
import com.example.matthew.mvvmgoods.mvvmbase.di.AppComponent;
import com.example.matthew.mvvmgoods.mvvmbase.di.AppContextModule;

import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class MvvmGoodsApplication extends MvvmApplication {

    private FixerService fixerService;
    private Scheduler scheduler;
    private String desiredCurrency;
    private Double exchangeRate;

    protected Basket basket = new Basket();

    private static MvvmGoodsApplication instance;

    public static MvvmGoodsApplication getInstance(){
        if(instance == null){
            instance = new MvvmGoodsApplication();
        }

        return instance;
    }

    public Double getExchangeRate(){
        if(exchangeRate==null){
            exchangeRate = 1.D;
        }
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate){
        this.exchangeRate = exchangeRate;
    }

    @Override
    protected AppComponent createAppComponent() {
        return DaggerGoodsAppComponent.builder()
                .appContextModule(new AppContextModule(this))
                .build();
    }

    public Basket getBasket(){
        return basket;
    }

    public void addToBasket(Item item){
        basket.addToBasket(item);
    }

    public void removeFromBasket(Item item){
        //iterate through the basket and remove an item if the name is the same
        for (Iterator<Item> iter = basket.getBasket().listIterator(); iter.hasNext(); ) {
            String itemName = iter.next().getName();
            if (item.getName().equals(itemName)) {
                iter.remove();
                //leave for loop so not to remove more than one.
                return;
            }
        }
    }

    public FixerService getFixerService() {
        if (fixerService == null) {
            fixerService = FixerFactory.create();
        }

        return fixerService;
    }

    public String getDesiredCurrency() {
        if (desiredCurrency == null) {
            desiredCurrency = "GBP";
        }

        return desiredCurrency;
    }

    public String getCurrencySymbol() {
        return Utils.getCurrencySymbol(getDesiredCurrency());
    }

    public void setDesiredCurrency(String currency) {
        desiredCurrency = currency;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setFixerService(FixerService fixerService) {
        this.fixerService = fixerService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    static class Utils {
        public static SortedMap<Currency, Locale> currencyLocaleMap;

        static {
            currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
                public int compare(Currency c1, Currency c2) {
                    return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
                }
            });
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    currencyLocaleMap.put(currency, locale);
                } catch (Exception e) {
                }
            }
        }

        public static String getCurrencySymbol(String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            return currency.getSymbol(currencyLocaleMap.get(currency));
        }
    }

    public void callForExchangeRate(CompositeDisposable compositeDisposable, final Runnable callback){
        if(getDesiredCurrency().equals("GBP")){
            setExchangeRate(1.D);
            callback.run();
        }else {
            String symbols = String.format("GBP,"+ getDesiredCurrency());
            compositeDisposable.add(getFixerService().fetchSpecific(BuildConfig.API_KEY, symbols)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<FixerResponse, HashMap<String, Double>>() {
                        @Override
                        public HashMap<String, Double> apply(
                                @io.reactivex.annotations.NonNull final FixerResponse fixerResponse)
                                throws Exception {
                            // we want to have the launches and not the wrapper object
                            return fixerResponse.getRates();
                        }
                    }).subscribe(new Consumer<HashMap<String, Double>>() {
                        @Override
                        public void accept(HashMap<String, Double> rates) throws Exception {
                            currencyExchange(rates,callback);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.d(getClass().getCanonicalName(), throwable.getMessage());
                        }
                    }));
        }
    }

    private void currencyExchange(HashMap<String,Double> rates, Runnable callback){
        //due to API key restrictions cannot convert directly from GBP to desired currency so have to convert via EUR
        //to do this we divide our price by the GBP to EUR exchange rate then multiply that value by the desired to EUR exchange rate
        Double GBP = rates.get("GBP");
        Double desired = rates.get(getDesiredCurrency());

        setExchangeRate((1.D / GBP) * desired);
        callback.run();
    }

}