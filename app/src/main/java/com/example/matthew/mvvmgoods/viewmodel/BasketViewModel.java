package com.example.matthew.mvvmgoods.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.matthew.mvvmgoods.model.Item;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.view.RecyclerViewAdapter;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.RecyclerViewViewModel;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModel;
import com.example.matthew.mvvmgoods.view.BasketAdapter;

import java.util.ArrayList;

public class BasketViewModel extends RecyclerViewViewModel  {

    BasketAdapter adapter;
    ArrayList<Item> basketList = new ArrayList<Item>();

    public BasketViewModel(@NonNull BasketAdapter adapter,
                          @NonNull ActivityComponent activityComponent,
                          @Nullable ViewModel.State savedInstanceState,
                          ArrayList<Item> basket) {
        super(activityComponent, savedInstanceState);
        this.adapter = adapter;

        ArrayList<Item> goods;
        if (savedInstanceState instanceof BasketViewModel.BasketState) {
            goods = ((BasketViewModel.BasketState) savedInstanceState).goods;
            adapter.setItems(goods);
        } else {

            for (Item item:
                 basket) {
                if(!basketList.contains(item)){
                    item.incremementQuantity();
                    basketList.add(item);
                }else{findAndIncrement(item.getName());}

            }

            adapter.setItems(basketList);
            adapter.notifyDataSetChanged();
        }
    }

    private void findAndIncrement(String name){
        for (Item item:
             basketList) {
            if(item.getName().equals(name)){
                item.incremementQuantity();
            }
        }

    }

    @Override
    protected RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(appContext);
    }

    @Override
    public RecyclerViewViewModelState getInstanceState() {
        return new BasketViewModel.BasketState(this);
    }

    private static class BasketState extends RecyclerViewViewModelState {

        private final ArrayList<Item> goods;

        BasketState(BasketViewModel viewModel) {
            super(viewModel);
            goods = viewModel.adapter.getItems();
        }
    }
}
