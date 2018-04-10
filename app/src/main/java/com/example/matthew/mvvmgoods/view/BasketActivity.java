package com.example.matthew.mvvmgoods.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asksira.dropdownview.DropDownView;
import com.example.matthew.mvvmgoods.BuildConfig;
import com.example.matthew.mvvmgoods.MvvmGoodsApplication;
import com.example.matthew.mvvmgoods.R;
import com.example.matthew.mvvmgoods.data.FixerResponse;
import com.example.matthew.mvvmgoods.data.FixerService;
import com.example.matthew.mvvmgoods.databinding.ActivityBasketBinding;
import com.example.matthew.mvvmgoods.model.Basket;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModel;
import com.example.matthew.mvvmgoods.viewmodel.BasketViewModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BasketActivity extends BaseActivity {

    private static final String EXTRA_BASKET = "EXTRA_BASKET";

    MvvmGoodsApplication mvvmGoodsApplication;
    private BasketViewModel basketViewModel;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    DropDownView dropDownView;

    @BindView(R.id.total)
    TextView totalTextView;

    private Basket basket;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FixerService fixerService;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixerService = mvvmGoodsApplication.getFixerService();
        getRates();
    }

    @Nullable
    @Override
    protected ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState) {

        getExtrasFromIntent();

        basketViewModel = viewModelFactory.createBasketViewModel(
                new BasketAdapter(viewModelFactory, getActivityComponent()), getActivityComponent(),
                savedViewModelState,basket);

        ActivityBasketBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_basket);

        //Get Root View
        View view = binding.getRoot();

        dropDownView = findViewById(R.id.dropdownview);

        ButterKnife.bind(this, view);

        binding.setViewModel(basketViewModel);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        mvvmGoodsApplication = MvvmGoodsApplication.getInstance();

        updateTextView(mvvmGoodsApplication.getExchangeRate());

        return basketViewModel;
    }

    public static Intent basketDetail(Context context, Basket basket) {
        Intent intent = new Intent(context, BasketActivity.class);
        intent.putExtra(EXTRA_BASKET, basket);
        return intent;
    }

    private void getExtrasFromIntent() {
        basket = (Basket)getIntent().getSerializableExtra(EXTRA_BASKET);
    }

    private void getRates(){
        compositeDisposable.add(fixerService.fetchLatest(BuildConfig.API_KEY)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Function<FixerResponse, HashMap<String,Double>>() {
            @Override
            public HashMap<String,Double> apply(
                    @io.reactivex.annotations.NonNull final FixerResponse fixerResponse)
                    throws Exception {
                // we want to have the launches and not the wrapper object
                return fixerResponse.getRates();
            }
        }).subscribe(new Consumer<HashMap<String,Double>>() {
            @Override
            public void accept(HashMap<String,Double> rates) throws Exception {
                populateSpinner(rates);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(getClass().getCanonicalName(), throwable.getMessage());
            }
        }));
    }

    public void populateSpinner(final HashMap<String,Double> rates){
        List<String> spinnerArray =  new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownView.setDropDownListItem(spinnerArray);

        Iterator it = rates.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            spinnerArray.add(pair.getKey().toString());
            it.remove(); // avoids a ConcurrentModificationException
        }

        dropDownView.setOnSelectionListener(new DropDownView.OnSelectionListener() {
            @Override
            public void onItemSelected(DropDownView view, int position) {
                String currency = view.getFilterTextView().getText().toString();
                mvvmGoodsApplication.setDesiredCurrency(currency);
                mvvmGoodsApplication.callForExchangeRate(compositeDisposable, new Runnable() {
                    @Override
                    public void run() {
                        recreate();
                    }
                });
            }
        });
    }

    private void updateTextView(Double exchangeRate){
        totalTextView.setText(String.format(getString(R.string.total_string), mvvmGoodsApplication.getCurrencySymbol(),
                String.format(Locale.getDefault(),"%10.2f", basket.calculateTotal() * exchangeRate)));
    }

}

