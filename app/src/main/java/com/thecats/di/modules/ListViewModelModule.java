package com.thecats.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.thecats.di.ViewModelKey;
import com.thecats.viewmodels.ListViewModelFactory;
import com.thecats.viewmodels.ListViewViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListViewViewModel.class)
    abstract ViewModel bindViewModel(ListViewViewModel listViewViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindFactory(ListViewModelFactory factory);
}
