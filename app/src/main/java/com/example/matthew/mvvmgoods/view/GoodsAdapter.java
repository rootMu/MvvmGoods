package com.example.matthew.mvvmgoods.view;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.matthew.mvvmgoods.R;
import com.example.matthew.mvvmgoods.databinding.ItemGoodsBinding;
import com.example.matthew.mvvmgoods.model.Item;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.view.RecyclerViewAdapter;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModelFactory;
import com.example.matthew.mvvmgoods.viewmodel.GoodsItemViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsAdapter
        extends RecyclerViewAdapter<Item, GoodsItemViewModel> {

    private final ViewModelFactory viewModelFactory;

    public GoodsAdapter(ViewModelFactory viewModelFactory,
                        @NonNull ActivityComponent activityComponent) {
        super(activityComponent);
        this.viewModelFactory = viewModelFactory;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goods, parent, false);

        GoodsItemViewModel viewModel =
                viewModelFactory.createGoodsItemViewModel(getActivityComponent());

        ItemGoodsBinding binding = ItemGoodsBinding.bind(itemView);
        binding.setViewModel(viewModel);

        return new GoodsViewHolder(itemView, binding, viewModel);
    }

    public void setItems(List<Item> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    static class GoodsViewHolder
            extends ItemViewHolder<Item, GoodsItemViewModel> {

        public GoodsViewHolder(View itemView, ViewDataBinding binding,
                               GoodsItemViewModel viewModel) {
            super(itemView, binding, viewModel);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.add)
        void onClickAddItem() {
            viewModel.onClick();
            Toast.makeText(itemView.getContext(),"added to basket", Toast.LENGTH_SHORT).show();

        }
    }
}