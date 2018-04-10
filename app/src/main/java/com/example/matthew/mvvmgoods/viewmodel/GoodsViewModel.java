package com.example.matthew.mvvmgoods.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.matthew.mvvmgoods.model.Item;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.view.RecyclerViewAdapter;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.RecyclerViewViewModel;
import com.example.matthew.mvvmgoods.view.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoodsViewModel extends RecyclerViewViewModel {

    GoodsAdapter adapter;
    private List<Item> itemList;

    public GoodsViewModel(@NonNull GoodsAdapter adapter,
                          @NonNull ActivityComponent activityComponent,
                          @Nullable State savedInstanceState) {
        super(activityComponent, savedInstanceState);
        this.adapter = adapter;

        ArrayList<Item> goods;
        if (savedInstanceState instanceof GoodsState) {
            goods = ((GoodsState) savedInstanceState).goods;
            adapter.setItems(goods);
        } else {
            fetchGoods();
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
        return new GoodsState(this);
    }

    private void fetchGoods(){
        //Usually this would be populated via api calls or something
        itemList = new ArrayList<>();
        itemList.add(new Item("Coffee",2.30f,"240g"));
        itemList.add(new Item("Tea",3.10f,"Box of 60"));
        itemList.add(new Item("Sugar",2.00f,"1kg"));
        itemList.add(new Item("Milk",1.20f,"Bottle"));
        itemList.add(new Item("Cup",0.50f));
        adapter.setItems(itemList);
    }

    private static class GoodsState extends RecyclerViewViewModelState {

        private final ArrayList<Item> goods;

        GoodsState(GoodsViewModel viewModel) {
            super(viewModel);
            goods = viewModel.adapter.getItems();
        }
    }
}
