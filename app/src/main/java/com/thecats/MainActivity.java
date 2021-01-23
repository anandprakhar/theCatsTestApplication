package com.thecats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thecats.databinding.ActivityMainBinding;
import com.thecats.models.CatsImage;
import com.thecats.viewmodels.GridViewViewModel;
import com.thecats.viewmodels.GridViewViewModelFactory;
import com.thecats.viewmodels.ListViewModelFactory;
import com.thecats.viewmodels.ListViewViewModel;
import com.thecats.viewmodels.ViewPagerViewModel;
import com.thecats.viewmodels.ViewPagerViewModelFactory;
import com.thecats.views.GridViewFragment;
import com.thecats.views.ListViewFragment;
import com.thecats.views.ViewPagerFragment;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static ActivityMainBinding binding;
    public static Fragment viewPagerFragment = new ViewPagerFragment();
    public static Fragment listViewFragment = new ListViewFragment();
    public static Fragment gridViewFragment = new GridViewFragment();
    @Inject
    ViewPagerViewModelFactory factory;

    @Inject
    ListViewModelFactory factoryList;

    @Inject
    GridViewViewModelFactory factoryGrid;


    public static ViewPagerViewModel viewPagerViewModel;
    public static ListViewViewModel listViewViewModel;
    public static GridViewViewModel gridViewViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


        ((BaseApplication)getApplication()).appComponent().inject(this);
        viewPagerViewModel= ViewModelProviders.of(this,factory).get(ViewPagerViewModel.class);
        listViewViewModel= ViewModelProviders.of(this,factoryList).get(ListViewViewModel.class);
        gridViewViewModel= ViewModelProviders.of(this,factoryGrid).get(GridViewViewModel.class);
        loadFragment(viewPagerFragment);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.bbn_item1:
                loadFragment(viewPagerFragment);
                return true;
            case R.id.bbn_item2:
                loadFragment(listViewFragment);
                return true;
            case R.id.bbn_item3:
                loadFragment(gridViewFragment);
                return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}