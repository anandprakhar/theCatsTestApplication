package com.thecats.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.thecats.di.ViewModelKey;
import com.thecats.viewmodels.ViewPagerViewModel;
import com.thecats.viewmodels.ViewPagerViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ViewPagerViewModel.class)
    abstract ViewModel bindViewModel(ViewPagerViewModel viewPagerViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindFactory(ViewPagerViewModelFactory factory);
}
