package com.example.matthew.mvvmgoods.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.matthew.mvvmgoods.R;

public class GoodsActivity extends BaseActivity {

    private static final String TAG_GOODS_FRAGMENT = "GoodsFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(TAG_GOODS_FRAGMENT);
        if (fragment == null) {
            fragment = new GoodsFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, TAG_GOODS_FRAGMENT)
                    .commit();
        }
    }
}
