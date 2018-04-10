package com.example.matthew.mvvmgoods.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matthew.mvvmgoods.MvvmGoodsApplication;
import com.example.matthew.mvvmgoods.R;
import com.example.matthew.mvvmgoods.databinding.FragmentGoodsBinding;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModel;
import com.example.matthew.mvvmgoods.viewmodel.GoodsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsFragment extends BaseFragment {

    private GoodsViewModel goodsViewModel;

    MvvmGoodsApplication mvvmGoodsApplication;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_goods, container, false);
        ButterKnife.bind(this, root);

        mvvmGoodsApplication = MvvmGoodsApplication.getInstance();
        return root;
    }

    @Nullable
    @Override
    protected ViewModel createAndBindViewModel(View root,
                                               @NonNull ActivityComponent activityComponent,
                                               @Nullable ViewModel.State savedViewModelState) {
        goodsViewModel = viewModelFactory.createGoodsViewModel(
                new GoodsAdapter(viewModelFactory, activityComponent), activityComponent,
                savedViewModelState);
        FragmentGoodsBinding binding = FragmentGoodsBinding.bind(root);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        binding.setViewModel(goodsViewModel);
        return goodsViewModel;
    }

    @OnClick(R.id.basket)
    public void LaunchBasket(View view){
        startActivity(BasketActivity.basketDetail(getContext(),mvvmGoodsApplication.getBasket()));
    }
}