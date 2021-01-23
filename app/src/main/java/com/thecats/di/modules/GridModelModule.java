package com.thecats.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.thecats.di.ViewModelKey;
import com.thecats.viewmodels.GridViewViewModel;
import com.thecats.viewmodels.GridViewViewModelFactory;
import com.thecats.viewmodels.ViewPagerViewModel;
import com.thecats.viewmodels.ViewPagerViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class GridModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GridViewViewModel.class)
    abstract ViewModel bindViewModel(GridViewViewModel gridViewViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindFactory(GridViewViewModelFactory factory);
}
