package com.example.matthew.mvvmgoods.mvvmbase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.matthew.mvvmgoods.mvvmbase.MvvmApplication;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.di.ActivityModule;
import com.example.matthew.mvvmgoods.mvvmbase.di.AppComponent;
import com.example.matthew.mvvmgoods.mvvmbase.di.DaggerActivityComponent;
import com.example.matthew.mvvmgoods.mvvmbase.viewmodel.ViewModel;

public abstract class ViewModelActivity extends AppCompatActivity {

    private static final String EXTRA_VIEW_MODEL_STATE = "viewModelState";

    private ActivityComponent activityComponent;
    private ViewModel viewModel;

    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((MvvmApplication) getApplication()).getAppComponent();
        inject(appComponent);

        activityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build();

        ViewModel.State savedViewModelState = null;
        if (savedInstanceState != null) {
            savedViewModelState = savedInstanceState.getParcelable(EXTRA_VIEW_MODEL_STATE);
        }
        viewModel = createViewModel(savedViewModelState);
    }

    @Nullable
    protected ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState) {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (viewModel != null) {
            viewModel.onStart();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            outState.putParcelable(EXTRA_VIEW_MODEL_STATE, viewModel.getInstanceState());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (viewModel != null) {
            viewModel.onStop();
        }
    }

    public final ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
