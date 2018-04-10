package com.example.matthew.mvvmgoods.view;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.matthew.mvvmgoods.MvvmGoodsApplication;
import com.example.matthew.mvvmgoods.R;
import com.example.matthew.mvvmgoods.databinding.ItemBasketBinding;
import com.example.matthew.mvvmgoods.model.Item;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.view.RecyclerViewAdapter;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModelFactory;
import com.example.matthew.mvvmgoods.viewmodel.BasketItemViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class BasketAdapter
        extends RecyclerViewAdapter<Item, BasketItemViewModel> {

    private final ViewModelFactory viewModelFactory;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    BasketAdapter(ViewModelFactory viewModelFactory,
                  @NonNull ActivityComponent activityComponent) {
        super(activityComponent);
        this.viewModelFactory = viewModelFactory;
    }

    @Override
    public BasketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket, parent, false);

        BasketItemViewModel viewModel =
                viewModelFactory.createBasketItemViewModel(getActivityComponent(),compositeDisposable);

        ItemBasketBinding binding = ItemBasketBinding.bind(itemView);
        binding.setViewModel(viewModel);

        return new BasketViewHolder(itemView, binding, viewModel);
    }

    public void setItems(List<Item> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    static class BasketViewHolder
            extends ItemViewHolder<Item, BasketItemViewModel> {

        BasketViewHolder(View itemView, ViewDataBinding binding,
                         BasketItemViewModel viewModel) {
            super(itemView, binding, viewModel);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.add)
        void onClickAddItem() {
            Toast.makeText(itemView.getContext(),"TODO: Increase number of items in basket", Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.remove)
        void onClickRemoveItem() {
            Toast.makeText(itemView.getContext(),"TODO: Decrease number of items in basket", Toast.LENGTH_SHORT).show();
        }
    }
}